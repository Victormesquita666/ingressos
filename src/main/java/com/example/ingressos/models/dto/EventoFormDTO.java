package com.example.ingressos.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EventoFormDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Data/hora é obrigatória") String dataHora,
        @NotNull(message = "Capacidade máxima é obrigatória") @Positive(message = "Capacidade deve ser maior que zero") Integer capacidadeMaxima,
        @NotNull(message = "Preço é obrigatório") @Positive(message = "Preço deve ser maior que zero") BigDecimal precoIngresso,
        @NotNull(message = "Local é obrigatório") Long localId) {
}