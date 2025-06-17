package org.dto;

import java.time.LocalDate;

public class NuevaReservaDTO {
    private String nombreCliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private long idVeh;

    public NuevaReservaDTO(String nombre, LocalDate fechaI, LocalDate fechaF, long IdVehSolicitado){
        this.nombreCliente= nombre;
        this.fechaInicio=fechaI;
        this.fechaFin=fechaF;
        this.idVeh=IdVehSolicitado;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public long getIdVeh() {
        return idVeh;
    }

    public void setIdVeh(long idVeh) {
        this.idVeh = idVeh;
    }
}
