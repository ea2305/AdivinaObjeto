/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package askme;

import creditos.FXMLCreditosController;
import depuracion.FXMLDepuracionController;
import estructuras.BinaryTree;
import estructuras.Nodo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.out;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



/**
 *
 * @author gerem
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Label lbTitulo,lbPregunta;
    @FXML private Button btnSi,btnNo,btnInicio,btnEnviar,btnDepuracion;
    @FXML private HBox paneSeleccion,paneInicio,paneNuevaPregunta,paneEnviarDatos;
    @FXML private TextField txtNuevaPregunta;
    
  
    //Generamos nuevo arbol
    static private BinaryTree<String> myTree = new BinaryTree<String>();
    private boolean Direccion = true;
    private boolean Direccion_prev = true;
    private boolean Final = false;
    private boolean Error = false;
    
    private void mostrarPregunta(){
        if(myTree.isEmpty() || Error){
            
            System.out.println("\nNodo acutal es nulo <<::");
            cargarPregunta();

        }    
        else
            if(myTree.actual == null)
                System.out.println(">>>>>>Vacio");
            else{
                System.out.println(">>>>>>IMPresion<<<<<<");   
                this.lbPregunta.setText(myTree.actual.info);
            }
    }
    
    private void mostrarRespuesta(){
        this.lbTitulo.setText("Lo que estas pensando es... ?");
        this.lbPregunta.setText(myTree.actual.info);
    }
    
    private void cargarPregunta(){
        this.lbPregunta.setText("¿Qué pregunta tendria que hacer para "
                                  + "saber que es?");
        this.paneSeleccion.setVisible(false);
        this.paneNuevaPregunta.setVisible(true);
        this.paneEnviarDatos.setVisible(true);
    }

    private void cargarRespuesta(){ 
        this.lbPregunta.setText("¿Qué éra lo que estaba pensando?");
        this.paneSeleccion.setVisible(false);
        this.paneNuevaPregunta.setVisible(true);
        this.paneEnviarDatos.setVisible(true);
    }
    
    @FXML
    private void Insertar(ActionEvent event){
        
        Nodo<String> a;//Generamos nodo nuevo
        a = new Nodo<>(this.txtNuevaPregunta.getText());//Obtenemos y cargamos
                                         //Asignamos informacion en nuevo nodo
        
        //Verificamos que la raiz no sea nula para insertar en nodo hijo...
        if(myTree.isEmpty()){
            
            //Si es nulo, asignamos nuevo nodo
            System.out.println("Arbol vacio ::");
            myTree.raiz = a;
            myTree.iniciarPuntero();
            
            System.out.println("se asigno a raiz : " + myTree.raiz.info);
            
            //Mandamos a llamar componentes de respuesta...
            cargarRespuesta();
            
        }else{//Si el componente era diferente de nulo...
            
            if(Direccion){

                System.out.println("Asignación a la izquierda ::");
                myTree.previo.izq = a;
                if(Final)
                    System.out.println("Nodo en derecha es ");
                if(myTree.raiz.der == null){
                    this.Direccion_prev = this.Direccion;
                    Direccion = false;
                    cargarRespuesta();
                }else{
                    saveObject();
                    restartGame();
                }    
            }    
            else{
                if(Error){
                    Nodo<String> temp = myTree.actual;
                    a.der = temp;
                    //asignar mi puntero al nodo actual, nodo de pregunta
                    if(this.Direccion_prev)
                        myTree.previo.izq = a;
                    else
                        myTree.previo.der = a;

                    myTree.previo = a;
                    this.Direccion_prev = this.Direccion;
                    this.Direccion = true;
                    saveObject();
                    Error = false;
                    cargarRespuesta();
                }else{
                    System.out.println("Asignación a la derecha ::");
                    System.out.println("Contenido del nodo : " + myTree.actual);
                    myTree.previo.der = a;
                    saveObject();
                    restartGame();
                }
            }    
        }
    }
    @FXML
    private void InicioJuego(ActionEvent event){
        this.lbPregunta.setText(null);
        this.lbTitulo.setText("El transporte...");
        this.paneInicio.setVisible(false);
        this.paneSeleccion.setVisible(true);
        mostrarPregunta();
    }
    
    @FXML
    private void DesplazarIzq(ActionEvent event){//Equivalente a boton SI
        System.out.println("\n::>nodo move to left: " + myTree.actual);
        this.Direccion_prev = this.Direccion;
        Direccion = true;        
        
        if(Final){
            myTree.moverIzquierda();
            this.lbTitulo.setText("Gracias por jugar!!");
            this.lbPregunta.setText("El medio de Transporte es: " +
                                    myTree.previo.info);
            this.paneSeleccion.setVisible(false);
            this.paneInicio.setVisible(true);
            myTree.iniciarPuntero();
            Final = false;

        }else{
            myTree.moverIzquierda();
            if(myTree.actual != null){
                if(myTree.actual.izq == null && myTree.actual.der == null){
                    Final = true;
                    System.out.println("dentro de la izquierda::");
                    mostrarRespuesta();
                }else{
                    mostrarPregunta();
                }
            }else{
                mostrarPregunta();    
            }
        }
    }
    
    @FXML
    private void DesplazarDer(ActionEvent event){//Equivalente a boton NO
        System.out.println("\n::>nodo move to right: " + myTree.actual);
        this.Direccion_prev = this.Direccion;
        Direccion = false;//Asignamos la direccion de insercion
        
        if(Final){
            System.out.println("final derecho:>>");
            Error = true;
            mostrarPregunta();
        }else{
            myTree.moverDerecha();
            if(myTree.actual != null){
                if(myTree.actual.der == null && myTree.actual.der == null){
                    System.out.println("dentro de la derecha::");
                    Final = true;
                    mostrarRespuesta();
                }else{
                    mostrarPregunta();
                }        
            }else{
                mostrarPregunta();
            }
        }
    }
   
    public static void saveObject(){

      // Serialize the List
      try
      {
        final FileOutputStream fo = new FileOutputStream("DatosArbol.bin");
          try (ObjectOutputStream oos = new ObjectOutputStream(fo)) {
              oos.writeObject(myTree);
              oos.flush();
          }
        System.out.println("\nArbol guardado exitosamente:::::\n");
      }
      catch (Exception ex)
      {
        // write stack trace to standard error
        ex.printStackTrace();
      }
  }

    public static void loadObject(){
      // Deserialize
      try
      {
        final FileInputStream fis = new FileInputStream("DatosArbol.bin");
          try (ObjectInputStream ois = new ObjectInputStream(fis)) {
              final Object deserializedObject = ois.readObject();
              out.println("Tipo de objeto " + deserializedObject.getClass().getName());
              if (deserializedObject instanceof BinaryTree)
              {
                  myTree = (BinaryTree<String>) deserializedObject;
              }
              else
              {
                  out.println("No se esperaba " + deserializedObject.getClass().getName());
              }   }

      if (myTree != null)
      {
        System.out.println("\nLos datos se han recargado exitosamente:::::\n");
      }
      }
      catch (Exception ex)
      {
      //
        //ex.printStackTrace();
        System.out.println("\nNo se pudo cargar el archivo.\n");  
      }
  }

    public void restartGame(){
        
        myTree.iniciarPuntero();//Prototipo de movimiento funciona ...
        this.paneEnviarDatos.setVisible(false);
        this.paneNuevaPregunta.setVisible(false);
        this.lbPregunta.setText(null);
        this.lbTitulo.setText("Desea iniciar?");
        this.paneInicio.setVisible(true);
        Final = false;
    }

    @FXML
    private void close(){
        saveObject();
        System.exit(0);
    }
    
    @FXML
    private void creditos(ActionEvent event) throws IOException{

        Stage stage = new Stage();
        FXMLLoader myLoader = new FXMLLoader 
        (getClass().getResource("/askme/FXMLCreditos.fxml"));
        Parent root = (Parent) myLoader.load();
        FXMLCreditosController myController = myLoader.getController();                
        Scene scene = new Scene(root);
        myController.setStage(stage);
        scene.getStylesheets().add
        (FXMLDocumentController.class.getResource("/utilerias/Gamestyle.css").toExternalForm());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();     
    }

    @FXML
    private void Depuracion(ActionEvent event) throws IOException{
        
        saveObject();//Salvamos arbol
        Stage stage = new Stage();
        FXMLLoader myLoader = new FXMLLoader 
        (getClass().getResource("/depuracion/FXMLDepuracion.fxml"));
        Parent root = (Parent) myLoader.load();
        FXMLDepuracionController myController = myLoader.getController();                
        Scene scene = new Scene(root);
        myController.setStage(stage);
        scene.getStylesheets().add
        (FXMLDocumentController.class.getResource("/utilerias/Gamestyle.css").toExternalForm());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.print(myTree.isEmpty() + "\n");//Vreificamos que no este vacio
        loadObject();
        this.lbTitulo.setText("Desea iniciar?");
        Final = false;
        myTree.iniciarPuntero();//Prototipo de movimiento funciona ...

    }    
    
}
