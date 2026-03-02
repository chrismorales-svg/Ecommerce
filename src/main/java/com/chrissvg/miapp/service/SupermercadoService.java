package com.chrissvg.miapp.service;

import com.chrissvg.miapp.model.Supermercado;
import com.chrissvg.miapp.repository.SupermercadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SupermercadoService {

    @Autowired
    private SupermercadoRepository supermercadoRepository;

    // Listar todos
    public List<Supermercado> listarTodos() {
        return supermercadoRepository.findAll();
    }

    // Buscar por ID
    public Optional<Supermercado> buscarPorId(Long id) {
        return supermercadoRepository.findById(id);
    }

    // Guardar o actualizar
    public Supermercado guardar(Supermercado supermercado) {
        return supermercadoRepository.save(supermercado);
    }

    // Eliminar
    public void eliminar(Long id) {
        supermercadoRepository.deleteById(id);
    }

    // Contar total
    public long contar() {
        return supermercadoRepository.count();
    }

    // Buscar por nombre
    public List<Supermercado> buscarPorNombre(String nombre) {
        return supermercadoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}