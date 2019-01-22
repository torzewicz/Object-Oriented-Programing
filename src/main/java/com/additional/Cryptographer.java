package com.additional;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cryptographer {

    public static void cryptfile(File fileToEncrypt, File outputFile, Algorithm algorithm) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileToEncrypt.getAbsolutePath())), StandardCharsets.UTF_8);

        String[] words = content.split("\\s+");

        String output = "";

        for (String word : words) {
            output += algorithm.crypt(word);
        }

        System.out.println(output);

        PrintWriter out = new PrintWriter(outputFile.getName());

        out.println(output);

    }


    public static void decryptfile(File fileToDecrypt, File outputFile, Algorithm algorithm) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileToDecrypt.getPath())), StandardCharsets.UTF_8);

        String[] words = content.split("\\s+");

        String output = "";

        for (String word : words) {
            output += algorithm.decrypt(word);
        }

        System.out.println(output);

        PrintWriter out = new PrintWriter(outputFile.getName());

        out.println(output);

    }

}
