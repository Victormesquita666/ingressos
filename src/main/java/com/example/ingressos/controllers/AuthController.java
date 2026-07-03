package com.example.ingressos.controllers;

import com.example.ingressos.models.dto.LoginFormDTO;
import com.example.ingressos.models.dto.TokenDTO;
import com.example.ingressos.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Login e geração de token JWT")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica um usuário e retorna o token JWT")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginFormDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}