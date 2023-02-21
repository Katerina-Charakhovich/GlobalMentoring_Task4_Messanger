package com.epam.ld.module2.testing.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class SaveResultTestingToFileExtension implements AfterTestExecutionCallback {
    private static final String FILE_NAME = "duration.text";

    private void saveToFile(String text) {
        try {
            Path path = Paths.get(FILE_NAME);
            if (!Files.exists(path)) {
                Files.createFile(Paths.get(FILE_NAME));
            }
            Files.write(Paths.get(FILE_NAME), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Boolean testResult = false;
        try {
            testResult = context.getExecutionException().isPresent();
        } finally {
            Optional<Method> testMethodName = context.getTestMethod();
            String result = testResult ? "FAIL" : "SUCCESS";
            saveToFile(String.format("%s:.  Result: %s \n", testMethodName.get(), result));
        }
    }
}

