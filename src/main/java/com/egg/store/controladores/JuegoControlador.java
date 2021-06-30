
package com.egg.store.controladores;

import com.egg.store.entidades.Juego;
import com.egg.store.servicios.JuegoServicio;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/juegos")
public class JuegoControlador {
    @Autowired
    private JuegoServicio juegoServicio;
    
    
    @GetMapping("/ver-todos")
    public ModelAndView mostrarTodos(){
        ModelAndView mav=new ModelAndView("juegos-lista");
        List<Juego> juegos= juegoServicio.buscarTodos();
        mav.addObject("juegos", juegos);
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearJuego(){
        ModelAndView mav=new ModelAndView("cargarJuegos");
        mav.addObject("juego", new Juego());
        return mav;
    }
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre,@RequestParam String genero,@RequestParam BigDecimal precio,@RequestParam String urlImagen){
        juegoServicio.crear(nombre, genero, precio,urlImagen);
        return new RedirectView("/ver-todos");
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarJuego(@PathVariable String id){
        ModelAndView mav=new ModelAndView("editar-juego");
        mav.addObject("juego", juegoServicio.buscarPorId(id));
        return mav;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id,@RequestParam String nombre,@RequestParam String genero,@RequestParam BigDecimal precio,@RequestParam String urlImagen){
        juegoServicio.modificar(id, nombre, genero, precio, urlImagen);
        return new RedirectView("/juegos/ver-todos");
    }
    
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id){
        juegoServicio.eliminar(id);
        return new RedirectView("/juegos/ver-todos");
    }
    
    
    
}
