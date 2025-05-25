package com.hotel.backend.repository;

import com.hotel.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombreUsuario(String nombre);
    Boolean existsByEmail(String email);
    Boolean existsByNombreUsuario(String username);
}
