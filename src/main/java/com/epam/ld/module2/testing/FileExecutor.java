package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.client.ClientCreator;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import com.epam.ld.module2.testing.template.TemplateParser;
import com.epam.ld.module2.testing.util.FileHelper;
import com.epam.ld.module2.testing.util.ScannerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileExecutor {
    private static final Logger logger = LogManager.getLogger(FileExecutor.class);
    private final String readFilePath;
    private String writeFilePath;
    private final TemplateEngine templateEngine;
    private final ScannerHelper scannerHelper;
    private final ClientCreator clientCreator = ClientCreator.getInstance();
    private final FileHelper fileHelper;
    /**
     * The constructor
     * @param readFilePath the read file path with template
     * @param writeFilePath the path for generated template
     * @param scannerHelper scannerHelper
     * @param fileHelper fileHelper
     * @return
     */
    public FileExecutor(String readFilePath,
                        String writeFilePath,
                        ScannerHelper scannerHelper,
                        FileHelper fileHelper) {

        this.readFilePath = readFilePath;
        this.writeFilePath = writeFilePath;
        this.scannerHelper = scannerHelper;
        this.templateEngine = new TemplateEngine(new TemplateParser());
        this.fileHelper = fileHelper;
    }

    /**
     * The main method of the class, responsible for running the application in file mode
     * @return boolean
     */
    public boolean execute() {
        Client client = scannerHelper.getClient(clientCreator.getClients());
        Map<String, String> tags = scannerHelper.reaTagValue(client.getTagNames());
        client.setTags((HashMap<String, String>) tags);
        int validatePathCopyTo = fileHelper.isFileOrDirectory(writeFilePath);
        if (fileHelper.isFileOrDirectory(readFilePath) == 0 && validatePathCopyTo != -1) {
            if (validatePathCopyTo == 1) {
                String newPathForTo = writeFilePath + "/" + new File(readFilePath).getName();
                File file = new File(newPathForTo);
                writeFilePath = newPathForTo;
            }
        }
        String templateString =  fileHelper.readTemplateFromFile(readFilePath);
        String content = templateEngine.generateMessage( new Template(templateString), client);
        fileHelper.saveContentToFile(content,writeFilePath);
        return true;
    }


}
