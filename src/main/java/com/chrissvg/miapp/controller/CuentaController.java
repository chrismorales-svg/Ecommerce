package com.chrissvg.miapp.controller;

import com.chrissvg.miapp.model.Usuario;
import com.chrissvg.miapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cuenta")
public class CuentaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ── Helper: obtener email del usuario autenticado ────────────────
    private String getEmail(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (principal instanceof OAuth2User oauth2User) {
            return oauth2User.getAttribute("email");
        }
        return auth.getName();
    }

    // ── GET /cuenta ──────────────────────────────────────────────────
    @GetMapping
    public String verCuenta(Model model, Authentication authentication) {
        String email = getEmail(authentication);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "cuenta";
    }

    // ── POST /cuenta/actualizar ──────────────────────────────────────
    @PostMapping("/actualizar")
    public String actualizarCuenta(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) Double latitud,
            @RequestParam(required = false) Double longitud,
            @RequestParam(required = false) String direccionCompleta,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        String email = getEmail(authentication);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCiudad(ciudad);
        usuario.setDepartamento(departamento);
        usuario.setTelefono(telefono);
        usuario.setLatitud(latitud);
        usuario.setLongitud(longitud);
        usuario.setDireccionCompleta(direccionCompleta);

        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("exito", "Perfil actualizado correctamente");
        return "redirect:/cuenta";
    }
}