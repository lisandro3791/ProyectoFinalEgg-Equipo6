 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store.servicios;

import com.egg.store.entidades.Usuario;
import com.egg.store.repositorios.UsuarioRepositorio;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mecha
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
       private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
 
     @ Transactional
    //Recibe tambien el parametro username, hace falta agregarlo String username
    public void crear(String nombre, String apellido ,String contrasena,String mail,  Date nacimiento, long dni){
        
        Usuario usuario = new Usuario ();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
     //   usuario.serUsername(username);
        usuario.setContrasena(encoder.encode(contrasena));
        usuario.setMail(mail);
        usuario.setNacimiento(nacimiento);
        usuario.setDni(dni);
        
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
 
        return new User(usuario.getMail(), usuario.getContrasena(), Collections.emptyList());
        }

        

}
    
    

