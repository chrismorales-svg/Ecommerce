package com.chrissvg.miapp.service;

import com.chrissvg.miapp.model.Producto;
import com.chrissvg.miapp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // Buscar por ID
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Guardar o actualizar
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    // Contar total
    public long contar() {
        return productoRepository.count();
    }

    // Buscar por supermercado
    public List<Producto> buscarPorSupermercado(Long supermercadoId) {
        return productoRepository.findBySupermercadoId(supermercadoId);
    }

    // Buscar por nombre
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}