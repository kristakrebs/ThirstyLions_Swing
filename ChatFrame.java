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
import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.*;

public class ChatFrame extends JFrame implements Observer {

    private JTextArea textArea;
    private JTextField inputTextField;
    private JButton sendButton;
    private ClientServerConnector chatAccess;

    public ChatFrame(ClientServerConnector chatAccess) {
        this.chatAccess = chatAccess;
        chatAccess.addObserver(this);
        buildGUI();
    }
    private void buildGUI() {
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setText("Enter your name\n");
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        Box box = Box.createHorizontalBox();
        add(box, BorderLayout.SOUTH);
        inputTextField = new JTextField();
        sendButton = new JButton("Send");
        box.add(inputTextField);
        box.add(sendButton);

        ActionListener sendListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = inputTextField.getText();
                if (str != null && str.trim().length() > 0)
                    chatAccess.send(str);
                    inputTextField.selectAll();
                    inputTextField.requestFocus();
                    inputTextField.setText("");
                }
            };
            inputTextField.addActionListener(sendListener);
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });
        }

      public void update(Observable o, Object arg) {
        final Object finalArg = arg;
          SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                 textArea.append(finalArg.toString());
                 textArea.append("\n");
            }
        });
    }
}