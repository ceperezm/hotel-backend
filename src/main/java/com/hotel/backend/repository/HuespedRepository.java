package com.hotel.backend.repository;

import com.hotel.backend.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    Optional<Huesped> findByDocumentoIdentidad(String documentoIdentidad);
    Optional<Huesped> findHuespedById(Long huespedId);
}
