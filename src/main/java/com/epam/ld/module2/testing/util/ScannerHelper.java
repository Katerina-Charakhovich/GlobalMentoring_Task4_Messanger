package com.epam.ld.module2.testing.util;

import com.epam.ld.module2.testing.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ScannerHelper {
    private final Scanner scanner;

    public ScannerHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Read number of client from console
     *
     * @param clientList the clients' List
     * @return Client
     */
    public Client getClient(List<Client> clientList) {
        System.out.println("Select a client from the list");
        int countClients = clientList.size();
        for (int i = 0; i < countClients; i++) {
            System.out.println(i + ":" + clientList.get(i).getName());
        }
        int number = readNumberFromConsole();
        return ((number < countClients) && (number > -1)) ? clientList.get(number) : null;
    }

    /**
     * Read template from  console
     *
     * @return String
     */
    public String readTemplateFromConsole() {
        System.out.println("Enter your template: ");
        String text = readTextFromConsole();
        return text;
    }

    /**
     * Read int number from console
     *
     * @return int
     */
    public int readNumberFromConsole() {
        while(!scanner.hasNextInt()) {
           System.out.println("Please enter correct number");
        }
        return scanner.nextInt();
    }

    /**
     * Read line from console
     *
     * @return String
     */
    public String readTextFromConsole() {
        String text = scanner.nextLine();
        while (text.isEmpty()) {
            System.out.println("Enter valid value:");
            text = scanner.nextLine();
        }
        return text;
    }

    /**
     * Read tag values  from  console
     *
     * @param tags List of tags to enter values for
     * @return Map
     */
    public HashMap<String, String> readTagValue(List<String> tags) {
        HashMap<String, String> fillTags = new HashMap<>();
        for (String tag : tags) {
            System.out.println("Enter '" + tag + "': ");
            String value = readTextFromConsole();
            fillTags.put(tag, value);
        }
        return fillTags;
    }
}
