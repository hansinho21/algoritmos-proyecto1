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
            System.out.println(aux[7] + " aqui es");
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

        foodList.cancel();
        drinkList.cancel();
        dessertList.cancel();
        otherList.cancel();
        
        if (restaurant.equals("McDonalds")) {
            foodList.insert(new Product("mcFood1", 12, "/images/mcdonalds/mcFood1.png"));
            foodList.insert(new Product("mcFood2", 12, "/images/mcdonalds/mcFood2.png"));
            foodList.insert(new Product("mcFood3", 12, "/images/mcdonalds/mcFood3.png"));
            foodList.insert(new Product("mcFood4", 12, "/images/mcdonalds/mcFood4.png"));
            foodList.insert(new Product("mcFood5", 12, "/images/mcdonalds/mcFood5.png"));
            foodList.insert(new Product("mcFood6", 12, "/images/mcdonalds/mcFood6.png"));

            drinkList.insert(new Product("mcDrink1", 10, "/images/mcdonalds/mcDrink1.png"));
            drinkList.insert(new Product("mcDrink2", 10, "/images/mcdonalds/mcDrink2.png"));
            drinkList.insert(new Product("mcDrink3", 10, "/images/mcdonalds/mcDrink3.png"));
            drinkList.insert(new Product("mcDrink4", 10, "/images/mcdonalds/mcDrink4.png"));

            dessertList.insert(new Product("mcDessert1", 11, "/images/mcdonalds/mcDessert1.png"));
            dessertList.insert(new Product("mcDessert2", 11, "/images/mcdonalds/mcDessert2.png"));
            dessertList.insert(new Product("mcDessert3", 11, "/images/mcdonalds/mcDessert3.png"));
            dessertList.insert(new Product("mcDessert4", 11, "/images/mcdonalds/mcDessert4.png"));
            dessertList.insert(new Product("mcDessert5", 11, "/images/mcdonalds/mcDessert5.png"));

            otherList.insert(new Product("mcOther1", 20, "/images/mcdonalds/mcOther1.png"));
            otherList.insert(new Product("mcOther2", 20, "/images/mcdonalds/mcOther2.png"));
            otherList.insert(new Product("mcOther3", 20, "/images/mcdonalds/mcOther3.png"));
            otherList.insert(new Product("mcOther4", 20, "/images/mcdonalds/mcOther4.png"));
            otherList.insert(new Product("mcOther5", 20, "/images/mcdonalds/mcOther5.png"));

        } else if (restaurant.equals("Burger King")) {
            foodList.insert(new Product("bkFood1", 12, "/images/burgerking/bkFood1.png"));
            foodList.insert(new Product("bkFood2", 12, "/images/burgerking/bkFood2.png"));
            foodList.insert(new Product("bkFood3", 12, "/images/burgerking/bkFood3.png"));
            foodList.insert(new Product("bkFood4", 12, "/images/burgerking/bkFood4.png"));

            drinkList.insert(new Product("bkDrink1", 10, "/images/burgerking/bkDrink1.png"));
            drinkList.insert(new Product("bkDrink2", 10, "/images/burgerking/bkDrink2.png"));
            drinkList.insert(new Product("bkDrink3", 10, "/images/burgerking/bkDrink3.png"));
            drinkList.insert(new Product("bkDrink4", 10, "/images/burgerking/bkDrink4.png"));

            dessertList.insert(new Product("bkDessert1", 11, "/images/burgerking/bkDessert1.png"));
            dessertList.insert(new Product("bkDessert2", 11, "/images/burgerking/bkDessert2.png"));
            dessertList.insert(new Product("bkDessert3", 11, "/images/burgerking/bkDessert3.png"));
            dessertList.insert(new Product("bkDessert4", 11, "/images/burgerking/bkDessert4.png"));

            otherList.insert(new Product("bkOther1", 20, "/images/burgerking/bkOther1.png"));
            otherList.insert(new Product("bkOther2", 20, "/images/burgerking/bkOther2.png"));
            otherList.insert(new Product("bkOther3", 20, "/images/burgerking/bkOther3.png"));
            otherList.insert(new Product("bkOther4", 20, "/images/burgerking/bkOther4.png"));

        } else if (restaurant.equals("Carls Jr.")) {
            foodList.insert(new Product("carlsjrFood1", 12, "/images/carlsjr/carlsjrFood1.png"));
            foodList.insert(new Product("carlsjrFood2", 12, "/images/carlsjr/carlsjrFood2.png"));
            foodList.insert(new Product("carlsjrFood3", 12, "/images/carlsjr/carlsjrFood3.png"));
            foodList.insert(new Product("carlsjrFood4", 12, "/images/carlsjr/carlsjrFood4.png"));

            drinkList.insert(new Product("carlsjrDrink1", 10, "/images/carlsjr/carlsjrDrink1.png"));
            drinkList.insert(new Product("carlsjrDrink2", 10, "/images/carlsjr/carlsjrDrink2.png"));
            drinkList.insert(new Product("carlsjrDrink3", 10, "/images/carlsjr/carlsjrDrink3.png"));
            drinkList.insert(new Product("carlsjrDrink4", 10, "/images/carlsjr/carlsjrDrink4.png"));

            dessertList.insert(new Product("carlsjrDessert1", 11, "/images/carlsjr/carlsjrDessert1.png"));
            dessertList.insert(new Product("carlsjrDessert2", 11, "/images/carlsjr/carlsjrDessert2.png"));
            dessertList.insert(new Product("carlsjrDessert3", 11, "/images/carlsjr/carlsjrDessert3.png"));
            dessertList.insert(new Product("carlsjrDessert4", 11, "/images/carlsjr/carlsjrDessert4.png"));

            otherList.insert(new Product("carlsjrOther1", 20, "/images/carlsjr/carlsjrOther1.png"));
            otherList.insert(new Product("carlsjrOther2", 20, "/images/carlsjr/carlsjrOther2.png"));
            otherList.insert(new Product("carlsjrOther3", 20, "/images/carlsjr/carlsjrOther3.png"));
            otherList.insert(new Product("carlsjrOther4", 20, "/images/carlsjr/carlsjrOther4.png"));
            
        } else if (restaurant.equals("Domino's Pizza")) {
            foodList.insert(new Product("dominospizzaFood1", 12, "/images/dominospizza/dominospizzaFood1.png"));
            foodList.insert(new Product("dominospizzaFood2", 12, "/images/dominospizza/dominospizzaFood2.png"));
            foodList.insert(new Product("dominospizzaFood3", 12, "/images/dominospizza/dominospizzaFood3.png"));
            foodList.insert(new Product("dominospizzaFood4", 12, "/images/dominospizza/dominospizzaFood4.png"));

            drinkList.insert(new Product("dominospizzaDrink1", 10, "/images/dominospizza/dominospizzaDrink1.png"));
            drinkList.insert(new Product("dominospizzaDrink2", 10, "/images/dominospizza/dominospizzaDrink2.png"));
            drinkList.insert(new Product("dominospizzaDrink3", 10, "/images/dominospizza/dominospizzaDrink3.png"));
            drinkList.insert(new Product("dominospizzaDrink4", 10, "/images/dominospizza/dominospizzaDrink4.png"));

            dessertList.insert(new Product("dominospizzaDessert1", 11, "/images/dominospizza/dominospizzaDessert1.png"));
            dessertList.insert(new Product("dominospizzaDessert2", 11, "/images/dominospizza/dominospizzaDessert2.png"));
            dessertList.insert(new Product("dominospizzaDessert3", 11, "/images/dominospizza/dominospizzaDessert3.png"));
            dessertList.insert(new Product("dominospizzaDessert4", 11, "/images/dominospizza/dominospizzaDessert4.png"));

            otherList.insert(new Product("dominospizzaOther1", 20, "/images/dominospizza/dominospizzaOther1.png"));
            otherList.insert(new Product("dominospizzaOther2", 20, "/images/dominospizza/dominospizzaOther2.png"));
            otherList.insert(new Product("dominospizzaOther3", 20, "/images/dominospizza/dominospizzaOther3.png"));
            otherList.insert(new Product("dominospizzaOther4", 20, "/images/dominospizza/dominospizzaOther4.png"));

        } else if (restaurant.equals("KFC")) {
            foodList.insert(new Product("kfcFood1", 12, "/images/kfc/kfcFood1.png"));
            foodList.insert(new Product("kfcFood2", 12, "/images/kfc/kfcFood2.png"));
            foodList.insert(new Product("kfcFood3", 12, "/images/kfc/kfcFood3.png"));
            foodList.insert(new Product("kfcFood4", 12, "/images/kfc/kfcFood4.png"));

            drinkList.insert(new Product("kfcDrink1", 10, "/images/kfc/kfcDrink1.png"));
            drinkList.insert(new Product("kfcDrink2", 10, "/images/kfc/kfcDrink2.png"));
            drinkList.insert(new Product("kfcDrink3", 10, "/images/kfc/kfcDrink3.png"));
            drinkList.insert(new Product("kfcDrink4", 10, "/images/kfc/kfcDrink4.png"));

            dessertList.insert(new Product("kfcDessert1", 11, "/images/kfc/kfcDessert1.png"));
            dessertList.insert(new Product("kfcDessert2", 11, "/images/kfc/kfcDessert2.png"));
            dessertList.insert(new Product("kfcDessert3", 11, "/images/kfc/kfcDessert3.png"));
            dessertList.insert(new Product("kfcDessert4", 11, "/images/kfc/kfcDessert4.png"));

            otherList.insert(new Product("kfcOther1", 20, "/images/kfc/kfcOther1.png"));
            otherList.insert(new Product("kfcOther2", 20, "/images/kfc/kfcOther2.png"));
            otherList.insert(new Product("kfcOther3", 20, "/images/kfc/kfcOther3.png"));
            otherList.insert(new Product("kfcOther4", 20, "/images/kfc/kfcOther4.png"));
            
        } else if (restaurant.equals("Pizza Hut")) {
            foodList.insert(new Product("pizzahutFood1", 12, "/images/pizzahut/pizzahutFood1.png"));
            foodList.insert(new Product("pizzahutFood2", 12, "/images/pizzahut/pizzahutFood2.png"));
            foodList.insert(new Product("pizzahutFood3", 12, "/images/pizzahut/pizzahutFood3.png"));
            foodList.insert(new Product("pizzahutFood4", 12, "/images/pizzahut/pizzahutFood4.png"));

            drinkList.insert(new Product("pizzahutDrink1", 10, "/images/pizzahut/pizzahutDrink1.png"));
            drinkList.insert(new Product("pizzahutDrink2", 10, "/images/pizzahut/pizzahutDrink2.png"));
            drinkList.insert(new Product("pizzahutDrink3", 10, "/images/pizzahut/pizzahutDrink3.png"));
            drinkList.insert(new Product("pizzahutDrink4", 10, "/images/pizzahut/pizzahutDrink4.png"));

            dessertList.insert(new Product("pizzahutDessert1", 11, "/images/pizzahut/pizzahutDessert1.png"));
            dessertList.insert(new Product("pizzahutDessert2", 11, "/images/pizzahut/pizzahutDessert2.png"));
            dessertList.insert(new Product("pizzahutDessert3", 11, "/images/pizzahut/pizzahutDessert3.png"));
            dessertList.insert(new Product("pizzahutDessert4", 11, "/images/pizzahut/pizzahutDessert4.png"));

            otherList.insert(new Product("pizzahutOther1", 20, "/images/pizzahut/pizzahutOther1.png"));
            otherList.insert(new Product("pizzahutOther2", 20, "/images/pizzahut/pizzahutOther2.png"));
            otherList.insert(new Product("pizzahutOther3", 20, "/images/pizzahut/pizzahutOther3.png"));
            otherList.insert(new Product("pizzahutOther4", 20, "/images/pizzahut/pizzahutOther4.png"));
            
        } else if (restaurant.equals("Taco Bell")) {
            foodList.insert(new Product("tacoFood1", 12, "/images/tacobell/tacoFood1.png"));
            foodList.insert(new Product("tacoFood2", 12, "/images/tacobell/tacoFood2.png"));
            foodList.insert(new Product("tacoFood3", 12, "/images/tacobell/tacoFood3.png"));
            foodList.insert(new Product("tacoFood4", 12, "/images/tacobell/tacoFood4.png"));

            drinkList.insert(new Product("tacoDrink1", 10, "/images/tacobell/tacoDrink1.png"));
            drinkList.insert(new Product("tacoDrink2", 10, "/images/tacobell/tacoDrink2.png"));
            drinkList.insert(new Product("tacoDrink3", 10, "/images/tacobell/tacoDrink3.png"));
            drinkList.insert(new Product("tacoDrink4", 10, "/images/tacobell/tacoDrink4.png"));

            dessertList.insert(new Product("tacoDessert1", 11, "/images/tacobell/tacoDessert1.png"));
            dessertList.insert(new Product("tacoDessert2", 11, "/images/tacobell/tacoDessert2.png"));
            dessertList.insert(new Product("tacoDessert3", 11, "/images/tacobell/tacoDessert3.png"));
            dessertList.insert(new Product("tacoDessert4", 11, "/images/tacobell/tacoDessert4.png"));

            otherList.insert(new Product("tacoOther1", 20, "/images/tacobell/tacoOther1.png"));
            otherList.insert(new Product("tacoOther2", 20, "/images/tacobell/tacoOther2.png"));
            otherList.insert(new Product("tacoOther3", 20, "/images/tacobell/tacoOther3.png"));
            otherList.insert(new Product("tacoOther4", 20, "/images/tacobell/tacoOther4.png"));
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
