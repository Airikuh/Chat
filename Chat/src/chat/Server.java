
package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    ServerSocket server;
    
    public Server(int port){
        new Thread(()->{
        try{
            server = new ServerSocket(port);
            System.out.println("Initializing Server...");
            while(true){
                try{
                    Socket socket = server.accept();
                    System.out.println("Connect to " + socket);
                    new Thread(new ServerRun(socket)).start();
                }
                catch(IOException ex){
                    ex.toString();
                    
            }
            }
        } catch(IOException ex){
            ex.toString();
        }
  
    }).start();
    }
    
    
}
