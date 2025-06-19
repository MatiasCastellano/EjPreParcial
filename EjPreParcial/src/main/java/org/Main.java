package org;

import org.dto.NuevaReservaDTO;
import org.dto.ReservaDTO;
import org.service.Logica;

import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

            boolean continuar = true;
            Logica logica = Logica.getInstance();
            Scanner in = new Scanner(System.in);
            while (continuar) {
                mostrarMenu();
                int accion = in.nextInt();
                switch (accion) {
                    case 1:
                        in.nextLine();
                        System.out.println("Ingrese el nombre del cliente");
                        String nombre = in.nextLine();
                        System.out.println("Ingrese la fecha INICIO(yyyy-MM-dd)");
                        String fechaI = in.nextLine();
                        System.out.println("Ingrese la fecha FIN(yyyy-MM-dd)");
                        String fechaF = in.nextLine();
                        System.out.println("Ingrese el Id de vehiculo a alquilar");
                        long id = in.nextInt();
                        LocalDate fechaInicio = LocalDate.parse(fechaI);
                        LocalDate fechaFin = LocalDate.parse(fechaF);
                        NuevaReservaDTO nuevaReserva = new NuevaReservaDTO(nombre, fechaInicio, fechaFin, id);
                        boolean resultado = logica.crearReserva(nuevaReserva);
                        if (resultado) {
                            System.out.println("Reserva creada con exito");
                        } else {
                            System.out.println("No se ha podido crear la reserva");
                        }
                        break;

                    case 4:
                        continuar = false;
                        break;
                }
            }

    }

    private static void mostrarMenu(){
        System.out.println("Ingrese la opcion que desea hacer:");
        System.out.println("1. Crear Reserva");
        System.out.println("2. Finalizar una reserva y calcular costos adicionales");
        System.out.println("3. Buscar Reserva con filtros");
        System.out.println("4. Salir");
    }
}