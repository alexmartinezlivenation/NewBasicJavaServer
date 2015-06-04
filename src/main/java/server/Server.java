package main.java.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server;
    private static Socket connection;
    private static PrintWriter output;
    private static InputStream input;

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

        try {
            server = new ServerSocket(portNum);
            while (true) {
                try {
                    waitForConnection();
                    setupStreams();
                    handleCommunication();
                }
                catch (EOFException eofException) {
                    showMessage("\n Server ended the connection!");
                }
                finally {
                    closeAndCleanup();
                }
            }

        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static void waitForConnection() throws IOException {
        showMessage("Debug: Waiting for someone to connect...\n");
        connection = server.accept();
        showMessage("Debug: Now connected to " + connection.getInetAddress().getHostName());
    }

    private static void setupStreams() throws IOException {
        output = new PrintWriter(connection.getOutputStream());
        output.flush();
        input = connection.getInputStream();
        showMessage("\nDebug: Streams are now set up! \n");
    }

    private static void handleCommunication() throws IOException {
        String CRLF = "\r\n";
        String message = "Debug: You are now connected! ";
        showMessage(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        do {
            message = br.readLine();
            showMessage("Debug: CLIENT - " + message);
        }while(!message.trim().isEmpty());
        messageToClient("HTTP/1.1 200 OK" + CRLF);
    }

    private static void messageToClient(String message) {
        output.write(message);
        output.flush();
        showMessage("\nDebug: Server - " + message);
    }

    private static void closeAndCleanup() {
        showMessage("\nDebug: Closing...");
        try {
            output.close();
            input.close();
            connection.close();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    private static void showMessage(String message) {
        System.out.println(message);
    }
}
