/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gerem
 */
public class FXMLCreditosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Stage stage;
    Scene scene;
    
    @FXML
    private void close(ActionEvent event){
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setStage(Stage stage){
        this.stage = stage;
    }

    void setScene(Scene scene) {
        this.scene = scene;
    }
}
