/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

/**
 *
 * @author gerem
 */
import java.io.Serializable;

public class BinaryTree<T extends Comparable<T>> implements Serializable {
        
      public Nodo<T> raiz;
      public Nodo<T> actual;
      public Nodo<T> previo;
      public int index;

      public BinaryTree() {
          raiz=null;
      }

      public void insertar (T info){
          Nodo<T> nuevo;//Generamos un nuevo nodo
          nuevo = new Nodo<T> ();
          
          nuevo.setInfo(info);//Asignamos informacion recivida a la rama
          
          nuevo.setIzq(null);//inicializamos puntero de rama izq. en nulo
          nuevo.setDer(null);//inicializamos puntero de rama der. en nulo
          
          if (raiz == null)//Verificamos que la raiz sea nula para asinar a esta
              raiz = nuevo;//Como raiz
          
          else//Si no procedemos a buscar.
          {
              Nodo<T> anterior = null, reco; //reco se utiliza en toso el 
                                             //programa para recorrer el arbol
              reco = raiz;
              while (reco != null)     // hasta encontrar un lugar vacio, respetando el orden
              {
                  anterior = reco;//asgnamos buffer de busqueda
                  //if (info < reco.info)
                  if (info.compareTo(reco.info) < 0)
                      reco = reco.getIzq();
                  else
                      reco = reco.getDer();
              }
              //if (info < anterior.info)     // de acuerdo a si es mayor o menor se coloca a la izq o der
              if (info.compareTo(anterior.getInfo()) < 0)
                  anterior.setIzq(nuevo);
              else
                  anterior.setDer(nuevo);
          }
      }

      private void preOrder (Nodo<T> reco){
          if (reco != null)
          {
              this.index++;
              System.out.print(reco.info + " ");
              preOrder (reco.izq);
              preOrder (reco.der);
          }
      }

      public void preOrder (){
          preOrder (raiz);
          System.out.println();
      }

      private void inOrder (Nodo<T> reco){
          if (reco != null)
          {
              inOrder (reco.izq);
              System.out.print(reco.info.toString() + " ");
              inOrder (reco.der);
          }
      }

      public void inOrder (){
          if(raiz != null)
            System.out.print(raiz.info.toString() + " ");
          inOrder (raiz);
          System.out.println();
      }

      private void postOrder (Nodo<T> reco){
          if (reco != null)
          {
              postOrder (reco.izq);
              postOrder (reco.der);
              System.out.print(reco.info + " ");
          }
      }

      public void postOrder (){
          postOrder (raiz);
          System.out.println();
      }

      public void moverDerecha(){
          previo = actual;
          actual = actual.getDer();
      }
      
      public void moverIzquierda(){
          previo = actual;
          actual = actual.getIzq();
      }
      
      public void iniciarPuntero(){
          actual = raiz;
          previo = actual;
      }
      
      public boolean isEmpty(){//Verifica si el nodo es nulo
          return(raiz == null);
      }

      public void clear() {
          this.raiz = null;
      }
}
