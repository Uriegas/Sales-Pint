package com.uriegas.Model;
/**
 * Interface for a searchable object.
 */
public interface Searchable extends Comparable<Searchable> {
    public int getId();
    public void setId(int id);
    public String getName();
    public void setName(String name);
}
