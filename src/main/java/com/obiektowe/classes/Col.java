package com.obiektowe.classes;

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
    }

    public boolean add(Object object) {
        if (object.getClass().getName() != this.type) {
            return false;
        } else {
            objects.add(object);
            return true;
        }

    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }


}
