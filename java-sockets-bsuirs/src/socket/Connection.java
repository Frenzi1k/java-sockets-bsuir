package socket;

import account.Account;

import java.io.*;
import java.net.Socket;

public class Connection extends Thread{
    DataInputStream in= null;
    DataOutputStream out = null;
    private Socket socket = null;
    private int number;
    private static Account account = new Account(2000);

    public Connection(Socket socket, int number) throws IOException{
        this.socket = socket;
        this.number = number;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {

            String request = in.readUTF();
            if(request.contains("get")){
                out.writeUTF(account.toString());
            } else if(request.contains("post")){

                int value = Integer.parseInt(request.substring(5,request.length()));

                if( checkBalance(value)) {
                    account.changeBalance(value);
                    out.writeUTF(account.toString());
                } else{
                    out.writeUTF("Error. Low balance");
                }

            }

            System.out.println(this.number);

        }catch (Exception e){
            System.out.println(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e){
                System.out.println("Can't close a socket");
            }
        }
    }

    private boolean checkBalance(int value){
        return account.getBalance() + value >= 0;
    }
}


