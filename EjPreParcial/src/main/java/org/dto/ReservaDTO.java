package org.dto;

import java.time.LocalDate;

public class ReservaDTO {
    private long id;
    private String nombreCliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double costo;
    private EstadoDTO estadoDTO;

    public ReservaDTO(){};
    public ReservaDTO(String nombre, LocalDate fechaI,LocalDate fechaF, double costoRes){
        this.nombreCliente=nombre;
        this.fechaInicio=fechaI;
        this.fechaFin=fechaF;
        this.costo=costoRes;
        this.estadoDTO=EstadoDTO.RESERVED;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public EstadoDTO getEstadoDTO() {
        return estadoDTO;
    }

    public void setEstadoDTO(EstadoDTO estadoDTO) {
        this.estadoDTO = estadoDTO;
    }

    public enum EstadoDTO{
        RESERVED,
        COMPLETED,
        CANCELED
    }


}
