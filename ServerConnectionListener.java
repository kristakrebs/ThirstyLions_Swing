import java.net.*;
import java.io.*;

public class ServerConnectionListener {

	public ServerConnectionListener(int port) throws IOException {

        int portNumber = port;
        boolean listening = true;

        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
                System.out.println("Waiting for clients to connect...");
	            MainServer.createClientThread(serverSocket.accept());
                System.out.println("Connected to "+serverSocket);
        	}
	      
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
