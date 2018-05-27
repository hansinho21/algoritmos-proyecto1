/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * Primer parte de la información de la orden que se va a almacenar en la tabla de administrador
 * Esta tiene los datos que el profe nos va a dar  en el archivo
 * Los demás datos que no están en el archivo del profe se encuentran en OrderPart2 y se van a asignar con
 * el idOrden como identificador
 * @author hvill
 */
public class OrderPart1 {
    
    private int idOrder;
    private int idClient;
    private int idRestaurant;
    private int idProduct;
    private int quantity;
    private double totalItems;

    public OrderPart1() {
    }

    public OrderPart1(int idOrder, int idClient, int idRestaurant, int idProduct, int quantity, double totalItems) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idRestaurant = idRestaurant;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.totalItems = totalItems;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(double totalItems) {
        this.totalItems = totalItems;
    }

    @Override
    public String toString() {
        return "OrderPart1{" + "idOrder=" + idOrder + ", idClient=" + idClient + ", idRestaurant=" + idRestaurant + ", idProduct=" + idProduct + ", quantity=" + quantity + ", totalItems=" + totalItems + '}';
    }
    
}
