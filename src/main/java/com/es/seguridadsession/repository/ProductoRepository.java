package com.es.seguridadsession.repository;

import com.es.seguridadsession.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    public Optional<Producto> findByNombre(String nombre);
}
