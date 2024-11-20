package com.es.seguridadsession.controller;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * CLASE CONTROLLER DE PRODUCTOS
 * ESTOS RECURSOS ESTÁN PROTEGIDOS, Y SÓLO SE PUEDE ACCEDER AQUÍ SI EL USUARIO TIENE UNA SESSION ACTIVA
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    /**
     * GET PRODUCTO POR SU ID
     * A este método pueden acceder todo tipo de usuarios
     * tanto los que tengan ROL USER como los que tengan ROL ADMIN
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(
            @PathVariable String id
    ) {
        // TODO
        return null;
    }

    /**
     * INSERTAR PRODUCTO
     * A este método sólo pueden acceder los usuarios que tengan ROL ADMIN
     * @param productoDTO
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<ProductoDTO> insert(
            @RequestBody ProductoDTO productoDTO
    ) {
        // TODO
        return null;
    }


}
