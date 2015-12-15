/*
 *    Clase nodo de arbol binario
 *@param Elihu Alejandro Cruz Albores
 *version 1.2
 */
package estructuras;

import java.io.Serializable;

public class Nodo<T>implements Serializable{
    public T info;
    public Nodo<T> izq, der;

    /**
     *Contructor por defecto
     */
    public Nodo(){
        this.info = null;
    }

    /**
     *  Constructor por defecto que recive como parametro la informacion a
     *  contener
     */
    public Nodo(T info){
        this.info = info;
    }

    /**
     *Obtencion de la parte de informacion del nodo
     *@return info de tipo T generico
     */
    public T getInfo() {
        return info;
    }

    /**
     *Obtine el nodo de la rama izquierda del nodo actual
     *@return izq de tipo nodo T
     */
    public Nodo<T> getIzq() {
        return izq;
    }

    /**
     *Obtine el nodo de la rama derecha del nodo actual
     *@return der de tipo nodo T
     */
    public Nodo<T> getDer() {
        return der;
    }

    /**
     *Coloca informacion al nodo actual.
     *@param info de Tipo T generico
     */
    public void setInfo(T info) {
        this.info = info;
    }

    /**
     * Coloca un nuevo nodo al nodo actual en su parte izquierda.
     *@param izq de Tipo T generico
     */
    public void setIzq(Nodo<T> izq) {
        this.izq = izq;
    }

    /**
     * Coloca un nuevo nodo al nodo actual en su parte derecha.
     *@param der de Tipo T generico
     */
    public void setDer(Nodo<T> der) {
        this.der = der;
    }


}
