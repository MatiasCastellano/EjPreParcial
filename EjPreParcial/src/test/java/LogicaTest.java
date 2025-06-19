import org.dto.FiltrosReservaDTO;
import org.dto.ReservaDTO;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.models.Reserva;
import org.models.Vehiculo;
import org.service.Logica;
import org.utils.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogicaTest {
    private Logica logica;
    private Session session;
    private Vehiculo vehiculo1;
    private Vehiculo vehiculo2;
    private Vehiculo vehiculo3;
    private Reserva reserva1;
    private Reserva reserva2;
    private Reserva reserva3;
    @BeforeAll
    public void setUp(){
        logica=Logica.getInstance();
        session= HibernateUtil.getSession();
        session.beginTransaction();

        vehiculo1=new Vehiculo();
        vehiculo1.setMarca("Toyota");
        vehiculo1.setModelo("Hilux");
        vehiculo1.setPatente("AG150AI");
        vehiculo1.setPrecioRenta(1000.0);
        vehiculo1.setDisponibilidad(true);

        vehiculo2=new Vehiculo();
        vehiculo2.setMarca("Ford");
        vehiculo2.setModelo("Ranger");
        vehiculo2.setPatente("AF180LG");
        vehiculo2.setPrecioRenta(900.0);
        vehiculo2.setDisponibilidad(true);

        vehiculo3=new Vehiculo();
        vehiculo3.setMarca("Honda");
        vehiculo3.setModelo("Civic");
        vehiculo3.setPatente("AA143LL");
        vehiculo3.setPrecioRenta(800.0);
        vehiculo3.setDisponibilidad(true);

        session.persist(vehiculo1);
        session.persist(vehiculo2);
        session.persist(vehiculo3);

        reserva1=new Reserva();
        reserva1.setVehiculo(vehiculo1);
        reserva1.setNombreCliente("Matias");
        reserva1.setEstado(Reserva.Estado.RESERVED);
        reserva1.setFechaInicio(LocalDate.of(2025, 6, 1));
        reserva1.setFechaFin(LocalDate.of(2025, 6, 5));
        reserva1.setCostoTotal(400.0);

        reserva2=new Reserva();
        reserva2.setVehiculo(vehiculo2);
        reserva2.setNombreCliente("Carlos");
        reserva2.setEstado(Reserva.Estado.COMPLETED);
        reserva2.setFechaInicio(LocalDate.of(2025, 5, 10));
        reserva2.setFechaFin(LocalDate.of(2025, 5, 15));
        reserva2.setCostoTotal(600.0);

        reserva3=new Reserva();
        reserva3.setVehiculo(vehiculo3);
        reserva3.setNombreCliente("Francisco");
        reserva3.setEstado(Reserva.Estado.CANCELLED);
        reserva3.setFechaInicio(LocalDate.of(2025, 7, 1));
        reserva3.setFechaFin(LocalDate.of(2025, 7, 3));
        reserva3.setCostoTotal(220.0);

        session.persist(reserva1);
        session.persist(reserva2);
        session.persist(reserva3);
        session.getTransaction().commit();
    }
    @AfterAll
    void tearDown() {
        if (session != null && session.isOpen()) {
            session.beginTransaction();
            session.createQuery("delete from Reserva").executeUpdate();
            session.createQuery("delete from Vehiculo").executeUpdate();
            session.getTransaction().commit();
            session.close();
        }
    }
    @Test
    public void testCantReservas_Ok(){
        FiltrosReservaDTO filtros= new FiltrosReservaDTO();
        filtros.setNombreCliente("Matias");
        List<ReservaDTO> reservas=logica.buscarReservas(filtros);
        assertEquals(reservas.size(),1);
    }

}
