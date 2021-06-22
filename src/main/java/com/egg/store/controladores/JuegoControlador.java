
package com.egg.store.controladores;

import com.egg.store.servicios.JuegoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/juegos")
public class JuegoControlador {
    @Autowired
    private JuegoServicio juegoServicio;
    
    
    
}
