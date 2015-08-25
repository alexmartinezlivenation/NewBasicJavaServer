package test.java.server.test.java.server.request;

import main.java.server.request.Request;
import main.java.server.request.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {
    public RequestParser requestParser;

    @Before
    public void setup() {
        requestParser = new RequestParser();
    }

    @Test
    public void parseBasicMessage() throws Exception {
        Request expectedRequest = new Request();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Request-Line", "GET / HTTP/1.1");
        expectedRequest.setHeaders(headers);
        String message = "GET / HTTP/1.1";
        Request request = requestParser.parse(message);
        assertEquals(expectedRequest.getHeaders(), request.getHeaders());
    }

    @Test
    public void parseMessageWithHeaders() throws Exception {
        Request expectedRequest = new Request();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Request-Line", "GET / HTTP/1.1");
        headers.put("Host", "localhost:5000");
        expectedRequest.setHeaders(headers);
        String message = "GET / HTTP/1.1\nHost: localhost:5000";
        Request request = requestParser.parse(message);
        assertEquals(expectedRequest.getHeaders(), request.getHeaders());
    }

    @Test
    public void parseMessageWithMultipleHeaders() throws Exception {
        Request expectedRequest = new Request();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Request-Line", "GET / HTTP/1.1");
        headers.put("Host", "localhost:5000");
        headers.put("Connection", "Keep-Alive");
        headers.put("User-Agent", "Apache-HttpClient/4.3.5 (java 1.5)");
        headers.put("Accept-Encoding", "gzip,deflate");
        expectedRequest.setHeaders(headers);
        String message = "GET / HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
        Request request = requestParser.parse(message);
        assertEquals(expectedRequest.getHeaders(), request.getHeaders());
    }

    @Test
    public void parseEmptyMessage() throws Exception {
        Request expectedRequest = new Request();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Request-Line", "");
        expectedRequest.setHeaders(headers);
        String message = "";
        Request request = requestParser.parse(message);
        assertEquals(expectedRequest.getHeaders(), request.getHeaders());
    }
}
