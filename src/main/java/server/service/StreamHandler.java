package main.java.server.service;

import java.io.*;
import java.net.Socket;

public class StreamHandler {
    private Socket connection;
    private PrintWriter output;
    private InputStream input;

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public void setInput(InputStream is) {
        input = is;
    }

    public void setupStream() throws IOException {
        output = new PrintWriter(connection.getOutputStream());
        output.flush();
        input = connection.getInputStream();
        showMessage("\nDebug: Streams are now set up! \n");
    }

    public String readFromStream() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String message = "";
        String line;
        do {
            line = br.readLine();
            if (line == null || line.equals(null))
                break;
            if (message.equals("")) {
                message = line;
            }
            else {
                message = message + "\n" + line;
            }
        }while(!line.trim().isEmpty());
        //a stream ends with -1?
        showMessage("Debug: CLIENT - " + message);
        return message;
    }

    public void writeToStream(String message) {
        output.write(message);
        output.flush();
        showMessage("\nDebug: Server - " + message);
    }

    public void closeAndCleanupStream() {
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
