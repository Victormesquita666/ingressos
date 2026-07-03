package com.example.ingressos.models.entities;

import com.example.ingressos.models.dto.EventoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "capacidade_maxima", nullable = false)
    private Integer capacidadeMaxima;

    @Column(name = "preco_ingresso", nullable = false)
    private BigDecimal precoIngresso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;

    public Evento(String nome, LocalDateTime dataHora, Integer capacidadeMaxima, BigDecimal precoIngresso, Local local) {
        this.nome = nome;
        this.dataHora = dataHora;
        this.capacidadeMaxima = capacidadeMaxima;
        this.precoIngresso = precoIngresso;
        this.local = local;
    }

    public EventoDTO toDTO() {
        return new EventoDTO(
                id,
                nome,
                dataHora.toString(),
                capacidadeMaxima,
                precoIngresso,
                local.toDTO());
    }
}