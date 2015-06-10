package main.java.server.request;

public class BasicRequest implements Request {
    private String headers;
    private String body;
    private boolean headerFlag;

    public BasicRequest() {
        headerFlag = true;
    }

    public void addLine(String message) {
        if(headerFlag) {
            addToHeader(message);
        }
        else {
            addToBody(message);
        }
    }

    private void addToHeader(String message) {
        if (headers == null) {
            headers = message;
        }
        else {
            headers = headers + "\n" + message;
        }
    }

    private void addToBody(String message) {

    }

    public boolean getHeaderFlag() {
        return headerFlag;
    }

    public void setHeaderFlag(boolean headerFlag) {
        this.headerFlag = headerFlag;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
