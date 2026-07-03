package com.example.ingressos.models.dto;

import jakarta.validation.constraints.NotBlank;

public record LocalFormDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Endereço é obrigatório") String endereco,
        @NotBlank(message = "Cidade é obrigatória") String cidade) {
}