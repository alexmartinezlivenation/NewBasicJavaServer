package main.java.server.response.handler;

import main.java.server.request.Request;

public class GETHandler extends ResponseHandler {

    public GETHandler() {
        super();
    }

    @Override
    public void generateResponse(Request request) {
        //response.setHeaders("HTTP/1.1 200 OK");
        setStatusLine("HTTP/1.1", 200);
    }
}
