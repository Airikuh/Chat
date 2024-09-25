
package chat;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatFXMLController implements Initializable {
private Client client;
    @FXML
    private TextArea displayChat;
    @FXML
    private TextField chatInput;
    @FXML
    private Button sendButton;
    Socket socket;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int port = 8000;
        client = new Client(port, displayChat);
        
    }    

    @FXML
    private void handleButton(ActionEvent event) {
        if(event.getSource() == sendButton){
            String TextField = chatInput.getText();
            displayChat.appendText(TextField);
            chatInput.clear();
        }

        
        
        
    }
    
    
    }
    
    class Client{
        Socket socket;
        TextArea displayChat;
        private ClientRun client;
        
        
        public Client(int port, TextArea displayChat){
            this.displayChat = displayChat;
            new Thread(()->{
            try{
                socket = new Socket("localhost", port);
                
            }
            catch(IOException ex){
                ex.toString();
            }
            client = new ClientRun(this.displayChat, socket);
            new Thread(client).start();
            
        }).start();
        }
        
        public void send(String chat){
            client.send(chat);
        }
    }
    
    
