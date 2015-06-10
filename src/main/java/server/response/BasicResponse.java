package main.java.server.response;

public class BasicResponse implements Response{

    private String headers;
    private String body;

    @Override
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getHeaders() {
        return headers;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getMessage() {
        return headers + "\n\n" + body + "\r\n";
    }
}
