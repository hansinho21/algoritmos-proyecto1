/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import exceptions.ListException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import logic.Data;

/**
 *
 * @author hvill
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private ImageIcon image;
    private Data data;
    private String restaurant;
    private int type;//1-food, 2-drink, 3-dessert, 4-other
    String url;


    public Product(int id,String name, double price, String imageAdress, String restaurant, int type) throws IOException {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = new ImageIcon(ImageIO.read(getClass().getResource(imageAdress)));
        this.restaurant = restaurant;
        this.type = type;
        this.url = imageAdress;
    }

    public Product(String imageAdress) throws IOException {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = new ImageIcon(ImageIO.read(getClass().getResource(imageAdress)));
        this.restaurant = restaurant;
        this.type = type;
        this.url = imageAdress;
    }
    
    

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

    @Override
    public String toString() {
        return id + ";" + name + ";" + price + ";" +  url+ ";" + restaurant +";" + type;
    }

}
