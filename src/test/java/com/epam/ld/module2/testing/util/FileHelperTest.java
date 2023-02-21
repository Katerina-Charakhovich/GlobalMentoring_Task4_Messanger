package com.epam.ld.module2.testing.util;

import com.epam.ld.module2.testing.annotations.DisableOnWeekends;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {
    @TempDir
    File tempDir;

    @Test
    @DisableOnWeekends
    public void readTemplateFromFile() throws IOException {
        final File tempFile = new File(tempDir, "tempFile.txt");
        FileUtils.writeStringToFile(tempFile, "hello world");
        FileHelper fileHelper = new FileHelper();
        String actual = fileHelper.readTemplateFromFile(tempFile.getPath());
        assertEquals("hello world", actual);
    }
}