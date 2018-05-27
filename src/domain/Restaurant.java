/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author hvill
 */
public class Restaurant {

    private int id;
    private String name;
    private ImageIcon image;
    private ArrayList<Location> locationList;
    private String url;

    public Restaurant(String name, String imageAdress, ArrayList<Location> locationList) throws IOException {
        this.name = name;
        this.image = new ImageIcon(ImageIO.read(getClass().getResource(imageAdress)));
        this.locationList = locationList;
        this.url= imageAdress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + url + ";" + locationList;
    }

    public Restaurant() {
    }
    
    

}
