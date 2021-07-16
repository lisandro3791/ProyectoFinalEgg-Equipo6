package com.egg.store.servicios;

import com.egg.store.entidades.Juego;
import com.egg.store.entidades.Usuario;
import com.egg.store.repositorios.JuegoRepositorio;
import com.egg.store.repositorios.RolRepositorio;
import com.egg.store.repositorios.UsuarioRepositorio;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JuegoRepositorio juegoRepositorio;

    @Transactional
    //Recibe tambien el parametro username, hace falta agregarlo String username
    public void crear(String nombre, String apellido, String contrasena, String mail, Date nacimiento, long dni, String rolId) {

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        //   usuario.serUsername(username);
        usuario.setContrasena(encoder.encode(contrasena));
        usuario.setMail(mail);
        usuario.setNacimiento(nacimiento);
        usuario.setDni(dni);
        usuario.setRol(rolRepositorio.getOne(rolId));
        usuario.setSaldo(BigDecimal.ZERO);
        usuario.setJuegoU(new ArrayList<>());
        usuarioRepositorio.save(usuario);

    }

    @Transactional
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;

    }

    // agregar saldo 
    @Transactional
    public void CargarSaldo(Long id, BigDecimal saldo) {
        Usuario usuario = usuarioRepositorio.buscarPorId(id);
        usuario.setSaldo(usuario.getSaldo().add(saldo));
    }

    //agregar juego
    @Transactional
    public void CargarJuego(Long idUsuario, String idJuego) {
        Usuario usuario = usuarioRepositorio.buscarPorId(idUsuario);
        Juego juego = juegoRepositorio.getById(idJuego);
        List<Juego> juegos = usuario.getJuegoU();
        usuario.setSaldo(usuario.getSaldo().subtract(juego.getPrecio()));
        juegos.add(juego);
        usuario.setJuegoU(juegos);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepositorio.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepositorio.buscarPorId(id);
    }

    @Transactional
    public void modificar(Long id, String newNombre, String newApellido, String newMail, Date newNacimiento) {
        usuarioRepositorio.modificar(id, newNombre, newApellido, newMail, newNacimiento);
    }

    @Transactional(readOnly = true)
    public List<Juego> juegos(Long id) {
        Usuario usuario = usuarioRepositorio.getById(id);
        return usuario.getJuegoU();
    }

    @Transactional
    public void comprarJuego(Long idUser, String idJuego) {
        Usuario usuario = usuarioRepositorio.buscarPorId(idUser);
        Juego juegos = juegoRepositorio.getById(idJuego);
        usuario.getJuegoU().add(juegos);

    }

    @Transactional
    public void EliminarDeBi(String idJuego, Long idUser) {
        Usuario usuario = usuarioRepositorio.buscarPorId(idUser);
        Juego juegos = juegoRepositorio.getById(idJuego);
        int jue = usuario.getJuegoU().indexOf(juegos);
        if (usuario.getJuegoU().indexOf(juegos)+1 >= 0) {
            usuario.getJuegoU().remove(jue+1);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorMail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("no se encontro ningun usuario con username" + username);

        }

        GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("mail", usuario.getMail());
        session.setAttribute("rol", rol);
        session.setAttribute("nombre", usuario.getNombre());
        session.setAttribute("apellido", usuario.getApellido());
        session.setAttribute("nacimiento", usuario.getNacimiento());
        session.setAttribute("rolNombre", usuario.getRol().getNombre());
        session.setAttribute("usuarioId", usuario.getId());

        return new User(usuario.getMail(), usuario.getContrasena(), Collections.singletonList(rol));

    }

}
