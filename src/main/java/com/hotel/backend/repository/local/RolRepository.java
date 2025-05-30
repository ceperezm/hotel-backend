package com.hotel.backend.repository.local;

import com.hotel.backend.enums.ERole;
import com.hotel.backend.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(ERole nombre);
}
