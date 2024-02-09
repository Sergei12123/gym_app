package com.example.learn_spring_core.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
@AllArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        logTransactionInfo(request);

        filterChain.doFilter(request, response);

        logTransactionCompletionInfo(response);
    }

    private void logTransactionInfo(HttpServletRequest request) {
        log.info("Send {} request for endpoint {}", request.getMethod(), request.getRequestURI());
    }

    private void logTransactionCompletionInfo(HttpServletResponse response) {
        log.info("Request completed with status {}", response.getStatus());

    }
}
