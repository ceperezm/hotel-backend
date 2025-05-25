package com.hotel.backend.service.impl;

import com.hotel.backend.dto.usuario.UsuarioDTORequest;
import com.hotel.backend.dto.usuario.UsuarioDTOResponse;
import com.hotel.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Override
    public UsuarioDTOResponse findUsuarioById(Long id) {
        return null;
    }

    @Override
    public UsuarioDTOResponse findUsuarioByNombreUsuario(String nombreUsuario) {
        return null;
    }

    @Override
    public List<UsuarioDTOResponse> listarUsuarios() {
        return List.of();
    }

    @Override
    public UsuarioDTOResponse crearUsuario(UsuarioDTORequest usuarioDTO) {
        return null;
    }

    @Override
    public UsuarioDTOResponse actualizarUsuario(Long id, UsuarioDTORequest usuarioDTO) {
        return null;
    }

    @Override
    public void eliminarUsuario(Long id) {

    }
}
