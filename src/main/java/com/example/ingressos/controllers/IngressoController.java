package com.example.ingressos.controllers;

import com.example.ingressos.models.dto.IngressoDTO;
import com.example.ingressos.models.dto.IngressoFormDTO;
import com.example.ingressos.models.dto.IngressoResumoDTO;
import com.example.ingressos.services.IngressoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingressos")
@RequiredArgsConstructor
@Tag(name = "Ingressos", description = "Compra e gerenciamento de ingressos")
public class IngressoController {
    private final IngressoService ingressoService;

    @PostMapping
    @Operation(summary = "Comprar ingresso", description = "Compra um ingresso para um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingresso comprado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Capacidade esgotada ou limite excedido", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Usuário ou evento não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<IngressoDTO> criar(@RequestBody @Valid IngressoFormDTO dto) {
        return new ResponseEntity<>(ingressoService.cria(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar ingressos", description = "Lista todos os ingressos ou filtra por eventoId")
    public ResponseEntity<List<IngressoDTO>> listar(@RequestParam(required = false) Long eventoId) {
        List<IngressoDTO> ingressos = eventoId != null
                ? ingressoService.listarPorEvento(eventoId)
                : ingressoService.listarTodos();
        return ResponseEntity.ok(ingressos);
    }

    @GetMapping("/resumo")
    @Operation(summary = "Resumo de vendas", description = "Retorna estatísticas de vendas de ingressos")
    public ResponseEntity<IngressoResumoDTO> obterResumo() {
        return ResponseEntity.ok(ingressoService.obterResumo());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ingresso por ID")
    public ResponseEntity<IngressoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(ingressoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar ingresso")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingressoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}