/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.ListaCircularDoblementeenlazada;

import domain.Product;
import exceptions.ListException;
import logic.List;
import logic.NodeList;

/**
 *
 * @author jeison
 */
public class ListaCircularDoblementeEnlazada implements List {

    private NodeList inicio;
    private NodeList fin;

    public ListaCircularDoblementeEnlazada() {
        this.inicio = this.fin = null;
    }

    @Override
    public int getSize() throws ListException {
        if (isEmpty()) {
            throw new ListException("La lista no existe");
        }
        NodeList auxNode = this.inicio;
        int cont = 1;
        while (auxNode != this.fin) {
            auxNode = auxNode.next;
            cont++;
        }
        return cont;
    }

    @Override
    public void cancel() throws ListException {
        this.inicio = this.fin = null;
    }

    @Override
    public boolean isEmpty() {
        return this.inicio == null;
    }

    @Override
    public int getPosition(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("La lista no existe");
        }
        NodeList auxNode = this.inicio;
        int pos = 1;
        while (auxNode != this.fin) {
            if (auxNode.element.equals(element)) {
                return pos;
            }
            auxNode = auxNode.next;
            pos++;
        }
        if (auxNode.element.equals(element)) {
            return pos;
        }
        return -1;
    }

    @Override
    public void insert(Product element) throws ListException {
        NodeList dNode = new NodeList(element);

        if (inicio == null) {
            inicio = dNode;
            //previous and next were null - now 'root'
            inicio.next = inicio;
            inicio.previous = inicio;
        } else {
            NodeList aux = inicio;
            //root was equal to null - now it's root.
            while (aux.next != inicio) {

                aux = aux.next;

            }

            //New
            aux.next = dNode;
            dNode.next = inicio;
            dNode.previous = aux;
            inicio.previous = dNode;
        }
    }

    @Override
    public void ordererInsert(Object element) throws ListException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("La lista no existe");
        }
        if (this.inicio.element.equals(element)) {
            this.inicio = this.inicio.next;
        }
        NodeList auxNode = this.inicio.next;
        NodeList previousNode = this.inicio;
        while (auxNode != this.fin) {
            if (auxNode.element.equals(element)) {
                previousNode.next = auxNode.next;
            } else {
                previousNode = auxNode;
            }
            auxNode = auxNode.next;

        }

        if (auxNode.element.equals(element)) {
            previousNode.next = auxNode.next;
        }

        if (auxNode == this.fin) {
            this.fin = previousNode;
        }

        this.fin.next = this.inicio;
    }

    @Override
    public boolean isElement(Object element) throws ListException {
        NodeList auxNode = this.inicio;
        while (auxNode != this.fin) {
            if (auxNode.element.equals(element)) {
                return true;
            }
            auxNode = auxNode.next;
        }
        return false;
    }

    @Override
    public Object first() throws ListException {
        if (isEmpty()) {
            throw new ListException("La lista no existe");
        }
        return inicio.element;
    }

    @Override
    public Object last() throws ListException {
        if (isEmpty()) {
            throw new ListException("La lista no existe");
        }

        return getNode(getSize()).element;
    }

    @Override
    public NodeList getNode(int position) throws ListException {
        if (isEmpty()) {
            throw new ListException("La lista no existe");
        }
        NodeList auxNode = inicio;
        int pos = 1;
        while (auxNode != fin) {
            if (pos == position) {
                return auxNode;
            }
            auxNode = auxNode.next;
            pos++;

        }
        if (pos == position) {
            return auxNode;
        }
        return null;
    }

}
