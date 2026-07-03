package com.example.ingressos.models.dto;

import java.math.BigDecimal;

public record IngressoResumoDTO(
        int totalIngressosVendidos,
        BigDecimal receitaTotal,
        double taxaOcupacaoMedia,
        String eventoComMaisVendas) {
}