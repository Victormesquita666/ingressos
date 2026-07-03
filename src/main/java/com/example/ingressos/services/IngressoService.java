package com.example.ingressos.services;

import com.example.ingressos.exceptions.BusinessException;
import com.example.ingressos.exceptions.ResourceNotFoundException;
import com.example.ingressos.models.dto.IngressoDTO;
import com.example.ingressos.models.dto.IngressoFormDTO;
import com.example.ingressos.models.dto.IngressoResumoDTO;
import com.example.ingressos.models.entities.Evento;
import com.example.ingressos.models.entities.Ingresso;
import com.example.ingressos.models.entities.Usuario;
import com.example.ingressos.repositories.IngressoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IngressoService {
    private static final int LIMITE_INGRESSOS_POR_USUARIO = 5;

    private final IngressoRepository ingressoRepository;
    private final UsuarioService usuarioService;
    private final EventoService eventoService;

    public IngressoDTO cria(IngressoFormDTO dto) {
        Usuario usuario = usuarioService.encontrarPorId(dto.usuarioId());
        Evento evento = eventoService.encontrarPorId(dto.eventoId());

        validaCapacidadeMaxima(evento);
        validaLimitePorUsuario(usuario.getId(), evento.getId());

        Ingresso ingresso = new Ingresso(usuario, evento, LocalDateTime.now(), evento.getPrecoIngresso());
        return ingressoRepository.save(ingresso).toDTO();
    }

    public List<IngressoDTO> listarTodos() {
        return ingressoRepository.findAll().stream().map(Ingresso::toDTO).toList();
    }

    public List<IngressoDTO> listarPorEvento(Long eventoId) {
        return ingressoRepository.findByEventoId(eventoId).stream().map(Ingresso::toDTO).toList();
    }

    public IngressoDTO buscarPorId(Long id) {
        return ingressoRepository.findById(id)
                .map(Ingresso::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Ingresso de id " + id + " não encontrado"));
    }

    public void deletar(Long id) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingresso de id " + id + " não encontrado"));
        ingressoRepository.delete(ingresso);
    }

    private void validaCapacidadeMaxima(Evento evento) {
        int vendidos = ingressoRepository.countByEventoId(evento.getId());
        if (vendidos >= evento.getCapacidadeMaxima()) {
            throw new BusinessException("Capacidade máxima do evento '" + evento.getNome() + "' já foi atingida");
        }
    }

    private void validaLimitePorUsuario(Long usuarioId, Long eventoId) {
        int quantidadeUsuario = ingressoRepository.countByUsuarioIdAndEventoId(usuarioId, eventoId);
        if (quantidadeUsuario >= LIMITE_INGRESSOS_POR_USUARIO) {
            throw new BusinessException("Limite de " + LIMITE_INGRESSOS_POR_USUARIO + " ingressos por usuário para este evento foi atingido");
        }
    }

    public IngressoResumoDTO obterResumo() {
        List<Ingresso> ingressos = ingressoRepository.findAll();

        if (ingressos.isEmpty()) {
            throw new ResourceNotFoundException("Não há ingressos vendidos");
        }

        int totalVendidos = ingressos.size();
        BigDecimal receitaTotal = BigDecimal.ZERO;
        Map<String, Integer> vendasPorEvento = new HashMap<>();
        Map<String, Integer> capacidadePorEvento = new HashMap<>();

        for (Ingresso ingresso : ingressos) {
            receitaTotal = receitaTotal.add(ingresso.getValorPago());

            String nomeEvento = ingresso.getEvento().getNome();
            vendasPorEvento.put(nomeEvento, vendasPorEvento.getOrDefault(nomeEvento, 0) + 1);
            capacidadePorEvento.put(nomeEvento, ingresso.getEvento().getCapacidadeMaxima());
        }

        double somaTaxasOcupacao = 0.0;
        for (Map.Entry<String, Integer> entry : vendasPorEvento.entrySet()) {
            int capacidade = capacidadePorEvento.get(entry.getKey());
            somaTaxasOcupacao += (double) entry.getValue() / capacidade;
        }
        double taxaOcupacaoMedia = somaTaxasOcupacao / vendasPorEvento.size();

        String eventoComMaisVendas = null;
        int maxVendas = -1;
        for (Map.Entry<String, Integer> entry : vendasPorEvento.entrySet()) {
            if (entry.getValue() > maxVendas) {
                maxVendas = entry.getValue();
                eventoComMaisVendas = entry.getKey();
            }
        }

        return new IngressoResumoDTO(totalVendidos, receitaTotal, taxaOcupacaoMedia, eventoComMaisVendas);
    }
}