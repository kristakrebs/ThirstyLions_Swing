import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.io.PrintStream;


public class ClientServerConnector extends Observable {
    private Socket socket;
    private OutputStream outputStream;

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    /** Create socket, and receiving thread */
    public ClientServerConnector(String server, int port) throws IOException {
        System.out.println("Establishing connection. Please wait ...");
        socket = new Socket(server, port);
        System.out.println("Connected: " + socket);
        outputStream = socket.getOutputStream();

        Thread receivingThread = new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null)
                            notifyObservers(line);
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
            }
            };
            receivingThread.start();
        }

    private static final String CRLF = "\r\n"; // newline

    public void send(String text) {
        try {
            outputStream.write((text + CRLF).getBytes());
            outputStream.flush();
            } catch (IOException ex) {
            notifyObservers(ex);
            }
     }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }
}
