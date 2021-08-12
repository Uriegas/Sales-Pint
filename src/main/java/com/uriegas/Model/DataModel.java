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
    public static int ID = 1, NAME = 2, DESCRIPTION = 3, TYPE = 3, PRICE = 4, PRODUCT_ID = 4;

    private Connection connection;
    StringProperty s = new SimpleStringProperty();

    public DataModel() {
        setS("Hola");
        setConnection();
    }

    public void setConnection() {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        }catch(SQLException e){
            System.out.println(ERROR + " " + LocalDate.now() + " " + e.getMessage());
            connection = null;
        }
    }   

    public StringProperty sProperty() { return s; }
    public String getS() { return s.get(); }
    public void setS(String s) { this.s.set(s); }

    /**
     * Get products and offers from database
     */
    public ObservableList<Searchable> getSearchables() {
        // Write query for a join btw products and offers
        String query = "SELECT products.id, products.name, products.price, offers.quantity, offers.price, offers.id "
                + "FROM products "
                + "LEFT JOIN offers ON products.id = offers.product_id "
                + "ORDER BY products.name";
        // Create a list to store the products
        ObservableList<Searchable> searchables = FXCollections.observableArrayList();
        try {
            // Open connection
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Get products from database
            while (rs.next()) { // Instantiate products and offers
            }
            // Close connection
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
            products.add(new Product(rs.getInt(ID), rs.getString(NAME), rs.getString(DESCRIPTION), rs.getInt(PRICE)));
        stmt.close();
        rs.close();
        return products;
    }
}
