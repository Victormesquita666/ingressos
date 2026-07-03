package com.example.ingressos.models.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginFormDTO(
        @NotBlank(message = "Email é obrigatório") String email,
        @NotBlank(message = "Senha é obrigatória") String senha) {
}