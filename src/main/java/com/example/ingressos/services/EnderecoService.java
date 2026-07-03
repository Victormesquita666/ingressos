package com.example.ingressos.services;

import com.example.ingressos.exceptions.ResourceNotFoundException;
import com.example.ingressos.models.entities.Endereco;
import com.example.ingressos.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    public Endereco encontrarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço de id " + id + " não encontrado"));
    }
}