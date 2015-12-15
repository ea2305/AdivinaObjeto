/**
 *Dibujado de arbol binario con logica preOrder.
 *@author Elihu Alejandro Cruz Albores
 *@version 1.3
 */
package depuracion;

import estructuras.*;
import askme.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.StageStyle;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static java.lang.System.out;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class FXMLDepuracionController implements Initializable {

    //Componenetes
    @FXML private Canvas canvasMain;

    //Variables para dibujado
    private GraphicsContext Lienzo;
    private Stage stage;
    private Stage parentStage;
    private Scene scene;

    //Contadores y referencias
    private int contador = 0;
    private int index = 0;
    private int top = 1;
    private double P_i = 0;//Punto inicial de dibujado

    //Dimensiones
    private double radio = 5;//Radio de la figura
    private double r_alto = 5;//posicion de
    private double Ancho = 0;//Ancho de canvas
    private double Alto = 0;//Alto de cavas
    private double movAlto = 20;//Movimietno de niveles para dibujado

    //Arbol binario principal
    private static BinaryTree<String> myTree = new BinaryTree();

    //Almacena las posiciones previas del nodo padre al dibujar
    private static Stack myStack = new Stack();

    @FXML
    private void close(ActionEvent event){
        stage.close();
    }

    //Generar arbol
    private void iniciarAnimacion(){
        preOrder();
    }

    /**
     *Recorrido del arbol preOrder modificado para dibujar.
     *@param reco : de Tipo Nodo T generico
     */
    private void preOrder (Nodo<String> reco){
          if (reco != null){

              this.contador++;
              this.index++;//Contador de elementos
              this.top = (index >= top) ? index:top;//Selector de nivel

              //Dibujamos lineas
              dibujaLineas(reco,index);
              //Dibuja figura
              dibujaOvalo(posicionX(index) - (radio),
                          this.movAlto,
                          this.radio*2,
                          this.radio*2);

              this.movAlto += this.r_alto;//incremento en Y

              preOrder (reco.izq);//Entra a nodo de la parte izq...

              this.P_i = (double) myStack.pop();//Obtencion de posicion X previa

              preOrder (reco.der);//Entra al nodo de la parte der...
              this.movAlto -= this.r_alto;//Decrementamos el alto de la rama

              index--;
          }
      }

    /**
     *Inicia recorrido preorder para dibujado.
     */
    public void preOrder (){
        preOrder (myTree.raiz);//Iniciamos busqueda de nodos.
        dibujaDatos(this.Ancho - 100,20);
    }

    /**
     * Obtiene la informacion para dibujado en X
     *@param Nivel de tipo entero
     *@return posicion adecuada en X tipo double
     */
    private double posicionX(int Nivel){

        double myPow = Math.pow(2, (double) Nivel);
        myStack.push(new Double(this.P_i + this.Ancho/myPow));

        if(Nivel == 1){
            return (this.Ancho/myPow);
        }else{
            return (this.P_i + this.Ancho/myPow);
        }
    }

    /**
     *Carga objeto previamente serializado
     */
    public static void loadObject(){
      try{
        final FileInputStream fis = new FileInputStream("DatosArbol.bin");
          try (ObjectInputStream ois = new ObjectInputStream(fis)) {
              final Object deserializedObject = ois.readObject();
              out.println("Tipo de objeto " + deserializedObject.getClass().getName());
              if (deserializedObject instanceof BinaryTree)
              {
                  myTree = (BinaryTree<String>) deserializedObject;
              }
              else{
                  out.println("No se esperaba " + deserializedObject.getClass().getName());
              }   }

      if (myTree != null)
      {
        System.out.println("\nLos datos se han recargado exitosamente:::::\n");
      }
      }
      catch (Exception ex){
        System.out.println("\nNo se pudo cargar el archivo.\n");
      }
    }

    /**
     * Dibuja lineas que conectan a los nodos padres con hijos
     *@param node : Tipo nodo T en referencia al arbol
     *@param Nivel : tipo entero indicando el nivel del puntero
     */
    private void dibujaLineas(Nodo<String> node, int Nivel){
        if(node != null){//Detecta el caso en que el nodo sea nulo...
            if(!(node.izq == null && node.der == null)){//Verifica si es hoja

                double myPow = Math.pow(2, (double) Nivel);//Obtenemos proporcion
                double size = this.P_i + this.Ancho / myPow;

                double nextPow = Math.pow(2, (double) Nivel + 1);
                double nextSize = this.Ancho / nextPow;

                //Dibujado en contexto grafico para canvas
                Lienzo.setStroke(Color.WHITE);
                Lienzo.strokeLine(size,this.movAlto + radio,size - nextSize,this.movAlto + this.r_alto);
                Lienzo.strokeLine(size,this.movAlto + radio,size + nextSize,this.movAlto + this.r_alto);

            }
        }
    }

    /**
     * Dibuja circulos u ovalos para referenciar a los nodos
     *@param startX : double valor inical de x
     *@param startY : double valor inicial de Y
     *@param ancho : double valor del ancho de figura
     *@param alto : double valor del alto para figura
     */
    private void dibujaOvalo(double startX,double startY,double ancho,double alto){
        
        Lienzo.setStroke(Color.WHITE);
        Lienzo.strokeOval(startX ,startY ,ancho,alto);
        Lienzo.setFill(Color.rgb(255,30,75,.5));
        Lienzo.fillOval(startX ,startY ,ancho,alto);
        Lienzo.setFill(Color.BLUE);
        String m = "" + (this.contador - 1);
        Lienzo.fillText(m,startX + this.radio,startY,ancho + 10);
    }

    /**
     * Dibuja datos para referenciar a los nodos
     *@param startX : double valor inical de x
     *@param startY : double valor inicial de Y
     *@param ancho : double valor del ancho de figura
     *@param alto : double valor del alto para figura
     */
    private void dibujaDatos(double startX, double startY){
        Lienzo.setFill(Color.BLACK);
        String m = "Niveles: " + this.top;
        Lienzo.fillText(m,startX,startY,70);
        m = "Nodos: " + this.contador;
        Lienzo.fillText(m,startX,startY + 20,70);
    }

    private void getRadio(){
        
        myTree.preOrder();
        double myLevels = Math.pow(2,(double)myTree.levels - 1);
        if(myLevels >= 0){
            
            this.radio = (this.Ancho / (2 * myLevels)) - 1;
            this.r_alto = this.Alto / (double) myTree.levels; 
        }else{
            this.radio = 50;
            this.r_alto = 100;
        }

    }

    public void initialize(URL url, ResourceBundle rb) {

        Alto = canvasMain.getHeight();
        Ancho = canvasMain.getWidth();

        Lienzo = canvasMain.getGraphicsContext2D();//Asignamos contexto grafico
        Lienzo.setFill(Color.TRANSPARENT);//Color de fondo
        Lienzo.fillRect(0, 0, Ancho, Alto);

        loadObject();
        getRadio();
        iniciarAnimacion();
    }

    protected void openMain()throws Exception {
        Stage stage = new Stage();
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
    
    @FXML 
    private void eraseTree(ActionEvent event){
      try{
        final FileOutputStream fo = new FileOutputStream("DatosArbol.bin");
          try (ObjectOutputStream oos = new ObjectOutputStream(fo)) {
              oos.writeObject(new BinaryTree<String>());
              oos.flush();
          }
        System.out.println("\nArbol borrado exitosamente:::::\n");
        reload();
        this.parentStage.close();//Cerramos main
        openMain();//Aperturamos nuevamente el main
        
      }
      catch (Exception ex)
      {
        // write stack trace to standard error
        ex.printStackTrace();
      }
    }
    
    @FXML
    private void reloadTree(ActionEvent event){
        reload();
    } 
    
    protected void reload(){
        
        Alto = canvasMain.getHeight();
        Ancho = canvasMain.getWidth();
        
        Lienzo = canvasMain.getGraphicsContext2D();//Asignamos contexto grafico
        
        Lienzo.setFill(Color.TRANSPARENT);//Color de fondo
        Lienzo.clearRect(0, 0, Ancho, Alto);
        //Contadores y referencias
        contador = 0;
        index = 0;
        top = 1;
        P_i = 0;//Punto inicial de dibujado
        
        //Dimensiones
        radio = 5;//Radio de la figura
        r_alto = 5;//posicion de
        movAlto = 20;//Movimietno de niveles para dibujado
        
        //Arbol binario principal
        myTree = new BinaryTree();
        
        //Almacena las posiciones previas del nodo padre al dibujar
        myStack = new Stack();
        loadObject();
        getRadio();
        iniciarAnimacion();
    }

    /**
     *Carga el stage de la instancia previa
     *@param stage : Stage de instancia previa
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setParent(Stage parentStage){
        this.parentStage = parentStage;
    }
}
