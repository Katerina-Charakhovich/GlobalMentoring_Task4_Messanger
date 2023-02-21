package com.epam.ld.module2.testing.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileHelper {

    /**
     * The method determines whether the path is a directory or a file
     *
     * @param path the path
     * @return int
     */
    public int isFileOrDirectory(String path) {
        File file = new File(path);
        if (file.exists()) {
            return Files.isDirectory(file.toPath()) ? 1 : 0;
        } else {
            return -1;
        }
    }

    /**
     * Read template from  file
     *
     * @param readFilePath the path
     * @return String
     */
    public String readTemplateFromFile(String readFilePath) {
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(readFilePath)) {
            int readLine;
            while ((readLine = fileInputStream.read()) != -1) {
                builder.append((char) readLine);
            }
        } catch (IOException e) {
        //    logger.error("Cannot read from file: " + readFilePath + ". Cause: " + e);
        }
        return builder.toString();
    }

    /**
     * Save content to file
     *
     * @param content the created content from template
     * @param writeFilePath path for writing content
     * @return boolean
     */
    public boolean saveContentToFile(String content, String writeFilePath ) {
        boolean isSave = false;
        try (FileOutputStream outputStream = new FileOutputStream(writeFilePath)) {
            outputStream.write(content.getBytes());
            isSave = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSave;
    }
}
