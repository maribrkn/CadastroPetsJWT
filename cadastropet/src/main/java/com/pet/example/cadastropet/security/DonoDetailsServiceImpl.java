package com.pet.example.cadastropet.security;


import com.pet.example.cadastropet.model.Dono;
import com.pet.example.cadastropet.repository.DonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonoDetailsServiceImpl implements UserDetailsService {

    private final DonoRepository donoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Dono dono = donoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return org.springframework.security.core.userdetails.User
                .withUsername(dono.getEmail())
                .password(dono.getSenha())
                .authorities("USER")
                .build();
    }
}
