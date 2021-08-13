package com.uriegas.Model;

import javafx.beans.property.*;
/**
 * Representation of an offer.
 */
public class Offer implements Searchable {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private IntegerProperty product_id = new SimpleIntegerProperty();
    /**
     * DB constructor (includes id)
     * @param id
     * @param name
     * @param description
     */
    public Offer(int id, String name, String description, int product_id){ setId(id); setName(name); setDescription(description); setProduct_id(product_id); }
    /**
     * UI constructor (without id)
     * @param id
     * @param name
     * @param description
     */
    public Offer(int id, String name, String description){ setId(id); setName(name); setDescription(description); }

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
    public IntegerProperty product_idProperty(){ return product_id; }
    public int getProduct_id(){ return product_id.get(); }
    public void setProduct_id(int product_id){ this.product_id.set(product_id); }
    // <== Getters and Setters
    public int compareTo(Searchable o){
        return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(o.getId()));
    }
    public String toString(){ return "Offer(" + getId() + ")" + ", " + getName() + ", " + getDescription() + ", " + getProduct_id(); }
}
