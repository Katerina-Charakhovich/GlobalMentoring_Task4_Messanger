package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.annotations.DynamicCustomTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TemplateParserTest {

    private static final  TemplateParser templateParser = new TemplateParser();

    @Test
    @Tag("test2")
    public void replacePlaceholders_shouldThrowIllegalStateException_whenValueIsUnknown() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        Assertions.assertThrows(IllegalArgumentException.class, () -> templateParser
                .replacePlaceholders("Some text: #{exception}", tags));
    }


    @Test
    @Tag("test3")
    public void replacePlaceholders_shouldReturnTemplate_whenReadUnknownTag() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        tags.put("unknown", "test");
        assertEquals("Some text: test", templateParser
                .replacePlaceholders("Some text: #{value}", tags));
    }

    @Test
    @Tag("test4")
    public void replacePlaceholders_shouldReturnTemplate_supportSpecificFormat() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "#{tag}");
        assertEquals("Some text: #{tag}", templateParser
                .replacePlaceholders("Some text: #{value}", tags));
    }

    @TestFactory
    @DynamicCustomTest
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

                            assertEquals(outputList.get(id), templateParser
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
        assertEquals(templateParser
                .replacePlaceholders(template,tags), expectedContent);
    }

    @Test
    void validateTemplate_shouldReturnFalse_whenTemplateNull() {
        assertFalse(templateParser.validateTemplate(null));
    }
    @Test
    void validateTemplate_shouldReturnFalse_whenTemplateEmpty() {
        assertFalse(templateParser.validateTemplate(""));
    }
    @Test
    void validateTemplate_shouldReturnTrue_whenTemplateIsNotEmpty() {
        assertTrue(templateParser.validateTemplate("JJsarfoesd[pr"));
    }
}