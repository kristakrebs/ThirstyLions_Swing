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

public class ClientServerConnectionStarter {
public static void main(String[] args) {
        String server = args[0];
        int port =Integer.parseInt(args[1]);
        ClientServerConnector clientServerConnector = null;
        try {
            clientServerConnector = new ClientServerConnector(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
        JFrame frame = new ThirstyLionsFrame("Thirsty Lions",clientServerConnector);
        frame.setVisible(true);

    }
}