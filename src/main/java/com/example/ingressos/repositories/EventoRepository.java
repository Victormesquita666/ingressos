package com.example.ingressos.repositories;

import com.example.ingressos.models.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}