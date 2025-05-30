package com.hotel.backend.repository.cloud;

import com.hotel.backend.enums.EstadoReserva;
import com.hotel.backend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepositoryCoud extends JpaRepository<Reserva, Long> {
    List<Reserva> findByHuespedId(Long huespedId);
    List<Reserva> findByEstadoReserva(EstadoReserva estado);

    @Query(""" 
    SELECT r FROM Reserva r
    WHERE r.habitacion.id = :habitacionId
    AND (r.fechaCheckin < :fechaCheckout AND r.fechaCheckout > :fechaCheckin)""")
    List<Reserva> findReservasEnConflicto(
            @Param("habitacionId") Long habitacionId,
            @Param("fechaCheckin") LocalDate fechaCheckin,
            @Param("fechaCheckout") LocalDate fechaCheckout
    );
    List<Reserva> findByUsuarioId(Long usuarioId);

}
