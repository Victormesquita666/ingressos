package com.example.ingressos.services;

import com.example.ingressos.exceptions.BusinessException;
import com.example.ingressos.exceptions.ResourceNotFoundException;
import com.example.ingressos.models.dto.UsuarioDTO;
import com.example.ingressos.models.dto.UsuarioFormDTO;
import com.example.ingressos.models.entities.Endereco;
import com.example.ingressos.models.entities.Usuario;
import com.example.ingressos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO cria(UsuarioFormDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um usuário cadastrado com este email");
        }

        Endereco endereco = null;
        if (dto.endereco() != null) {
            endereco = new Endereco(
                    dto.endereco().rua(),
                    dto.endereco().numero(),
                    dto.endereco().bairro(),
                    dto.endereco().cidade(),
                    dto.endereco().estado(),
                    dto.endereco().cep());
        }

        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        Usuario usuario = new Usuario(dto.nome(), dto.email(), senhaCriptografada, endereco);

        return usuarioRepository.save(usuario).toDTO();
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream().map(Usuario::toDTO).toList();
    }

    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(Usuario::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário de id " + id + " não encontrado"));
    }

    public Usuario encontrarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário de id " + id + " não encontrado"));
    }

    public void deletar(Long id) {
        Usuario usuario = encontrarPorId(id);
        usuarioRepository.delete(usuario);
    }
}