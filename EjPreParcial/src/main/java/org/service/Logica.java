package org.service;

import jakarta.persistence.criteria.*;
import org.dto.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Reserva;
import org.models.Vehiculo;
import org.utils.HibernateUtil;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Logica {
    private static Logica instance;
    ResultadoDTO resultado= new ResultadoDTO();

    private Logica() {
    }

    public static Logica getInstance() {
        if (instance == null) {
            instance = new Logica();
        }
        return instance;
    }
    //crear reserva
    public ResultadoDTO crearReserva(NuevaReservaDTO reservaDTO){
        try(Session session= HibernateUtil.getSession()){
            Transaction transaction= session.beginTransaction();
            CriteriaBuilder cb= session.getCriteriaBuilder();
            CriteriaQuery<Vehiculo> query=cb.createQuery(Vehiculo.class);
            Root<Vehiculo> vehiculo= query.from(Vehiculo.class);
            Predicate predicate= cb.equal(vehiculo.get("id"),reservaDTO.getIdVeh());
            query.select(vehiculo).where(predicate);
            List<Vehiculo> vehiculosList= session.createQuery(query).getResultList(); //la lista contendra 1 vehiculo o ninguno si no lo encontro
            if(vehiculosList.isEmpty()){
                resultado.setSucceso(false);
                resultado.setMensaje("no se encontro el vehiculo con el que se quiere hacer la reserva");
                return resultado;
            }
            if(!vehiculosList.get(0).getDisponibilidad()){
                resultado.setSucceso(false);
                resultado.setMensaje("El vehiculo no esta disponible");
            }
            long cantDias= ChronoUnit.DAYS.between(reservaDTO.getFechaInicio(),reservaDTO.getFechaFin());
            if(cantDias==0){cantDias=1;}
            double costoReserva= cantDias*vehiculosList.get(0).getPrecioRenta();
            Reserva reservaNueva= new Reserva();
            reservaNueva.setCostoTotal(costoReserva);
            reservaNueva.setFechaFin(reservaDTO.getFechaFin());
            reservaNueva.setFechaInicio(reservaDTO.getFechaInicio());
            reservaNueva.setVehiculo(vehiculosList.get(0));
            reservaNueva.setNombreCliente(reservaDTO.getNombreCliente());
            reservaNueva.setEstado(Reserva.Estado.RESERVED);
            vehiculosList.get(0).setDisponibilidad(false);
            session.persist(reservaNueva);
            transaction.commit();
            resultado.setSucceso(true);
            resultado.setMensaje("Reserva creada con exito");
            return resultado;
        }
    }
    //finalizar reserva
    public ResultadoDTO finalizarReserva(FinalizarReservaDTO finReserva){
        try(Session session= HibernateUtil.getSession()){
            Transaction transaction= session.beginTransaction();
            CriteriaBuilder cb= session.getCriteriaBuilder();
            CriteriaQuery<Reserva> query= cb.createQuery(Reserva.class);
            Root<Reserva> reserva= query.from(Reserva.class);
            Predicate predicate= cb.equal(reserva.get("idReserva"),finReserva.getIdReserva());
            query.select(reserva).where(predicate);
            List<Reserva> reservaList= session.createQuery(query).getResultList();
            if(reservaList.isEmpty()){
                resultado.setSucceso(false);
                resultado.setMensaje("No existe la reserva que desea finalizar");
                return resultado;
            }
            Reserva reserva1= reservaList.get(0);
            if(!(reserva1.getEstado()== Reserva.Estado.RESERVED)){
                resultado.setSucceso(false);
                resultado.setMensaje("La reserva que se desea finalizar, NO esta activa. No es posible finalizarla");
                return resultado;
            }
            if(reserva1.getFechaFin().isBefore(finReserva.getFechaFinEfectiva())){
                //si se cumple, quiere decir que hay que penalizar, el vehiculo se devolvio mas tarde.
                long cantDiasExcedido= ChronoUnit.DAYS.between(reserva1.getFechaFin(),finReserva.getFechaFinEfectiva());
                double costoAgregadoPorDia= reserva1.getCostoTotal()*0.10;
                double costoAgregado= costoAgregadoPorDia*cantDiasExcedido;
                reserva1.setCostoTotal(reserva1.getCostoTotal()+costoAgregado);
            }
            reserva1.setEstado(Reserva.Estado.COMPLETED);
            Vehiculo vehiculoUsado= reserva1.getVehiculo();
            vehiculoUsado.setDisponibilidad(true);
            resultado.setSucceso(true);
            resultado.setMensaje("Reserva finalizada con exito");
            transaction.commit();
            return resultado;
        }
    }

    //Buscar Reservas con filtros dinamicos
    public List<ReservaDTO> buscarReservas(FiltrosReservaDTO filtros){
        List<ReservaDTO> reservasFiltradas= new ArrayList<>();
        try(Session session=HibernateUtil.getSession()){
            Transaction transaccion= session.beginTransaction();
            CriteriaBuilder cb= session.getCriteriaBuilder();
            CriteriaQuery<Reserva> query= cb.createQuery(Reserva.class);
            Root<Reserva> reserva= query.from(Reserva.class);
            Join<Reserva,Vehiculo> vehiculo= reserva.join("vehiculo");
            List<Predicate> predicate= new ArrayList<>();
            predicate.add(cb.like(reserva.get("nombreCliente"),filtros.getNombreCliente())); //el de nombre es obligatorio.
            if(filtros.getFechaInicio()!=null){
                predicate.add(cb.greaterThanOrEqualTo(reserva.get("fechaInicio"),filtros.getFechaInicio()));
            }
            if(filtros.getFechaFin()!=null){
                predicate.add(cb.lessThanOrEqualTo(reserva.get("fechaInicio"),filtros.getFechaFin()));
            }
            if(filtros.getEstado()!=null){
                Reserva.Estado estadoParametro= Reserva.Estado.valueOf(filtros.getEstado().name());
                predicate.add(cb.equal(reserva.get("estado"),estadoParametro));
            }
            if(!(filtros.getMarca().isEmpty())){
                predicate.add(cb.like(vehiculo.get("marca"),filtros.getMarca()));
            }
            query.select(reserva).where(cb.and(predicate.toArray(new Predicate[0]))).orderBy(cb.desc(reserva.get("fechaInicio")));

            List<Reserva> reservasList= session.createQuery(query).getResultList();
            reservasFiltradas= convertirReservaAReservaDTO(reservasList);
            return reservasFiltradas;
        }
    }

    public List<ReservaDTO> convertirReservaAReservaDTO(List<Reserva> reservas){
        List<ReservaDTO> reservasConvertidas= new ArrayList<>();
        for(Reserva r:reservas){
            ReservaDTO rDto= new ReservaDTO();
            rDto.setId(r.getIdReserva());
            rDto.setNombreCliente(r.getNombreCliente());
            rDto.setEstadoDTO(ReservaDTO.EstadoDTO.valueOf(r.getEstado().name())); //Convertir de estado a EstadoDTO
            rDto.setCosto(r.getCostoTotal());
            rDto.setVehiculoDTO(convertirVehiculoAVehiculoDTO(r.getVehiculo()));
            rDto.setFechaInicio(r.getFechaInicio());
            rDto.setFechaFin(r.getFechaFin());
            reservasConvertidas.add(rDto);
        }
        return reservasConvertidas;
    }
    public VehiculoDTO convertirVehiculoAVehiculoDTO(Vehiculo veh){
        if(veh==null){return null;}
        VehiculoDTO vDto= new VehiculoDTO();
        vDto.setDisponibilidad(veh.getDisponibilidad());
        vDto.setId(veh.getId());
        vDto.setMarca(veh.getMarca());
        vDto.setModelo(veh.getModelo());
        vDto.setPatente(veh.getPatente());
        vDto.setPrecioRenta(veh.getPrecioRenta());
        return vDto;
    }
}
