
package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.TextArea;


public class ClientRun implements Runnable{
    private int counter;
    private TextArea displayChat;
    private boolean sentCheck = false;
    
        
    private ObjectInputStream serverInput;
    private ObjectOutputStream serverOutput;
    
    public ClientRun(TextArea displayChat, Socket socket){
    counter = 0;
    this.displayChat = displayChat;
    
    try{
        serverInput = new ObjectInputStream(socket.getInputStream());
        serverOutput = new ObjectOutputStream(socket.getOutputStream());
        
    }
    catch(IOException ex){
        Platform.runLater(()->displayChat.appendText("CLIENT RUN CHECK " + ex.toString() + '\n'));
    }
    }     
    public int sizeCheck(){
        try{
            serverOutput.writeObject(1);
            serverOutput.flush();
        }
        catch(IOException ex){
            ex.toString();
        }
   
    try{
        counter = Integer.parseInt((String)serverInput.readObject());
    }
    catch(IOException ex){
        Platform.runLater(()->displayChat.appendText("ERROR IN SIZE CHECK " + ex.toString()));
    }
        catch(ClassNotFoundException ex){
            ex.toString();
        }
    return counter;
    }
    
    public void send(String chat){
        try{
            counter++;
            sentCheck = true;
            serverOutput.writeObject(2);
            serverOutput.flush();
            serverOutput.writeObject(chat);
            serverOutput.flush();
            displayChat.appendText("YOU: " + chat + '\n');
            
        }
        catch(IOException ex){
            ex.toString();
        }
    }
    
    
    public String receive(int whichClient){
        String clientMessage = "";
        
        try{
            serverOutput.writeObject(3);
            serverOutput.flush();
            serverOutput.writeObject(whichClient);
            serverOutput.flush();
            clientMessage = serverInput.readObject().toString();
            System.out.println("RECEIVE CHECK ERASE LATER");
            
        }
        catch(IOException ex){
            ex.toString();
        }
        catch(ClassNotFoundException ex){
            ex.toString();
        }
        return clientMessage;
    }
    
    @Override
    public void run() {
        String reader = "";
        
        while(true){
            if(sentCheck){
                try{
                    reader = (String)serverInput.readObject();
                    sentCheck = false;
                }
                catch(IOException ex){
                    ex.toString();
                }
                catch(ClassNotFoundException ex){
                    ex.toString();
                }
            }
            
            int arcievedChat = sizeCheck();
            if(arcievedChat > counter){
                reader = receive(arcievedChat);
                displayChat.appendText("FRIEND: " + reader + "\n");
                counter++;
            }
            else{
                try{
                    Thread.sleep(250);
                }
                catch(InterruptedException ex){
                    ex.toString();
                }
            }
        }




    }
    
}
