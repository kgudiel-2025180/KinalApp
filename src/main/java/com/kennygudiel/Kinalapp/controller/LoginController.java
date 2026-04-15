package com.kennygudiel.Kinalapp.controller;

import com.kennygudiel.Kinalapp.entity.Usuario;
import com.kennygudiel.Kinalapp.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //  RUTA RAÍZ - Redirige a login
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Mostrar página de login
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/web/dashboard";
        }
        return "login";
    }

    // Procesar login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        Optional<Usuario> usuarioOpt = usuarioService.login(username, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            session.setAttribute("usuario", usuario);
            session.setAttribute("usuarioId", usuario.getIdUsuario());
            session.setAttribute("usuarioNombre", usuario.getNombreUsuario());
            session.setAttribute("usuarioRol", usuario.getRol());

            redirectAttributes.addFlashAttribute("success", "✅ Bienvenido, " + usuario.getNombreUsuario() + "!");
            return "redirect:/web/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ Usuario o contraseña incorrectos");
            return "redirect:/login";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "✅ Has cerrado sesión correctamente");
        return "redirect:/login";
    }
}