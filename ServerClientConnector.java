import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.*;
import java.io.*;


  public class ServerClientConnector extends Thread{
  String clientName = null;
  private BufferedReader is = null;
  PrintStream os = null;
  private Socket clientSocket = null;

  private int maxClientsCount;
  String name;


  public ServerClientConnector(Socket clientSocket) {
    this.clientSocket = clientSocket;

    
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;


    try {
      is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      os = new PrintStream(clientSocket.getOutputStream());


      //Get client name
      while (true) {
        name = is.readLine().trim();
        if (name.indexOf('@') == -1) {
          break;
        } else {
          os.println("The name should not contain '@' character.");
        }
      }
      os.println("Welcome " + name
          + " to our chat room.\nTo leave enter /quit in a new line.");
      synchronized (this) {
            clientName = "@" + name;
            MainServer.welcomeNewClient(this, name, os);
      }

      //While in the chat room
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        //Private message to another client
        if (line.startsWith("@")) {
          String[] words = line.split("\\s", 2);
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              MainServer.privateMessage(this, name, os, words);
              this.os.println(">" + name + "> " + words[1]);
            }
          }
        } else {
          //Public Message
          MainServer.sendMessageToAllClients(this, name, os, line);
        }
      }
      //Tell other clients the client has quit
      MainServer.clientLeaves(this, name, os);
      
      os.println("*** Bye " + name + " ***");

      MainServer.removeClient(this);

      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}