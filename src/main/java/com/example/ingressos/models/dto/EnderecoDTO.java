package com.example.ingressos.models.dto;

public record EnderecoDTO(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep) {
}