package test.java.server.response;

import main.java.server.request.BasicRequest;
import main.java.server.request.Request;
import main.java.server.response.BasicResponse;
import main.java.server.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicResponseTest {
    private Response response = new BasicResponse();
    private String directory = "/Users/alex.martinez/work/apprenticeship/trunk/cob_spec/cob_spec/public/";

    @Test
    public void testGetMessage() throws Exception {
        response.setHeaders("a header");
        response.setBody("a body");
        assertEquals("a header\n\na body\r\n", response.getMessage());
    }

    @Test
    public void testCreateResponseFromGETRequest() throws Exception {
        Request request = new BasicRequest();
        request.setHeaders("GET / HTTP/1.1\nHost: localhost:5000\nConnection: Keep-Alive\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\nAccept-Encoding: gzip,deflate");
        response.createResponse(request, directory);
        assertEquals("HTTP/1.1 200 OK", response.getHeaders());
    }

    @Test
    public void testCreateResponseWithBodyFromGETRequest() throws Exception {
        Request request = new BasicRequest();
        request.setHeaders("GET /file1 HTTP/1.1\nHost: localhost:5000\nConnection: Keep-Alive\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\nAccept-Encoding: gzip,deflate");
        response.createResponse(request, directory);
        assertEquals("HTTP/1.1 200 OK", response.getHeaders());
        assertEquals("file1 contents", response.getBody());
    }

    @Test
    public void testCreateResponseFourOhFour() throws Exception {
        Request request = new BasicRequest();
        request.setHeaders("GET /foobar HTTP/1.1\nHost: localhost:5000\nConnection: Keep-Alive\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\nAccept-Encoding: gzip,deflate");
        response.createResponse(request, directory);
        assertEquals("HTTP/1.1 404 Not Found", response.getHeaders());
    }
}