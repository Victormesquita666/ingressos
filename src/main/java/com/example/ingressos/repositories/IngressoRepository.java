package com.example.ingressos.repositories;

import com.example.ingressos.models.entities.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    List<Ingresso> findByEventoId(Long eventoId);
    int countByEventoId(Long eventoId);
    int countByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
}