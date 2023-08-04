package com.example.unscramblejava.data;

import java.util.Arrays;
import java.util.List;

public final class DataProvider {

    public static final int MAX_NO_OF_WORDS = 10;
    public static final int SCORE_INCREASE = 20;
    private DataProvider(){}
    public static final List<String> words = Arrays.asList(
            "kot", "pies", "drab", "kawa", "samolot", "wino", "gracz", "woda", "dom",
            "karma", "tanie", "bilet", "muzyk", "rybak", "chleb", "motyw",
            "las", "papuga", "talerz", "stacja", "grupa", "butelka", "kurczak",
            "okno", "drzewo", "lampa", "sklep", "kasa", "broda", "papier", "szafa",
            "dzwon", "kotek"
    );
}