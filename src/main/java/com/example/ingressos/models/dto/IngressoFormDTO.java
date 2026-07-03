package com.example.ingressos.models.dto;

import jakarta.validation.constraints.NotNull;

public record IngressoFormDTO(
        @NotNull(message = "Usuário é obrigatório") Long usuarioId,
        @NotNull(message = "Evento é obrigatório") Long eventoId) {
}