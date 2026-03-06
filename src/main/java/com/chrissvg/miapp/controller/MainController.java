package com.chrissvg.miapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chrissvg.miapp.model.Producto;
import com.chrissvg.miapp.service.ProductoService;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @Autowired
    private ProductoService productoService;

    @GetMapping({"/" , "/home"})
    public String home(Model model) {
        List<Producto> todos = productoService.listarTodos();

        // Todos los productos para el carrusel principal
        model.addAttribute("productos", todos);

        // Agrupar por categoría (ignorando productos sin categoría)
        Map<String, List<Producto>> porCategoria = todos.stream()
            .filter(p -> p.getCategoria() != null && !p.getCategoria().isBlank())
            .collect(Collectors.groupingBy(Producto::getCategoria));

        model.addAttribute("categorias", porCategoria);

        return "home";
    }

    @GetMapping("/categoria/{nombre}")
    public String verCategoria(@PathVariable String nombre, Model model) {
        List<Producto> productos = productoService.listarTodos().stream()
            .filter(p -> nombre.equalsIgnoreCase(p.getCategoria()))
            .collect(Collectors.toList());

        model.addAttribute("categoria", nombre);
        model.addAttribute("productos", productos);
        return "categoria";
    }

    @GetMapping({ "/about" })
    public String mostrarabout() {
        return "about";
    }

    @GetMapping({ "/creacuenta"})
    public String mostrarcreacuenta(){
        return "creacuenta";
    }

    @GetMapping({ "/carrito"})
    public String mostrarcarrito(){
        return "miscompras";
    }
}