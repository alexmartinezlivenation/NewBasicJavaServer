package main.java.server.response.handler;

import main.java.server.request.Request;
import main.java.server.response.Response;

public abstract class ResponseHandler {
    protected Response response;

    protected ResponseHandler() {
        this.response = new Response();
    }

    public Response getResponse() {
        return response;
    }

    protected void setStatusLine(String httpVersion, int errorCode) {
        switch(errorCode) {
            case 200:
                response.setHeaders(httpVersion + " " + errorCode + " OK");
                break;
            case 404:
                response.setHeaders(httpVersion + " " + errorCode + " Not Found");
                break;
            default:
                response.setHeaders(httpVersion + " 500 Internal Server Error");
                break;
        }
    }

    abstract public void generateResponse(Request request);
}
