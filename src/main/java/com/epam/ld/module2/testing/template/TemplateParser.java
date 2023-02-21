package com.epam.ld.module2.testing.template;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser {
    private static final Pattern PATTERN_TAG = Pattern.compile("#\\{(\\w+\\.?)+}");
    private static final String EMPTY_STRING = "";

    /**
     * Replace placeholders.
     *
     * @param template     the template
     * @param placeholders the placeholders
     * @return the string*
     * @throws IllegalArgumentException exception
     */
    public String replacePlaceholders(String template, Map<String, String> placeholders) throws IllegalArgumentException {
        Matcher matcher = Pattern.compile(String.valueOf(PATTERN_TAG)).matcher(template);
        while (matcher.find()) {
            String match = matcher.group(1);
            String placeholder = placeholders.get(match);
            if (placeholder == null) {
                throw new IllegalArgumentException("Placeholder for " + match + " not found");
            }
            template = template.replace("#{" + match + "}", placeholder);
        }
        return template;
    }

    /**
     * Generate message string.
     *
     * @param template the template
     * @return boolean
     */
    public boolean validateTemplate(String template) {
        return Objects.isNull(template) || Objects.equals(template.trim(), EMPTY_STRING) ? false : true;
    }
}
