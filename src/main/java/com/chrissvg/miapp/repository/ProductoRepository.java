package com.chrissvg.miapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrissvg.miapp.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findBySupermercadoId(Long supermercadoId);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
