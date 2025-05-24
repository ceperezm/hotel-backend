package com.hotel.backend.repository;

import com.hotel.backend.model.Reservacion;
import com.hotel.backend.model.emuns.EstadoReservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
    List<Reservacion> findByHuespedId(Long huespedId);
    List<Reservacion> findByEstadoReservacion(EstadoReservacion estado);

    @Query(""" 
    SELECT r FROM Reservacion r
    WHERE r.habitacion.id = :habitacionId
    AND (r.fechaCheckin < :fechaCheckout AND r.fechaCheckout > :fechaCheckin)""")
    List<Reservacion> findReservasEnConflicto(
            @Param("habitacionId") Long habitacionId,
            @Param("fechaCheckin") LocalDate fechaCheckin,
            @Param("fechaCheckout") LocalDate fechaCheckout
    );
}
