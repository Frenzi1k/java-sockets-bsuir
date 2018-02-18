package main;
import socket.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Main extends JFrame implements ActionListener{

    private Button start = new Button("Start");
    private Button firstClient = new Button("First client");
    private Button secondClient = new Button("Second client");
    private TextField balance = new TextField("Balance");

    public Main(){
        this.setSize(500,250);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.getContentPane().setBackground(new Color(0,150,255));

        start.setSize(100,30);
        start.setLocation((this.getWidth() - start.getWidth()) / 2 ,10);
        start.setBackground(new Color(5, 180,0));
        this.add(start);

        firstClient.setSize(100,30);
        firstClient.setLocation(start.getX()- firstClient.getWidth() - 10,start.getY()+ start.getHeight());
        firstClient.setBackground(new Color(5, 180,0));
        this.add(firstClient);

        secondClient.setSize(100,30);
        secondClient.setLocation(start.getX() + secondClient.getWidth() + 10,start.getY()+ start.getHeight());
        secondClient.setBackground(new Color(5, 180,0));
        this.add(secondClient);

        balance.setSize(100,30);
        balance.setLocation(start.getX(), start.getHeight() + 20);

        this.add(balance);

        start.addActionListener(this);
        firstClient.addActionListener(this);
        secondClient.addActionListener(this);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e){
        try {
            if (e.getSource() == firstClient) {
                getChangingBalance("post", -200);
            } else if (e.getSource() == secondClient){
                getChangingBalance("post", 20);
            } else if (e.getSource() == start){
                Server server = new Server();
                server.start();
                getChangingBalance("get",null);
            }

        } catch (Exception en){}
    }


    private Socket connectServer(){
        Socket socket = null;
        try{
            socket = new Socket("127.0.0.1", 9090);
        } catch (IOException e){}
        return socket;
    }

    private void getChangingBalance(String type, Integer value) throws IOException{
        new Thread(()->{
            try {
                DataOutputStream out;
                DataInputStream in;
                Socket socket = connectServer();
                out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(type + " " + (value == null ? "" : value));
                in = new DataInputStream(socket.getInputStream());
                balance.setText(in.readUTF());
            }catch (Exception e){
                if (e.getClass().equals(ConnectException.class)){
                    balance.setText("Server is down");
                }
            }
        }).start();
    }



    public static void main(String[] args) {
        new Main();
    }
}
