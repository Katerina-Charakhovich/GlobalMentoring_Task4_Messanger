package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.util.ScannerHelper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleExecutorTest {

    @Test
    void execute_withMockScanner() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        Client client = mock (Client.class);
        ScannerHelper scannerHelper = mock(ScannerHelper.class);
        ConsoleExecutor consoleExecutor = new ConsoleExecutor(scannerHelper);
        when(scannerHelper.readTemplateFromConsole()).thenReturn("Some template #{value}");
        when(scannerHelper.getClient(any())).thenReturn(client);
        when(scannerHelper.readTagValue(anyList())).thenReturn(tags);
        when(client.getTags()).thenReturn(tags);
        boolean result = consoleExecutor.execute();
        assertTrue(result);
    }
    @Test
    void execute_withPartMockScanner() {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        Client client = mock (Client.class);
        ScannerHelper scannerHelper = mock(ScannerHelper.class);
        ConsoleExecutor consoleExecutor = new ConsoleExecutor(scannerHelper);
        when(scannerHelper.readTextFromConsole()).thenReturn("Some template #{value}");
        when(scannerHelper.readTemplateFromConsole()).thenCallRealMethod();
        when(scannerHelper.getClient(any())).thenReturn(client);
        when(scannerHelper.readTagValue(anyList())).thenReturn(tags);
        when(client.getTags()).thenReturn(tags);
        boolean result = consoleExecutor.execute();
        assertTrue(result);
    }
}