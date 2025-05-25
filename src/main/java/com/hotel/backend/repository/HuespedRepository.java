package com.hotel.backend.repository;

import com.hotel.backend.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    Optional<Huesped> findByDocumentoIdentidad(String documentoIdentidad);
    Optional<Huesped> findHuespedById(Long huespedId);
}
