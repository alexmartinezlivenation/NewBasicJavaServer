package main.java.server.response;

import main.java.server.request.Request;

public interface Response {

    public void setHeaders(String headers);
    public void setBody(String body);
    public String getHeaders();
    public String getBody();

    public String getMessage();

    void createResponse(Request request, String directory);
}
