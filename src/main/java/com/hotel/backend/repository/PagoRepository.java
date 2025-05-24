package com.hotel.backend.repository;

import com.hotel.backend.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByFacturaId(Long facturaId);
}
