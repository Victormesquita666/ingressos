package com.example.ingressos.controllers;

import com.example.ingressos.models.dto.LocalDTO;
import com.example.ingressos.models.dto.LocalFormDTO;
import com.example.ingressos.services.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locais")
@RequiredArgsConstructor
@Tag(name = "Locais", description = "Gerenciamento de locais de eventos")
public class LocalController {
    private final LocalService localService;

    @PostMapping
    @Operation(summary = "Criar local")
    public ResponseEntity<LocalDTO> criar(@RequestBody @Valid LocalFormDTO dto) {
        return new ResponseEntity<>(localService.cria(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar locais")
    public ResponseEntity<List<LocalDTO>> listar() {
        return ResponseEntity.ok(localService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar local por ID")
    public ResponseEntity<LocalDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(localService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover local")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        localService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}