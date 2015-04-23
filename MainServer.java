import java.net.*;
import java.io.*;

public class MainServer {

	static ServerConnectionListener listener;
	static ServerClientConnector[] clients = new ServerClientConnector[50];

    static int clientCount = 0;

    public static void main(String[] args) throws IOException {

    if (args.length != 1) {
        System.err.println("Usage: ServerConnectionListener <port number>");
        System.exit(1);
    }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
        
        listener = new ServerConnectionListener(portNumber);
    }

    static void createClientThread(Socket s){
    	for (int i = 0; i < clients.length; i++) 

          			if (clients[i] == null) {
            			clients[i] = new ServerClientConnector(s);
            			clients[i].start();
                        clientCount++;
            		break;
          		}
    	}
	

    static void sendMessageToAllClients(ServerClientConnector scc, String name, PrintStream os, String line){
          synchronized (scc) {
            for (int i = 0; i < clients.length; i++) {
              if ((clients[i] != null) && (clients[i].clientName != null)) {
                clients[i].os.println("<" + name + "> " + line);
              }
            }
          }
    }


    static void welcomeNewClient(ServerClientConnector scc, String name, PrintStream os){
          synchronized (scc) {
          String message ="Users in chatroom:";
           for (int i = 0; i < clients.length; i++) {
                if (clients[i] != null && clients[i] != scc) {
                    clients[i].os.println("*** A new user " + name
                        + " entered the chat room !!! ***");
                }
                if (clientCount == 1 && clients[i] == scc){
                    scc.os.println("You are the first one here.\nOnce other users join, this chat room will broadcast to everyone.\nTo send a private message, type @name.");
                }
                if (clientCount > 1 && clients[i] == scc){
                    
                    int x = 0;
                    while(x < clientCount){
                        message = message + " " + clients[x].name;
                        x++;
                    }
                    
                    scc.os.println(message+ "\nThis chat room broadcasts to everyone.\nTo send a private message, type @name.");
                }
                }
            }
        }
          
     static void whoIsHere(ServerClientConnector scc, String name, PrintStream os){
          synchronized (scc) {
           for (int i = 0; i < clients.length; i++) {
                if (clients[i] != null && clients[i] == scc){
                    String message ="Users currently chatting:";
                    for(int x = 0; x < clients.length; x++){
                        message = message + " " + clients[x].name;

                    }
                        clients[i].os.println(message);
                }
                else{
                    clients[i].os.println("You are the first one here.");
                }
            }
          }


        }
    static void clientLeaves(ServerClientConnector scc, String name, PrintStream os){
        synchronized (scc) {
            for (int i = 0; i < clients.length; i++) {
              if (clients[i] != null && clients[i] != scc
                  && clients[i].clientName != null) {
                clients[i].os.println("*** The user " + name
                    + " is leaving the chat room !!! ***");
              }
            }
        }

    }

    static void privateMessage(ServerClientConnector scc, String name, PrintStream os, String[] words){
        synchronized (scc) {
            for (int i = 0; i < clients.length; i++) {
              if (clients[i] != null && clients[i] != scc
                  && clients[i].clientName != null
                  && clients[i].clientName.equals(words[0])) {
                clients[i].os.println("<(privatemsg)" + name + "> " + words[1]);
                break;
              }
            }
        }

    }

    static void removeClient(ServerClientConnector scc){
        synchronized (scc) {
        for (int i = 0; i < clients.length; i++) {
          if (clients[i] == scc) {
            clients[i] = null;
            clientCount--; 
          }
        }
      }
    }
}


