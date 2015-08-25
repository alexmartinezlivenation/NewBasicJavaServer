package main.java.server.response.handler;

public class ResponseHandlerFactory {
    public ResponseHandler generateResponseHandler(String requestType) {
        if (requestType.equals("GET")) {
            return new GETHandler();
        }
        else return null;
    }
}
