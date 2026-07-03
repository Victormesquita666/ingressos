package com.example.ingressos.models.dto;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        EnderecoDTO endereco) {
}