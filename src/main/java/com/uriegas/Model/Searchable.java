package com.uriegas.Model;

import javafx.beans.property.*;

/**
 * Interface for a searchable object.
 */
public interface Searchable extends Comparable<Searchable> {
    public IntegerProperty idProperty();
    public int getId();
    public void setId(int id);
    public StringProperty nameProperty();
    public String getName();
    public void setName(String name);
    public StringProperty descriptionProperty();
    public String getDescription();
    public void setDescription(String description);
}
