package com.es.seguridadsession.controller;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.service.ProductoService;
import com.es.seguridadsession.service.SessionService;
import com.es.seguridadsession.utils.TokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private SessionService sessionService;


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
        ProductoDTO productoDTO = productoService.getById(id);

        return new ResponseEntity<>(productoDTO,HttpStatus.OK);
    }

    /**
     * INSERTAR PRODUCTO
     * A este método sólo pueden acceder los usuarios que tengan ROL ADMIN
     * @param productoDTO
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<ProductoDTO> insert(
            @RequestBody ProductoDTO productoDTO,
            HttpServletRequest request
    ) {
        // TODO
        if(productoDTO == null){
            //throw exception
        }

        String token = "";
        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("tokenSession")){
                token = cookie.getValue();
                break;
            }
        }

        // comprobar si el token es valido
        if(!sessionService.checkToken(token)){
            throw new RuntimeException();
        }

        String decrypt= "";
        try {
            decrypt = TokenUtil.decrypt(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String[] decrypter = decrypt.split(":");
        String nombreUsuario = decrypter[0];

        ProductoDTO productoDTO1 = productoService.insert(productoDTO, nombreUsuario);

        return new ResponseEntity<>(productoDTO1, HttpStatus.OK);
    }


}
