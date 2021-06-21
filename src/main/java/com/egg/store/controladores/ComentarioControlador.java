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
    
    @GetMapping("/ver-todos")
    public ModelAndView buscarTodos(){
        ModelAndView mav = new ModelAndView("comentarios-lista");
        List<Comentario> comentarios = comentarioServicio.buscarTodos();
        mav.addObject("comentarios", comentarios);
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView mostrarFormulario(){
        return new ModelAndView("comentario-formulario");
    }
    
    @PostMapping("/comentar")
    public RedirectView guardar(@RequestParam Usuario usuario, @RequestParam Juego juego, @RequestParam String texto, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha){
        comentarioServicio.comentar(usuario, juego, texto, fecha);
        return new RedirectView("/comentarios/ver-todos");
    }
    
    @PostMapping("/puntuar")
    public RedirectView puntuar(@RequestParam Usuario usuario, @RequestParam Juego juego, @RequestParam Integer puntuacion, @RequestParam String texto, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha){
        comentarioServicio.puntuar(usuario, juego, puntuacion, texto, fecha);
        return new RedirectView("/comentarios/ver-todos");
    }
}
