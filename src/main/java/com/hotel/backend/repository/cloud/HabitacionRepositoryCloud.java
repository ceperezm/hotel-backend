package com.hotel.backend.repository.cloud;

import com.hotel.backend.enums.EstadoHabitacion;
import com.hotel.backend.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepositoryCloud extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByEstado(EstadoHabitacion estado);
    boolean existsByNumero(String numero);
}
