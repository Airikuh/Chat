//Erika Valle-Baird
//CIS 296
//Programming Project 4
package chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Chat extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ChatFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Chat With Friends");
        stage.setScene(scene);
        stage.show();


    }

    
    
    
    
    public static void main(String[] args) {
        launch(args);
    }


    
}
