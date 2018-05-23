/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LinkedStack;

import exceptions.StackException;
import logic.Node;
import logic.Stack;

/**
 *
 * @author hvill
 */
public class LinkedStack implements Stack {

    private Node top;
    private int cont;

    public LinkedStack() {
        this.top = null;
        this.cont = 0;
    }

    /**
     * Valida si la pila está vacía.
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return this.top == null || cont == 0;
    }

    /**
     * Retorna el tamaño de la pila.
     *
     * @return int
     */
    @Override
    public int getSize() {
        return this.cont;
    }

    /**
     * Cancela la pila
     *
     * @throws StackException
     */
    @Override
    public void anular() throws StackException {
        this.top = null;
    }

    /**
     * Añade un elemento a la pila
     *
     * @param element
     * @throws StackException
     */
    @Override
    public void push(Object element) throws StackException {
        Node newNode = new Node(element);
        if (isEmpty()) {
            this.top = newNode;
        } else {
            newNode.next = this.top;
            this.top = newNode;
        }
        this.cont++;
    }

    /**
     * Consulta el valor del primer elemento sin retirarlo de la pila
     *
     * @return Object
     */
    @Override
    public Object peek() {
        return this.top.element;
    }

    @Override
    public Object pop() throws StackException {
        if (isEmpty()) {
            throw new StackException("La pila está vacía");
        } else {
            Object tempElement = this.top.element;
            this.top = this.top.next;
            this.cont--;
            return tempElement;
        }
    }

    /**
     *
     * @param element
     * @return
     * @throws StackException
     */
    @Override
    public int search(Object element) throws StackException {
        if (isEmpty()) {
            throw new StackException("La pila está vacía");
        } else {
            LinkedStack auxStack = new LinkedStack();
            int auxCont = -1;
            while(!this.isEmpty()){
                if(this.peek().equals(element)){
                    auxCont = this.getSize();
                } 
                auxStack.push(this.pop());
            }
            while(!auxStack.isEmpty()){
                this.push(auxStack.pop());
            }
            return auxCont;
        }
    }

}