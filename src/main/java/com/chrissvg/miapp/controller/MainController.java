package com.chrissvg.miapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chrissvg.miapp.model.Producto;
import com.chrissvg.miapp.service.ProductoService;

import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @Autowired
    private ProductoService productoService;

    // ── Método helper para no repetir código ──
    private void agregarDatosUsuario(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuth2User oauthUser) {
                model.addAttribute("fotoUsuario", oauthUser.getAttribute("picture"));
                model.addAttribute("nombreUsuario", oauthUser.getAttribute("given_name"));
            } else {
                model.addAttribute("fotoUsuario", null);
                model.addAttribute("nombreUsuario", authentication.getName());
            }
        }
    }

    @GetMapping({ "/", "/home" })
    public String home(Model model, Authentication authentication) {
        List<Producto> todos = productoService.listarTodos();

        model.addAttribute("productos", todos);

        Map<String, List<Producto>> porCategoria = todos.stream()
                .filter(p -> p.getCategoria() != null && !p.getCategoria().isBlank())
                .collect(Collectors.groupingBy(Producto::getCategoria));

        model.addAttribute("categorias", porCategoria);

        agregarDatosUsuario(model, authentication); // ← Añade foto y nombre

        return "home";
    }

    @GetMapping("/categoria/{nombre}")
    public String verCategoria(@PathVariable String nombre, Model model, Authentication authentication) {
        List<Producto> productos = productoService.listarTodos().stream()
                .filter(p -> nombre.equalsIgnoreCase(p.getCategoria()))
                .collect(Collectors.toList());

        model.addAttribute("categoria", nombre);
        model.addAttribute("productos", productos);

        agregarDatosUsuario(model, authentication); // ← Añade foto y nombre

        return "categoria";
    }

    @GetMapping({ "/about" })
    public String mostrarabout(Model model, Authentication authentication) {
        agregarDatosUsuario(model, authentication); // ← Añade foto y nombre
        return "about";
    }

    @GetMapping({ "/creacuenta" })
    public String mostrarcreacuenta() {
        return "creacuenta";
    }

    @GetMapping({ "/carrito" })
    public String mostrarcarrito(Model model, Authentication authentication) {
        agregarDatosUsuario(model, authentication); // ← Añade foto y nombre
        return "miscompras";
    }
}