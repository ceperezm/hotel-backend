package com.hotel.backend.service.impl;

import com.hotel.backend.dto.mapper.UsuarioMapper;
import com.hotel.backend.dto.usuario.UsuarioDTORequest;
import com.hotel.backend.dto.usuario.UsuarioDTOResponse;
import com.hotel.backend.enums.ERole;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.exception.UsuarioYaExiste;
import com.hotel.backend.model.Rol;
import com.hotel.backend.model.Usuario;
import com.hotel.backend.repository.RolRepository;
import com.hotel.backend.repository.UsuarioRepository;
import com.hotel.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTOResponse findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public UsuarioDTOResponse findUsuarioByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con nombre de usuario: " + nombreUsuario));
    }

    @Override
    public List<UsuarioDTOResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    @Override
    public UsuarioDTOResponse crearUsuario(UsuarioDTORequest dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UsuarioYaExiste("El correo ya está registrado.");
        }
        if (usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
            throw new UsuarioYaExiste("Ya existe un usuario con ese nombre de usuario.");
        }

        // Convertir String a Enum
        ERole rolEnum;
        try {
            rolEnum = ERole.valueOf("ROLE_" + dto.getRol().toUpperCase());// dto.getRol() debe ser "ROLE_RECEPCIONISTA" o "ROLE_ADMIN"
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Rol inválido: " + dto.getRol());
        }

        Rol rol = rolRepository.findByNombre(rolEnum)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + dto.getRol()));

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setRol(rol);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTOResponse actualizarUsuario(Long id, UsuarioDTORequest dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // Convertir String a Enum
        ERole rolEnum;
        try {
            rolEnum = ERole.valueOf("ROLE_" + dto.getRol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Rol inválido: " + dto.getRol());
        }

        Rol rol = rolRepository.findByNombre(rolEnum)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + dto.getRol()));

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // Cifrar también en la actualización
        usuario.setRol(rol);

        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }


    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
