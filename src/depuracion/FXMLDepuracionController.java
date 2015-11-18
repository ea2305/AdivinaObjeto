/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depuracion;

import estructuras.BinaryTree;
import estructuras.Nodo;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import static java.lang.System.out;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gerem
 */
public class FXMLDepuracionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Canvas canvasMain;
    
    private GraphicsContext Lienzo;
    private Stage stage;
    private Scene scene;
    private int index = 0;
    private int P_i = 0;//Punto inicial de dibujado
    
    private double radio = 10;//Radio de la figura
    private double Ancho = 0;//Ancho de canvas
    private double Alto = 0;//Alto de cavas
    
    private double movAlto = 10;//Movimietno de niveles para dibujado
    private double direccion = -1;//Direccion ..?
    
    //Arbol binario principal
    private static BinaryTree<String> myTree = new BinaryTree();
    
    @FXML
    private void close(ActionEvent event){
        stage.close();
    }
    
    //Generar arbol
    private void iniciarAnimacion(){
        preOrder();
        System.out.print("index : "+ index);

    }

    private void preOrder (Nodo<String> reco){
          if (reco != null)
          {
              this.index++;//Contador de elementos
              
              System.out.print("[" + reco.info + "], :: index ::" + index + " direccion " + direccion + "\n");
              
              //Dibujamos figura.
              dibujaOvalo(posicionX(index,direccion) + (direccion * radio),this.movAlto,this.radio*2,10);
              this.movAlto += 20;              
              preOrder (reco.izq);//Entra a nodo de la parte izq...

              this.direccion *= -1;
              
              preOrder (reco.der);//Entra al nodo de la parte der...
              this.movAlto -= 20;//Decrementamos el alto de la rama
              index--;
              this.direccion *= -1;
          }
      }

    public void preOrder (){

        preOrder (myTree.raiz);//Iniciamos busqueda de nodos.

    }
    
    private double posicionX(int Nivel,double direccion){
        
        if(Nivel == 1){
            return (this.Ancho/(Math.pow(2, (double) Nivel)));
        }else{
            
            double powAnterior = Math.pow(2,(double) Nivel - 1);
            double Ancho_I = this.Ancho / powAnterior;
            
            if(direccion > 0){
                return (Ancho_I + (this.Ancho/Math.pow(2, (double) Nivel)));
            }else{        
                return (this.Ancho/Math.pow(2, (double) Nivel));
            }
        }        
    }

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
    
    private void dibujaOvalo(double startX,double startY,double ancho,double alto){
        Lienzo.setFill(Color.AQUA);
        Lienzo.strokeOval(startX ,startY ,ancho,alto);
        Lienzo.fillOval(startX ,startY ,ancho,alto);
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Alto = canvasMain.getHeight();
        Ancho = canvasMain.getWidth();
        
        Lienzo = canvasMain.getGraphicsContext2D();//Asignamos contexto grafico
        Lienzo.setFill(Color.WHITE);//Color de fondo
        Lienzo.fillRect(0, 0, Ancho, Alto);
        
        loadObject();
        iniciarAnimacion();
    }    

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
