/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store.controladores;

import com.egg.store.entidades.Comentario;
import com.egg.store.entidades.Usuario;
import com.egg.store.entidades.Juego;
import com.egg.store.servicios.ComentarioServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/comentarios")
public class ComentarioControlador {
    
    @Autowired
    private ComentarioServicio comentarioServicio;
    
    @GetMapping
    public ModelAndView buscarTodos(){
        ModelAndView mav = new ModelAndView("comentarios-lista");
        List<Comentario> comentarios = comentarioServicio.buscarTodos();
        mav.addObject("comentarios", comentarios);
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearComentario(){
        ModelAndView mav = new ModelAndView("comentario-formulario");
        mav.addObject("comentario", new Comentario());
        mav.addObject("title", "Crear Comentario");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    
    @PostMapping("/comentar/{usuarioId}")
    public RedirectView comentar(@PathVariable Long usuarioId, @RequestParam String juegoId, @RequestParam String puntuacion, @RequestParam String texto){
        comentarioServicio.comentar(usuarioId, juegoId, Integer.parseInt(puntuacion), texto);
        return new RedirectView("/juegos/"+juegoId);
        
    }
}
