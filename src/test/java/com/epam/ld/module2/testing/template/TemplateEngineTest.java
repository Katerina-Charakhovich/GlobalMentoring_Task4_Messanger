package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.extension.SaveResultTestingToFileExtension;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

//1.The system replaces variable placeholders like #{subject} from a template with values provided at runtime.
//2.If at least one placeholder value is not provided at runtime – template generator should throw an exception.
//3.Template generator ignores values for variables provided at runtime that aren’t found from the template.
//4.System should support values passed in runtime with #{…}. E.g. template is  “Some text: #{value} and  at runtime #{value} passed as  #{tag}. Output should be “Some text: #{tag}
//5.The system supports the full Latin-1 character set in templates and in variables.
//6/Messenger should work in two modes: console and file mode.
//
//In console mode the application takes expression from console and prints result to console. No application parameters should be specified to use this mode.
//In file mode application takes expression from file and output results to file. To use this mode user should specify input and output file names as application parameters.
@ExtendWith(SaveResultTestingToFileExtension.class)
public class TemplateEngineTest {
    private static final TemplateEngine templateEngine = new TemplateEngine(new TemplateParser());
    private static final Client client = new Client();

    @BeforeAll
    public static void init() {
        client.setAddresses("test@test.com");
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        client.setTags(tags);
    }

    //   1.The system replaces variable placeholders like #{subject} from a template with values provided
    //   at runtime.
    @Test
    @Tag("test1")
    public void shouldReturnTemplate() {
        assertEquals("Some text: test", templateEngine
                .generateMessage(new Template("Some text: #{value}"), client));
    }

    //3.Template generator ignores values for variables provided at runtime that aren’t found from
    // the template.
    @Test
    @Tag("test3")
    public void shouldReturnTemplate_whenReadUnknownTag() {
        client.getTags().put("unknown", "test");
        assertEquals("Some text: test", templateEngine
                .generateMessage(new Template("Some text: #{value}"), client));
    }

    //4.System should support values passed in runtime with #{…}. E.g. template is  “Some text: #{value}
    // and  at runtime #{value} passed as  #{tag}. Output should be “Some text: #{tag}
    @Test
    @Tag("test4")
    public void shouldReturnTemplate_supportSpecificFormat() {
        client.getTags().put("sometag", "#{tag}");
        assertEquals("Some text: #{tag}", templateEngine
                .generateMessage(new Template("Some text: #{sometag}"), client));
    }

    @Test//junit 5
    @Tag("test2")
    public void shouldThrowIllegalStateException_whenValueIsUnknown() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> templateEngine
                .generateMessage(new Template("Some text: #{exception}"), client));
    }

    @Rule //junit 4
    public ExpectedException thrown = ExpectedException.none();

    @org.junit.Test
    public void validateTemplate_emptyTemplate_testUsingRuleAnnotation() {
        thrown.expect(IllegalArgumentException.class);
        templateEngine
                .generateMessage(new Template("Some text: #{exception}"), client);
    }

    @Test
    public void generateMessageWithPartialMock() {
        TemplateParser templateParser = mock(TemplateParser.class);
        TemplateEngine templateEngine = new TemplateEngine(templateParser);
        when(templateParser.replacePlaceholders(any(), anyMap())).thenCallRealMethod();
        when(templateParser.validateTemplate(any())).thenReturn(true);
        assertEquals("Some text: test", templateEngine
                .generateMessage(new Template("Some text: #{value}"), client));
    }

    @Test
    public void generateMessageWithSpy() {
        TemplateParser templateParser = spy(TemplateParser.class);
        TemplateEngine templateEngine = new TemplateEngine(templateParser);
        doReturn(true).when(templateParser).validateTemplate(any());
        assertEquals("Some text: test", templateEngine
                .generateMessage(new Template("Some text: #{value}"), client));
    }
}

