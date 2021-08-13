package com.uriegas.Model;

import java.sql.*;
import java.time.LocalDate;

import javafx.beans.property.*;
import javafx.collections.*;

/**
 * Data model, load the database
 */
public class DataModel {
    public static final String DB_PATH = "data.db";
    
    public static String ERROR ="\033[91m[ERROR]\033[0m ";
    public static String WARNING ="\033[93m[WARNING]\033[0m ";
    public static String INFO ="\033[94m[INFO]\033[0m ";
    public static String DEBUG ="\033[95m[DEBUG]\033[0m ";
    public static String SUCCESS ="\033[92m[SUCCESS]\033[0m ";

    public static String PRODUCTS = "products";
    public static String OFFERS = "offers";
    public static String SALES = "sales";
    public static String ORDERS = "orders";//An order is a bunch of sales
    public static int ID = 1, NAME = 2, DESCRIPTION = 3, TYPE = 3, PRICE = 4, PRODUCT_ID = 4, STOCK = 5;

    private Connection connection;
    //The cart is a list of products
    private ObservableList<Product> cart = FXCollections.observableArrayList();
    DoubleProperty totalpriceProperty = new SimpleDoubleProperty(0.0);
    //  cart.forEach(p -> p.getPrice()).sum(p -> p.getPrice());

    //Total price property to be equal to the sum of the prices times the quantity of the products in the cart
    public DoubleProperty totalpriceProperty() {
        return totalpriceProperty;
    }
    public void setTotalPrice(double totalprice) {
        this.totalpriceProperty.set(totalprice);
    }
    public void addToTotalPrice(double price) {
        this.totalpriceProperty.set(this.totalpriceProperty.get() + price);
    }
    public double getTotalPrice() {
        return totalpriceProperty.get();
    }

    public DataModel() {
        setConnection();
        cart.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
                setTotalPrice(cartProperty().stream().mapToDouble(p -> p.getPrice() * p.getStock()).sum());
            }
        });
    }

    public void setConnection() {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        }catch(SQLException e){
            System.out.println(ERROR + " " + LocalDate.now() + " " + e.getMessage());
            connection = null;
        }
    }   
    public ObservableList<Product> cartProperty() {
        return cart;
    }
    public void addToCart(Searchable searchable) {
        if(searchable instanceof Product) {
            Product product = (Product) searchable;
            int stock = 1;
            for(Product p : cart) {
                if(p.getId() == product.getId()) {
                    stock = p.getStock() + 1;
                    cart.remove(p);
                    break;
                }
            }
            cart.add(new Product(product.getId(), product.getName(), product.getDescription(), product.getPrice(), stock));
            // cart.add(product);
        } else if(searchable instanceof Offer) {
            Offer offer = (Offer) searchable;
            System.out.println("Implement parsing offers: " + offer.getId());
        }
    }
    public void addToCart(Product product) {
        cart.add(product);
    }
    public void removeFromCart(Product product) {
        cart.remove(product);
    }
    public void clearCart() {
        cart.clear();
    }
    public void insertCartToDB() throws SQLException {
        for(Product product : cart)
            upsertProduct(product);
        System.out.println(INFO + " " + LocalDate.now() + " " + cart.size() + " products added to the cart");
    }
    /**
     * Insert or update a product in the database 
     */
    public void upsertProduct(Product product) throws SQLException {
        String query = "INSERT INTO " + PRODUCTS + " VALUES (?, ?, ?, ?, ?, ?)" +
                       " ON CONFLICT (id) DO UPDATE SET name = " + product.getName() +
                       ", description = " + product.getDescription() +
                       ", price = " + product.getPrice() + ", stock = " + product.getStock();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        System.out.println(INFO + " " + LocalDate.now() + " " + "Product " + product.getName() + " inserted");
    }

    /**
     * Get products and offers from database
     */
    public ObservableList<Searchable> getSearchables() {
        // Write query for a join btw products and offers
        String query = "SELECT * FROM " + PRODUCTS;
        // Create a list to store the products
        ObservableList<Searchable> searchables = FXCollections.observableArrayList();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                searchables.add(new Product(rs.getInt(ID), rs.getString(NAME), rs.getString(DESCRIPTION), rs.getDouble(PRICE), rs.getInt(STOCK) ));
            rs = stmt.executeQuery("SELECT * FROM " + OFFERS);
            while (rs.next())
                searchables.add(new Offer(rs.getInt(ID), rs.getString(NAME), rs.getString(TYPE), rs.getInt(PRODUCT_ID)));
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(ERROR + " " + LocalDate.now() + " " + e.getMessage());
        }
        return searchables;
    }
    public ObservableList<Product> getProducts() throws SQLException {
        // Write query for a select of products
        String query = "SELECT * FROM " +PRODUCTS + " ORDER BY id;";
        // Create a list to store the products
        ObservableList<Product> products = FXCollections.observableArrayList();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next())
            products.add(new Product(rs.getInt(ID), rs.getString(NAME), rs.getString(DESCRIPTION), rs.getInt(PRICE), rs.getInt(STOCK)));
        stmt.close();
        rs.close();
        return products;
    }
}
