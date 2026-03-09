package com.chrissvg.miapp.controller;

import com.chrissvg.miapp.model.Producto;
import com.chrissvg.miapp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoApiController {

    @Autowired
    private ProductoService productoService;

    // Crear un producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.guardar(producto);
        return ResponseEntity.ok(nuevo);
    }

    // Crear varios productos de una vez
    @PostMapping("/batch")
    public ResponseEntity<List<Producto>> crearProductos(@RequestBody List<Producto> productos) {
        List<Producto> nuevos = productos.stream()
            .map(productoService::guardar)
            .toList();
        return ResponseEntity.ok(nuevos);
    }

    // Listar todos (para verificar)
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }
}