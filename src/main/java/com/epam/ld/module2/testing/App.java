package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.util.FileHelper;
import com.epam.ld.module2.testing.util.ScannerHelper;
import picocli.CommandLine;

import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "template-generator",
        description = "template-generator ",
        mixinStandardHelpOptions = true
)
public class App implements Callable<Integer> {

    public App() {
    }

    /**
     * The main method of the application
     *
     * @param args the arguments of application
     */
    public static void main(String[] args) {
        System.exit(
                new CommandLine(new App())
                        .setCaseInsensitiveEnumValuesAllowed(true)
                        .execute(args)
        );
    }

    @CommandLine.Option(names = {"-f", "--file-exec"},
            description = "File executor is used",
            defaultValue = "false")
    private boolean isFileExecutor;

    @CommandLine.Parameters(index = "0",
            description = "Get template from",
            defaultValue = "")
    private String readFilePath;

    @CommandLine.Parameters(index = "1",
            description = "Save template to",
            defaultValue = "")
    private String writeFilePath;

    @Override
    public Integer call(){
        ScannerHelper scannerHelper = new ScannerHelper(new Scanner(System.in));
        if (!isFileExecutor) {
            ConsoleExecutor consoleExecutor = new ConsoleExecutor(scannerHelper);
            consoleExecutor.execute();
        } else {
            FileExecutor fileExecutor = new FileExecutor(readFilePath, writeFilePath, scannerHelper,new FileHelper());
            fileExecutor.execute();
        }
        return 1;
    }
}
