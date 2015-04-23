import java.awt.FlowLayout;

import javax.swing.*;
import java.awt.*; 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ThirstyLionsFrame extends JFrame implements Observer{
    private JTextArea textArea;
    private JButton[] lions = new JButton[10];
    private JButton[] cases = new JButton[5];
    private JLabel playerScoreL, playerScoreCount, opponentScoreL, opponentScoreCount, timerL, timer;
    private ClientServerConnector player;
    private JPanel scoreboard, customers, resources;

    public ThirstyLionsFrame(String title, ClientServerConnector client){
        super(title);
        this.player=client;
        player.addObserver(this);

        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildGUI();
    }

    private void buildGUI(){

        //Scoreboard
        scoreboard = new JPanel();
        scoreboard.setLayout(null);
        scoreboard.setBounds(0,0,600,100);
        scoreboard.setVisible(true);
        scoreboard.setBackground(Color.PINK);

        //Scoreboard assets
        //player score
        playerScoreL = new JLabel("Your Score:");
        playerScoreL.setBounds(5,5,150,50);
        playerScoreCount = new JLabel("0");
        playerScoreCount.setBounds(25,25,150,50);
        scoreboard.add(playerScoreL);
        scoreboard.add(playerScoreCount);
        //opponent score
        opponentScoreL = new JLabel("Their score:");
        opponentScoreL.setBounds(505,5,150,50);
        opponentScoreCount = new JLabel("0");
        opponentScoreCount.setBounds(535,25,150,50);
        scoreboard.add(opponentScoreL);
        scoreboard.add(opponentScoreCount);
        //timer
        timerL = new JLabel("Time Remaining");
        timerL.setBounds(255,5,150,50);
        timer = new JLabel("1:00");
        timer.setBounds(275,25,150,50);
        scoreboard.add(timerL);
        scoreboard.add(timer);
        this.add(scoreboard);


        //Customers or Lions Area
        customers = new JPanel();
        customers.setLayout(new GridLayout(2,5));
        customers.setBounds(0,100,600,200);
        customers.setVisible(true);
        customers.setBackground(Color.BLUE);

        //lion buttons
        for (int i =0; i<lions.length;i++){
            lions[i]= new JButton("");
            customers.add(lions[i]);
        }
        this.add(customers);

        //Bar Tap/ Resources Area
        resources = new JPanel();
        resources.setLayout(new GridLayout(1,5));
        resources.setBounds(0,300,600,199);
        resources.setVisible(true);
        resources.setBackground(Color.GREEN);
        for (int x =0; x<cases.length;x++){
            cases[x]= new JButton("");
            resources.add(cases[x]);
        }
        this.add(resources);  

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        this.add(panel);
        


    }
      public void update(Observable o, Object arg) {
        final Object finalArg = arg;
          SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                 //timerL.append(finalArg.toString());
                 //imerL.append("\n");
            }
        });
    }
}
    
