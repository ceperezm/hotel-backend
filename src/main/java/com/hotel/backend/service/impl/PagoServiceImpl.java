package com.hotel.backend.service.impl;

import com.hotel.backend.dto.PagoDTO;
import com.hotel.backend.dto.mapper.PagoMapper;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.model.Factura;
import com.hotel.backend.model.Pago;
import com.hotel.backend.repository.local.FacturaRepository;
import com.hotel.backend.repository.local.PagoRepository;
import com.hotel.backend.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {
    private final PagoRepository pagoRepository;
    private final FacturaRepository facturaRepository;
    private final PagoMapper pagoMapper;

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public PagoDTO registrarPago(PagoDTO dto) {
        Factura factura = facturaRepository.findById(dto.getFacturaId())
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + dto.getFacturaId()));

        Pago pago = pagoMapper.toEntity(dto);
        pago.setFactura(factura);

        return pagoMapper.toDTO(pagoRepository.save(pago));
    }

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public PagoDTO actualizarPago(Long id, PagoDTO dto) {
        Pago pagoExistente = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));

        Factura factura = facturaRepository.findById(dto.getFacturaId())
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + dto.getFacturaId()));

        pagoExistente.setMonto(dto.getMonto());
        pagoExistente.setMetodo(dto.getMetodo());
        pagoExistente.setReferencia(dto.getReferencia());
        pagoExistente.setFechaPago(dto.getFechaPago());
        pagoExistente.setFactura(factura);

        return pagoMapper.toDTO(pagoRepository.save(pagoExistente));
    }

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public void eliminarPago(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
    }

    @Override
    public PagoDTO obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id)
                .map(pagoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
    }

    @Override
    public List<PagoDTO> listarPagosPorFactura(Long facturaId) {
        return pagoRepository.findByFacturaId(facturaId).stream()
                .map(pagoMapper::toDTO)
                .toList();
    }

    @Override
    public List<PagoDTO> listarTodosPagos() {
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toDTO)
                .toList();
    }
}
