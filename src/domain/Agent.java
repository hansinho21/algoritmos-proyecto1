/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Creacion de la clase Agente con sus respectivos atributos
 *
 * @author hvill y jeison
 */
public class Agent {

    private String Id;
    private String name;
    private String email;
    private String user;
    private String password;
    private String code;
    
    private boolean active;

    public Agent(String name, String email, String user, String password, String code) {
        this.name = name;
        this.email = email;
        this.user = user;
        this.password = password;
        this.code = code;
        this.active = false;
    }

    public Agent() {
    }
    
    

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return  name + ";" + email + ";" + user + ";" + password + ";" + code ;
    }

    

    

    

}
