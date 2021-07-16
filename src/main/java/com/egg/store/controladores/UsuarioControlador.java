package com.egg.store.controladores;

import com.egg.store.entidades.Usuario;
import com.egg.store.servicios.JuegoServicio;
import com.egg.store.servicios.RolServicio;
import com.egg.store.servicios.UsuarioServicio;
import java.math.BigDecimal;
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
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private RolServicio rolServicio;

    @GetMapping("/ver-todos")
    public ModelAndView buscarTodos() {
        ModelAndView mav = new ModelAndView("usuarios-lista");
        List<Usuario> usuarios = usuarioServicio.buscarTodos();
        mav.addObject("usuarios", usuarios);
        return mav;

    }

    @GetMapping("/{id}")
    public ModelAndView mostrarPorId(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("saldo");
        mav.addObject("usuario", usuarioServicio.buscarPorId(id));
        return mav;
    }

    @GetMapping("/buscar/{nombre}")
    public ModelAndView mostrarPorNombre(@PathVariable String nombre) {
        ModelAndView mav = new ModelAndView("Usuarios");
        mav.addObject("usuarios", usuarioServicio.buscarPorNombre(nombre));
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView formularioRegistro() {
        ModelAndView mav = new ModelAndView("registrarse");
        mav.addObject("usuario", new Usuario());
        mav.addObject("roles", rolServicio.buscarTodos());
        return mav;
    }

    // Hace falta crear los @RequestParam String username y tambien agregar al .crear  un username        
    @PostMapping("/guardar")
    public RedirectView guardar(
            @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String contrasena, @RequestParam long dni,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date nacimiento, @RequestParam String mail, @RequestParam("rol") String rolId) {
        usuarioServicio.crear(nombre, apellido, contrasena, mail, nacimiento, dni, rolId);
        return new RedirectView("/");
    }

    @GetMapping("/mi-perfil/{id}")
    public ModelAndView verPerfil(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("perfil");
        mav.addObject("usuario", usuarioServicio.buscarPorId(id));
        return mav;
    }

    @PostMapping("/cargar-saldo/{id}")
    public RedirectView cargarSaldo(
            @PathVariable Long id,
            @RequestParam BigDecimal saldo) {
        usuarioServicio.CargarSaldo(id, saldo);
        return new RedirectView("/usuario/mi-perfil/{id}");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPerfeil(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("editarperfil");
        mav.addObject("usuario", usuarioServicio.buscarPorId(id));
        mav.addObject("roles", rolServicio.buscarTodos());

        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam("id") Long id, @RequestParam("nombre") String newNombre, @RequestParam("apellido") String newApellido, @RequestParam("mail") String newMail, @RequestParam("nacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date newNaciemiento) {
        usuarioServicio.modificar(id, newNombre, newApellido, newMail, newNaciemiento);
        return new RedirectView("/");
    }

    @GetMapping("/mis-juegos/{id}")
    public ModelAndView biblioteca(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("biblioteca");
        mav.addObject("juegos", usuarioServicio.juegos(id));
        return mav;
    }

    @PostMapping("/comprar/{id}/{idUser}")
    public RedirectView comparar(@PathVariable String id, @PathVariable Long idUser) {
        usuarioServicio.comprarJuego(idUser, id);
        return new RedirectView("/juegos/ver-todo");
    }
    
    @PostMapping("/eliminar/{id}/{idUser}")
    public RedirectView eliminarDeBiblioteca(@PathVariable String id,@PathVariable Long idUser){
        usuarioServicio.EliminarDeBi(id, idUser);
        return new RedirectView("/");
    }
}
