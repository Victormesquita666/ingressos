package com.example.ingressos.repositories;

import com.example.ingressos.models.entities.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
}