/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import exceptions.StackException;

/**
 *
 * @author hvill
 */
public interface Stack {
    
    public boolean isEmpty();
    public int getSize();
    public void anular() throws StackException;
    public void push(Object element) throws StackException; //Ingresa un elemento a la pila
    public Object peek(); // o top(); -> Consulta el inicio de la pila
    public Object pop() throws StackException; //Saca de la pila el ultimo elemento ingresado
    public int search(Object element) throws StackException;
}
