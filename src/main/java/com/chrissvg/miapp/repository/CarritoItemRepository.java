package com.chrissvg.miapp.repository;

import com.chrissvg.miapp.model.Carrito;
import com.chrissvg.miapp.model.CarritoItem;
import com.chrissvg.miapp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    Optional<CarritoItem> findByCarritoAndProducto(Carrito carrito, Producto producto);
}