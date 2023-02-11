package com.epam.ld.module2.testing.template;


import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

//1.The system replaces variable placeholders like #{subject} from a template with values provided at runtime.
//2.If at least one placeholder value is not provided at runtime – template generator should throw an exception.
//3.Template generator ignores values for variables provided at runtime that aren’t found from the template.
//4.System should support values passed in runtime with #{…}. E.g. template is  “Some text: #{value}” and  at runtime #{value} passed as  #{tag}. Output should be “Some text: #{tag}”.
//5.The system supports the full Latin-1 character set in templates and in variables.
//6/Messenger should work in two modes: console and file mode.
//
//In console mode the application takes expression from console and prints result to console. No application parameters should be specified to use this mode.
//In file mode application takes expression from file and output results to file. To use this mode user should specify input and output file names as application parameters.
public class TemplateEngineTest {
    TemplateEngine templateEngine;
    Client client;

    @BeforeAll
    public static void init() {
        TemplateEngine templateEngine = new TemplateEngine();
        Client client = new Client();
        client.setAddresses("test@test.com");
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        client.setTags(new HashMap<>());
    }

//   1.The system replaces variable placeholders like #{subject} from a template with values provided
//   at runtime.
    @Test
    public void shouldReturnTemolate(){
        assertEquals("Some text: test", templateEngine
                .generateMessage(new Template("Some text: #{value}"),client));
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
}
