package com.chrissvg.miapp.controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.chrissvg.miapp.service.ProductoService;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @Autowired
    private ProductoService productoService;

    @GetMapping({"/" , "/home"})
    public String home(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "home";
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
