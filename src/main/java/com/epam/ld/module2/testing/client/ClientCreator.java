package com.epam.ld.module2.testing.client;

import com.epam.ld.module2.testing.Client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ClientCreator {
    private static final String PATH_CLIENTS = "clients.properties";
    private static ClientCreator instance;
    private List<Client> clients;

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    /**
     * Get instance of object
     * @return ClientCreator
     */
    public static ClientCreator getInstance() {
        if (instance == null) {
            instance = new ClientCreator();
        }
        return instance;
    }

    private ClientCreator() {
        clients = loadClients();
    }
    /**
     * Downloading list of clients from properties file
     * @return List
     */
    private List<Client> loadClients() {
        List<Client> clients = new ArrayList<>();
        List<String> clientPropertiesFiles = getClientNames();
        assert clientPropertiesFiles != null;
        if (clientPropertiesFiles.size() > 0) {
            for (String fileName : clientPropertiesFiles
            ) {
                Client client = loadClient(fileName);
                clients.add(client);
            }
        }
        return clients;
    }

    /**
     * Downloading a single client from a properties file
     * @param filename filename of file resource
     * @return List
     */
    private Client loadClient(String filename) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename + ".client.properties")) {
            properties.load(inputStream);
            String stringTags = properties.getProperty("client.tags");
            List<String> listTags = Arrays.stream(stringTags.split(";")).map(String::trim).filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            HashMap<String, String> tags = new HashMap<>();
            for (String tagName : listTags
            ) {
                tags.put(tagName, null);
            }
            String addresses = properties.getProperty("client.addresses");
            Client client = new Client();
            client.setName(filename);
            client.setAddresses(addresses);
            client.setTags(tags);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Get client's names from a properties file
     * @return List
     */
    private List<String> getClientNames() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ClientCreator.PATH_CLIENTS)) {
            properties.load(inputStream);
            String stringTags = properties.getProperty("clients");
            return Arrays.stream(stringTags.split(";")).map(String::trim).filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
