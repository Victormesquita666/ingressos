package com.example.ingressos.models.entities;

import com.example.ingressos.models.dto.IngressoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingressos")
@Getter
@Setter
@NoArgsConstructor
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(name = "data_compra", nullable = false)
    private LocalDateTime dataCompra;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago;

    public Ingresso(Usuario usuario, Evento evento, LocalDateTime dataCompra, BigDecimal valorPago) {
        this.usuario = usuario;
        this.evento = evento;
        this.dataCompra = dataCompra;
        this.valorPago = valorPago;
    }

    public IngressoDTO toDTO() {
        return new IngressoDTO(
                id,
                usuario.toDTO(),
                evento.toDTO(),
                dataCompra.toString(),
                valorPago);
    }
}