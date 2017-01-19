package com.mindbuilders.cognitivemoodlog;

/**
 * Created by Peter on 1/18/2017.
 */

public class CognitiveDistortionobj {
    private int id;
    private String name;
    private String Description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString(){
        return getName();
    }
}

