package test.java.server.response;

import main.java.server.response.BasicResponse;
import main.java.server.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicResponseTest {
    private Response response = new BasicResponse();

    @Test
    public void testGetMessage() throws Exception {
        response.setHeaders("a header");
        response.setBody("a body");
        assertEquals("a header\n\na body\r\n", response.getMessage());
    }
}