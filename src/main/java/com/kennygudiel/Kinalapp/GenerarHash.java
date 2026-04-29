package com.kennygudiel.Kinalapp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String admin = encoder.encode("admin123");
        String vendedor = encoder.encode("vendedor123");
        String cliente = encoder.encode("cliente123");

        System.out.println("=== HASHES GENERADOS ===");
        System.out.println("admin123     → " + admin);
        System.out.println("vendedor123  → " + vendedor);
        System.out.println("cliente123   → " + cliente);
        System.out.println("========================");
    }
}