package main.java.server.response;

public class Response {
    private String body;
    private String headers;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getMessage() {
        //if (body != null) {
        //    return headers + "\r\n\r\n" + body;
        //}
        //else {
            return headers + "\r\n";
        //}
    }
}
