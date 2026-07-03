package com.example.ingressos.controllers;

import com.example.ingressos.models.dto.UsuarioDTO;
import com.example.ingressos.models.dto.UsuarioFormDTO;
import com.example.ingressos.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Cadastro e gerenciamento de usuários")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário (rota pública)")
    public ResponseEntity<UsuarioDTO> criar(@RequestBody @Valid UsuarioFormDTO dto) {
        return new ResponseEntity<>(usuarioService.cria(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados")
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}