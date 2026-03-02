package com.chrissvg.miapp.controller;

import com.chrissvg.miapp.model.Oferta;
import com.chrissvg.miapp.model.Producto;
import com.chrissvg.miapp.model.Supermercado;
import com.chrissvg.miapp.service.OfertaService;
import com.chrissvg.miapp.service.ProductoService;
import com.chrissvg.miapp.service.SupermercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;              
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private SupermercadoService supermercadoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalProductos", productoService.contar());
        model.addAttribute("totalOfertas", ofertaService.contar());
        model.addAttribute("totalSupermercados", supermercadoService.contar());
        return "admin/dashboard";
    }

    // ── PRODUCTOS ──
    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("producto", new Producto());
        return "admin/productos";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/admin/productos";
    }

    // ── OFERTAS ──
    @GetMapping("/ofertas")
    public String ofertas(Model model) {
        model.addAttribute("ofertas", ofertaService.listarTodas());
        model.addAttribute("oferta", new Oferta());
        return "admin/ofertas";
    }

    @PostMapping("/ofertas/guardar")
    public String guardarOferta(@ModelAttribute Oferta oferta) {
        ofertaService.guardar(oferta);
        return "redirect:/admin/ofertas";
    }

    @GetMapping("/ofertas/eliminar/{id}")
    public String eliminarOferta(@PathVariable Long id) {
        ofertaService.eliminar(id);
        return "redirect:/admin/ofertas";
    }

    // ── SUPERMERCADOS ──
    @GetMapping("/supermercados")
    public String supermercados(Model model) {
        model.addAttribute("supermercados", supermercadoService.listarTodos());
        model.addAttribute("supermercado", new Supermercado());
        return "admin/supermercados";
    }

    @PostMapping("/supermercados/guardar")
    public String guardarSupermercado(@ModelAttribute Supermercado supermercado) {
        supermercadoService.guardar(supermercado);
        return "redirect:/admin/supermercados";
    }

    @GetMapping("/supermercados/eliminar/{id}")
    public String eliminarSupermercado(@PathVariable Long id) {
        supermercadoService.eliminar(id);
        return "redirect:/admin/supermercados";
    }
}
