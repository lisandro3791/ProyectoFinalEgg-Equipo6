 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store.servicios;

import com.egg.store.entidades.Usuario;
import com.egg.store.repositorios.RolRepositorio;
import com.egg.store.repositorios.UsuarioRepositorio;
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

/**
 *
 * @author Mecha
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
       private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private RolRepositorio rolRepositorio;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
 
     @ Transactional
    //Recibe tambien el parametro username, hace falta agregarlo String username
    public void crear(String nombre, String apellido ,String contrasena,String mail,  Date nacimiento, long dni,String rolId){
        
        Usuario usuario = new Usuario ();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
     //   usuario.serUsername(username);
        usuario.setContrasena(encoder.encode(contrasena));
        usuario.setMail(mail);
        usuario.setNacimiento(nacimiento);
        usuario.setDni(dni);
        usuario.setRol(rolRepositorio.getOne(rolId));
        
        usuarioRepositorio.save(usuario);
                       
    }
    
     @ Transactional
    public List <Usuario> buscarTodos() {
             List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;

    }
    

        @Transactional(readOnly =true)
        public List<Usuario> buscarPorNombre (String nombre){
            return usuarioRepositorio.buscarPorNombre(nombre);
        }
        
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id){
        return  usuarioRepositorio.buscarPorId(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Usuario usuario = usuarioRepositorio.buscarPorMail(username);
            if (usuario == null){
               throw new UsernameNotFoundException ("no se encontro ningun usuario con username" + username);
            }
            
            GrantedAuthority rol=new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().getNombre());
            
            ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session= attributes.getRequest().getSession(true);

            session.setAttribute("mail", usuario.getMail());
            session.setAttribute("rol", rol);
            session.setAttribute("nombre", usuario.getNombre());
            session.setAttribute("apellido", usuario.getApellido());
            session.setAttribute("nacimiento", usuario.getNacimiento());
            
            
        return new User(usuario.getMail(), usuario.getContrasena(), Collections.singletonList(rol));
        }

        

}
    
    

