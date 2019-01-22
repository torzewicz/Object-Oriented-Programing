package com.additional;

public class Polibiusz implements Algorithm {

    @Override
    public String crypt(String word) {
        String crypted = "";
        int charIndex = 0;
        for (charIndex = 0; charIndex < word.length(); charIndex ++) {
            for (int i = 0; i < chessBoard.length; i++) {
                for (int j = 0; j < chessBoard[i].length; j++) {
                    if (chessBoard[i][j].equals(String.valueOf(word.charAt(charIndex)))) {
                        crypted += Integer.toString(i) + Integer.toString(j) + " ";
                    }
                }
            }
        }
        return crypted;
    }

    @Override
    public String decrypt(String word) {
        String[] splitted = word.split(" ");
        String encrypted = "";

        for (String split : splitted) {
            char[] indexes = split.toCharArray();
            encrypted += chessBoard[Character.getNumericValue(indexes[0])][Character.getNumericValue(indexes[1])];
        }

        return encrypted;
    }

}
