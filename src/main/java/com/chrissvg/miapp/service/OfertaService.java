package com.chrissvg.miapp.service;

import com.chrissvg.miapp.model.Oferta;
import com.chrissvg.miapp.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    // Listar todas
    public List<Oferta> listarTodas() {
        return ofertaRepository.findAll();
    }

    // Buscar por ID
    public Optional<Oferta> buscarPorId(Long id) {
        return ofertaRepository.findById(id);
    }

    // Guardar o actualizar
    public Oferta guardar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    // Eliminar
    public void eliminar(Long id) {
        ofertaRepository.deleteById(id);
    }

    // Contar total
    public long contar() {
        return ofertaRepository.count();
    }

    // Listar ofertas activas (vigentes hoy)
    public List<Oferta> listarOfertasActivas() {
        LocalDate hoy = LocalDate.now();
        return ofertaRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(hoy, hoy);
    }

    // Listar ofertas por producto
    public List<Oferta> buscarPorProducto(Long productoId) {
        return ofertaRepository.findByProductoId(productoId);
    }
}