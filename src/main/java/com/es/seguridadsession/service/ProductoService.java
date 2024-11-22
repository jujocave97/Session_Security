package com.es.seguridadsession.service;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.model.Producto;
import com.es.seguridadsession.model.Usuario;
import com.es.seguridadsession.repository.ProductoRepository;
import com.es.seguridadsession.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene un producto buscándolo por su ID
     * @param id
     * @return
     */
    public ProductoDTO getById(String id) {
        // TODO
        long idL = 0L;
        try{
            idL = Long.parseLong(id);
        }catch (Exception e){
            // lanzar excepcion
        }

        Producto p = productoRepository.findById(idL).orElse(null);
        if(p == null ){
            //throw exception
        }
        return getDTOFromProducto(p);
    }

    /**
     * Inserta un producto dentro la tabla productos
     * @param productoDTO
     * @return
     */
    public ProductoDTO insert(ProductoDTO productoDTO, String nombreUsuario) {
        // TODO
        Usuario usuarioHandler = getUsuario(nombreUsuario);

        if(!usuarioHandler.getAdmin()){
            // 401
            throw new RuntimeException();
        }

        Producto p = getProductoFromDTO(productoDTO);

        productoRepository.save(p);

        return productoDTO;
    }


    public ProductoDTO getDTOFromProducto(Producto p){
        ProductoDTO pDTO = new ProductoDTO();
        pDTO.setNombre(p.getNombre());
        pDTO.setPrecio(p.isPrecio());
        pDTO.setStock(p.getStock());

        return pDTO;
    }

    // obtener usuario para saber si es admin
    private Usuario getUsuario(String nombreUsuario){
        List<Usuario> users = usuarioRepository.findByNombre(nombreUsuario);

        return users
                .stream()
                .filter(user -> user.getNombre().equals(nombreUsuario))
                .findFirst()
                .orElseThrow(); // LANZAR EXCEPCION PROPIA

    }

    private Producto getProductoFromDTO (ProductoDTO productoDTO){
        Producto p = new Producto();
        p.setNombre(productoDTO.getNombre());
        p.setPrecio(productoDTO.isPrecio());
        p.setStock(productoDTO.getStock());

        return p;
    }
}
