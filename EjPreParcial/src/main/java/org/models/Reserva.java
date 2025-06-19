package org.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name= "reservation")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private long idReserva;


    @Column(name="client_name",nullable = false,length =100)
    private String nombreCliente;

    @ManyToOne
    @JoinColumn(name= "vehicle_id")
    private Vehiculo vehiculo;

    @Column(name="start_date",nullable = false)
    private LocalDate fechaInicio;

    @Column(name="end_date",nullable = false)
    private LocalDate fechaFin;

    @Column(name="total_cost", nullable = false)
    private double costoTotal;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    public Reserva(){}

    public Reserva(String nombre,Vehiculo vehiculo,LocalDate fechaI, LocalDate fechaF, double costo){
        this.nombreCliente=nombre;
        this.vehiculo=vehiculo;
        this.fechaInicio=fechaI;
        this.fechaFin=fechaF;
        this.costoTotal=costo;
        this.estado=Estado.RESERVED;
    }
    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(long idReserva) {
        this.idReserva = idReserva;
    }

    public void setEstado(Estado estado1){
        this.estado=estado1;
    }
    public Estado getEstado(){
        return estado;
    }

    public enum Estado{
        RESERVED,
        COMPLETED,
        CANCELLED
    }





}
