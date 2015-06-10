package main.java.server;

import main.java.server.request.BasicRequest;
import main.java.server.request.Request;
import main.java.server.response.BasicResponse;
import main.java.server.response.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ServerSocket server;
    private static Socket connection;

    public static void main(String[] args) {
        int portNum = 5000;
        String directory = null;

        for (int index=0; index<(args.length-1); index++) {
            if (args[index].equals("-p")) {
                portNum = Integer.parseInt(args[index+1]);
            }
            if (args[index].equals("-d")) {
                directory = args[index+1];
            }
        }

        new Server().startServer(portNum, directory);
    }

    public void startServer(final int portNum, String directory) {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(portNum);
                    while (true) {
                        waitForConnection();
                        clientProcessingPool.submit(new ClientTask(connection));
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

        private ClientTask(Socket connection) {
            this.clientSocket = connection;
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
            Request request = new BasicRequest();

            String CRLF = "\r\n";
            showMessage("Debug: You are now connected! ");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String message;
            do {
                message = br.readLine();
                request.addLine(message);
                showMessage("Debug: CLIENT - " + message);
            }while(!message.trim().isEmpty());

            Response response = new BasicResponse();
            response.setHeaders("HTTP/1.1 200 OK");
            response.setBody("file1 contents");
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
