package com.chrissvg.miapp.repository;

import com.chrissvg.miapp.model.Carrito;
import com.chrissvg.miapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuario(Usuario usuario);
}