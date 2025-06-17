package org.service;

import org.dto.NuevaReservaDTO;

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
    //crear reserva
    public boolean crearReserva(NuevaReservaDTO reservaDTO){
        

        return true;
    }
}
