package com.deliverytech.controller;

import com.deliverytech.dto.request.LoginRequestDTO;
import com.deliverytech.dto.request.UsuarioRequestDTO;
import com.deliverytech.dto.response.LoginResposeDTO;
import com.deliverytech.dto.response.UsuarioResponseDTO;
import com.deliverytech.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO login = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(login);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResposeDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResposeDTO login = usuarioService.login(dto);
        return ResponseEntity.ok(login);
    }
}
