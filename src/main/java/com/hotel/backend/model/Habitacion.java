package com.hotel.backend.model;

import com.hotel.backend.enums.EstadoHabitacion;
import com.hotel.backend.listeners.EntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "habitaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListener.class)
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String numero;

    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado;

    @Lob
    private String descripcion;

    // Relación con reservaciones
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL)
    private List<Reserva> reservaciones = new ArrayList<>();

    // Relación con tipo de habitaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_habitacion_id")
    private TipoHabitacion tipoHabitacion;
}