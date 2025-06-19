package org.dto;

import org.models.Reserva;

import java.time.LocalDate;

public class FiltrosReservaDTO {
    private String nombreCliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ReservaDTO.EstadoDTO estado;
    private String marca;
    public FiltrosReservaDTO(){};

    public FiltrosReservaDTO(String nombre, LocalDate fechaI, LocalDate fechaF, ReservaDTO.EstadoDTO estadoRes, String Marca){
        this. nombreCliente=nombre;
        this.fechaInicio=fechaI;
        this.fechaFin=fechaF;
        this.estado=estadoRes;
        this.marca=Marca;
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

    public ReservaDTO.EstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(ReservaDTO.EstadoDTO estado) {
        this.estado = estado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
