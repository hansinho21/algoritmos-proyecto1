/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Product;
import exceptions.ListException;

/**
 * Especificaión de la interfaz List
 * @version  1.0
 * @author Hans Villalobos
 */
public interface List {

    public int getSize() throws ListException;
    public void cancel() throws ListException;
    public boolean isEmpty();
    public int getPosition(Object element) throws ListException;
    public void insert(Product element) throws ListException;
    public void ordererInsert(Object element) throws ListException;
    public void delete(Object element) throws ListException;
    public boolean isElement(Object element) throws ListException;
    public Object first() throws ListException;
    public Object last() throws ListException;
    public Object getNode(int position) throws ListException;

}
