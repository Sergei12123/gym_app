package com.example.gym_app.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@WebFilter(urlPatterns = "/*")
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    protected static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String transactionId = UUID.randomUUID().toString();
        logTransactionInfo(transactionId, request);
        TransactionIdHolder.setTransactionId(transactionId);

        filterChain.doFilter(request, response);

        logTransactionCompletionInfo(transactionId, response);
    }

    private void logTransactionInfo(String transactionId, HttpServletRequest request) {
        logger.info("Transaction {} started for endpoint {}", transactionId, request.getRequestURI());
    }

    private void logTransactionCompletionInfo(String transactionId, HttpServletResponse response) {
        logger.info("Transaction {} completed with status {}", transactionId, response.getStatus());
    }
}
