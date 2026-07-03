package com.example.ingressos.services;

import com.example.ingressos.exceptions.ResourceNotFoundException;
import com.example.ingressos.models.dto.EventoDTO;
import com.example.ingressos.models.dto.EventoFormDTO;
import com.example.ingressos.models.entities.Evento;
import com.example.ingressos.models.entities.Local;
import com.example.ingressos.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final EventoRepository eventoRepository;
    private final LocalService localService;

    public EventoDTO cria(EventoFormDTO dto) {
        Local local = localService.encontrarPorId(dto.localId());
        LocalDateTime dataHora = LocalDateTime.parse(dto.dataHora());

        Evento evento = new Evento(dto.nome(), dataHora, dto.capacidadeMaxima(), dto.precoIngresso(), local);
        return eventoRepository.save(evento).toDTO();
    }

    public List<EventoDTO> listarTodos() {
        return eventoRepository.findAll().stream().map(Evento::toDTO).toList();
    }

    public EventoDTO buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .map(Evento::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Evento de id " + id + " não encontrado"));
    }

    public Evento encontrarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento de id " + id + " não encontrado"));
    }

    public void deletar(Long id) {
        Evento evento = encontrarPorId(id);
        eventoRepository.delete(evento);
    }
}