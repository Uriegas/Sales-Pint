package com.uriegas.Model;

import javafx.beans.property.*;

/**
 * Represents an employee.
 */
public class Employee implements Searchable {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    /**
     * The employee's constructor.
     * @param id The employee's id.
     * @param name The employee's name.
     */
    public Employee(int id, String name) {
        this.id.set(id);
        this.name.set(name);
    }
    /**
     * Constructor with password. <br>
     * Use only for creating new employees.
     * @param id The employee's id.
     * @param name The employee's name.
     * @param password The employee's password.
     */
    public Employee(int id, String name, String password) {
        this.id.set(id);
        this.name.set(name);
        this.password.set(password);
    }
    // ==> Getters and setters
    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public IntegerProperty idProperty() { return id; }
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public StringProperty descriptionProperty() { return name; }
    public String getDescription() { return name.get(); }
    public void setDescription(String description) { this.name.set(description); }
    public StringProperty passwordProperty() { return password; }
    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    // <== Getters and setters
    public int compareTo(Searchable o){
        return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(((Employee)o).getId()));
    }
    @Override
    public String toString() {return "Employee(" + id.get() + ", " + name.get() + ", " + password.get() + ")";}
}
