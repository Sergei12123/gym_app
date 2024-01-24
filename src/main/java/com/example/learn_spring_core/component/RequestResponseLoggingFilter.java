package com.example.learn_spring_core.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String transactionId = UUID.randomUUID().toString();
        logTransactionInfo(transactionId, request);
        TransactionIdHolder.setTransactionId(transactionId);

        filterChain.doFilter(request, response);

        logTransactionCompletionInfo(transactionId, response);
    }

    private void logTransactionInfo(String transactionId, HttpServletRequest request) {
        log.info("Transaction {} started for endpoint {}", transactionId, request.getRequestURI());
    }

    private void logTransactionCompletionInfo(String transactionId, HttpServletResponse response) {
        log.info("Transaction {} completed with status {}", transactionId, response.getStatus());
    }
}
