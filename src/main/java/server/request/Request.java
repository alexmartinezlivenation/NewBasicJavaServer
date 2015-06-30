package main.java.server.request;

import java.io.IOException;

public interface Request {

    public void setHeaders(String headers);
    public void setBody(String body);
    public void addLine(String message);
    public void setHeaderFlag(boolean headerFlag);
    public String getHeaders();
    public String getBody();
    public boolean getHeaderFlag();
}
