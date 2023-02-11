package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    private static final String PATH_TAGS = "tags.properties";
    private List<String> tags;

    public TemplateEngine() {
        tags = loadTags(PATH_TAGS);
    }

    public List<String> getTags() {
        return (List<String>) Collections.unmodifiableCollection(tags);
    }

    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        return null;
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


}
