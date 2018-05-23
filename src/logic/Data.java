/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Agent;
import domain.Client;
import domain.Driver;
import domain.Location;
import domain.Product;
import domain.Restaurant;
import exceptions.ListException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import logic.ListaCircularDoblementeenlazada.ListaCircularDoblementeEnlazada;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import logic.LinkedStack.LinkedStack;

/**
 * Declaración y asignación de datos predeterminados de los tdas que se van a
 * utilizar
 *
 * @author hvill
 */
public class Data {

    private static LinkedList<Client> clientsList = new LinkedList<>();
    private static LinkedList<Agent> agentsList = new LinkedList<>();
    private static ListaCircularDoblementeEnlazada iconsList = new ListaCircularDoblementeEnlazada();
    private static Queue<Driver> driversList = new LinkedList<>();
    private static LinkedStack ordersStack = new LinkedStack();
    private static Queue<Restaurant> restaurantsList = new LinkedList<>();

    //Comidas
    private static ListaCircularDoblementeEnlazada foodList = new ListaCircularDoblementeEnlazada();
    private static ListaCircularDoblementeEnlazada drinkList = new ListaCircularDoblementeEnlazada();
    private static ListaCircularDoblementeEnlazada dessertList = new ListaCircularDoblementeEnlazada();
    private static ListaCircularDoblementeEnlazada otherList = new ListaCircularDoblementeEnlazada();

    public Data() throws ListException, IOException {
        if (clientsList.isEmpty()) {
            fillClientsList();
            fillAgentsList();
            fillIconsList();
            fillDriversList();
            fillRestaurantsList();
        }
    }

    private void fillClientsList() throws FileNotFoundException, IOException {
        String sCadena;
        FileReader fr = new FileReader("Clients.txt");
        BufferedReader bf = new BufferedReader(fr);
        while ((sCadena = bf.readLine()) != null) {
            Client client = new Client();
            String[] aux = sCadena.split("\t");
//            System.out.println(Arrays.toString(sCadena.split(";")));
            client.setId(aux[0]);
            client.setName(aux[1]);
            client.setLastName1(aux[2]);
            client.setLastName2(aux[3]);
            System.out.println(aux[7]+" aqui es");
            client.setEmail(aux[4]);
            client.setPhone(aux[5]);
            client.setProvince(aux[6]);
            client.setAdress(aux[7]);
            clientsList.add(client);
        }
        bf.close();
    }

    private void fillAgentsList() throws FileNotFoundException, IOException {
        String sCadena;
        FileReader fr = new FileReader("Agents.txt");
        BufferedReader bf = new BufferedReader(fr);
        while ((sCadena = bf.readLine()) != null) {
            Agent agent = new Agent();
            String[] aux = sCadena.split(";");
//            System.out.println(Arrays.toString(sCadena.split(";")));
            agent.setName(aux[0]);
            agent.setEmail(aux[1]);
            agent.setUser(aux[2]);
            agent.setPassword(aux[3]);
            agent.setCode(aux[4]);
            agentsList.add(agent);
        }
        bf.close();

    }

    private void fillIconsList() throws ListException, IOException {
        iconsList.insert(new Product("apple", 10, "/icons/apple.png"));
        iconsList.insert(new Product("chrome", 15, "/icons/chrome.png"));
        iconsList.insert(new Product("facebook", 20, "/icons/facebook.png"));
        iconsList.insert(new Product("google", 5, "/icons/google.png"));
//        iconsList.insert(new ImageIcon(ImageIO.read(getClass().getResource("/icons/googlePlay.png"))));
//        iconsList.insert(new ImageIcon(ImageIO.read(getClass().getResource("/icons/hp.png"))));
//        iconsList.insert(new ImageIcon(ImageIO.read(getClass().getResource("/icons/instagram.png"))));
//        iconsList.insert(new ImageIcon(ImageIO.read(getClass().getResource("/icons/internetExplorer.png"))));
//        iconsList.insert(new ImageIcon(ImageIO.read(getClass().getResource("/icons/messenger.png"))));
    }

