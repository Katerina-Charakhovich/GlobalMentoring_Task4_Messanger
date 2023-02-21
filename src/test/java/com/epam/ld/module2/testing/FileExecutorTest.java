package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.util.FileHelper;
import com.epam.ld.module2.testing.util.ScannerHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileExecutorTest {

    @Test
    public void execute_withMockReadFromFile()  throws IOException {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        Client client = mock (Client.class);
        ScannerHelper scannerHelper = mock(ScannerHelper.class);
        FileHelper fileHelper= mock(FileHelper.class);
        FileExecutor fileExecutor = new FileExecutor("test","test", scannerHelper,fileHelper);
        when(scannerHelper.getClient(any())).thenReturn(client);
        when(scannerHelper.readTagValue(anyList())).thenReturn(tags);
        when(client.getTags()).thenReturn(tags);
        when(fileHelper.isFileOrDirectory(any())).thenReturn(1);
        when(fileHelper.readTemplateFromFile(any())).thenReturn("Some template #{value}");
        when(fileHelper.saveContentToFile(any(),any())).thenReturn(true);
        boolean result = fileExecutor.execute();
        assertTrue(result);
    }



}