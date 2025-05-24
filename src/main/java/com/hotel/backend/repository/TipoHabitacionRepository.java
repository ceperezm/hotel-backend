package com.hotel.backend.repository;

import com.hotel.backend.model.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoHabitacionRepository extends JpaRepository<TipoHabitacion, Long> {
    Optional<TipoHabitacion> findByNombre(String nombre);
}
