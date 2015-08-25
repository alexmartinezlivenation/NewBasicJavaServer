package main.java.server.service;

public class ServerOptions {
    private int portNum;
    private String directory;

    public ServerOptions(String[] args) {
        portNum = 5000;
        directory = null;

        for (int index=0; index<(args.length-1); index++) {
            if (args[index].equals("-p")) {
                portNum = Integer.parseInt(args[index+1]);
            }
            if (args[index].equals("-d")) {
                directory = args[index+1];
            }
        }
    }

    public int getPortNum() {
        return portNum;
    }

    public String getDirectory() {
        return directory;
    }
}
