package com.hotel.backend.model;

import com.hotel.backend.enums.EstadoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_factura", unique = true)
    private String numeroFactura;

    @Column(precision = 10, scale = 2)
    @PositiveOrZero
    private BigDecimal total;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento")
    @Future
    private LocalDate fechaVencimiento;

    @Column(name = "estado_pago")
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    // Relación con pagos
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Pago> pagos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;
}
