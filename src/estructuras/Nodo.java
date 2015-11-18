/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.io.Serializable;

/**
 *
 * @author gerem
*/
public class Nodo<T>implements Serializable{
    public T info;
    public Nodo<T> izq, der;
    
    public Nodo(){
        this.info = null;
    }
    
    public Nodo(T info){
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public Nodo<T> getIzq() {
        return izq;
    }

    public Nodo<T> getDer() {
        return der;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public void setIzq(Nodo<T> izq) {
        this.izq = izq;
    }

    public void setDer(Nodo<T> der) {
        this.der = der;
    }
  
  
}
