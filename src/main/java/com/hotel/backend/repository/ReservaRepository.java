package com.hotel.backend.repository;
import com.hotel.backend.model.Reserva;
import com.hotel.backend.model.emuns.EstadoReservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByHuespedId(Long huespedId);
    List<Reserva> findByEstadoReserva(EstadoReservacion estado);

    @Query(""" 
    SELECT r FROM Reserva r
    WHERE r.habitacion.id = :habitacionId
    AND (r.fechaCheckin < :fechaCheckout AND r.fechaCheckout > :fechaCheckin)""")
    List<Reserva> findReservasEnConflicto(
            @Param("habitacionId") Long habitacionId,
            @Param("fechaCheckin") LocalDate fechaCheckin,
            @Param("fechaCheckout") LocalDate fechaCheckout
    );
}
