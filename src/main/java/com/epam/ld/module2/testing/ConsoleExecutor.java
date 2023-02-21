package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.client.ClientCreator;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import com.epam.ld.module2.testing.template.TemplateParser;
import com.epam.ld.module2.testing.util.ScannerHelper;

import java.util.HashMap;
import java.util.Map;

public class ConsoleExecutor {
    private TemplateEngine templateEngine;
    private ScannerHelper scannerHelper;
    private final ClientCreator clientCreator = ClientCreator.getInstance();

    /**
     * The constructor
     *
     * @param scannerHelper the scannerExecutor object              *
     * @return
     */
    public ConsoleExecutor(ScannerHelper scannerHelper) {
        this.scannerHelper = scannerHelper;
        this.templateEngine = new TemplateEngine(new TemplateParser());
    }

    /**
     * The main method of the class, responsible for running the application in console mode
     *
     * @return boolean
     */
    public boolean execute() {
        Client client = scannerHelper.getClient(clientCreator.getClients());
        Map<String, String> tags = scannerHelper.readTagValue(client.getTagNames());
        client.setTags((HashMap<String, String>) tags);
        String template = scannerHelper.readTemplateFromConsole();
        String message = templateEngine.generateMessage(new Template(template), client);
        System.out.println(message);
        return true;
    }
}
