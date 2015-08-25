package test.java.server.service;

import main.java.server.service.StreamHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class StreamHandlerTest {
    public StreamHandler streamHandler;

    @Before
    public void setup() {
        streamHandler = new StreamHandler();
    }

    @Test
    public void readBasicMessage() throws Exception {
        byte[] data = "GET / HTTP/1.1\r\n".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        streamHandler.setInput(input);
        String message = streamHandler.readFromStream();
        assertTrue(message.equals("GET / HTTP/1.1"));
    }

    @Test
    public void readGETMessage() throws Exception {
        byte[] data = "GET / HTTP/1.1\nHost: localhost:5000\nConnection: Keep-Alive\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\nAccept-Encoding: gzip,deflate\r\n".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        streamHandler.setInput(input);
        String message = streamHandler.readFromStream();
        assertTrue(message.equals("GET / HTTP/1.1\nHost: localhost:5000\nConnection: Keep-Alive\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\nAccept-Encoding: gzip,deflate"));
    }

    @Test
    public void readEmptyMessage() throws Exception {
        byte[] data = "".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        streamHandler.setInput(input);
        String message = streamHandler.readFromStream();
        assertTrue(message.equals(""));
    }
}
