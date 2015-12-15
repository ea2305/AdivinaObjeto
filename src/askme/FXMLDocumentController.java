/**
 *@author Elihu Alejandro Cruz Albores
 *@version 1.3
 */
package askme;

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

public class FXMLDocumentController implements Initializable {

    //Instacia de componentes
    @FXML private Label lbTitulo,lbPregunta;
    @FXML private Button btnSi,btnNo,btnInicio,btnEnviar,btnDepuracion;
    @FXML private HBox paneSeleccion,paneInicio,paneNuevaPregunta,paneEnviarDatos;
    @FXML private TextField txtNuevaPregunta;


    //Generamos nuevo arbol
    static private BinaryTree<String> myTree = new BinaryTree<String>();

    //Variables de control
    private boolean Direccion = true;
    private boolean Direccion_prev = true;
    private boolean Final = false;
    private boolean Error = false;
    private Stage parentStage;

    /**
     * Cambia la visilidad de paneles para mostrar la pregunta en caso de no saber
     * una hoja.
     */
    private void mostrarPregunta(){
        if(myTree.isEmpty() || Error)
            cargarPregunta();
        else
            this.lbPregunta.setText(myTree.actual.info);
    }

    /**
     *Carga los paneles y asigna texto de respuesta en caso de que sea una hoja
     */
    private void mostrarRespuesta(){
        this.lbTitulo.setText("Lo que estas pensando es... ?");
        this.lbPregunta.setText(myTree.actual.info);
    }

    /**
     *Carga los paneles y asigna texto de pregunta si no es una hoja
     */
    private void cargarPregunta(){
        if(myTree.raiz != null)//Primera insercion
            this.lbPregunta.setText("¿Escriba una pregunta que sea verdadera para lo \nque estas pensando, \npero falsa para un(a): " + myTree.actual.info + " ?");
        else//Insercion regular
            this.lbPregunta.setText("Escriba una pregunta que sea verdadera para lo \nque estas pensando");
        this.paneSeleccion.setVisible(false);
        this.paneNuevaPregunta.setVisible(true);
        this.paneEnviarDatos.setVisible(true);
        this.txtNuevaPregunta.clear();
    }

    /**
     * Carga la respuesta, en caso de no asertar a la pregunta
     *@param Msg : de tipo booleano que envia mensaje dependiendo el inicio.
     */
    private void cargarRespuesta(boolean Msg){
        if(Msg)
            this.lbPregunta.setText("¿Qué éra lo que estaba pensando?");
        else
            this.lbPregunta.setText("¿Podria dar un opción que se falsa para la pregunta que menciono?");
        this.txtNuevaPregunta.clear();
        this.paneSeleccion.setVisible(false);
        this.paneNuevaPregunta.setVisible(true);
        this.paneEnviarDatos.setVisible(true);
    }

