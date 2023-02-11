package com.epam.ld.module2.testing;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Client.
 */
public class Client {
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
}
