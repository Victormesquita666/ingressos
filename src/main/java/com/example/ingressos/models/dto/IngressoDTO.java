package com.example.ingressos.models.dto;

import java.math.BigDecimal;

public record IngressoDTO(
        Long id,
        UsuarioDTO usuario,
        EventoDTO evento,
        String dataCompra,
        BigDecimal valorPago) {
}