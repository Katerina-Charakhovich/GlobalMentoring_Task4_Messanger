package com.epam.ld.module2.testing.template;


import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.extension.SaveResultTestingToFileExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private static TemplateEngine templateEngine = new TemplateEngine();
    private static Client client = new Client();

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
    public void shouldReturnTemplate() {
            assertEquals("Some text: test", templateEngine
                    .generateMessage(new Template("Some text: #{value}"), client));
    }

    //2.If at least one placeholder value is not provided at runtime – template generator should throw
    // an exception.
    @Test
    public void shouldThrowIllegalStateException_whenValueIsUnknown() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            templateEngine
                    .generateMessage(new Template("Some text: #{exception}"), client);
        });
    }

    //3.Template generator ignores values for variables provided at runtime that aren’t found from
    // the template.
    @Test
    public void shouldReturnTemplate_whenReadUnknownTag() {
        client.getTags().put("unknown", "test");
        assertEquals("Some text: test", templateEngine
                .generateMessage(new Template("Some text: #{value}"), client));
    }

    //4.System should support values passed in runtime with #{…}. E.g. template is  “Some text: #{value}
    // and  at runtime #{value} passed as  #{tag}. Output should be “Some text: #{tag}
    @Test
    public void shouldReturnTemplate_supportSpecificFormat() {
        client.getTags().put("sometag", "#{tag}");
        assertEquals("Some text: #{tag}", templateEngine
                .generateMessage(new Template("Some text: #{sometag}"), client));
    }
    //2.If at least one placeholder value is not provided at runtime – template generator should throw
    // an exception.
    @Test
    public void replacePlaceholders_shouldThrowIllegalStateException_whenValueIsUnknown() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            templateEngine
                    .replacePlaceholders("Some text: #{exception}", tags);
        });
    }

    //3.Template generator ignores values for variables provided at runtime that aren’t found from
    // the template.
    @Test
    public void replacePlaceholders_shouldReturnTemplate_whenReadUnknownTag() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        tags.put("unknown", "test");
        assertEquals("Some text: test", templateEngine
                    .replacePlaceholders("Some text: #{value}", tags));
    }
    //4.System should support values passed in runtime with #{…}. E.g. template is  “Some text: #{value}
    // and  at runtime #{value} passed as  #{tag}. Output should be “Some text: #{tag}

    @Test
    public void replacePlaceholders_shouldReturnTemplate_supportSpecificFormat() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "#{tag}");
        assertEquals("Some text: #{tag}", templateEngine
                .replacePlaceholders("Some text: #{value}", tags));
    }

    @TestFactory
    @com.epam.ld.module2.testing.annotations.DynamicTest
    Stream<DynamicTest> dynamicTestsFromStream() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        tags.put("unknown", "test");
        tags.put("value1", "#{tag}");
        List<String> templates = Arrays.asList(
                "Some text: #{value}", "Some text: #{value1}");
        List<String> outputList = Arrays.asList(
                "Some text: test","Some text: #{tag}");

        return templates.stream()
                .map(temp -> DynamicTest.dynamicTest("Test for template: " + temp,
                        () -> {int id = templates.indexOf(temp);

                            assertEquals(outputList.get(id), templateEngine
                                    .replacePlaceholders(temp,tags));
                        }));
    }
    @ParameterizedTest
    @CsvSource(value = {"Some text: #{value};Some text: test", "Some text: #{value1};Some text: #{tag}"}, delimiter = ';')
    void parameterizedTest(String template, String expectedContent) {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        tags.put("unknown", "test");
        tags.put("value1", "#{tag}");
        assertEquals(templateEngine
                .replacePlaceholders(template,tags), expectedContent);
    }
}
