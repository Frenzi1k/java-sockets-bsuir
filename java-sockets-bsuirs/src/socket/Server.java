package socket;

import java.io.*;
import java.net.ServerSocket;

public class Server extends Thread {
    private int number = 0;
    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(9090);
            try {
                while (true) {
                    new Connection(listener.accept(), ++number).start();
                }
            } finally {
                listener.close();
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }

}
