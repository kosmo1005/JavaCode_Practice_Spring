package com.kulushev.app.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        wrappedRequest.getInputStream();
        logRequest(wrappedRequest);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        forceWriteResponseBody(wrappedResponse);
        logResponse(wrappedRequest, wrappedResponse);
        wrappedResponse.copyBodyToResponse();

    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String method = request.getMethod();
        String path = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String body = extractRequestBody(request);

        logger.info("[REQUEST] {} {} | From: {} | Body: {}", method, path, remoteAddr, body);
    }

    private void logResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        int status = response.getStatus();
        String body = extractResponseBody(response);
        String message = extractJsonField(body, "message");

        if (request.getRequestURI().startsWith("/app/auth")) {
            String token = extractJsonField(body, "token");
            String refreshToken = extractJsonField(body, "refreshToken");
            String blocked = extractJsonField(body, "accountBlocked");
            String count = extractJsonField(body, "countOfFailedAuth");

            logger.info("[AUTH RESPONSE] Token: {}, Refresh Token: {}, Blocked: {}, CountOfFailedAuth: {}",
                    token, refreshToken, blocked, count);

            if (status != 200) {
                logger.warn("[AUTH ERROR] Status: {}, Message: {}", status, message);
            }
        } else {
            if (status != 200) {
                logger.warn("[ERROR] Status: {}, Message: {}", status, message);
            } else {
                logger.info("[RESPONSE] Status: {}, {}", status, body);
            }
        }
    }

    private String extractRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        return content.length > 0 ? new String(content, StandardCharsets.UTF_8) : "[empty]";
    }

    private String extractResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        return content.length > 0 ? new String(content, StandardCharsets.UTF_8) : "[empty]";
    }

    private String extractJsonField(String json, String field) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode.has(field) ? jsonNode.get(field).asText() : "[not found]";
        } catch (Exception e) {
            return "[invalid json]";
        }
    }

    private void forceWriteResponseBody(ContentCachingResponseWrapper response) throws IOException {
        response.getContentAsByteArray();
    }
}
