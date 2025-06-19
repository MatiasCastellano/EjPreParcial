package org.dto;

import java.time.LocalDate;

public class FinalizarReservaDTO {
    private long idReserva;
    private LocalDate fechaFinEfectiva;

    public FinalizarReservaDTO(){};
    public FinalizarReservaDTO(long id,LocalDate fechaF){
        this.idReserva=id;
        this.fechaFinEfectiva= fechaF;
    }

    public long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(long idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getFechaFinEfectiva() {
        return fechaFinEfectiva;
    }

    public void setFechaFinEfectiva(LocalDate fechaFinEfectiva) {
        this.fechaFinEfectiva = fechaFinEfectiva;
    }
}
