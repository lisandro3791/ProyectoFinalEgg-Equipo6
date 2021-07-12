package com.egg.store.controladores;

import com.egg.store.entidades.Comentario;
import com.egg.store.entidades.Juego;
import com.egg.store.servicios.ComentarioServicio;
import com.egg.store.servicios.JuegoServicio;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/ver-todo")
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("catalogo");
        List<Juego> juego = juegoServicio.buscarTodos();
        mav.addObject("juegos", juego);
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/crear")
    public ModelAndView crearJuego() {
        ModelAndView mav = new ModelAndView("cargarJuegos");
        mav.addObject("juego", new Juego());
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam String genero, @RequestParam BigDecimal precio, @RequestParam String urlImagen) {
        juegoServicio.crear(nombre, genero, precio, urlImagen);
        return new RedirectView("/juegos/ver-todo");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public ModelAndView editarJuego(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("editar-juego");
        mav.addObject("juego", juegoServicio.buscarPorId(id));
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView verJuego(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("juegoindividual");
        mav.addObject("juego", juegoServicio.buscarPorId(id));
        mav.addObject("promedio", juegoServicio.calcularPromedio(id));
        List<Comentario> comentarios = comentarioServicio.buscarPorJuego(id);
        mav.addObject("comentarios", comentarios);

        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam String nombre, @RequestParam String genero, @RequestParam BigDecimal precio, @RequestParam String urlImagen) {
        juegoServicio.modificar(id, nombre, genero, precio, urlImagen);
        return new RedirectView("/juegos/ver-todos");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) {
        juegoServicio.eliminar(id);
        return new RedirectView("/juegos/ver-todo");
    }

    @GetMapping("/ver")
    public ModelAndView mostrarJuego(@RequestParam("nombre") String nombre) {
        ModelAndView mav = new ModelAndView("catalogo");
        mav.addObject("juegos", juegoServicio.buscarPorNombre(nombre));

        return mav;
    }

}
