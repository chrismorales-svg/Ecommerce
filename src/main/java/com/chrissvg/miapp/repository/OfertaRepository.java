package com.chrissvg.miapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrissvg.miapp.model.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findByProductoId(Long productoId);
    List<Oferta> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
        LocalDate inicio, LocalDate fin
    );
}
