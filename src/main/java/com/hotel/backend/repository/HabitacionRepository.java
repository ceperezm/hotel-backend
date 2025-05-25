package com.hotel.backend.repository;

import com.hotel.backend.model.Habitacion;
import com.hotel.backend.enums.EstadoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByEstado(EstadoHabitacion estado);
    boolean existsByNumero(String numero);
}
