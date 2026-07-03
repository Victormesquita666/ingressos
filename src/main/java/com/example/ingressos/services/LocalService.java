package com.example.ingressos.services;

import com.example.ingressos.exceptions.ResourceNotFoundException;
import com.example.ingressos.models.dto.LocalDTO;
import com.example.ingressos.models.dto.LocalFormDTO;
import com.example.ingressos.models.entities.Local;
import com.example.ingressos.repositories.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {
    private final LocalRepository localRepository;

    public LocalDTO cria(LocalFormDTO dto) {
        Local local = new Local(dto.nome(), dto.endereco(), dto.cidade());
        return localRepository.save(local).toDTO();
    }

    public List<LocalDTO> listarTodos() {
        return localRepository.findAll().stream().map(Local::toDTO).toList();
    }

    public LocalDTO buscarPorId(Long id) {
        return localRepository.findById(id)
                .map(Local::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Local de id " + id + " não encontrado"));
    }

    public Local encontrarPorId(Long id) {
        return localRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Local de id " + id + " não encontrado"));
    }

    public void deletar(Long id) {
        Local local = encontrarPorId(id);
        localRepository.delete(local);
    }
}