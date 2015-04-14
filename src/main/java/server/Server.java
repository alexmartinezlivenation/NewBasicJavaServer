package main.java.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public static void main(String[] args) {
        int portNum = 5000;
        String directory;

        for (int index=0; index<(args.length-1); index++) {

            if (args[index].equals("-p")) {
                portNum = Integer.parseInt(args[index+1]);
            }
            if (args[index].equals("-d")) {
                directory = args[index+1];
            }
        }
        new Server(portNum);
    }

    public Server(int portNum) {
        try {
            ServerSocket sSocket = new ServerSocket(portNum);
            System.out.println("Server started at: " + new Date() + " on port " + portNum);

            //Wait for client to connect
            Socket socket = sSocket.accept();

            //Create the streams
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            //Tell the client that they have connected
            output.println("You have connected at: " + new Date());

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
