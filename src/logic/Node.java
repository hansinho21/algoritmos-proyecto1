/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * Almacena un valor y un puntero hacia el siguiente elemento
 * @version 1.0
 * @author Hans Villalobos
 */
public class Node {

    public Object element;
    public Node next;
    public Node previous;

    public Node(Object element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

}
