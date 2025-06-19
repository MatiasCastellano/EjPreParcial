package org.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.dto.NuevaReservaDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Reserva;
import org.models.Vehiculo;
import org.utils.HibernateUtil;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class Logica {
    private static Logica instance;

    private Logica() {
    }

    public static Logica getInstance() {
        if (instance == null) {
            instance = new Logica();
        }
        return instance;
    }
    //crear reserva
    public boolean crearReserva(NuevaReservaDTO reservaDTO){
        try(Session session= HibernateUtil.getSession()){
            Transaction transaccion= session.beginTransaction();
            CriteriaBuilder cb= session.getCriteriaBuilder();
            CriteriaQuery<Vehiculo> query=cb.createQuery(Vehiculo.class);
            Root<Vehiculo> vehiculo= query.from(Vehiculo.class);
            Predicate predicate= cb.equal(vehiculo.get("id"),reservaDTO.getIdVeh());
            query.select(vehiculo).where(predicate);
            List<Vehiculo> vehiculosList= session.createQuery(query).getResultList(); //la lista contendra 1 vehiculo o ninguno si no lo encontro
            if(vehiculosList.isEmpty()){return false;} //no se encontro el vehiculo con el que se quiere hacer la reserva
            if(!vehiculosList.get(0).getDisponibilidad()){return false;} // no esta disponible
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
            transaccion.commit();
            return true;
        }
    }
    
}
