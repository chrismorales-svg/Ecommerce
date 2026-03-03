package com.chrissvg.miapp.controller;

import com.chrissvg.miapp.model.Rol;
import com.chrissvg.miapp.model.Usuario;
import com.chrissvg.miapp.model.Oferta;
import com.chrissvg.miapp.model.Producto;
import com.chrissvg.miapp.model.Supermercado;
import com.chrissvg.miapp.repository.RolRepository;
import com.chrissvg.miapp.repository.UsuarioRepository;
import com.chrissvg.miapp.service.OfertaService;
import com.chrissvg.miapp.service.ProductoService;
import com.chrissvg.miapp.service.SupabaseStorageService;
import com.chrissvg.miapp.service.SupermercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private SupermercadoService supermercadoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private SupabaseStorageService storageService;

    // ── DASHBOARD ──
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalProductos", productoService.contar());
        model.addAttribute("totalOfertas", ofertaService.contar());
        model.addAttribute("totalSupermercados", supermercadoService.contar());
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        return "superadmin/dashboard";
    }

    // ── PRODUCTOS ──
    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("producto", new Producto());
        model.addAttribute("supermercados", supermercadoService.listarTodos());
        return "superadmin/productos";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(
            @ModelAttribute Producto producto,
            @RequestParam(required = false) Long supermercadoId,
            @RequestParam("archivoImagen") MultipartFile archivoImagen) {
        try {
            // Asignar supermercado por ID
            if (supermercadoId != null) {
                supermercadoService.buscarPorId(supermercadoId)
                        .ifPresent(producto::setSupermercado);
            }
            // Subir imagen si se seleccionó
            if (archivoImagen != null && !archivoImagen.isEmpty()) {
                String urlImagen = storageService.subirImagen(archivoImagen);
                producto.setImagen(urlImagen);
            }
            productoService.guardar(producto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/superadmin/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/superadmin/productos";
    }

    // ── OFERTAS ──
    @GetMapping("/ofertas")
    public String ofertas(Model model) {
        model.addAttribute("ofertas", ofertaService.listarTodas());
        model.addAttribute("oferta", new Oferta());
        model.addAttribute("productos", productoService.listarTodos());
        return "superadmin/ofertas";
    }

    @PostMapping("/ofertas/guardar")
    public String guardarOferta(@ModelAttribute Oferta oferta) {
        ofertaService.guardar(oferta);
        return "redirect:/superadmin/ofertas";
    }

    @GetMapping("/ofertas/eliminar/{id}")
    public String eliminarOferta(@PathVariable Long id) {
        ofertaService.eliminar(id);
        return "redirect:/superadmin/ofertas";
    }

    // ── SUPERMERCADOS ──
    @GetMapping("/supermercados")
    public String supermercados(Model model) {
        model.addAttribute("supermercados", supermercadoService.listarTodos());
        model.addAttribute("supermercado", new Supermercado());
        return "superadmin/supermercados";
    }

    @PostMapping("/supermercados/guardar")
    public String guardarSupermercado(
            @ModelAttribute Supermercado supermercado,
            @RequestParam("archivoLogo") MultipartFile archivoLogo) {
        try {
            if (!archivoLogo.isEmpty()) {
                String urlLogo = storageService.subirImagen(archivoLogo);
                supermercado.setLogo(urlLogo);
            }
            supermercadoService.guardar(supermercado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/superadmin/supermercados";
    }

    @GetMapping("/supermercados/eliminar/{id}")
    public String eliminarSupermercado(@PathVariable Long id) {
        supermercadoService.eliminar(id);
        return "redirect:/superadmin/supermercados";
    }

    // ── GESTIÓN DE ROLES ──
    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("roles", rolRepository.findAll());
        return "superadmin/usuarios";
    }

    @PostMapping("/usuarios/{id}/asignarRol")
    public String asignarRol(@PathVariable Long id, @RequestParam String nombreRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        if (!usuario.getRoles().contains(rol)) {
            usuario.getRoles().add(rol);
            usuarioRepository.save(usuario);
        }
        return "redirect:/superadmin/usuarios";
    }

    @PostMapping("/usuarios/{id}/quitarRol")
    public String quitarRol(@PathVariable Long id, @RequestParam String nombreRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.getRoles().remove(rol);
        usuarioRepository.save(usuario);
        return "redirect:/superadmin/usuarios";
    }
}