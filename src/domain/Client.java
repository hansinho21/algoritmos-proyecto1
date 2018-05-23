/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * Creacion de la clase cliente con sus respectivos atributos
 *
 * @author hvill y jeison
 */
public class Client {

    String id;
    String name;
    String lastName1;
    String lastName2;
    String email;
    String phone;
    String province;
    String adress;

    public Client(String id, String name, String lastName1, String lastName2, String email, String phone, String province, String adress) {
        this.id = id;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.email = email;
        this.phone = phone;
        this.province = province;
        this.adress = adress;
    }

    

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    

    @Override
    public String toString() {
        return  id+"\t"+name + "\t"+lastName1+"\t"+lastName2+"\t"+ email + "\t" + phone + "\t" + province + "\t" + adress;
    }
    

    

}
