package askme;
/**
    *   Arbol binario para adivina un transporte
    *@author Elihu Alejandro Cruz Albores
    *@version 1.3
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AskMe extends Application {

    /**
     *  Cargador del aplicacion
     *@param stage : Tipo Stage ue recive el stage para instancia.
     */
    public void start(Stage stage) throws Exception {
        
        FXMLLoader myLoader = new FXMLLoader
        (getClass().getResource("/askme/FXMLDocument.fxml"));
        Parent root = (Parent) myLoader.load();
        FXMLDocumentController myController = myLoader.getController();
        Scene scene = new Scene(root);
        myController.setParent(stage);
        
        scene.getStylesheets().add
        (FXMLDocumentController.class.getResource("/utilerias/Gamestyle.css").toExternalForm());
        
        //stage.initStyle(StageStyle.TRANSPARENT);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //main :v
    public static void main(String[] args) {
        launch(args);
    }
}
