package socket;

import java.io.*;
import java.net.ServerSocket;

public class Server extends Thread {
    private int number = 0;
    private ServerSocket  listener= null;
    @Override
    public void run() {
        try {

            listener = new ServerSocket(9090);

                while (true) {
                    new Connection(listener.accept(), ++number).start();
                }

        }catch (IOException e){
            System.out.println("Listener: "+e.getMessage());
        }
    }

    @Override
    public void interrupt() {
        try{
            listener.close();
        }catch (IOException e){
            System.out.println("Closing socket error: " + e);
        }
        //super.interrupt();
    }


}
