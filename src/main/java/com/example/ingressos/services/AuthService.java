package com.example.ingressos.services;

import com.example.ingressos.exceptions.BusinessException;
import com.example.ingressos.models.dto.LoginFormDTO;
import com.example.ingressos.models.dto.TokenDTO;
import com.example.ingressos.models.entities.Usuario;
import com.example.ingressos.repositories.UsuarioRepository;
import com.example.ingressos.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public TokenDTO login(LoginFormDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new BusinessException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new BusinessException("Email ou senha inválidos");
        }

        String token = tokenService.gerarToken(usuario);
        return new TokenDTO(token);
    }
}