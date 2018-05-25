/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Client;
import exceptions.ListException;
import logic.Data;
import logic.ListaCircularDoblementeenlazada.ListaCircularDoblementeEnlazada;
import logic.Logic;
import logic.NodeList;
import java.awt.ComponentOrientation;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import com.mxrck.autocompleter.TextAutoCompleter;
import domain.Agent;
import domain.Driver;
import domain.Order;
import domain.Product;
import domain.Restaurant;
import exceptions.StackException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import logic.Clock;
import logic.LinkedStack.LinkedStack;
import logic.Notification;

/**
 *
 * @author jeison
 */
public class AgentInterface extends javax.swing.JFrame {

    //Instancias
    private Data data;
    private Logic logic;
    private Client client;
    private Clock clock;

    //TDAs
    private LinkedList<Client> clientsList;
    private LinkedList<Agent> agentsList;
    private Queue<Driver> driversList;
    private LinkedStack ordersStack;
    private Queue<Restaurant> restaurantsList;
    private Queue<Restaurant> restaurantsByProvince;

    private ListaCircularDoblementeEnlazada foodList;
    private ListaCircularDoblementeEnlazada drinkList;
    private ListaCircularDoblementeEnlazada dessertList;
    private ListaCircularDoblementeEnlazada otherList;

    //AutoCompleter
    private TextAutoCompleter textAutoCompleter;

    //Nodos
    private NodeList nodeFood1;
    private NodeList nodeFood2;
    private NodeList nodeFood3;

    private NodeList nodeDrink1;
    private NodeList nodeDrink2;
    private NodeList nodeDrink3;

    private NodeList nodeDessert1;
    private NodeList nodeDessert2;
    private NodeList nodeDessert3;

    private NodeList nodeOther1;
    private NodeList nodeOther2;
    private NodeList nodeOther3;

    //Estado del agente
    private boolean activeAgent;

    //Table
    private DefaultTableModel tableModel;
    private int contTable;

    //Precio del pedido
    private double subTotal;
    private double iva;
    private double total;

    /**
     * Creates new form PanelDeControl
     */
    public AgentInterface() throws ListException, IOException {
        //Interfaz
        initComponents();
        this.setLocationRelativeTo(null);
        jMenuBar1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        jLabelId.setVisible(false);

        //Deshabilitar los botones
//        jButtonClean.setEnabled(false);
//        jButtonDelete.setEnabled(false);
//        jButtonNextRestaurant.setEnabled(false);
//        jButtonNextFood.setEnabled(false);
//        jButtonPreviousFood.setEnabled(false);
//        jButtonNextDrink.setEnabled(false);
//        jButtonPreviousDrink.setEnabled(false);
//        jButtonNextDessert.setEnabled(false);
//        jButtonPreviousDessert.setEnabled(false);
//        jButtonNextOther.setEnabled(false);
//        jButtonPreviousOther.setEnabled(false);
//        jButtonConfirmOrder.setEnabled(false);
        //Instancias
        data = new Data();
        logic = new Logic();
        clock = new Clock(jLabelTime);
        restaurantsByProvince = new LinkedList<>();

        //TDAs
        clientsList = data.getClientsList();
        agentsList = Data.getAgentsList();
        driversList = data.getDriversList();
        ordersStack = data.getOrdersStack();
        restaurantsList = data.getRestaurantsList();

        //Numero de orden
        jLabelOrderNumber.setText(String.valueOf(ordersStack.getSize() + 1));

        //AutoCompleter
        textAutoCompleter = new TextAutoCompleter(jTextFieldCorreoCliente);

        //Estado de agente
        activeAgent = false;

        //Tabla
        contTable = 0;

        //Precio del pedido
        subTotal = 0;
        iva = 0;
        total = 0;

        //Carga la fecha al label
        jLabelDate.setText(logic.getDate());

        //Cambia estado del agente y carga datos a la interfaz
        setActiveAgent();
        //Llena el autocompleter
        fillAutoCompleter();
        //Inicializa la tabla
        initializeTable();
        //Carga información del siguiente conductor en la cola
        driverInformation();
    }

    /**
     * Metodo para inicializar la tabla con la cantidad de columnas deseadas.
     */
    private void initializeTable() {
        String x[][] = {};
        String columns[] = {"Id Client", "Id Restaurant", "Id Product", "Quantity", "Total Items"};
        tableModel = new DefaultTableModel(x, columns);
        jTableFactura.setModel(tableModel);
    }

