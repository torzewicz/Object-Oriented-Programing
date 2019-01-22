package com.additional;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

//        if (args.length == 0 || args.length > 1) {
//            throw new Exception("Wrong input file");
//        }
//
//        String pathToFile = args[0];

        System.out.println("encrypt/decrypt?");

        Polibiusz polibiusz = new Polibiusz();

        Scanner scanner = new Scanner(System.in);
        String procedure = scanner.nextLine();
        System.out.println("You've chosen: " + procedure);


        if (procedure.equals("encrypt")) {
            Cryptographer.cryptfile(new File("src/main/java/com/additional/dawaj.txt"), new File("./elko.txt"), polibiusz);
        }

        else {
            Cryptographer.decryptfile(new File("src/main/java/com/additional/dawaj.txt"), new File("./narka.txt"), polibiusz);
        }

    }
}
