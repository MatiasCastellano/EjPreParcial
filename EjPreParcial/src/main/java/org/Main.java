package org;

import org.dto.FinalizarReservaDTO;
import org.dto.NuevaReservaDTO;
import org.dto.ReservaDTO;
import org.dto.ResultadoDTO;
import org.service.Logica;

import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            boolean continuar = true;
            Logica logica = Logica.getInstance();
            ResultadoDTO resultado= new ResultadoDTO();
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
                        resultado = logica.crearReserva(nuevaReserva);
                        imprimirResultado(resultado);
                        break;
                    case 2:
                        in.nextLine();
                        System.out.println("Ingrese el Id de la reserva que desea finalizar");
                        long idReserva = in.nextInt();
                        in.nextLine();
                        System.out.println("Ingrese la fecha FIN efectiva(yyyy-MM-dd)");
                        String fechaFinEfectiva = in.nextLine();
                        LocalDate fechaFinefec = LocalDate.parse(fechaFinEfectiva);
                        FinalizarReservaDTO fin= new FinalizarReservaDTO(idReserva,fechaFinefec);
                        resultado= logica.finalizarReserva(fin);
                        imprimirResultado(resultado);
                        break;
                    case 4:
                        continuar = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        }

        private static void mostrarMenu () {
            System.out.println("Ingrese la opcion que desea hacer:");
            System.out.println("1. Crear Reserva");
            System.out.println("2. Finalizar una reserva y calcular costos adicionales");
            System.out.println("3. Buscar Reserva con filtros");
            System.out.println("4. Salir");
        }

        private static void imprimirResultado(ResultadoDTO resultado){
            if (resultado.getSucceso()) {
                System.out.println(resultado.getMensaje());
            } else {
                System.out.println(resultado.getMensaje());
            }
        }

}