/*
 * Arbol binario generico en java
 *@author Elihu Alejandro Cruz Albores
 *@version 1.2
 */
package estructuras;

import java.io.Serializable;

public class BinaryTree<T extends Comparable<T>> implements Serializable {

      public Nodo<T> raiz;
      public Nodo<T> actual;
      public Nodo<T> previo;
      public int index;
      public int contador;
      public int levels;

      /**
       *Constructor por defecto
       */
      public BinaryTree() {
          raiz=null;
      }

      /**
       *inserta informacion al arbol binario de manera balanceada
       *@param info : de tipo T generico con informacion para el nodo
       */
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
                  anterior = reco;//asignamos buffer de busqueda
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

      /**
       *Recorrido en preorder
       *@param reco : de tipo Nodo T generico
       */
      private void preOrder (Nodo<T> reco){
          if (reco != null)
          {
              this.contador++;
              this.index++;
              this.levels = (this.index > this.levels) ? this.index:this.levels;
              System.out.print(reco.info + " ");
              preOrder (reco.izq);
              preOrder (reco.der);
              this.index--;
          }
      }

      /**
       *Recorrido en preorder
       */
      public void preOrder (){
          this.contador = 0;
          this.index = 0;
          this.levels = 0;
          preOrder (raiz);
          System.out.println();
      }

      /**
       *Recorrido en inorder
       *@param reco : de tipo Nodo T generico
       */
      private void inOrder (Nodo<T> reco){
          if (reco != null)
          {
              inOrder (reco.izq);
              System.out.print(reco.info.toString() + " ");
              inOrder (reco.der);
          }
      }

      /**
       *Recorrido en inorder
       */
      public void inOrder (){
          if(raiz != null)
            System.out.print(raiz.info.toString() + " ");
          inOrder (raiz);
          System.out.println();
      }

      /**
       *Recorrido en postorder
       *@param reco : de tipo Nodo T generico
       */
      private void postOrder (Nodo<T> reco){
          if (reco != null)
          {
              postOrder (reco.izq);
              postOrder (reco.der);
              System.out.print(reco.info + " ");
          }
      }

      /**
       *Recorrido en postorder
       */
      public void postOrder (){
          postOrder (raiz);
          System.out.println();
      }

      /**
       * Realiza delplazamiento de puntero hacia la derecha
       */
      public void moverDerecha(){
          previo = actual;
          actual = actual.getDer();
      }

      /**
       * Realiza delplazamiento de puntero hacia la derecha
       */
      public void moverIzquierda(){
          previo = actual;
          actual = actual.getIzq();
      }

      /**
       *Reinicia puntero de desplazamiento del arbol y el anterior
       */
      public void iniciarPuntero(){
          actual = raiz;
          previo = actual;
      }

      /**
       * Verifica si el nodo esta vacio
       */
      public boolean isEmpty(){//Verifica si el nodo es nulo
          return(raiz == null);
      }

      /**
       *Limpia el arbol binario
       */
      public void clear() {
          this.raiz = null;
      }
}
