package org.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class VehiculoDTO {

    private long id;
    private String patente;
    private String marca;
    private String modelo;
    private double precioRenta;
    private boolean disponibilidad;

    public VehiculoDTO(){};
    public VehiculoDTO(String patenteVeh, String marcaV, String modeloV, double precio, boolean disp){
        this.patente=patenteVeh;
        this.marca= marcaV;
        this.modelo=modeloV;
        this.precioRenta= precio;
        this.disponibilidad=disp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecioRenta() {
        return precioRenta;
    }

    public void setPrecioRenta(double precioRenta) {
        this.precioRenta = precioRenta;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
