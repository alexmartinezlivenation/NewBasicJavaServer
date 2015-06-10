package main.java.server.request;

public interface Request {

    public void setHeaders(String headers);
    public void setBody(String body);
    public void setHeaderFlag(boolean headerFlag);
    public String getHeaders();
    public String getBody();
    public boolean getHeaderFlag();
    public void addLine(String message);
}
