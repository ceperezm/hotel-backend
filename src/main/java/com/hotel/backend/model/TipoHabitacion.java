package com.hotel.backend.model;

import com.hotel.backend.listeners.EntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipos_habitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListener.class)
public class TipoHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nombre;

    @Column(name = "precio_por_noche", precision = 10, scale = 2)
    @Positive
    private BigDecimal precioPorNoche;

    @Positive
    private Integer capacidad;

    // Relación con habitaciones
    @OneToMany(mappedBy = "tipoHabitacion")
    private List<Habitacion> habitaciones = new ArrayList<>();
}
