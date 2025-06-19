package org;

import org.dto.*;
import org.service.Logica;

import java.time.LocalDate;
import java.util.List;
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
                LocalDate fechaInicioFiltro= null;
                LocalDate fechaFinFiltro=null;
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
                    case 3:
                        in.nextLine();
                        System.out.println("Ingrese el nombre del cliente que desea filtrar(obligatorio):");
                        nombre= in.nextLine();
                        in.nextLine();
                        System.out.println("Ingrese la fecha INICIO(yyyy-MM-dd), si desea filtrar por ella(opcional)");
                        fechaI = in.nextLine();
                        System.out.println("Ingrese la fecha FIN(yyyy-MM-dd), si desea filtrar por ella(opcional)");
                        fechaF = in.nextLine();
                        System.out.println("Ingrese si desea filtrar por el estado de la reserva: 0) Reserved 1)Completed 2)Cancelled o 3 si desea no filtrar por el");
                        int num= in.nextInt();
                        in.nextLine();
                        System.out.println("Ingrese la marca si desea filtrar por ella");
                        String marca= in.nextLine();
                        if(!fechaI.isEmpty()){
                            fechaInicioFiltro=LocalDate.parse(fechaI);
                        }
                        if(!fechaF.isEmpty()){
                            fechaFinFiltro=LocalDate.parse(fechaF);
                        }
                        ReservaDTO.EstadoDTO estado=null;
                        if(num!=3){
                            estado= ReservaDTO.EstadoDTO.values()[num];
                        }
                        FiltrosReservaDTO filtros= new FiltrosReservaDTO(nombre,fechaInicioFiltro,fechaFinFiltro,estado,marca);
                        List<ReservaDTO> reservasDto= logica.buscarReservas(filtros);
                        imprimirLista(reservasDto);
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
        private static void imprimirLista(List<ReservaDTO> reservasDTO){
            for(ReservaDTO r:reservasDTO){
                System.out.println("Id reserva"+r.getId());
                System.out.println("Nombre reserva"+r.getNombreCliente());
                System.out.println("Costo reserva"+r.getCosto());
                System.out.println("Fecha Inicio reserva"+r.getFechaInicio());
                System.out.println("Fecha Fin reserva"+r.getFechaFin());
                System.out.println("Marca del auto usado"+ r.getvehiculo().getMarca());
            }
        }

}