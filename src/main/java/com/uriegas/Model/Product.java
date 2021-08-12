package com.uriegas.Model;

import javafx.beans.property.*;

/**
 * Representation of a product.
 */
public class Product implements Searchable {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty description;
    private DoubleProperty price;
    /**
     * DB constructor (includes id)
     * @param id
     * @param name
     * @param description
     * @param price
     */
    public Product(int id, String name, String description, double price) { setId(id); setName(name); setDescription(description); setPrice(price); }
    /**
     * UI constructor (without id)
     * @param name
     * @param description
     * @param price
     */
    public Product(String name, String description, double price) { setName(name); setDescription(description); setPrice(price); }

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
    // <== Getters and Setters
    public int compareTo(Searchable o){
        return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(o.getId()));
    }
}
