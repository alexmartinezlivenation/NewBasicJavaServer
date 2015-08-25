package main.java.server.service;

import main.java.server.request.Request;
import main.java.server.request.RequestParser;
import main.java.server.response.Response;
import main.java.server.response.handler.ResponseHandler;
import main.java.server.response.handler.ResponseHandlerFactory;

import java.io.*;
import java.net.Socket;

public class CommunicationThread implements Runnable {
    private String directory;
    private StreamHandler streamHandler;


    public CommunicationThread(Socket connection, String directory) {
        this.directory = directory;
        streamHandler = new StreamHandler();
        this.streamHandler.setConnection(connection);
    }

    @Override
    public void run() {
        try {
            streamHandler.setupStream();
            Request request = readRequest();
            Response response = generateResponseFromRequest(request);
            streamHandler.writeToStream(response.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            streamHandler.closeAndCleanupStream();
        }
    }

    private Request readRequest() throws IOException {
        String message = streamHandler.readFromStream();
        RequestParser requestParser = new RequestParser();
        return requestParser.parse(message);
    }

    private Response generateResponseFromRequest(Request request) {
        String[] parsedRequestLine = request.getRequestLine().split(" ");
        ResponseHandlerFactory rf = new ResponseHandlerFactory();
        ResponseHandler responseHandler = rf.generateResponseHandler(parsedRequestLine[0]);
        responseHandler.generateResponse(request);
        return responseHandler.getResponse();
    }
}
