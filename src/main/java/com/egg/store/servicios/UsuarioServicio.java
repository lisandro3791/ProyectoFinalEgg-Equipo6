 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store.servicios;

import com.egg.store.entidades.Usuario;
import com.egg.store.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mecha
 */
@Service
public class UsuarioServicio {
    
    @Autowired
       private UsuarioRepositorio usuarioRepositorio;
 
     @ Transactional
    
    public void crear(String nombre, String apellido ,String contraseña,String mail,  Date nacimiento, long dni){
        
        Usuario usuario = new Usuario ();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setContraseña(contraseña);
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
    
}
