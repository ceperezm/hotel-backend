package com.hotel.backend.repository;

import com.hotel.backend.model.Habitacion;
import com.hotel.backend.enums.EstadoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByEstado(EstadoHabitacion estado);
    boolean existsByNumero(String numero);
}
