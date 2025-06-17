package org.service;

public class Logica {
    private static Logica instance;

    private Logica() {
    }

    public static Logica getInstance() {
        if (instance == null) {
            instance = new Logica();
        }
        return instance;
    }
}
