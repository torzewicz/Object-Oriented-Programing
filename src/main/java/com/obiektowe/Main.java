package com.obiektowe;

import com.obiektowe.classes.DataFrame;

public class Main {

    public static void main(String[] args) {

        String[] names = {"Imie", "Nazwisko", "Wiek"};
        String[] types = {"String", "String", "Integer"};

        DataFrame dataFrame = null;

        try {
            dataFrame = new DataFrame(names, types);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataFrame.dropDatabase();

        dataFrame.insert("Imie", "Pawel");
        dataFrame.insert("Wiek", 17);


    }

}
