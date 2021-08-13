package com.uriegas.Model;

import javafx.beans.property.*;

/**
 * Representation of a product.
 */
public class Product implements Searchable {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty stock = new SimpleIntegerProperty();
    /**
     * DB constructor (includes id)
     * @param id
     * @param name
     * @param description
     * @param price
     */
    public Product(int id, String name, String description, double price, int stock) { setId(id); setName(name); setDescription(description); setPrice(price); setStock(stock); }
    /**
     * UI constructor (without id)
     * @param name
     * @param description
     * @param price
     */
    public Product(String name, String description, double price) { setName(name); setDescription(description); setPrice(price); }
    /**
     * Searchable constructor
     * @param id
     * @param name
     * @param description
     */
    // public Product(int id, String name, String description) { setId(id); setName(name); setDescription(description); }

    // ==> Getters and Setters
    public IntegerProperty idProperty(){ return id; }
    public int getId(){ return id.get(); }
    public void setId(int id){ this.id.set(id); }
    public StringProperty nameProperty(){ return name; }
    public String getName(){ return name.get(); }
    public void setName(String name){ this.name.set(name); }
    public StringProperty descriptionProperty(){ return description; }
    public String getDescription(){ return description.get(); }
    public void setDescription(String description){ this.description.set(description); }
    public DoubleProperty priceProperty(){ return price; }
    public double getPrice(){ return price.get(); }
    public void setPrice(double price){ this.price.set(price); }
    public IntegerProperty stockProperty(){ return stock; }
    public int getStock(){ return stock.get(); }
    public void setStock(int stock){ this.stock.set(stock); }
    // <== Getters and Setters
    public int compareTo(Searchable o){
        return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(o.getId()));
    }
    public String toString(){ return "Product(" + getId() + "): " + getName() + ", " + getDescription() + ", " + getPrice() + ", " + getStock(); }
}
