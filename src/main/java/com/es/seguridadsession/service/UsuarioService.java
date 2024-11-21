package com.es.seguridadsession.service;

import com.es.seguridadsession.dto.UsuarioDTO;
import com.es.seguridadsession.dto.UsuarioInsertDTO;
import com.es.seguridadsession.model.Session;
import com.es.seguridadsession.model.Usuario;
import com.es.seguridadsession.repository.SessionRepository;
import com.es.seguridadsession.repository.UsuarioRepository;
import com.es.seguridadsession.utils.Crypter;
import com.es.seguridadsession.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public String login(UsuarioDTO userLogin) {

        // Comprobar si user y pass son correctos -> obtener de la BDD el usuario
        String nombreUser = userLogin.getNombre();

        String passUser = Crypter.hashPassword(userLogin.getPassword());
        System.out.println("CONTRRASEÑAA::"+passUser);
        Usuario u = getUsuario(nombreUser, passUser);

        // Si coincide -> Insertar una sesión
        // Genero un TOKEN
        String token = null; // Esto genera un token aleatorio
        try {
            token = TokenUtil.encrypt(u.getNombre()+":"+u.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);  // manejar la exception
        }
        System.out.println("Token generado: "+token);
        // Almaceno la Session en la base de datos
        Session s = new Session();
        s.setToken(token);
        s.setUsuario(u);
//        s.setExpirationDate(
//                LocalDateTime.of(
//                        LocalDate.of(2024, 11, 20),
//                        LocalTime.now()
//                ));
        s.setExpirationDate(
                LocalDateTime.now().plusDays(1)
        );

        sessionRepository.save(s);

        return token;

    }



    public UsuarioInsertDTO insert(String nombreUsuario,UsuarioInsertDTO usuarioInsertDTO){
        String nombre = usuarioInsertDTO.getNombre();
        String password1= usuarioInsertDTO.getPassword1();
        String password2= usuarioInsertDTO.getPassword2();

        if (!password1.equals(password2)){
            // throw exception
        }

        Usuario usuarioHandler = getUsuario(nombreUsuario);

        if(!usuarioHandler.getAdmin()){
            // 401
            throw new RuntimeException();
        }

        List<Usuario> users = usuarioRepository.findByNombre(nombre);

        if (!users.isEmpty()){
            // Throw exception -> El nombre de usuario ya existe y tiene que ser único
        }

        String passHashed = Crypter.hashPassword(password1);
        Usuario u = getUsuarioFromInsertDTO(usuarioInsertDTO, passHashed);

        usuarioRepository.save(u);

        return usuarioInsertDTO;
    }


// obtener un usuario a partir de su nombre y pass
    private Usuario getUsuario(String nombreUser, String passUser) {
        List<Usuario> users = usuarioRepository.findByNombre(nombreUser);

        Usuario u = users
                .stream()
                .filter(user -> user.getNombre().equals(nombreUser) && user.getPassword().equals(passUser))
                .findFirst()
                .orElseThrow(); // LANZAR EXCEPCION PROPIA
        return u;
    }

    // obtener un usuario a partir de insertDTO
    public Usuario getUsuarioFromInsertDTO(UsuarioInsertDTO usuarioInsertDTO, String passHashed){
        Usuario u = new Usuario();
        u.setPassword(passHashed);
        u.setNombre(usuarioInsertDTO.getNombre());
        u.setAdmin(usuarioInsertDTO.getAdmin());

        return u;
    }


    // obtener un usuario a partir de su nombre
    private Usuario getUsuario(String nombreUsuario){
        List<Usuario> users = usuarioRepository.findByNombre(nombreUsuario);

        return users
                .stream()
                .filter(user -> user.getNombre().equals(nombreUsuario))
                .findFirst()
                .orElseThrow(); // LANZAR EXCEPCION PROPIA

    }

}
