package main.java.server.service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CommunicationHandler {
    private final ExecutorService clientProcessingPool;
    private static ServerSocket server;
    private static Socket connection; // look into
    private ServerOptions serverOptions;

    public CommunicationHandler(ServerOptions serverOptions) {
        clientProcessingPool = Executors.newFixedThreadPool(10);
        this.serverOptions = serverOptions;
    }

    public void listen() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(serverOptions.getPortNum());
                    while (true) {
                        waitForConnection();
                        clientProcessingPool.submit(new CommunicationThread(connection, serverOptions.getDirectory()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        clientProcessingPool.shutdown();
                        clientProcessingPool.awaitTermination(60, TimeUnit.SECONDS);
                        connection.close();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        this.createThread(serverTask);
    }

    public void createThread(Runnable serverTask) {
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private void waitForConnection() throws IOException {
        System.out.println("Debug: Waiting for someone to connect...\n");
        connection = server.accept();
        System.out.println("Debug: Now connected to " + connection.getInetAddress().getHostName());
    }
}
