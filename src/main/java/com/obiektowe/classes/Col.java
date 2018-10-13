package com.obiektowe.classes;

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

    public boolean add(Object object) {

        if (!object.getClass().getSimpleName().equals(this.type)) {
            return false;
        } else {
            objects.add(object);
            return true;
        }

    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }


}
