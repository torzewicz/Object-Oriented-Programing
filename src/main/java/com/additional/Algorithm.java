package com.additional;

public interface Algorithm {

    String[][] chessBoard = {
            {"A", "B", "C", "D", "E"},
            {"F", "G", "H", "I", "J"},
            {"K", "L", "M", "N", "O"},
            {"P", "R", "S", "T", "U"},
            {"V", "W", "X", "Y", "Z"},
            {".", ",", "-", "_", "Q"},
    };

    String crypt(String word);

    String decrypt(String word);

}
