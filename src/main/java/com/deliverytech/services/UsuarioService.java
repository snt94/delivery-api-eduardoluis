package com.deliverytech.services;


import com.deliverytech.dto.request.LoginRequestDTO;
import com.deliverytech.dto.request.UsuarioRequestDTO;
import com.deliverytech.dto.response.LoginResposeDTO;
import com.deliverytech.dto.response.UsuarioResponseDTO;

public interface UsuarioService {

    UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto);

    LoginResposeDTO login(LoginRequestDTO dto);

}