    @FXML
    private void Insertar(ActionEvent event){

        Nodo<String> Entrada;//Generamos nodo nuevo
        Entrada = new Nodo<>(this.txtNuevaPregunta.getText());//Obtenemos y cargamos
                                         //Asignamos informacion en nuevo nodo

        //Verificamos que la raiz no sea nula para insertar en nodo hijo...
        if(myTree.isEmpty()){

            //Si es nulo, asignamos nuevo nodo
            myTree.raiz = Entrada;
            myTree.iniciarPuntero();

            //Mandamos Entrada llamar componentes de respuesta...
            cargarRespuesta(true);

        }else{//Si el componente era diferente de nulo...

            if(Direccion){//Verificamos la respuesta

                myTree.previo.izq = Entrada;

                if(myTree.raiz.der == null){
                    this.Direccion_prev = this.Direccion;
                    Direccion = false;//Rama derecha
                    cargarRespuesta(false);
                }else{
                    saveObject();
                    restartGame();
                }
            }
            else{
                if(Error){//Activado si la respuesta no fue correcta
                    Nodo<String> temp = myTree.actual;
                    Entrada.der = temp;
                    //asignar mi puntero al nodo actual, nodo de pregunta
                    if(this.Direccion_prev)
                        myTree.previo.izq = Entrada;
                    else
                        myTree.previo.der = Entrada;

                    //Nos posicionamos en el nodo correcto para insertar respuesta
                    myTree.previo = Entrada;
                    this.Direccion_prev = this.Direccion;
                    this.Direccion = true;

                    saveObject();//Salvamos el arbol binario
                    Error = false;//Normalizamos el estado de la respuesta equivoca
                    cargarRespuesta(true);//Solicitamos la entradad de una respuesta

                }else{
                    myTree.previo.der = Entrada; //Cargamos datos nuevos
                    saveObject();//Salvamos arbol binario
                    restartGame();//Reiniciamos juego.
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
    private void DesplazarIzq(ActionEvent event){//Equivalente Entrada boton SI

        this.Direccion_prev = this.Direccion;
        Direccion = true;

        if(Final){
            myTree.moverIzquierda();
            this.lbTitulo.setText("Gracias por jugar!\nPiense en un medio de transporte.\nInicie cuando este listo.");
            this.lbPregunta.setText("El medio de Transporte es: " + myTree.previo.info);
            this.paneSeleccion.setVisible(false);
            this.paneInicio.setVisible(true);
            myTree.iniciarPuntero();
            Final = false;

        }else{
            myTree.moverIzquierda();
            if(myTree.actual != null)
                if(myTree.actual.izq == null && myTree.actual.der == null){
                    Final = true;
                    mostrarRespuesta();
                }else
                    mostrarPregunta();
            else
                mostrarPregunta();
        }
    }

    @FXML
    private void DesplazarDer(ActionEvent event){//Equivalente Entrada boton NO

        this.Direccion_prev = this.Direccion;
        Direccion = false;//Asignamos la direccion de insercion

        if(Final){
            Error = true;
            mostrarPregunta();
        }else{
            myTree.moverDerecha();
            if(myTree.actual != null){
                if(myTree.actual.der == null && myTree.actual.der == null){
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

    @FXML
    private void close(){
        saveObject();
        System.exit(0);
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
        myController.setParent(parentStage);
        scene.getStylesheets().add//Carga de Css
        (FXMLDocumentController.class.getResource("/utilerias/Gamestyle.css").toExternalForm()); 
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void saveObject(){
      try{
        final FileOutputStream fo = new FileOutputStream("DatosArbol.bin");
          try (ObjectOutputStream oos = new ObjectOutputStream(fo)) {
              oos.writeObject(myTree);
              oos.flush();
          }
        System.out.println("\nArbol guardado exitosamente:::::\n");
      }
      catch (Exception ex){
        System.out.println("El arbol no pudo ser guardado.");
      }
    }

    public static void loadObject(){
      try{
        final FileInputStream fis = new FileInputStream("DatosArbol.bin");
          try (ObjectInputStream ois = new ObjectInputStream(fis)) {
              final Object deserializedObject = ois.readObject();
              out.println("Tipo de objeto " + deserializedObject.getClass().getName());
              if (deserializedObject instanceof BinaryTree)
                  myTree = (BinaryTree<String>) deserializedObject;
              else
                  out.println("No se esperaba " + deserializedObject.getClass().getName());   
          }

        if (myTree != null)
            System.out.println("\nLos datos se han recargado exitosamente:::::\n");
      }
      catch (Exception ex){
        System.out.println("\nNo se pudo cargar el archivo.\n");
      }
    }

    public void restartGame(){
        myTree.iniciarPuntero();//Prototipo de movimiento funcional.
        this.paneEnviarDatos.setVisible(false);
        this.paneNuevaPregunta.setVisible(false);
        this.lbPregunta.setText(null);
        this.lbTitulo.setText("Piense en un medio de transporte.\nPresione inciar cuando este listo");
        this.paneInicio.setVisible(true);
        Final = false;
    }

    public void initialize(URL url, ResourceBundle rb) {
        loadObject();//Cargamos arbol serializado.
        this.lbTitulo.setWrapText(true);
        this.lbPregunta.setWrapText(true);
        this.lbTitulo.setText("Piense en un medio de transporte\nPresione inciar cuando este listo");
        Final = false;
        myTree.iniciarPuntero();//Prototipo de movimiento funciona ...
    }
    
    //Enviamos stage a nueva ventanda en controlador
    public void setParent(Stage parentStage){
        this.parentStage = parentStage;
    }

}
