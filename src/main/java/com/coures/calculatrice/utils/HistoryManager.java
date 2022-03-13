package com.coures.calculatrice.utils;

import com.coures.calculatrice.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    private static ArrayList<History> histories;
    static {
        histories = new ArrayList<>();
    }
    public static void add(History h){
        histories.add(h);
    }
    public static void clear(){
        histories.clear();
    }

    public static ArrayList<History> get(){return histories;}
}
