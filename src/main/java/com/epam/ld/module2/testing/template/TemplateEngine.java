package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    private static final String PATH_TAGS = "tags.properties";
    private static final String EMPTY_STRING = "";
    private static final Pattern PATTERN_TAG = Pattern.compile("#\\{(\\w+\\.?)+}");
    private List<String> tags;

    public TemplateEngine() {
        tags = loadTags(PATH_TAGS);
    }

    public List<String> getTags() {
        return (List<String>) Collections.unmodifiableCollection(tags);
    }

    /**
     * Get tags from file resources.
     *
     * @param filename the filename
     * @return List result
     */
    public List<String> loadTags(String filename) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            properties.load(inputStream);
            String stringTags = properties.getProperty("tags");
            return Arrays.stream(stringTags.split(",")).map(String::trim).filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @throws IllegalArgumentException exception
     * @return the string
     */
    public String generateMessage(Template template, Client client) throws IllegalArgumentException {
        if (Objects.isNull(template)) {
            throw new IllegalArgumentException("Null value cannot be passed.");
        }
        if (Objects.equals(template.getTemplate().trim(), EMPTY_STRING)) {
            throw new IllegalArgumentException("Empty string cannot be passed.");
        }
        return replacePlaceholders(template.getTemplate(),client.getTags());
    }
    /**
     * Replace placeholders.
     *
     * @param template the template
     * @param placeholders the placeholders
     * @throws IllegalArgumentException exception
     * @return the string*
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
}
