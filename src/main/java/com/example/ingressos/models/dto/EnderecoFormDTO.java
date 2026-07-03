package com.example.ingressos.models.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoFormDTO(
        @NotBlank(message = "Rua é obrigatória") String rua,
        @NotBlank(message = "Número é obrigatório") String numero,
        @NotBlank(message = "Bairro é obrigatório") String bairro,
        @NotBlank(message = "Cidade é obrigatória") String cidade,
        @NotBlank(message = "Estado é obrigatório") String estado,
        @NotBlank(message = "CEP é obrigatório") String cep) {
}