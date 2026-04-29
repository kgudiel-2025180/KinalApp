package com.kennygudiel.Kinalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Público
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/acceso-denegado").permitAll()
                        .requestMatchers("/api/**").permitAll()

                        // Solo ADMIN puede ELIMINAR
                        .requestMatchers("/web/ventas/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/web/clientes/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/web/productos/eliminar/**").hasRole("ADMIN")

                        // Solo ADMIN gestiona productos
                        .requestMatchers("/web/productos/nuevo", "/web/productos/guardar",
                                "/web/productos/editar/**", "/web/productos/actualizar/**").hasRole("ADMIN")

                        // Solo ADMIN gestiona clientes
                        .requestMatchers("/web/clientes/nuevo", "/web/clientes/guardar",
                                "/web/clientes/editar/**", "/web/clientes/actualizar/**").hasRole("ADMIN")

                        // ADMIN y VENDEDOR - Dashboard y Ventas
                        .requestMatchers("/web/dashboard", "/web/ventas/**").hasAnyRole("ADMIN", "VENDEDOR")

                        // ADMIN y VENDEDOR - Ver clientes
                        .requestMatchers("/web/clientes").hasAnyRole("ADMIN", "VENDEDOR")

                        // Todos - Ver productos
                        .requestMatchers("/web/productos").hasAnyRole("ADMIN", "VENDEDOR", "CLIENTE")

                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            String rol = authentication.getAuthorities().toString();
                            if (rol.contains("ROLE_CLIENTE")) {
                                response.sendRedirect("/web/productos");
                            } else {
                                response.sendRedirect("/web/dashboard");
                            }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/acceso-denegado")
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}