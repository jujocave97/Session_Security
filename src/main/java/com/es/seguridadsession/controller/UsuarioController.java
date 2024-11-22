package com.es.seguridadsession.controller;

import com.es.seguridadsession.dto.UsuarioDTO;
import com.es.seguridadsession.dto.UsuarioInsertDTO;

import com.es.seguridadsession.service.SessionService;
import com.es.seguridadsession.service.UsuarioService;
import com.es.seguridadsession.utils.Crypter;
import com.es.seguridadsession.utils.TokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SessionService sessionService;

    // CR
    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(
            @RequestBody UsuarioDTO userLogin,
            HttpServletResponse response
    ) {

        // 1º Asegurarnos que userLogin no viene null
        if(userLogin == null || userLogin.getNombre() == null || userLogin.getPassword() == null) {
            // Lanzamos una excepcion
        }

        // 2º Comprobar usuario y contraseña en el service y obtener token
        String token = usuarioService.login(userLogin);

        // 3º añado la cookie a la respuesta
        Cookie cookie = new Cookie("tokenSession", token);
        response.addCookie(cookie);

        return new ResponseEntity<>(userLogin, HttpStatus.OK);
    }


    // INSERT
    @PostMapping("/")
    public ResponseEntity<UsuarioInsertDTO> insert(
            @RequestBody UsuarioInsertDTO nuevoUser,
            HttpServletRequest request
    ) {

        if(nuevoUser == null){
            // throw exception
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


        UsuarioInsertDTO uInsert =  usuarioService.insert(nombreUsuario,nuevoUser);
        if (uInsert == null){
            // throw exception
        }

        return new ResponseEntity<>(uInsert, HttpStatus.OK);
    }

}
