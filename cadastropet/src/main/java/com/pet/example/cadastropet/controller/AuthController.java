package com.pet.example.cadastropet.controller;

import com.pet.example.cadastropet.dto.DonoDTO.DonoLoginDTO;
import com.pet.example.cadastropet.service.DonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicado à autenticação.
 * Endpoint para login que gera token JWT.
 */
@RestController
@RequestMapping("/auth")  // Base para autenticação
@RequiredArgsConstructor
public class AuthController {

    private final DonoService donoService;

    /**
     * Endpoint para login.
     * POST /auth/login
     * Recebe email e senha, retorna token JWT se autenticado com sucesso.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody DonoLoginDTO dto) {
        String token = donoService.login(dto);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    /**
     * DTO interno para encapsular o token JWT na resposta JSON.
     */
    public static class TokenResponse {
        private final String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}


