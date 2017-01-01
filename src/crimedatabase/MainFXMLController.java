/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crimedatabase;

import crimedatabase.CrimeDatabase;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Purusharth
 */
public class MainFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static Stage mainStage;
    @FXML Button btn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btn.setOnAction(e->{
           System.out.println("Logged In!");
            try {
                new MainFXMLController().startMenu();
                
            } catch (Exception ex) {
                System.out.println("Opened!!");
            }
        });
        
    }
    public void startMenu() throws Exception {
        //Stage stage=CrimeDatabase.mainStage;
      //  stage.close();
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
        stage=CrimeDatabase.mainStage;
        stage.close();
    }
    
}
