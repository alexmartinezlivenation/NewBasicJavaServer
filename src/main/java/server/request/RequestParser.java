package main.java.server.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private Request request;

    public RequestParser() {
        request = new Request();
    }

    public Request parse(String message) throws IOException {
        Map<String, String> headers = new HashMap<String, String>();
        String[] parsedMessage = message.split("\\r?\\n");
        headers.put("Request-Line", parsedMessage[0]);
        for (int index = 1; index < parsedMessage.length; index++) {
            String[] headerPair = parsedMessage[index].split(": ");
            headers.put(headerPair[0], headerPair[1]);
        }
        request.setHeaders(headers);
        return request;
    }

    /* private void addToRequestHeaders(String headers) {
        String currentHeaders = request.getHeaders();
        if (currentHeaders == null) {
            request.setHeaders(headers);
        }
        else {
            request.setHeaders(currentHeaders + "\n" + headers);
        }
    }

    private static void showMessage(String message) {
        System.out.println(message);
    } */
}
