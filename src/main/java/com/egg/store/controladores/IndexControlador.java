
package com.egg.store.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexControlador {
    
    @GetMapping
    public ModelAndView inicio() {
        return new ModelAndView("index");
    }
    
}
