package com.es.seguridadsession.service;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene un producto buscándolo por su ID
     * @param id
     * @return
     */
    public ProductoDTO getById(String id) {
        // TODO
        return null;
    }

    /**
     * Inserta un producto dentro la tabla productos
     * @param productoDTO
     * @return
     */
    public ProductoDTO insert(ProductoDTO productoDTO) {
        // TODO
        return null;
    }

}
