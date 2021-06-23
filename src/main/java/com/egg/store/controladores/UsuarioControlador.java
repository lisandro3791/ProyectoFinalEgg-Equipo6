
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


@Controller
@RequestMapping("/usuario")
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
    public ModelAndView formularioRegistro(){
    return new ModelAndView("/registrarse");
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(
            @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String password, @RequestParam long dni,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date nacimiento, @RequestParam String email) {
        usuarioServicio.crear(nombre, apellido, password, email, nacimiento, dni);
        return new RedirectView("/perfil");
    }
}
