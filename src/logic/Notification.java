/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author hvill
 */
public class Notification implements Runnable{

    private JLabel label;
    private Thread h1;
    
    public Notification(JLabel label){
        this.label = label;
        this.h1 = new Thread(this);
        this.h1.start();
    }
    
    @Override
    public void run() {
        this.label.setText("Producto agregado");
        this.label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.label.setText("");
        this.label.setBorder(null);
    }
    
}
