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
        int number = scanner.nextInt();
        return ((number < countClients) && (number > -1)) ? clientList.get(number) : null;
    }

    /**
     * Read template from  console
     *
     * @return String
     */
    public String readTemplateFromConsole() {
        System.out.println("Enter your template: ");
        return scanner.nextLine();
    }
    /**
     * Read tag values  from  console
     * @param tags List of tags to enter values for
     * @return Map
     */
    public HashMap<String, String> reaTagValue(List<String> tags) {
        HashMap<String, String> fillTags = new HashMap<>();
        for (String tag : tags) {
            System.out.println("Enter '" + tag + "': ");
            String value = scanner.nextLine();
            while (value.isEmpty()) {
                System.out.println("Enter valid tag value:");
                value = scanner.nextLine();
            }
            fillTags.put(tag, value);
        }
        return fillTags;
    }
}
