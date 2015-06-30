package test.java.server.test.java.server.request;

import main.java.server.request.BasicRequest;
import main.java.server.request.Request;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class BasicRequestTest {

    private Request request;

    @Test
    public void testAddLine() throws Exception {
        request = new BasicRequest();
        request.addLine("hello");
        assertEquals("hello", request.getHeaders());

        request.addLine("my name is Earl");
        assertEquals("hello\nmy name is Earl", request.getHeaders());

    }

    @Test
    public void testAddLineWithBody() throws Exception {
        request = new BasicRequest();
        request.addLine("hello");

    }
}