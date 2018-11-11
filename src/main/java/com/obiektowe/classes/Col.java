package com.obiektowe.classes;

import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;

import java.util.ArrayList;

public class Col {

    private String name;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    private ArrayList<Object> objects;

    private String type;

    public Col(String name, String type) {
        this.name = name;
        this.type = type;
        this.objects = new ArrayList<>();
    }

    public boolean add(Object object) throws WrongInsertionTypeException {

        boolean inserted = false;

        if (!object.getClass().getSimpleName().equals(getType())) {
            throw new WrongInsertionTypeException("Is: " + object.getClass().getSimpleName() + ", should be: " + this.type);
        } else {
            objects.add(object);
            inserted = true;
        }

        return inserted;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }


}
