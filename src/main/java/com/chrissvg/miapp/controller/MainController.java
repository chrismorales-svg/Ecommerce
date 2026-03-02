package com.chrissvg.miapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @GetMapping({ "/", "home"})
    public String mostrarnavbar(){
        return "home";
    }

    @GetMapping({ "/about"})
    public String mostrarabout(){
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
