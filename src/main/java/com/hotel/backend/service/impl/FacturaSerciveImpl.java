package com.hotel.backend.service.impl;

import com.hotel.backend.dto.FacturaDTO;
import com.hotel.backend.dto.mapper.FacturaMapper;
import com.hotel.backend.exception.FacturaYaExiste;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.model.Factura;
import com.hotel.backend.model.Reserva;
import com.hotel.backend.repository.local.FacturaRepository;
import com.hotel.backend.repository.local.ReservaRepository;
import com.hotel.backend.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  // <-- Import correcto de Spring


import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaSerciveImpl implements FacturaService {
    private final FacturaRepository facturaRepository;
    private final ReservaRepository reservaRepository;
    private final FacturaMapper facturaMapper;
    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public FacturaDTO generarFactura(FacturaDTO dto) {
        Reserva reserva = reservaRepository.findById(dto.getReservaId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservación no encontrada"));

        if (facturaRepository.existsByNumeroFactura(dto.getNumeroFactura())) {
            throw new FacturaYaExiste("Ya existe una factura con el número: " + dto.getNumeroFactura());
        }

        Factura factura = facturaMapper.toEntity(dto);
        factura.setReserva(reserva);

        factura = facturaRepository.save(factura);
        return facturaMapper.toDTO(factura);
    }

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public FacturaDTO actualizarFactura(Long id, FacturaDTO dto) {
        Factura facturaExistente = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + id));

        // Validar si el número de factura está en uso por otra factura
        if (!facturaExistente.getNumeroFactura().equals(dto.getNumeroFactura()) &&
                facturaRepository.existsByNumeroFactura(dto.getNumeroFactura())) {
            throw new FacturaYaExiste("El número de factura ya está en uso.");
        }

        Reserva reserva = reservaRepository.findById(dto.getReservaId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservación no encontrada"));

        facturaExistente.setNumeroFactura(dto.getNumeroFactura());
        facturaExistente.setFechaEmision(dto.getFechaEmision());
        facturaExistente.setFechaVencimiento(dto.getFechaVencimiento());
        facturaExistente.setEstadoPago(dto.getEstadoPago());
        facturaExistente.setTotal(dto.getTotal());
        facturaExistente.setReserva(reserva);

        facturaExistente = facturaRepository.save(facturaExistente);
        return facturaMapper.toDTO(facturaExistente);
    }

    @Override
    public FacturaDTO obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id)
                .map(facturaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + id));
    }

    @Override
    public List<FacturaDTO> listarFacturas() {
        return facturaRepository.findAll().stream()
                .map(facturaMapper::toDTO)
                .toList();
    }


    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public void eliminarFactuta(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + id));

        facturaRepository.delete(factura);
    }

}
