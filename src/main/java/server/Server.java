package main.java.server;

import main.java.server.service.CommunicationHandler;
import main.java.server.service.ServerOptions;


public class Server {

    public static void main(String[] args) {
        ServerOptions serverOptions = new ServerOptions(args);
        new Server().startServer(serverOptions);
    }

    public void startServer(final ServerOptions serverOptions) {
        CommunicationHandler communicationHandler = new CommunicationHandler(serverOptions);
        communicationHandler.listen();
    }
}
