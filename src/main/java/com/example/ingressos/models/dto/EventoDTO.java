package com.example.ingressos.models.dto;

import java.math.BigDecimal;

public record EventoDTO(
        Long id,
        String nome,
        String dataHora,
        Integer capacidadeMaxima,
        BigDecimal precoIngresso,
        LocalDTO local) {
}