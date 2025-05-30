package com.hotel.backend.model;

import com.hotel.backend.enums.ERole;
import com.hotel.backend.listeners.EntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListener.class)
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ERole nombre;

    // Relación con usuarios
    @OneToMany(mappedBy = "rol")
    private Set<Usuario> usuarios;
}