    private void fillDriversList() throws IOException {
        driversList.add(new Driver("1", "Juan Solano", "/images/driver2.png"));
        driversList.add(new Driver("2", "Carlos Rodriguez", "/images/driver2.png"));
        driversList.add(new Driver("3", "Natalia Alvarado", "/images/driver2.png"));
        driversList.add(new Driver("4", "Nancy Pereira", "/images/driver2.png"));
        driversList.add(new Driver("5", "Allan Solano", "/images/driver2.png"));
//         String sCadena;
//        FileReader fr = new FileReader("Drivers.txt");
//        BufferedReader bf = new BufferedReader(fr);
//        while ((sCadena = bf.readLine()) != null) {
//            Driver driver = new Driver();
//            String[] aux = sCadena.split("\t");
////            System.out.println(Arrays.toString(sCadena.split(";")));
//            driver.setId(aux[0]);
//            driver.setName(aux[1]);
//            driver.setLastName1(aux[2]);
//            driver.setLastName2(aux[3]);
//            driver.setAge(aux[4]);
//            driver.setType(aux[5]);
//            driver.setPhone(aux[6]);
//            driver.setPlate(aux[7]);
////            driver.setCedula(aux[8]);
//            driversList.add(driver);
//        }
//        bf.close();

    }

    private void fillRestaurantsList() throws IOException {
        ArrayList<Location> mcDonaldsLocation = new ArrayList<>();
        ArrayList<Location> burgerKingLocation = new ArrayList<>();
        ArrayList<Location> carlsJrLocation = new ArrayList<>();
        ArrayList<Location> dominosPizzaLocation = new ArrayList<>();
        ArrayList<Location> kfcLocation = new ArrayList<>();
        ArrayList<Location> pizzaHutLocation = new ArrayList<>();
        ArrayList<Location> tacoBellLocation = new ArrayList<>();

        mcDonaldsLocation.add(new Location("Cartago", "North"));
        mcDonaldsLocation.add(new Location("San José", "Central"));
        mcDonaldsLocation.add(new Location("Alajuela", "South"));
        mcDonaldsLocation.add(new Location("Limón", "Central"));
        mcDonaldsLocation.add(new Location("Puntarenas", "Central"));

        burgerKingLocation.add(new Location("San José", "North"));
        burgerKingLocation.add(new Location("Cartago", "South"));
        burgerKingLocation.add(new Location("Heredia", "Central"));

        carlsJrLocation.add(new Location("Guanacaste", "Central"));
        carlsJrLocation.add(new Location("Guanacaste", "South"));
        carlsJrLocation.add(new Location("Guanacaste", "North"));
        carlsJrLocation.add(new Location("Cartago", "Central"));
        carlsJrLocation.add(new Location("San José", "South"));
        carlsJrLocation.add(new Location("Alajuela", "North"));

        dominosPizzaLocation.add(new Location("Alajuela", "Central"));
        dominosPizzaLocation.add(new Location("Heredia", "North"));
        dominosPizzaLocation.add(new Location("Heredia", "South"));
        dominosPizzaLocation.add(new Location("Puntarenas", "North"));

        kfcLocation.add(new Location("Puntarenas", "South"));
        kfcLocation.add(new Location("Cartago", "Central"));
        kfcLocation.add(new Location("San José", "Central"));
        kfcLocation.add(new Location("Limón", "North"));
        kfcLocation.add(new Location("Alajuela", "Central"));
        kfcLocation.add(new Location("Guanacaste", "South"));
        kfcLocation.add(new Location("Heredia", "South"));

        pizzaHutLocation.add(new Location("Limón", "South"));
        pizzaHutLocation.add(new Location("Cartago", "North"));
        pizzaHutLocation.add(new Location("Alajuela", "North"));

        tacoBellLocation.add(new Location("San José", "Central"));
        tacoBellLocation.add(new Location("Cartago", "Central"));

        restaurantsList.add(new Restaurant("McDonalds", "/images/restaurants/McDonalds.png", mcDonaldsLocation));
        restaurantsList.add(new Restaurant("Burger King", "/images/restaurants/burgerking.png", burgerKingLocation));
        restaurantsList.add(new Restaurant("Carls Jr.", "/images/restaurants/carlsjr.png", carlsJrLocation));
        restaurantsList.add(new Restaurant("Domino's Pizza", "/images/restaurants/dominospizza.png", dominosPizzaLocation));
        restaurantsList.add(new Restaurant("KFC", "/images/restaurants/kfc.png", kfcLocation));
        restaurantsList.add(new Restaurant("Pizza Hut", "/images/restaurants/pizzahut.png", pizzaHutLocation));
        restaurantsList.add(new Restaurant("Taco Bell", "/images/restaurants/tacobell.png", tacoBellLocation));
    }

