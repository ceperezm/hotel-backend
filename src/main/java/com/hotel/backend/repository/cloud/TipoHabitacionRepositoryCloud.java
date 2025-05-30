package com.hotel.backend.repository.cloud;

import com.hotel.backend.model.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoHabitacionRepositoryCloud extends JpaRepository<TipoHabitacion, Long> {
    Optional<TipoHabitacion> findByNombre(String nombre);
}
