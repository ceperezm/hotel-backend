package com.hotel.backend.repository.local;

import com.hotel.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByNombreUsuario(String username);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    //findByNombreUsuarioWithRol
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.nombreUsuario = :nombreUsuario")
    Optional<Usuario> findByNombreUsuarioWithRol(@Param("nombreUsuario") String nombreUsuario);

}
