/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fileClients, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Agent;
import exceptions.ListException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JLabel;
import org.apache.commons.codec.digest.DigestUtils;
import domain.Client;
import domain.Driver;
import domain.Order;
import domain.Product;
import java.util.LinkedList;
import gui.AgentInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logic.LinkedStack.LinkedStack;

/**
 * Diferentes metodos para darle función al programa
 *
 * @author hvill
 */
public class Logic implements Runnable {

    private Data data;
    private LinkedList<Agent> agentsList;
    private LinkedList<Client> clientsList;
    private Queue<Driver> driversList;

    /**
     * Constructor
     *
     * @throws ListException
     * @throws IOException
     */
    public Logic() throws ListException, IOException {
        data = new Data();
        agentsList = data.getAgentsList();
        clientsList = data.getClientsList();
        driversList = data.getDriversList();
    }

    /**
     * Valida si la tecla precionada es un número o no.
     *
     * @param event
     * @return Retorna true si la tecla presionada es un número y un false si no
     * lo es.
     */
    public boolean isNumeric(java.awt.event.KeyEvent event) {
        char charType = event.getKeyChar();
        if (Character.isDigit(charType)) {
            return true;
        }
        return false;
    }

    /**
     * Verifica la información de un agente para el inicio de seción y lo pone
     * como activo.
     *
     * @param user
     * @param password
     * @return Retorna true si la información es correcta y false si no lo es.
     */
    public boolean verifyAgent(String user, String password) {
        boolean active = false;
        for (int i = 0; i < agentsList.size(); i++) {
            if (user.equals(agentsList.get(i).getUser())
                    && DigestUtils.md5Hex(password).equals(agentsList.get(i).getPassword())) {
                agentsList.get(i).setActive(true);
                active = true;
            }
        }
        return active;
    }

