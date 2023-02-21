package com.epam.ld.module2.testing.util;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScannerHelperTest {

    @Test
    void readTemplateFromConsole_shouldReturnTemplate() {
        ScannerHelper scannerHelper = mock(ScannerHelper.class);
        String template = "Some text";
        doReturn(template).when(scannerHelper).readTextFromConsole();
        when(scannerHelper.readTemplateFromConsole()).thenCallRealMethod();
        assertEquals(template,scannerHelper.readTemplateFromConsole());
    }
    @Test
    void readTagValueFromConsole_shouldReturnMapTags() {
        List<String> tags = Arrays.asList("value");
        ScannerHelper scannerHelper = mock(ScannerHelper.class);
        String template = "test";
        doReturn(template).when(scannerHelper).readTextFromConsole();
        when(scannerHelper.readTagValue(tags)).thenCallRealMethod();
        HashMap<String, String> expected= new HashMap<>();
        expected.put("value","test");
        HashMap<String, String> actual = scannerHelper.readTagValue(tags);
        assertEquals(actual.get("value"),expected.get("value"));
    }
    @Test
    void readClientFromConsole_shouldReturnClient_whenCorrectNumberOfClient() {
        Client client = new Client();
        client.setAddresses("test@test.com");
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        client.setTags(tags);
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        ScannerHelper scannerHelper = mock(ScannerHelper.class);
        when(scannerHelper.readNumberFromConsole()).thenReturn(0);
        when(scannerHelper.getClient(clients)).thenCallRealMethod();
        Client actual= scannerHelper.getClient(clients);
        assertEquals(actual.getName(),client.getName());
    }
    @Test
    void readClientFromConsole_shouldReturnNull_whenIncorrectNumberOfClient() {
        Client client = new Client();
        client.setAddresses("test@test.com");
        HashMap<String, String> tags = new HashMap<>();
        tags.put("value", "test");
        client.setTags(tags);
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        ScannerHelper scannerHelper = mock(ScannerHelper.class);
        when(scannerHelper.readNumberFromConsole()).thenReturn(2);
        when(scannerHelper.getClient(clients)).thenCallRealMethod();
        Client actual= scannerHelper.getClient(clients);
        assertEquals(actual,null);
    }
}