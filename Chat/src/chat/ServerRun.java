
package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ServerRun implements Runnable{
Socket socket;
ObjectInputStream clientInput;
ObjectOutputStream clientOutput;
    
static ArrayList<ObjectOutputStream> multipleClients = new ArrayList<ObjectOutputStream>();
static ArrayList<String> loggedMessages = new ArrayList<String>();


ServerRun(Socket socket){
    try{
    this.socket = socket;
    clientOutput = new ObjectOutputStream(socket.getOutputStream());
    clientInput = new ObjectInputStream(socket.getInputStream());
    multipleClients.add(clientOutput);

    
    
    }
    catch(IOException ex){
        ex.toString();
    }
}

public void send(String chat){
    try{
        for(ObjectOutputStream messages : multipleClients){
            messages.writeObject(chat);
            messages.flush();
            clientOutput.flush();
        }
    }
    catch(IOException ex){
        ex.toString();
    }
}
    
    @Override
    public void run() {
        System.out.println("SERVER THREAD");
        while(true){
            try{
                String chat;
                int decision = (int)clientInput.readObject();
                
                switch(decision){
                    case 1:
                        clientOutput.writeObject(Integer.toString(loggedMessages.size()));
                        clientOutput.flush();
                        break;
                    case 2:
                        chat = clientInput.readObject().toString();
                        loggedMessages.add(chat);
                        break;
                    case 3:
                        int whichClient = (int)clientInput.readObject();
                        send(loggedMessages.get(whichClient -1));
                        break;
                }
            }
            catch(ClassNotFoundException ex){
                ex.toString();
            }
            catch(IOException ex){
                ex.toString();
            }
        }



    }
    
}
