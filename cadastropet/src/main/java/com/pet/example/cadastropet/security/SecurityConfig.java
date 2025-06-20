package com.pet.example.cadastropet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Indica que essa classe configura beans do Spring (configurações gerais)
@RequiredArgsConstructor  // Injeta automaticamente o JwtFilter via construtor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    // Método que define a cadeia de filtros e regras de segurança
    @Bean  // Anota esse método para o Spring chamar e usar o objeto retornado como bean gerenciado
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilita CSRF (cross-site request forgery), útil em APIs REST
                .headers(headers -> headers.disable())  // Desabilita headers padrão (ex: X-Frame-Options)
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso livre a esses endpoints
                        .requestMatchers("/auth/login", "/donos/register", "/h2-console/**").permitAll()
                        // Qualquer outro endpoint exige autenticação
                        .anyRequest().authenticated()
                )
                // Configura para não criar sessão HTTP (stateless), pois usamos JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Adiciona o filtro JwtFilter antes do filtro de autenticação padrão
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();  // Constroi a cadeia de filtros
    }

    // Bean que cria um codificador de senha BCrypt para armazenar senhas seguras
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


