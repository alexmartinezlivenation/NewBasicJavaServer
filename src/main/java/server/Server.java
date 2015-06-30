package main.java.server;

import main.java.server.request.BasicRequest;
import main.java.server.request.Request;
import main.java.server.response.BasicResponse;
import main.java.server.response.Response;
import main.java.server.service.ServerOptions;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ServerSocket server;
    private static Socket connection;

    public static void main(String[] args) {
        ServerOptions serverOptions = new ServerOptions(args);
        new Server().startServer(serverOptions);
    }

    public void startServer(final ServerOptions serverOptions) {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(serverOptions.getPortNum());
                    while (true) {
                        waitForConnection();
                        clientProcessingPool.submit(new ClientTask(connection, serverOptions.getDirectory()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        connection.close();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;
        private PrintWriter output;
        private InputStream input;
        private String directory;

        private ClientTask(Socket connection, String directory) {
            this.clientSocket = connection;
            this.directory = directory;
        }

        @Override
        public void run() {
            try {
                setupStreams();
                handleCommunication();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                closeAndCleanup();
            }

        }

        private void setupStreams() throws IOException {
            output = new PrintWriter(clientSocket.getOutputStream());
            output.flush();
            input = clientSocket.getInputStream();
            showMessage("\nDebug: Streams are now set up! \n");
        }

        private void handleCommunication() throws IOException {
            String CRLF = "\r\n";
            showMessage("Debug: You are now connected! ");

            Request request = new BasicRequest();

            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String message;
            do {
                message = br.readLine();
                request.addLine(message);
                showMessage("Debug: CLIENT - " + message);
            }while(!message.trim().isEmpty());

            Response response = new BasicResponse();
            response.createResponse(request, directory);
            messageToClient(response.getMessage());
        }

        private void messageToClient(String message) {
            output.write(message);
            output.flush();
            showMessage("\nDebug: Server - " + message);
        }

        private void closeAndCleanup() {
            showMessage("\nDebug: Closing...");
            try {
                output.close();
                input.close();
                clientSocket.close();
            }
            catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
    }

    private static void waitForConnection() throws IOException {
        showMessage("Debug: Waiting for someone to connect...\n");
        connection = server.accept();
        showMessage("Debug: Now connected to " + connection.getInetAddress().getHostName());
    }



    private static void showMessage(String message) {
        System.out.println(message);
    }
}
