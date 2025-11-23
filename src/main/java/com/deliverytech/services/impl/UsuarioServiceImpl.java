package com.deliverytech.services.impl;

import com.deliverytech.dto.request.LoginRequestDTO;
import com.deliverytech.dto.request.UsuarioRequestDTO;
import com.deliverytech.dto.response.LoginResposeDTO;
import com.deliverytech.dto.response.UsuarioResponseDTO;
import com.deliverytech.entity.Usuario;
import com.deliverytech.exception.BusinessException;
import com.deliverytech.repository.UsuarioRepository;
import com.deliverytech.security.JwtUtil;
import com.deliverytech.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if(usuarioRepository.existsByEmail(dto.getEmail())){
            throw new BusinessException("Email já cadastrado: " + dto.getEmail());
        }
        Usuario usuario = Usuario.builder()
                .email(dto.getEmail())
                .nome(dto.getNome())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .role(dto.getRole())
                .restauranteId(dto.getRestauranteId())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();
        usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioResponseDTO.class);

    }

    @Override
    public LoginResposeDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessException("Usuário inválido", String.valueOf(HttpStatus.UNAUTHORIZED)));

        LoginResposeDTO responseDTO = new LoginResposeDTO();
        responseDTO.setUsuario(modelMapper.map(usuario, UsuarioResponseDTO.class));
        responseDTO.setTipo("Bearer");
        responseDTO.setExpiracao(86400000L); // 1 dia em segundos
        responseDTO.setToken(jwtUtil.generateToken(User.withUsername(usuario.getEmail()).password(usuario.getSenha()).authorities("ROLE_" + usuario.getRole().name()).build(), usuario));

        return responseDTO;

    }
}
