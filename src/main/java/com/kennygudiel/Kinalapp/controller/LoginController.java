package com.kennygudiel.Kinalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "❌ Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("success", "✅ Has cerrado sesión correctamente");
        }
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}