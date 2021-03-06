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
import domain.OrderPart1;
import domain.OrderPart2;
import domain.Product;
import domain.Restaurant;
import exceptions.StackException;
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
    private LinkedList<Product> productsList;
    private LinkedStack orderPart1Stack;
    private LinkedStack orderPart2Stack;
    private Queue<Restaurant> restaurantsList;

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
        productsList = data.getProductList();
        restaurantsList = data.getRestaurantsList();
        orderPart1Stack = data.getOrdersPart1Stack();
        orderPart2Stack = data.getOrdersPart2Stack();
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
                    && password.equals(agentsList.get(i).getPassword())) {
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
     * Metodo para agregar un producto al archivo
     *
     * @param product
     * @return LinkedList
     */
    public LinkedList saveProducts(Product product) {
        boolean exist = false;
        if (productsList.isEmpty()) {
            productsList.add(product);
            data.setProductList(productsList);
            exist = true;
        } else {
            for (int i = 0; i < productsList.size(); i++) {
                if (productsList.get(i).getId() == product.getId()) {
                    exist = true;
                    JOptionPane.showMessageDialog(null, "The product already exists");
                }
            }
        }
        if (exist == false) {
            productsList.add(product);
            data.setProductList(productsList);
        }
        return productsList;
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

    public LinkedList deleteProduct(Product product) {
        boolean exist = false;
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getId() == (product.getId())) {
                exist = true;
                productsList.remove(i);
                data.setProductList(productsList);
                JOptionPane.showMessageDialog(null, "product removed succesfull");
            }
        }

        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The product does not exist, verify the information.");
        }
        return productsList;
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

    public LinkedList updateProduct(Product product) {
        boolean exist = false;
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getId() == (product.getId())) {
                exist = true;
                productsList.remove(i);
                productsList.add(i, product);
                Data.setProductList(productsList);
                JOptionPane.showMessageDialog(null, "correctly modified product");
            }
        }

        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The product does not exist, verify the information.");
        }
        return productsList;
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
            while (exist && !driversList.isEmpty()) {
                if (driversList.peek().getId() == driver.getId()) {
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
     * Metodo para eliminar un conductor especifico
     *
     * @param driver
     * @return Queue
     */
    public Queue deleteDriver(Driver driver) {
        boolean exist = false;
        Queue<Driver> aux = new LinkedList<>();
        if (driversList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The driver don't exist ");
        } else {
            while (!driversList.isEmpty()) {
                if (driversList.peek().getId() == driver.getId()) {
                    exist = true;
                    driversList.remove();
                    Data.setDriversList(driversList);
                    JOptionPane.showMessageDialog(null, "Driver deleted succesfull");
                } else {
                    aux.add(driversList.peek());
                    driversList.poll();
                }
            }
            while (!aux.isEmpty()) {
                driversList.add(aux.peek());
                aux.remove();
            }

        }
        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The driver don't exist ");
            System.out.println(driversList.size());

        }
        return driversList;
    }

    public Queue updateDriversFile(Driver driver) {
        boolean exist = false;
        Queue<Driver> aux = new LinkedList<>();
        if (driversList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The driver don't exist ");
        } else {
            while (!driversList.isEmpty()) {
                if (exist == false && driversList.peek().getId() == driver.getId()) {
                    exist = true;
                    driversList.remove();
                    driversList.add(driver);
                    Data.setDriversList(driversList);
                    JOptionPane.showMessageDialog(null, "Driver modified succesfull");
                } else {
                    aux.add(driversList.peek());
                    driversList.poll();
                }
            }
            while (!aux.isEmpty()) {
                driversList.add(aux.peek());
                aux.remove();
            }

        }
        if (exist == false) {
            JOptionPane.showMessageDialog(null, "The driver don't exist ");
            System.out.println(driversList.size());

        }
        return driversList;
    }

    /**
     * metodo run para el hilo que actualiza los files cada 20 segundos
     */
    @Override
    public void run() {
        LinkedStack auxPart1Order = new LinkedStack();
        LinkedStack auxPart2Order = new LinkedStack();
        Queue<Driver> auxDrivers = new LinkedList<>();
        Queue<Restaurant> auxRestaurants = new LinkedList<>();
        while (true) {
            String orderStackPart1 = "";
            String clients = "";
            String agents = "";
            String drivers = "";
            String products = "";
            String restaurants = "";
            String orderStackPart2 = "";
            for (int i = 0; i < clientsList.size(); i++) {
                clients += clientsList.get(i).toString() + "\r\n";
            }
            for (int i = 0; i < agentsList.size(); i++) {
                agents += agentsList.get(i).toString() + "\r\n";
            }
            for (int i = 0; i < productsList.size(); i++) {
                products += productsList.get(i).toString() + "\r\n";
            }
            for (int i = 0; i < productsList.size(); i++) {
                products += productsList.get(i).toString() + "\r\n";
            }
            while (!orderPart1Stack.isEmpty()) {
                try {
                    orderStackPart1 += orderPart1Stack.peek() + "\r\n";
                    auxPart1Order.push(orderPart1Stack.pop());
                    
                } catch (StackException ex) {
                    Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while(!auxPart1Order.isEmpty()){
                try {
                    orderPart1Stack.push(auxPart1Order.pop());
                    
                } catch (StackException ex) {
                    Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //---------------------------------------
            while (!orderPart2Stack.isEmpty()) {
                try {
                    orderStackPart2 += orderPart2Stack.peek() + "\r\n";
                    auxPart2Order.push(orderPart2Stack.pop());
                    
                } catch (StackException ex) {
                    Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while(!auxPart2Order.isEmpty()){
                try {
                    orderPart2Stack.push(auxPart2Order.pop());
                    
                } catch (StackException ex) {
                    Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //-----------------------------------------
            while (!driversList.isEmpty()) {
                drivers += driversList.peek() + "\r\n";
                auxDrivers.add(driversList.peek());
                driversList.remove();
            }
            while (!auxDrivers.isEmpty()) {
                driversList.add(auxDrivers.peek());
                auxDrivers.remove();
            }
            while (!restaurantsList.isEmpty()) {
                restaurants += restaurantsList.peek() + "\r\n";
                auxRestaurants.add(restaurantsList.peek());
                restaurantsList.remove();
            }
            while (!auxRestaurants.isEmpty()) {
                restaurantsList.add(auxRestaurants.peek());
                auxRestaurants.remove();
            }

            File fileDrivers = new File("Drivers.txt");
            File fileAgents = new File("Agents.txt");
            File fileClients = new File("Clients.txt");
            File fileProducts = new File("Products.txt");
            File fileRestaurants = new File("Restaurants.txt");
            File fileOrderPart1 = new File("OrderPart1Stack.txt");
            BufferedWriter bwRestaurants;
            BufferedWriter bwClients;
            BufferedWriter bwAgents;
            BufferedWriter bwDrivers;
            BufferedWriter bwProducts;
            BufferedWriter bwOrderPart1;
            try {
                bwOrderPart1 = new BufferedWriter(new FileWriter(fileOrderPart1));
                bwOrderPart1.write(orderStackPart1);
                bwOrderPart1.close();
                
                bwClients = new BufferedWriter(new FileWriter(fileClients));
                bwClients.write(clients);
                bwClients.close();

                bwAgents = new BufferedWriter(new FileWriter(fileAgents));
                bwAgents.write(agents);
                bwAgents.close();

                bwDrivers = new BufferedWriter(new FileWriter(fileDrivers));
                bwDrivers.write(drivers);
                bwDrivers.close();

                bwProducts = new BufferedWriter(new FileWriter(fileProducts));
                bwProducts.write(products);
                bwProducts.close();

                bwRestaurants = new BufferedWriter(new FileWriter(fileRestaurants));
                bwRestaurants.write(restaurants);
                bwRestaurants.close();

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
    public void addDataInBillTable(int idRestaurant, Product product, DefaultTableModel tableModel, int contTable, String idClient) {
        if (verifyProduct(tableModel, product.getId()) == false) {
            tableModel.insertRow(contTable, new Object[]{});
            tableModel.setValueAt(idClient, contTable, 0);
            tableModel.setValueAt(idRestaurant, contTable, 1);
            tableModel.setValueAt(product.getId(), contTable, 2);
            tableModel.setValueAt(1, contTable, 3);
            tableModel.setValueAt(product.getPrice(), contTable, 4);
            contTable++;
        } else {
            changeQuantityByName(tableModel, product);
        }
    }

    public void addOrderPart1InOrdersTable(OrderPart1 order, DefaultTableModel tableModel, int contTable) {
        tableModel.insertRow(contTable, new Object[]{});
        tableModel.setValueAt(order.getIdOrder(), contTable, 0);
        tableModel.setValueAt(order.getIdClient(), contTable, 1);
        tableModel.setValueAt(order.getIdRestaurant(), contTable, 2);
        tableModel.setValueAt(order.getIdProduct(), contTable, 3);
        tableModel.setValueAt(order.getQuantity(), contTable, 4);
        tableModel.setValueAt(order.getTotalItems(), contTable, 5);

    }

    public void addOrderPart2InOrdersTable(DefaultTableModel tableModel, int contTable) throws StackException {
        LinkedStack auxOrder1 = new LinkedStack();
        LinkedStack auxOrder2 = new LinkedStack();
        LinkedList<OrderPart1> list = new LinkedList<>();
        int size1 = orderPart1Stack.getSize();
        int size2 = orderPart2Stack.getSize();

        while (!orderPart1Stack.isEmpty()) {
            auxOrder1.push(orderPart1Stack.pop());
        }
        while (!auxOrder1.isEmpty()) {
            list.add((OrderPart1) auxOrder1.peek());
            orderPart1Stack.push(auxOrder1.pop());
        }

        while (!orderPart2Stack.isEmpty()) {
            auxOrder2.push(orderPart2Stack.pop());
        }

        while (!auxOrder2.isEmpty()) {
            OrderPart2 aux2 = (OrderPart2) auxOrder2.peek();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIdOrder() == aux2.getIdOrder()) {
                    for (int j = 0; j < tableModel.getRowCount(); j++) {
                        if ((int) tableModel.getValueAt(j, 0) == aux2.getIdOrder()) {
                            tableModel.setValueAt(aux2.getAgentName(), j, 6);
                            tableModel.setValueAt(aux2.getDate(), j, 7);
                            tableModel.setValueAt(aux2.getTotalOrder(), j, 8);
                            tableModel.setValueAt(aux2.getProvince(), j, 9);
                            tableModel.setValueAt(aux2.getAddress(), j, 10);
                            tableModel.setValueAt(aux2.getDriverName(), j, 11);
                        }
                    }
                }
            }
            orderPart2Stack.push(auxOrder2.pop());

        }
    }

    /**
     * Verifica la existensia de un elemento en la tabla.
     *
     * @param tableModel
     * @param name
     * @return Retorna true si lo encuentra y false si no lo hace.
     */
    private boolean verifyProduct(DefaultTableModel tableModel, int id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 2).equals(id)) {
                return true;
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
    private void changeQuantityByName(DefaultTableModel tableModel, Product product) {
        int quantity = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 2).equals(product.getId())) {
                quantity = (int) tableModel.getValueAt(i, 3);
                tableModel.setValueAt(++quantity, i, 3);
                tableModel.setValueAt(product.getPrice() * quantity, i, 4);
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