    public void fillRestaurantFood(String restaurant) throws IOException, ListException {

        if (restaurant.equals("McDonalds")) {
            foodList.insert(new Product("BigMac", 12, "/images/mcdonalds/big-mac.png"));
            foodList.insert(new Product("Alitas", 12, "/images/mcdonalds/alitas-pollo.png"));
            foodList.insert(new Product("McNuggets", 12, "/images/mcdonalds/chicken-mcnuggets.png"));
            foodList.insert(new Product("Cuarto de libra", 12, "/images/mcdonalds/cuarto-de-libra-doble.png"));
            foodList.insert(new Product("Mini hamburguesa", 12, "/images/mcdonalds/hamburguesa_mini.png"));
            foodList.insert(new Product("McPapas", 12, "/images/mcdonalds/mcpapas.png"));

            drinkList.insert(new Product("Coca Cola", 10, "/images/mcdonalds/coca-cola.png"));
            drinkList.insert(new Product("Coca Cola Dieta", 10, "/images/mcdonalds/diet-coca-cola.png"));
            drinkList.insert(new Product("McCoca", 10, "/images/mcdonalds/mcdonalds-coca.png"));
            drinkList.insert(new Product("Naranja", 10, "/images/mcdonalds/naranja.png"));

            dessertList.insert(new Product("Cono1", 11, "/images/mcdonalds/Casquinha-Mista-Colombia_new.png"));
            dessertList.insert(new Product("Cono2", 11, "/images/mcdonalds/cono-coronado.png"));
            dessertList.insert(new Product("McFlurry1", 11, "/images/mcdonalds/mcflurry_choco_swing.png"));
            dessertList.insert(new Product("McFlurry2", 11, "/images/mcdonalds/mc-flurry-mm_new_cr.png"));
            dessertList.insert(new Product("Sundae1", 11, "/images/mcdonalds/sundae-caramelo.png"));
            dessertList.insert(new Product("Sundae2", 11, "/images/mcdonalds/sundae-chocolate.png"));
            dessertList.insert(new Product("Sundae3", 11, "/images/mcdonalds/sundae-fresa.png"));

            otherList.insert(new Product("McCafe1", 20, "/images/mcdonalds/Cafe-Americano.png"));
            otherList.insert(new Product("McCafe2", 20, "/images/mcdonalds/Cafe-Mocha.png"));
            otherList.insert(new Product("Galletas", 20, "/images/mcdonalds/cookies-mc.png"));
            otherList.insert(new Product("McCafe3", 20, "/images/mcdonalds/product-iced-chocolate.png"));
        }
    }

    public static LinkedList<Client> getClientsList() {
        return clientsList;
    }

    public static void setClientsList(LinkedList<Client> clientsList) {
        Data.clientsList = clientsList;
    }

    public static LinkedList<Agent> getAgentsList() {
        return agentsList;
    }

    public static void setAgentsList(LinkedList<Agent> agentsList) {
        Data.agentsList = agentsList;
    }

    public static ListaCircularDoblementeEnlazada getIconsList() {
        return iconsList;
    }

    public static void setIconsList(ListaCircularDoblementeEnlazada iconsList) {
        Data.iconsList = iconsList;
    }

    public static Queue<Driver> getDriversList() {
        return driversList;
    }

    public static void setDriversList(Queue<Driver> driversList) {
        Data.driversList = driversList;
    }

    public LinkedStack getOrdersStack() {
        return ordersStack;
    }

    public void setOrdersStack(LinkedStack ordersStack) {
        Data.ordersStack = ordersStack;
    }

    public static Queue<Restaurant> getRestaurantsList() {
        return restaurantsList;
    }

    public void setRestaurantsList(Queue<Restaurant> restaurantsList) {
        Data.restaurantsList = restaurantsList;
    }

    public ListaCircularDoblementeEnlazada getFoodList() {
        return foodList;
    }

    public void setFoodList(ListaCircularDoblementeEnlazada foodList) {
        Data.foodList = foodList;
    }

    public ListaCircularDoblementeEnlazada getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(ListaCircularDoblementeEnlazada drinkList) {
        Data.drinkList = drinkList;
    }

    public ListaCircularDoblementeEnlazada getDessertList() {
        return dessertList;
    }

    public void setDessertList(ListaCircularDoblementeEnlazada dessertList) {
        Data.dessertList = dessertList;
    }

    public ListaCircularDoblementeEnlazada getOtherList() {
        return otherList;
    }

    public void setOtherList(ListaCircularDoblementeEnlazada otherList) {
        Data.otherList = otherList;
    }

}
