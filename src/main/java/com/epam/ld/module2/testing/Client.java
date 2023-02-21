package com.epam.ld.module2.testing;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Client.
 */
public class Client {
    private String name;
    private String addresses;
    private HashMap<String, String> tags;

    /**
     * Gets addresses.
     *
     * @return the addresses
     */
    public String getAddresses() {
        return addresses;
    }

    /**
     * Sets addresses.
     *
     * @param addresses the addresses
     */
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get list of tags names
     * @return List
     */
    public List<String> getTagNames() {
        return tags.entrySet()
                .stream()
                .map(s->s.getKey())
                .collect(Collectors.toList());
    }
}
