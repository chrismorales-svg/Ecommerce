package com.chrissvg.miapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrissvg.miapp.model.Supermercado;

@Repository
public interface SupermercadoRepository extends JpaRepository<Supermercado, Long> {
    List<Supermercado> findByNombreContainingIgnoreCase(String nombre);
}
