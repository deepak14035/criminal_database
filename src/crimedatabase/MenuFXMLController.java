/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crimedatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Purusharth
 */
public class MenuFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static String currentOp="";
    @FXML Button addBtn;
    @FXML Button delBtn;
    @FXML Button modBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addBtn.setOnAction(e->{
            currentOp="add";
            try {
                new MenuFXMLController().startMenu();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        delBtn.setOnAction(e->{
            currentOp="delete";
            try {
                new MenuFXMLController().startMenu();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        modBtn.setOnAction(e->{
            currentOp="modify";
            try {
                new MenuFXMLController().startMenu();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
    }
 public void startMenu() throws Exception {
        //Stage stage=CrimeDatabase.mainStage;
      //  stage.close();
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("editFXML.fxml"));
        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
