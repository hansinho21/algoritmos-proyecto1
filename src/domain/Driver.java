/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author hvill
 */
public class Driver {
    
    
    private int id;
    private String name;
    private String lastName1;
    private String lastName2;
    private String age;
    private String type;
    private String phone;
    private String plate;
    private String cedula;

    public Driver(String id, String name, String lastName1, String lastName2, String age, 
            String type, String phone, String plate, String cedula) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.age = age;
        this.type = type;
        this.phone = phone;
        this.plate = plate;
        this.cedula = cedula;
    }
    
//    public Driver(String id, String name) throws IOException {
//        this.id = Integer.parseInt(id);
//        this.name = name;
//        
//    }

    public Driver() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + lastName1 + "\t" + lastName2 + "\t" + age + "\t" +
                type + "\t" + phone + "\t" + plate + "\t" + cedula;
    }


    
    
}
