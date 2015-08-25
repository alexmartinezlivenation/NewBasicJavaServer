package main.java.server.request;

import java.util.Map;

public class Request {
    private Map<String, String> headers;
    private String body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestLine() {
        return headers.get("Request-Line");
    }
}