    /**
     * Cambia el icono de java por el de la empresa.
     *
     * @return Retorna la imagen de la impresa.
     */
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/JH-logo-black.png"));
        return retValue;
    }

    /**
     * Limpia lo que tengan los textfields.
     */
    void cleanTextFields() {
        jTextFieldCorreoCliente.setText("");
        jTextFieldNomCliente.setText("");
        jComboBoxProvinciaCliente.setSelectedItem("Seleccione una opción");
        jTextFieldTelCliente.setText("");
        jTexfieldDireccion.setText("");
        jTextFieldLastName1.setText("");
        jTextFieldLastName2.setText("");
        jLabelId.setVisible(false);
        jLabelIdClient.setText("");
    }

    /**
     * LLena el AutoCompleter con los correos de los clientes.
     */
    private void fillAutoCompleter() {
        textAutoCompleter.removeAllItems();
        for (int i = 0; i < clientsList.size(); i++) {
//            System.out.println(clientsList.get(i).toString());
            textAutoCompleter.addItem(clientsList.get(i).getEmail());
        }
    }

    /**
     * Inicializa las imagenes de las comidas.
     *
     * @throws ListException
     * @throws IOException
     */
    private void initializeImages() throws ListException, IOException {

        data.fillRestaurantFood(restaurantsByProvince.peek().getName());
        foodList = data.getFoodList();
        drinkList = data.getDrinkList();
        dessertList = data.getDessertList();
        otherList = data.getOtherList();

        nodeFood1 = foodList.getNode(1);
        nodeFood2 = foodList.getNode(2);
        nodeFood3 = foodList.getNode(3);
        jLabelFood1.setIcon((Icon) foodList.getNode(1).element.getImage());
        jLabelFood2.setIcon((Icon) foodList.getNode(2).element.getImage());
        jLabelFood3.setIcon((Icon) foodList.getNode(3).element.getImage());

        nodeDrink1 = drinkList.getNode(1);
        nodeDrink2 = drinkList.getNode(2);
        nodeDrink3 = drinkList.getNode(3);
        jLabelDrink1.setIcon((Icon) drinkList.getNode(1).element.getImage());
        jLabelDrink2.setIcon((Icon) drinkList.getNode(2).element.getImage());
        jLabelDrink3.setIcon((Icon) drinkList.getNode(3).element.getImage());

        nodeDessert1 = dessertList.getNode(1);
        nodeDessert2 = dessertList.getNode(2);
        nodeDessert3 = dessertList.getNode(3);
        jLabelDessert1.setIcon((Icon) dessertList.getNode(1).element.getImage());
        jLabelDessert2.setIcon((Icon) dessertList.getNode(2).element.getImage());
        jLabelDessert3.setIcon((Icon) dessertList.getNode(3).element.getImage());

        nodeOther1 = otherList.getNode(1);
        nodeOther2 = otherList.getNode(2);
        nodeOther3 = otherList.getNode(3);
        jLabelOther1.setIcon((Icon) otherList.getNode(1).element.getImage());
        jLabelOther2.setIcon((Icon) otherList.getNode(2).element.getImage());
        jLabelOther3.setIcon((Icon) otherList.getNode(3).element.getImage());

        restaurantsByProvince.add(restaurantsByProvince.poll());

    }

    /**
     * Cambia el estado del agente y carga la informacion a la interfaz.
     */
    public void setActiveAgent() {
        if (activeAgent == false) {
            for (int i = 0; i < agentsList.size(); i++) {
                if (agentsList.get(i).isActive() == true) {
                    jLabelAgentName.setText(agentsList.get(i).getName());
                    jLabelAgentCode.setText(agentsList.get(i).getCode());
                    this.activeAgent = true;
                }
            }
        }

    }

    /**
     * Calcula el precio de la orden y lo carga a la interfaz.
     *
     * @param product
     */
    private void updatePrice(Product product) {
        subTotal += product.getPrice();
        iva += product.getPrice() * 10 / 100;
        total = subTotal + iva;
        jLabelSubTotal.setText(String.valueOf(subTotal));
        jLabelIva.setText(String.valueOf(iva));
        jLabelTotalPrice.setText(String.valueOf(total));
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón anterior de comidas.
     */
    private void previousFoodButton() {
        jLabelFood1.setIcon((Icon) nodeFood1.previous.element.getImage());
        jLabelFood2.setIcon((Icon) nodeFood2.previous.element.getImage());
        jLabelFood3.setIcon((Icon) nodeFood3.previous.element.getImage());
        nodeFood1 = nodeFood1.previous;
        nodeFood2 = nodeFood2.previous;
        nodeFood3 = nodeFood3.previous;
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón anterior de bebidas.
     */
    private void previousDrinkButton() {
        jLabelDrink1.setIcon((Icon) nodeDrink1.previous.element.getImage());
        jLabelDrink2.setIcon((Icon) nodeDrink2.previous.element.getImage());
        jLabelDrink3.setIcon((Icon) nodeDrink3.previous.element.getImage());
        nodeDrink1 = nodeDrink1.previous;
        nodeDrink2 = nodeDrink2.previous;
        nodeDrink3 = nodeDrink3.previous;
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón anterior de comidas.
     */
    private void previousDessertButton() {
        jLabelDessert1.setIcon((Icon) nodeDessert1.previous.element.getImage());
        jLabelDessert2.setIcon((Icon) nodeDessert2.previous.element.getImage());
        jLabelDessert3.setIcon((Icon) nodeDessert3.previous.element.getImage());
        nodeDessert1 = nodeDessert1.previous;
        nodeDessert2 = nodeDessert2.previous;
        nodeDessert3 = nodeDessert3.previous;
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón anterior de comidas.
     */
    private void previousOtherButton() {
        jLabelOther1.setIcon((Icon) nodeOther1.previous.element.getImage());
        jLabelOther2.setIcon((Icon) nodeOther2.previous.element.getImage());
        jLabelOther3.setIcon((Icon) nodeOther3.previous.element.getImage());
        nodeOther1 = nodeOther1.previous;
        nodeOther2 = nodeOther2.previous;
        nodeOther3 = nodeOther3.previous;
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón siguiente de comidas.
     */
    private void nextFoodButton() {
        jLabelFood1.setIcon((Icon) nodeFood1.next.element.getImage());
        jLabelFood2.setIcon((Icon) nodeFood2.next.element.getImage());
        jLabelFood3.setIcon((Icon) nodeFood3.next.element.getImage());
        nodeFood1 = nodeFood1.next;
        nodeFood2 = nodeFood2.next;
        nodeFood3 = nodeFood3.next;
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón siguiente de bebidas.
     */
    private void nextDrinkButton() {
        jLabelDrink1.setIcon((Icon) nodeDrink1.next.element.getImage());
        jLabelDrink2.setIcon((Icon) nodeDrink2.next.element.getImage());
        jLabelDrink3.setIcon((Icon) nodeDrink3.next.element.getImage());
        nodeDrink1 = nodeDrink1.next;
        nodeDrink2 = nodeDrink2.next;
        nodeDrink3 = nodeDrink3.next;
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón siguiente de postres.
     */
    private void nextDessertButton() {
        jLabelDessert1.setIcon((Icon) nodeDessert1.next.element.getImage());
        jLabelDessert2.setIcon((Icon) nodeDessert2.next.element.getImage());
        jLabelDessert3.setIcon((Icon) nodeDessert3.next.element.getImage());
        nodeDessert1 = nodeDessert1.next;
        nodeDessert2 = nodeDessert2.next;
        nodeDessert3 = nodeDessert3.next;
    }

    /**
     * Añade funcion del carrucel de imagenes con el botón siguiente de otros.
     */
    private void nextOtherButton() {
        jLabelOther1.setIcon((Icon) nodeOther1.next.element.getImage());
        jLabelOther2.setIcon((Icon) nodeOther2.next.element.getImage());
        jLabelOther3.setIcon((Icon) nodeOther3.next.element.getImage());
        nodeOther1 = nodeOther1.next;
        nodeOther2 = nodeOther2.next;
        nodeOther3 = nodeOther3.next;
    }

    /**
     * Carga a los JLabel la información del siguiente conductor en la cola.
     */
    private void driverInformation() {
        Driver driver = driversList.peek();
        jLabelDriverName.setText(driver.getName());
        jLabelDriverId.setText(String.valueOf(driver.getId()));
    }

    /**
     * Verifica toda la información
     *
     * @return
     */
    private boolean verifyInformation() {
        if (jTextFieldNomCliente.getText().equals("")
                || jTextFieldCorreoCliente.getText().equals("")
                || jTextFieldTelCliente.getText().equals("")
                || jComboBoxProvinciaCliente.getSelectedItem().toString().equals("Seleccione una opción")
                || jTexfieldDireccion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Información del cliente incompleta");
            return false;
        } else if (this.total == 0) {
            JOptionPane.showMessageDialog(null, "No se ha agregado ningún producto.");
            return false;
        }
        return true;
    }

    /**
     * Reduce el precio de la factura cuando se elimina un producto.
     *
     * @param row
     */
    private void reducePrice(int row) {
        subTotal -= (double) tableModel.getValueAt(row, 3) * (int) tableModel.getValueAt(row, 2);
        iva -= ((double) tableModel.getValueAt(row, 3) * 10 / 100) * (int) tableModel.getValueAt(row, 2);
        total = subTotal + iva;
        jLabelSubTotal.setText(String.valueOf(subTotal));
        jLabelIva.setText(String.valueOf(iva));
        jLabelTotalPrice.setText(String.valueOf(total));
    }

    /**
     * Habilita el botón delet si se selecciona una fila en la tabla o lo
     * deshabilita si no se selecciona una fila
     */
    private void setEnabledDeleteButton() {
        if (jTableFactura.getSelectedRow() == -1) {
            jButtonDelete.setEnabled(false);
        } else {
            jButtonDelete.setEnabled(true);
        }
    }

    private Queue choseRestaurant() {
        int cont = 0;
        Queue<Restaurant> auxList = new LinkedList<>();
        for (int i = 0; i < restaurantsList.size(); i++) {
            for (int j = 0; j < restaurantsList.peek().getLocationList().size(); j++) {
                if (restaurantsList.peek().getLocationList().get(j).getProvince().equals(jComboBoxProvince.getSelectedItem().toString())
                        && cont == 0
                        && restaurantsList.peek().getLocationList().get(j).getLocation().equals(jComboBoxLocation.getSelectedItem().toString())) {
                    auxList.add(restaurantsList.peek());
                    cont++;
                }
            }
            cont = 0;
            restaurantsList.add(restaurantsList.poll());
        }
        return auxList;
    }

    private void threadNotification() {
        Notification notification = new Notification(jLabel7);
    }

    private int getLastIdClient() {
        return clientsList.size() + 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabelCliente = new javax.swing.JLabel();
        jLabelNombreCliente = new javax.swing.JLabel();
        jLabelCorreoCliente = new javax.swing.JLabel();
        jLabelTelCliente = new javax.swing.JLabel();
        jTextFieldNomCliente = new javax.swing.JTextField();
        jTextFieldTelCliente = new javax.swing.JTextField();
        jTextFieldCorreoCliente = new javax.swing.JTextField();
        jLabelRestaurante = new javax.swing.JLabel();
        jComboBoxProvinciaCliente = new javax.swing.JComboBox<>();
        jLabelRestaurant = new javax.swing.JLabel();
        jLabelFood3 = new javax.swing.JLabel();
        jLabelDrink1 = new javax.swing.JLabel();
        jLabelFood1 = new javax.swing.JLabel();
        jButtonNextFood = new javax.swing.JButton();
        jButtonPreviousDrink = new javax.swing.JButton();
        jButtonPreviousDessert = new javax.swing.JButton();
        jButtonPreviousOther = new javax.swing.JButton();
        jButtonNextDrink = new javax.swing.JButton();
        jButtonNextDessert = new javax.swing.JButton();
        jButtonNextOther = new javax.swing.JButton();
        jButtonPreviousFood = new javax.swing.JButton();
        jLabelDessert1 = new javax.swing.JLabel();
        jLabelOther1 = new javax.swing.JLabel();
        jLabelDrink2 = new javax.swing.JLabel();
        jLabelDessert2 = new javax.swing.JLabel();
        jLabelOther2 = new javax.swing.JLabel();
        jLabelDrink3 = new javax.swing.JLabel();
        jLabelDessert3 = new javax.swing.JLabel();
        jLabelOther3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelFood2 = new javax.swing.JLabel();
        jLabelDireccion = new javax.swing.JLabel();
        jTexfieldDireccion = new javax.swing.JTextField();
        jComboBoxLocation = new javax.swing.JComboBox<>();
        jButtonNextRestaurant = new javax.swing.JButton();
        jButtonClean = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxProvince = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldLastName1 = new javax.swing.JTextField();
        jTextFieldLastName2 = new javax.swing.JTextField();
        jLabelBordeProductos = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jLabelIdClient = new javax.swing.JLabel();
        jLabelFondoTab1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFactura = new javax.swing.JTable();
        jLabelSubtotal = new javax.swing.JLabel();
        jLabelIV = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButtonConfirmOrder = new javax.swing.JButton();
        jLabelSubTotal = new javax.swing.JLabel();
        jLabelIva = new javax.swing.JLabel();
        jLabelTotalPrice = new javax.swing.JLabel();
        jLabelDriverIcon = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabelDriverName = new javax.swing.JLabel();
        jLabelDriverId = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabelOrderNumber = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jLabelFondoTab2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelLogo = new javax.swing.JLabel();
        jLabelAgentName = new javax.swing.JLabel();
        jLabelAgentCode = new javax.swing.JLabel();
        jLabelBorder = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelFondo2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Date:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 40, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Time:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 100, -1, 20));

        jLabelDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 60, 160, 20));

        jLabelTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 100, 160, 20));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelCliente.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabelCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCliente.setText("Client");
        jPanel1.add(jLabelCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabelNombreCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelNombreCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombreCliente.setText("Name:");
        jPanel1.add(jLabelNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabelCorreoCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelCorreoCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCorreoCliente.setText("Email:");
        jPanel1.add(jLabelCorreoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        jLabelTelCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTelCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTelCliente.setText("Phone:");
        jPanel1.add(jLabelTelCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        jTextFieldNomCliente.setBackground(new java.awt.Color(0, 0, 51));
        jTextFieldNomCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextFieldNomCliente.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jTextFieldNomCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 260, 30));

        jTextFieldTelCliente.setBackground(new java.awt.Color(0, 0, 51));
        jTextFieldTelCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextFieldTelCliente.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldTelCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTelClienteKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldTelCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 240, 30));

        jTextFieldCorreoCliente.setBackground(new java.awt.Color(0, 0, 51));
        jTextFieldCorreoCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextFieldCorreoCliente.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldCorreoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCorreoClienteFocusGained(evt);
            }
        });
        jPanel1.add(jTextFieldCorreoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 240, 30));

        jLabelRestaurante.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabelRestaurante.setForeground(new java.awt.Color(255, 255, 255));
        jLabelRestaurante.setText("Restaurant");
        jPanel1.add(jLabelRestaurante, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        jComboBoxProvinciaCliente.setBackground(new java.awt.Color(0, 0, 102));
        jComboBoxProvinciaCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBoxProvinciaCliente.setForeground(new java.awt.Color(255, 255, 255));
        jComboBoxProvinciaCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select an option", "San José", "Alajuela", "Heredia", "Cartago", "Guanacaste", "Puntarenas", "Limón" }));
        jComboBoxProvinciaCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBoxProvinciaCliente.setName(""); // NOI18N
        jComboBoxProvinciaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProvinciaClienteActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxProvinciaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 240, 30));

        jLabelRestaurant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRestaurant.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/restaurantLogo.png"))); // NOI18N
        jPanel1.add(jLabelRestaurant, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 120, 80));

        jLabelFood3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFood3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelFood3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelFood3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelFood3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelFood3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, 90, 60));

        jLabelDrink1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDrink1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelDrink1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDrink1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDrink1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelDrink1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 90, 60));

        jLabelFood1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFood1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelFood1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelFood1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelFood1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelFood1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, 90, 60));

        jButtonNextFood.setBackground(new java.awt.Color(0, 0, 102));
        jButtonNextFood.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonNextFood.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNextFood.setText(">");
        jButtonNextFood.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonNextFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextFoodActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNextFood, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 130, 50, 40));

        jButtonPreviousDrink.setBackground(new java.awt.Color(0, 0, 102));
        jButtonPreviousDrink.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonPreviousDrink.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPreviousDrink.setText("<");
        jButtonPreviousDrink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPreviousDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousDrinkActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonPreviousDrink, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 50, 40));

        jButtonPreviousDessert.setBackground(new java.awt.Color(0, 0, 102));
        jButtonPreviousDessert.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonPreviousDessert.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPreviousDessert.setText("<");
        jButtonPreviousDessert.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPreviousDessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousDessertActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonPreviousDessert, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, 50, 40));

        jButtonPreviousOther.setBackground(new java.awt.Color(0, 0, 102));
        jButtonPreviousOther.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonPreviousOther.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPreviousOther.setText("<");
        jButtonPreviousOther.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPreviousOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousOtherActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonPreviousOther, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 370, 50, 40));

        jButtonNextDrink.setBackground(new java.awt.Color(0, 0, 102));
        jButtonNextDrink.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonNextDrink.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNextDrink.setText(">");
        jButtonNextDrink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonNextDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextDrinkActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNextDrink, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 210, 50, 40));

        jButtonNextDessert.setBackground(new java.awt.Color(0, 0, 102));
        jButtonNextDessert.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonNextDessert.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNextDessert.setText(">");
        jButtonNextDessert.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonNextDessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextDessertActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNextDessert, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 290, 50, 40));

        jButtonNextOther.setBackground(new java.awt.Color(0, 0, 102));
        jButtonNextOther.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonNextOther.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNextOther.setText(">");
        jButtonNextOther.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonNextOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextOtherActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNextOther, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 370, 50, 40));

        jButtonPreviousFood.setBackground(new java.awt.Color(0, 0, 102));
        jButtonPreviousFood.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonPreviousFood.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPreviousFood.setText("<");
        jButtonPreviousFood.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPreviousFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousFoodActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonPreviousFood, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 50, 40));

        jLabelDessert1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDessert1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelDessert1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDessert1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDessert1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelDessert1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 280, 90, 60));

        jLabelOther1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOther1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelOther1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelOther1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelOther1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelOther1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, 90, 60));

        jLabelDrink2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDrink2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelDrink2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDrink2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDrink2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelDrink2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 200, 90, 60));

        jLabelDessert2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDessert2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelDessert2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDessert2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDessert2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelDessert2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 280, 90, 60));

        jLabelOther2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOther2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelOther2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelOther2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelOther2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelOther2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 360, 90, 60));

        jLabelDrink3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDrink3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelDrink3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDrink3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDrink3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelDrink3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 200, 90, 60));

        jLabelDessert3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDessert3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelDessert3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDessert3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDessert3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelDessert3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 280, 90, 60));

        jLabelOther3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOther3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelOther3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelOther3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelOther3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelOther3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 360, 90, 60));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Province:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jLabelFood2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFood2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultFood.png"))); // NOI18N
        jLabelFood2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelFood2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelFood2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelFood2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 120, 90, 60));

        jLabelDireccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelDireccion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDireccion.setText("Address:");
        jPanel1.add(jLabelDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, 20));

        jTexfieldDireccion.setBackground(new java.awt.Color(0, 0, 51));
        jTexfieldDireccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTexfieldDireccion.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jTexfieldDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 240, 30));

        jComboBoxLocation.setBackground(new java.awt.Color(0, 0, 51));
        jComboBoxLocation.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBoxLocation.setForeground(new java.awt.Color(255, 255, 255));
        jComboBoxLocation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select an option", "North", "Central", "South" }));
        jComboBoxLocation.setSelectedItem("Provincia");
        jComboBoxLocation.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBoxLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLocationActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 170, 30));

        jButtonNextRestaurant.setBackground(new java.awt.Color(0, 0, 102));
        jButtonNextRestaurant.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonNextRestaurant.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNextRestaurant.setText(">");
        jButtonNextRestaurant.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonNextRestaurant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextRestaurantActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNextRestaurant, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, 50, 40));

        jButtonClean.setBackground(new java.awt.Color(0, 0, 102));
        jButtonClean.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonClean.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClean.setText("Clean");
        jButtonClean.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCleanActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonClean, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, 90, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Food:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Drink:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Dessert:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, -1, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Other:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 370, -1, -1));

        jComboBoxProvince.setBackground(new java.awt.Color(0, 0, 102));
        jComboBoxProvince.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBoxProvince.setForeground(new java.awt.Color(255, 255, 255));
        jComboBoxProvince.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select an option", "San José", "Alajuela", "Heredia", "Cartago", "Guanacaste", "Puntarenas", "Limón" }));
        jComboBoxProvince.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBoxProvince.setName(""); // NOI18N
        jComboBoxProvince.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProvinceActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxProvince, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 170, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Last name 1:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Last name 2:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jTextFieldLastName1.setBackground(new java.awt.Color(0, 0, 51));
        jTextFieldLastName1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextFieldLastName1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jTextFieldLastName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 220, 30));

        jTextFieldLastName2.setBackground(new java.awt.Color(0, 0, 51));
        jTextFieldLastName2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextFieldLastName2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jTextFieldLastName2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 220, 30));

        jLabelBordeProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jLabelBordeProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 600, 420));

        jLabelId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelId.setForeground(new java.awt.Color(255, 255, 255));
        jLabelId.setText("Id:");
        jPanel1.add(jLabelId, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, 30));

        jLabelIdClient.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelIdClient.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLabelIdClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 120, 30));

        jLabelFondoTab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/FondoAzul.png"))); // NOI18N
        jPanel1.add(jLabelFondoTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 490));

        jTabbedPane1.addTab("Page 1", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableFactura.setAutoCreateRowSorter(true);
        jTableFactura.setBackground(new java.awt.Color(204, 204, 255));
        jTableFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id Cliente", "Id Restaurante", "Id Producto", "Cantidad", "Total Items"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableFacturaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableFactura);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 730, 130));

        jLabelSubtotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelSubtotal.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSubtotal.setText("Subtotal:");
        jPanel2.add(jLabelSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        jLabelIV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelIV.setForeground(new java.awt.Color(255, 255, 255));
        jLabelIV.setText("IVA:");
        jPanel2.add(jLabelIV, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, -1, -1));

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelTotal.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotal.setText("TOTAL:");
        jPanel2.add(jLabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Next driver:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, -1, -1));

        jButtonConfirmOrder.setBackground(new java.awt.Color(0, 0, 102));
        jButtonConfirmOrder.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonConfirmOrder.setForeground(new java.awt.Color(255, 255, 255));
        jButtonConfirmOrder.setText("Confirm order");
        jButtonConfirmOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmOrderActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonConfirmOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 390, 170, 30));

        jLabelSubTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabelSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 350, 20));

        jLabelIva.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelIva.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabelIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 300, 30));

        jLabelTotalPrice.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelTotalPrice.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabelTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 310, 20));

        jLabelDriverIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDriverIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/driver2.png"))); // NOI18N
        jPanel2.add(jLabelDriverIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 130, 110));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Driver information");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Name:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, -1, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(240, 240, 240));
        jLabel16.setText("Id:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 390, -1, 20));

        jLabelDriverName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelDriverName.setForeground(new java.awt.Color(240, 240, 240));
        jPanel2.add(jLabelDriverName, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 360, 180, 20));

        jLabelDriverId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelDriverId.setForeground(new java.awt.Color(240, 240, 240));
        jPanel2.add(jLabelDriverId, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 390, 190, 20));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Order #:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 80, 20));

        jLabelOrderNumber.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelOrderNumber.setForeground(new java.awt.Color(240, 240, 240));
        jPanel2.add(jLabelOrderNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 110, 20));

        jButtonDelete.setBackground(new java.awt.Color(0, 0, 102));
        jButtonDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonDelete.setForeground(new java.awt.Color(240, 240, 240));
        jButtonDelete.setText("Delete row");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, -1, -1));

        jButtonNext.setBackground(new java.awt.Color(0, 0, 102));
        jButtonNext.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonNext.setForeground(new java.awt.Color(240, 240, 240));
        jButtonNext.setText("Next");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, -1, -1));

        jLabelFondoTab2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/FondoAzul.png"))); // NOI18N
        jPanel2.add(jLabelFondoTab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 450));

        jTabbedPane1.addTab("Page 2", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 1020, 470));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Agent Information");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Code:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, -1, -1));

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogoMediano.png"))); // NOI18N
        getContentPane().add(jLabelLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 140, -1));

        jLabelAgentName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelAgentName.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabelAgentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 430, 20));

        jLabelAgentCode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelAgentCode.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabelAgentCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 430, 20));

        jLabelBorder.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBorder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        getContentPane().add(jLabelBorder, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 530, 130));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 190, 50));

        jLabelFondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/FondoAzul.png"))); // NOI18N
        getContentPane().add(jLabelFondo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 620));

        jMenu1.setText("Salir");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Administrador");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxProvinciaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProvinciaClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxProvinciaClienteActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here:
        int salida = JOptionPane.showConfirmDialog(null,
                "Realmente desea salir de la apilcación?", "Confirmar salida",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (salida == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jTextFieldCorreoClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCorreoClienteFocusGained
        // TODO add your handling code here:
        for (int i = 0; i < clientsList.size(); i++) {
            if (jTextFieldCorreoCliente.getText().equals(clientsList.get(i).getEmail())) {
                jLabelId.setVisible(true);
                jTextFieldNomCliente.setText(clientsList.get(i).getName());
                jTextFieldLastName1.setText(clientsList.get(i).getLastName1());
                jTextFieldLastName2.setText(clientsList.get(i).getLastName2());
                jComboBoxProvinciaCliente.setSelectedItem(clientsList.get(i).getProvince());
                jTextFieldTelCliente.setText(clientsList.get(i).getPhone());
                jTexfieldDireccion.setText(clientsList.get(i).getAdress());
                jLabelIdClient.setText(clientsList.get(i).getId());
            }
        }
    }//GEN-LAST:event_jTextFieldCorreoClienteFocusGained

    private void jTextFieldTelClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTelClienteKeyTyped
        // TODO add your handling code here:
        if (!logic.isNumeric(evt)) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldTelClienteKeyTyped

    private void jButtonPreviousFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousFoodActionPerformed
        // TODO add your handling code here:
        previousFoodButton();
    }//GEN-LAST:event_jButtonPreviousFoodActionPerformed

    private void jButtonPreviousDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousDrinkActionPerformed
        // TODO add your handling code here:
        previousDrinkButton();
    }//GEN-LAST:event_jButtonPreviousDrinkActionPerformed

    private void jButtonPreviousDessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousDessertActionPerformed
        // TODO add your handling code here:
        previousDessertButton();
    }//GEN-LAST:event_jButtonPreviousDessertActionPerformed

    private void jButtonPreviousOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousOtherActionPerformed
        // TODO add your handling code here:
        previousOtherButton();
    }//GEN-LAST:event_jButtonPreviousOtherActionPerformed

    private void jButtonNextOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextOtherActionPerformed
        // TODO add your handling code here:
        nextOtherButton();
    }//GEN-LAST:event_jButtonNextOtherActionPerformed

    private void jButtonNextDessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextDessertActionPerformed
        // TODO add your handling code here:
        nextDessertButton();
    }//GEN-LAST:event_jButtonNextDessertActionPerformed

    private void jButtonNextDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextDrinkActionPerformed
        // TODO add your handling code here:
        nextDrinkButton();
    }//GEN-LAST:event_jButtonNextDrinkActionPerformed

    private void jButtonNextFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextFoodActionPerformed
        // TODO add your handling code here:
        nextFoodButton();

    }//GEN-LAST:event_jButtonNextFoodActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        try {
            // TODO add your handling code here:
            AdministratorInterface adm = new AdministratorInterface();
            adm.setVisible(true);
            dispose();

        } catch (ListException ex) {
            Logger.getLogger(AgentInterface.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(AgentInterface.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (StackException ex) {
            Logger.getLogger(AgentInterface.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenu2MouseClicked

    private void jComboBoxLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLocationActionPerformed
        // TODO add your handling code here:
        restaurantsByProvince = choseRestaurant();
        try {
            if (!jComboBoxProvince.getSelectedItem().equals("Select an option")) {
                if (restaurantsByProvince.size() == 1) {
                    jButtonNextRestaurant.setEnabled(false);
                } else {
                    jButtonNextRestaurant.setEnabled(true);
                }
                jLabelRestaurant.setIcon(restaurantsByProvince.peek().getImage());
                initializeImages();
            }
        } catch (ListException ex) {
            Logger.getLogger(AgentInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AgentInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxLocationActionPerformed

    private void jButtonNextRestaurantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextRestaurantActionPerformed
        try {
            // TODO add your handling code here:
            restaurantsByProvince.add(restaurantsByProvince.poll());

            jLabelRestaurant.setIcon(restaurantsByProvince.peek().getImage());
            initializeImages();
        } catch (ListException ex) {
            Logger.getLogger(AgentInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AgentInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonNextRestaurantActionPerformed

    private void jButtonConfirmOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmOrderActionPerformed
        try {
            if (verifyInformation() == true) {

                //Añadir la orden a la pila y actualizar en la clase data
                ordersStack.push(new Order(jTextFieldNomCliente.getText(), ordersStack.getSize() + 1, jLabelAgentName.getText(),
                        logic.getDate(), this.total, "Cartago", jTexfieldDireccion.getText(), driversList.peek().getName()));
                data.setOrdersStack(ordersStack);
                //Actializar cola de conductores
                logic.updateDriverQueue(driversList);

                //Verifica y añade un cliente en caso de que éste no esté en la lista
                Client c = new Client(String.valueOf(getLastIdClient()), jTextFieldNomCliente.getText(),
                        jTextFieldLastName1.getText(), jTextFieldLastName2.getText(), jTextFieldCorreoCliente.getText(), jTextFieldTelCliente.getText(),
                        jComboBoxProvinciaCliente.getSelectedItem().toString(), jTexfieldDireccion.getText());
                logic.saveClients(c);

                JOptionPane.showMessageDialog(null, "Pedido realizado con exito!!!");

                AgentInterface adm = new AgentInterface();
                adm.cleanTextFields();
                adm.setVisible(true);
                dispose();

            }
        } catch (ListException | IOException | StackException ex) {
            Logger.getLogger(AgentInterface.class
                    .getName()).log(Level.SEVERE, null, ex);

        }
    }//GEN-LAST:event_jButtonConfirmOrderActionPerformed

    private void jLabelFood1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelFood1MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeFood1.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeFood1.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelFood1MouseClicked

    private void jLabelFood2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelFood2MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeFood2.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeFood2.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelFood2MouseClicked

    private void jLabelFood3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelFood3MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeFood3.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeFood3.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelFood3MouseClicked

    private void jButtonCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCleanActionPerformed
        // TODO add your handling code here:
        cleanTextFields();
//        System.out.println(jLabelRestaurant.getIcon().toString());
    }//GEN-LAST:event_jButtonCleanActionPerformed

    private void jComboBoxProvinceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProvinceActionPerformed
        // TODO add your handling code here:
        restaurantsByProvince = choseRestaurant();
        try {
            if (!jComboBoxLocation.getSelectedItem().equals("Select an option")) {
                if (restaurantsByProvince.size() == 1) {
                    jButtonNextRestaurant.setEnabled(false);
                } else {
                    jButtonNextRestaurant.setEnabled(true);
                }
                jLabelRestaurant.setIcon(restaurantsByProvince.peek().getImage());
                initializeImages();
            }
        } catch (ListException ex) {
            Logger.getLogger(AgentInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AgentInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxProvinceActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // TODO add your handling code here:
        if (jTableFactura.getSelectedRow() != -1) {
            reducePrice(jTableFactura.getSelectedRow());
            tableModel.removeRow(jTableFactura.getSelectedRow());
            setEnabledDeleteButton();
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            // TODO add your handling code here:
            new Thread(new Logic()).start();

        } catch (ListException | IOException ex) {
            Logger.getLogger(AgentInterface.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        // TODO add your handling code here:
        logic.updateDriverQueue(driversList);
        driverInformation();
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jTableFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableFacturaMouseClicked
        // TODO add your handling code here:
        setEnabledDeleteButton();
    }//GEN-LAST:event_jTableFacturaMouseClicked

    private void jLabelDrink1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDrink1MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeDrink1.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeDrink1.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelDrink1MouseClicked

    private void jLabelDrink2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDrink2MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeDrink2.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeDrink2.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelDrink2MouseClicked

    private void jLabelDrink3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDrink3MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeDrink3.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeDrink3.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelDrink3MouseClicked

    private void jLabelDessert1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDessert1MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeDessert1.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeDessert1.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelDessert1MouseClicked

    private void jLabelDessert2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDessert2MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeDessert2.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeDessert2.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelDessert2MouseClicked

    private void jLabelDessert3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDessert3MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeDessert3.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeDessert3.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelDessert3MouseClicked

    private void jLabelOther1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelOther1MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeOther1.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeOther1.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelOther1MouseClicked

    private void jLabelOther2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelOther2MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeOther2.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeOther2.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelOther2MouseClicked

    private void jLabelOther3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelOther3MouseClicked
        // TODO add your handling code here:
        if (restaurantsByProvince.size() != 0) {
            logic.addDataInBillTable(restaurantsByProvince.peek().getName(), nodeOther3.element, tableModel, contTable, jLabelIdClient.getText());
            updatePrice(nodeOther3.element);
            threadNotification();
        }
    }//GEN-LAST:event_jLabelOther3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgentInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgentInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgentInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgentInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AgentInterface().setVisible(true);

                } catch (ListException | IOException ex) {
                    Logger.getLogger(AgentInterface.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClean;
    private javax.swing.JButton jButtonConfirmOrder;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonNextDessert;
    private javax.swing.JButton jButtonNextDrink;
    private javax.swing.JButton jButtonNextFood;
    private javax.swing.JButton jButtonNextOther;
    private javax.swing.JButton jButtonNextRestaurant;
    private javax.swing.JButton jButtonPreviousDessert;
    private javax.swing.JButton jButtonPreviousDrink;
    private javax.swing.JButton jButtonPreviousFood;
    private javax.swing.JButton jButtonPreviousOther;
    private javax.swing.JComboBox<String> jComboBoxLocation;
    private javax.swing.JComboBox<String> jComboBoxProvince;
    private javax.swing.JComboBox<String> jComboBoxProvinciaCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAgentCode;
    private javax.swing.JLabel jLabelAgentName;
    private javax.swing.JLabel jLabelBordeProductos;
    private javax.swing.JLabel jLabelBorder;
    private javax.swing.JLabel jLabelCliente;
    private javax.swing.JLabel jLabelCorreoCliente;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelDessert1;
    private javax.swing.JLabel jLabelDessert2;
    private javax.swing.JLabel jLabelDessert3;
    private javax.swing.JLabel jLabelDireccion;
    private javax.swing.JLabel jLabelDrink1;
    private javax.swing.JLabel jLabelDrink2;
    private javax.swing.JLabel jLabelDrink3;
    private javax.swing.JLabel jLabelDriverIcon;
    private javax.swing.JLabel jLabelDriverId;
    private javax.swing.JLabel jLabelDriverName;
    private javax.swing.JLabel jLabelFondo2;
    private javax.swing.JLabel jLabelFondoTab1;
    private javax.swing.JLabel jLabelFondoTab2;
    private javax.swing.JLabel jLabelFood1;
    private javax.swing.JLabel jLabelFood2;
    private javax.swing.JLabel jLabelFood3;
    private javax.swing.JLabel jLabelIV;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelIdClient;
    private javax.swing.JLabel jLabelIva;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelNombreCliente;
    private javax.swing.JLabel jLabelOrderNumber;
    private javax.swing.JLabel jLabelOther1;
    private javax.swing.JLabel jLabelOther2;
    private javax.swing.JLabel jLabelOther3;
    private javax.swing.JLabel jLabelRestaurant;
    private javax.swing.JLabel jLabelRestaurante;
    private javax.swing.JLabel jLabelSubTotal;
    private javax.swing.JLabel jLabelSubtotal;
    private javax.swing.JLabel jLabelTelCliente;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalPrice;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableFactura;
    private javax.swing.JTextField jTexfieldDireccion;
    private javax.swing.JTextField jTextFieldCorreoCliente;
    private javax.swing.JTextField jTextFieldLastName1;
    private javax.swing.JTextField jTextFieldLastName2;
    private javax.swing.JTextField jTextFieldNomCliente;
    private javax.swing.JTextField jTextFieldTelCliente;
    // End of variables declaration//GEN-END:variables
}