    public String getDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedLocalDate = localDate.format(formatter);
        return formattedLocalDate;
    }

    /**
     * Metodo para guaradar o verificar si los clientes ya existen en el TDA
     * linkedList
     *
     * @param client
     * @return LinkedList
     */
    public LinkedList saveClients(Client client) {

        boolean exist = false;
        if (clientsList.isEmpty()) {
            clientsList.add(client);
            Data.setClientsList(clientsList);
            exist = true;
        } else {
            for (int i = 0; i < clientsList.size(); i++) {
                if (clientsList.get(i).getEmail().equals(client.getEmail())) {
                    exist = true;
                    JOptionPane.showMessageDialog(null, "The client already exists");
                }
            }
        }
        if (exist == false) {
            clientsList.add(client);
            Data.setClientsList(clientsList);
        }

        System.out.println(clientsList.size());
        for (int i = 0; i < clientsList.size(); i++) {
            System.out.println(clientsList.get(i).toString());
        }
        return clientsList;
    }

    /**
     * Metodo para eliminar un cliente de la lista de clientes
     */
    public LinkedList deleteClient(Client client) {
        boolean exist = false;
        System.out.println(clientsList.size() + "Inicio");

        for (int i = 0; i < clientsList.size(); i++) {
            if (clientsList.get(i).getId().equals(client.getId())) {
                exist = true;
                clientsList.remove(i);
                data.setClientsList(clientsList);
                JOptionPane.showMessageDialog(null, "client removed succesfull");
            }
        }

        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The client does not exist, verify the information.");
        }

        System.out.println(clientsList.size() + "Final");
        return clientsList;
    }

    /**
     * Metodo para editar un cliente del LinkedList 
     */
    public LinkedList updateClient(Client Client) {
        boolean exist = false;
        for (int i = 0; i < clientsList.size(); i++) {
            if (clientsList.get(i).getId().equals(Client.getId())) {
                exist = true;
                clientsList.remove(i);
                clientsList.add(Client);
//                clientsList.add(Client);
                data.setClientsList(clientsList);
                JOptionPane.showMessageDialog(null, "correctly modified client");
            }
        }

        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The client does not exist, verify the information.");
        }
        return clientsList;
    }

    /**
     * Metodo para guardar un agente
     *
     * @param agent
     * @return LinkedList
     */
    public LinkedList saveAgent(Agent agent) {

        boolean exist = false;
        if (agentsList.isEmpty()) {
            agentsList.add(agent);
            Data.setAgentsList(agentsList);
            exist = true;
        } else {
            for (int i = 0; i < agentsList.size(); i++) {
                if (agentsList.get(i).getEmail().equals(agent.getEmail())) {
                    exist = true;
                    JOptionPane.showMessageDialog(null, "The agent already exists");
                }
            }
        }
        if (exist == false) {
            agentsList.add(agent);
            Data.setAgentsList(agentsList);
        }

        System.out.println(agentsList.size());
        for (int i = 0; i < agentsList.size(); i++) {
            System.out.println(agentsList.get(i).toString());
        }
        return agentsList;

    }

    /**
     * metodo para eliminar los agentes del TDA
     *
     * @param agent
     * @return LinkedList
     */
    public LinkedList deleteAgent(Agent agent) {
        boolean exist = false;
        System.out.println(agentsList.size() + "Inicio");

        for (int i = 0; i < agentsList.size(); i++) {
            if (agentsList.get(i).getEmail().equals(agent.getEmail())) {
                exist = true;
                agentsList.remove(i);
                Data.setAgentsList(agentsList);
                JOptionPane.showMessageDialog(null, "agent removed succesfull");
            }
        }

        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The client does not exist, verify the information.");
        }

        System.out.println(clientsList.size() + "Final");
        return clientsList;

    }

    /**
     * Metodo para editar los agentes del tda
     *
     * @param agent
     * @return LinkedList
     */
    public LinkedList updateAgent(Agent agent) {
        boolean exist = false;
        for (int i = 0; i < agentsList.size(); i++) {
            if (agentsList.get(i).getEmail().equals(agent.getEmail())) {
                exist = true;
                agentsList.remove(i);
                agentsList.add(i, agent);
                Data.setAgentsList(agentsList);
                JOptionPane.showMessageDialog(null, "correctly modified agent");
            }
        }

        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The agent does not exist, verify the information.");
        }
        return agentsList;
    }

    /**
     * metodo para agregar conductores a la queue
     *
     * @param driver
     * @return Queue
     */
    public Queue saveDrivers(Driver driver) {
        boolean exist = false;
        Queue<Driver> aux = new LinkedList<>();
        if (driversList.isEmpty()) {
            driversList.add(driver);
            Data.setDriversList(driversList);
            exist = true;
            JOptionPane.showMessageDialog(null, "Driver added successfully");
        } else {
//            System.out.println(exist+" estoy antes ");
            while (exist&&!driversList.isEmpty()) {
                if (driversList.peek().getId().equalsIgnoreCase(driver.getId())) {
                    exist = !exist;
                    JOptionPane.showMessageDialog(null, "The driver already exists");
//                    System.out.println(exist+" estoy despues ");
                    
                } else {
                    aux.add(driversList.peek());
                    driversList.poll();
                }
            }
        }
        if (exist == false) {
            while (!aux.isEmpty()) {
                driversList.add(aux.peek());
                aux.remove();
            }
            driversList.add(driver);
            Data.setDriversList(driversList);
            JOptionPane.showMessageDialog(null, "Driver added successfully");
        }
        System.out.println(driversList.size());
        return driversList;
    }

    /**
     * metodo run para el hilo que actualiza los files cada 20 segundos
     */
    @Override
    public void run() {
        Queue<Driver> aux = new LinkedList<>();
        while (true) {
            String clients = "";
            String agents = "";
            String drivers = "";
            for (int i = 0; i < clientsList.size(); i++) {
                clients += clientsList.get(i).toString() + "\r\n";
            }
            for (int i = 0; i < agentsList.size(); i++) {
                agents += agentsList.get(i).toString() + "\r\n";
            }
            while(!driversList.isEmpty()){
                drivers += driversList.peek() + "\r\n";
                aux.add(driversList.peek());
                driversList.remove();
            }
            while(!aux.isEmpty()){
                driversList.add(aux.peek());
                aux.remove();
            }
            File fileDrivers = new File("Drivers.txt");
            File fileAgents = new File("Agents.txt");
            File fileClients = new File("Clients.txt");
            BufferedWriter bwClients;
            BufferedWriter bwAgents;
            BufferedWriter bwDrivers;
            try {
                bwClients = new BufferedWriter(new FileWriter(fileClients));
                bwClients.write(clients);
                bwClients.close();

                bwAgents = new BufferedWriter(new FileWriter(fileAgents));
                bwAgents.write(agents);
                bwAgents.close();

                bwDrivers = new BufferedWriter(new FileWriter(fileDrivers));
                bwDrivers.write(drivers);
                bwDrivers.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

//    public LinkedList saveSummary(){
//        
//    }
    /**
     * Mueve el primer elemento de la cola a la ultima posición.
     *
     * @param queue
     */
    public void updateDriverQueue(Queue queue) {
        queue.add(queue.poll());
        data.setDriversList(queue);
    }

    /**
     * Añade un elemento de tipo Product a la tabla.
     *
     * @param restaurant
     * @param product
     * @param tableModel
     * @param contTable
     */
    public void addDataInBillTable(String restaurant, Product product, DefaultTableModel tableModel, int contTable) {
        if (verifyProduct(tableModel, product.getName()) == false) {
            tableModel.insertRow(contTable, new Object[]{});
            tableModel.setValueAt(restaurant, contTable, 0);
            tableModel.setValueAt(product.getName(), contTable, 1);
            tableModel.setValueAt(1, contTable, 2);
            tableModel.setValueAt(product.getPrice(), contTable, 3);
            contTable++;
        } else {
            changeQuantityByName(tableModel, product.getName());
        }
    }

    public void addDataInOrdersTable(Order order, DefaultTableModel tableModel, int contTable) {
        tableModel.insertRow(contTable, new Object[]{});
        tableModel.setValueAt(order.getClient(), contTable, 0);
        tableModel.setValueAt(order.getOrderNumber(), contTable, 1);
        tableModel.setValueAt(order.getAgent(), contTable, 2);
        tableModel.setValueAt(order.getDate(), contTable, 3);
        tableModel.setValueAt(order.getPrice(), contTable, 4);
        tableModel.setValueAt(order.getProvince(), contTable, 5);
        tableModel.setValueAt(order.getAdress(), contTable, 6);
        tableModel.setValueAt(order.getDriver(), contTable, 7);
    }

    /**
     * Verifica la existensia de un elemento en la tabla.
     *
     * @param tableModel
     * @param name
     * @return Retorna true si lo encuentra y false si no lo hace.
     */
    private boolean verifyProduct(DefaultTableModel tableModel, String name) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                if (tableModel.getValueAt(i, j).equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cambia la cantidad de productos.
     *
     * @param tableModel
     * @param name
     */
    private void changeQuantityByName(DefaultTableModel tableModel, String name) {
        int quantity = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 1).equals(name)) {
                quantity = (int) tableModel.getValueAt(i, 2);
                tableModel.setValueAt(++quantity, i, 2);
            }
        }
    }

    /**
     * Verifica en la lista si existe el cliente con el email ingresado
     *
     * @param email
     * @return Retorna true si este cliente existe y false si no.
     */
    public boolean verifyClientByEmail(String email) {
        for (int i = 0; i < data.getClientsList().size(); i++) {
            if (data.getClientsList().get(i).getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

}
