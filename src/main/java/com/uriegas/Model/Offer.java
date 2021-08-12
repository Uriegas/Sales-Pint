package com.uriegas.Model;

import javafx.beans.property.*;
/**
 * Representation of an offer.
 */
public class Offer implements Searchable {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty type;
    private IntegerProperty product_id;
    /**
     * DB constructor (includes id)
     * @param id
     * @param name
     * @param type
     */
    public Offer(int id, String name, String type, int product_id){ setId(id); setName(name); setType(type); setProduct_id(product_id); }
    /**
     * UI constructor (without id)
     * @param name
     * @param type
     */
    public Offer(String name, String type){ setName(name); setType(type); }

    // ==> Getters and Setters
    public IntegerProperty idProperty(){ return id; }
    public int getId(){ return id.get(); }
    public void setId(int id){ this.id.set(id); }
    public StringProperty nameProperty(){ return name; }
    public String getName(){ return name.get(); }
    public void setName(String name){ this.name.set(name); }
    public StringProperty typeProperty(){ return type; }
    public String getType(){ return type.get(); }
    public void setType(String type){ this.type.set(type); }
    public IntegerProperty product_idProperty(){ return product_id; }
    public int getProduct_id(){ return product_id.get(); }
    public void setProduct_id(int product_id){ this.product_id.set(product_id); }
    // <== Getters and Setters
    public int compareTo(Searchable o){
        return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(o.getId()));
    }
}
