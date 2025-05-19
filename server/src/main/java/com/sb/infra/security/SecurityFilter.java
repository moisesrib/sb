package com.sb.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sb.core.exceptions.NotFoundException;
import com.sb.user.model.User;
import com.sb.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = this.recoverToken(request);
        
        if (token != null) {
            try {
                String userEmail = tokenService.validateToken(token);
                
                if (userEmail == null) {
                    log.warn("Token inválido ou expirado");
                    filterChain.doFilter(request, response);
                    return;
                }

                log.debug("Token válido. Email recuperado: {}", userEmail);
                
                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

                var authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Usuário {} autenticado com sucesso", userEmail);

            } catch (Exception e) {
                log.error("Erro ao processar autenticação: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            log.debug("Nenhum token encontrado na requisição");
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
