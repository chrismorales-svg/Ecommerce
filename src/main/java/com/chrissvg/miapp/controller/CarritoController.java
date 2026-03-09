package com.chrissvg.miapp.controller;

import com.chrissvg.miapp.model.Carrito;
import com.chrissvg.miapp.service.CarritoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    /** Extrae el email del usuario sin importar si es OAuth2 o form login */
    private String getEmail(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User oauthUser) {
            return oauthUser.getAttribute("email");
        }
        // Form login: el username es el email
        return authentication.getName();
    }

    /** Ver el carrito */
    @GetMapping
    public String verCarrito(Authentication authentication, Model model) {
        Carrito carrito = carritoService.obtenerOCrearCarrito(getEmail(authentication));
        model.addAttribute("carrito", carrito);
        return "carrito/ver";
    }

    /** Agregar producto al carrito */
    @PostMapping("/agregar")
    public String agregar(Authentication authentication,
            @RequestParam Long productoId,
            @RequestParam(defaultValue = "1") int cantidad,
            RedirectAttributes redirect) {
        carritoService.agregarProducto(getEmail(authentication), productoId, cantidad);
        redirect.addFlashAttribute("mensaje", "Producto agregado al carrito ✓");
        return "redirect:/carrito";
    }

    /** Actualizar cantidad de un item */
    @PostMapping("/actualizar")
    public String actualizar(@RequestParam Long itemId,
            @RequestParam int cantidad) {
        carritoService.actualizarCantidad(itemId, cantidad);
        return "redirect:/carrito";
    }

    /** Eliminar un item del carrito */
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long itemId) {
        carritoService.eliminarItem(itemId);
        return "redirect:/carrito";
    }

    /** Vaciar todo el carrito */
    @PostMapping("/vaciar")
    public String vaciar(Authentication authentication) {
        carritoService.vaciarCarrito(getEmail(authentication));
        return "redirect:/carrito";
    }
}