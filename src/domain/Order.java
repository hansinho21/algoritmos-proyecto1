/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author hvill
 */
public class Order {

    String client;
    int orderNumber;
    String agent;
    String date;
    double price;
    String province;
    String adress;
    String driver;

    public Order(String client, int orderNumber, String agent, String date, double price, String province, String adress, String driver) {
        this.client = client;
        this.orderNumber = orderNumber;
        this.agent = agent;
        this.date = date;
        this.price = price;
        this.province = province;
        this.adress = adress;
        this.driver = driver;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Order{" + "client=" + client + ", orderNumber=" + orderNumber + ", agent=" + agent + ", date=" + date + ", price=" + price + ", province=" + province + ", adress=" + adress + ", driver=" + driver + '}';
    }

}
