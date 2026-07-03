package com.example.ingressos.controllers;

import com.example.ingressos.models.dto.EventoDTO;
import com.example.ingressos.models.dto.EventoFormDTO;
import com.example.ingressos.services.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "Gerenciamento de eventos culturais")
public class EventoController {
    private final EventoService eventoService;

    @PostMapping
    @Operation(summary = "Criar evento")
    public ResponseEntity<EventoDTO> criar(@RequestBody @Valid EventoFormDTO dto) {
        return new ResponseEntity<>(eventoService.cria(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar eventos")
    public ResponseEntity<List<EventoDTO>> listar() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<EventoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover evento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}