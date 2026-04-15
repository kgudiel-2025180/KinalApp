package com.kennygudiel.Kinalapp.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();

        // Rutas públicas (no requieren autenticación)
        boolean isPublicRoute = path.startsWith("/login") ||
                path.startsWith("/css") ||
                path.startsWith("/js") ||
                path.startsWith("/images") ||
                path.startsWith("/api/");  // API REST pública

        // Rutas de API REST (no requieren sesión web)
        boolean isApiRoute = path.startsWith("/clientes") ||
                path.startsWith("/ventas") ||
                path.startsWith("/productos") ||
                path.startsWith("/usuarios") ||
                path.startsWith("/detalleventa");

        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);

        if (isLoggedIn || isPublicRoute || isApiRoute) {
            // Usuario autenticado o ruta pública → Continuar
            chain.doFilter(request, response);
        } else {
            // No autenticado → Redirigir a login
            res.sendRedirect("/login");
        }
    }
}