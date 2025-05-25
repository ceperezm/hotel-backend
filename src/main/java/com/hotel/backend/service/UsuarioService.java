package com.hotel.backend.service;

import com.hotel.backend.dto.usuario.UsuarioDTORequest;
import com.hotel.backend.dto.usuario.UsuarioDTOResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {
    UsuarioDTOResponse findUsuarioById(Long id);
    UsuarioDTOResponse findUsuarioByNombreUsuario(String nombreUsuario);
    List<UsuarioDTOResponse> listarUsuarios();
    UsuarioDTOResponse crearUsuario(UsuarioDTORequest usuarioDTO);
    UsuarioDTOResponse actualizarUsuario(Long id, UsuarioDTORequest usuarioDTO);
    void eliminarUsuario(Long id);

}
