/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store.controladores;

import com.egg.store.entidades.Usuario;
import com.egg.store.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Mecha
 */

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {
    
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/ver-todos") 
    public ModelAndView buscarTodos(){
        ModelAndView mav = new ModelAndView("usuarios-lista");
        List<Usuario>usuarios = usuarioServicio.buscarTodos();
        mav.addObject("usuarios", usuarios);
        return mav ;
        
    }
    
    @GetMapping("/crear")
    public ModelAndView mostrarFormulario(){
        return new ModelAndView("usuario-formulario");
    }
    
    
    @PostMapping("/guardar")
    public RedirectView guardar( @RequestParam String nombre,@RequestParam String apellido, @RequestParam String contraseña,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd" ) Date nacimiento, @RequestParam long dni){
    
            usuarioServicio.crear(nombre, apellido,contraseña, nacimiento, dni);
       
        return new RedirectView("/usuarios/ver-todos") ;
    }
    
    
}

