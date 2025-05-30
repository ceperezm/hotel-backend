package com.hotel.backend.repository.cloud;

import com.hotel.backend.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepositoryCloud extends JpaRepository<Pago, Long> {
    List<Pago> findByFacturaId(Long facturaId);
}
