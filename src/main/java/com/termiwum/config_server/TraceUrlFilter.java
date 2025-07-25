package com.termiwum.config_server;

import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class TraceUrlFilter extends OncePerRequestFilter {

    private final Tracer tracer;

    public TraceUrlFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String fullUrl = request.getRequestURL().toString() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        tracer.currentSpan().tag("http.url.full", fullUrl);

        filterChain.doFilter(request, response);
    }
}
