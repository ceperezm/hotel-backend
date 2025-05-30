package com.hotel.backend.model;

import com.hotel.backend.listeners.EntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListener.class)
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    @PositiveOrZero
    private BigDecimal monto;

    @NotBlank
    private String metodo;

    @NotBlank
    private String referencia;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    //Relacion con factura
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;
}