package main.java.server.response;

public interface Response {

    public void setHeaders(String headers);
    public void setBody(String body);
    public String getHeaders();
    public String getBody();

    public String getMessage();
}
