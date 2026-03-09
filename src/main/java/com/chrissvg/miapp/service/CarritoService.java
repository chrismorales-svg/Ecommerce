package com.chrissvg.miapp.service;

import com.chrissvg.miapp.model.*;
import com.chrissvg.miapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepo;
    private final CarritoItemRepository itemRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    public CarritoService(CarritoRepository carritoRepo,
            CarritoItemRepository itemRepo,
            ProductoRepository productoRepo,
            UsuarioRepository usuarioRepo) {
        this.carritoRepo = carritoRepo;
        this.itemRepo = itemRepo;
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    /** Obtiene el carrito del usuario, o lo crea si no existe */
    public Carrito obtenerOCrearCarrito(String email) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
        return carritoRepo.findByUsuario(usuario)
                .orElseGet(() -> carritoRepo.save(new Carrito(usuario)));
    }

    /** Agrega un producto al carrito. Si ya existe, incrementa la cantidad */
    @Transactional
    public void agregarProducto(String email, Long productoId, int cantidad) {
        Carrito carrito = obtenerOCrearCarrito(email);
        Producto producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoId));

        itemRepo.findByCarritoAndProducto(carrito, producto)
                .ifPresentOrElse(
                        item -> item.setCantidad(item.getCantidad() + cantidad),
                        () -> itemRepo.save(new CarritoItem(carrito, producto, cantidad)));
    }

    /** Elimina un item del carrito por su id */
    @Transactional
    public void eliminarItem(Long itemId) {
        itemRepo.deleteById(itemId);
    }

    /** Actualiza la cantidad de un item */
    @Transactional
    public void actualizarCantidad(Long itemId, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            itemRepo.deleteById(itemId);
        } else {
            CarritoItem item = itemRepo.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item no encontrado: " + itemId));
            item.setCantidad(nuevaCantidad);
        }
    }

    /** Vacía el carrito completo */
    @Transactional
    public void vaciarCarrito(String email) {
        Carrito carrito = obtenerOCrearCarrito(email);
        carrito.getItems().clear();
        carritoRepo.save(carrito);
    }
}