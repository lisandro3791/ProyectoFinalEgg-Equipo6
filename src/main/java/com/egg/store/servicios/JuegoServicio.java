
package com.egg.store.servicios;

import com.egg.store.entidades.Juego;
import com.egg.store.repositorios.JuegoRepositorio;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JuegoServicio {
    
    @Autowired
    private JuegoRepositorio juegoRepositorio;
    
    @Transactional
    public void crear(String nombre, String genero,BigDecimal precio, String urlImagen){
        Juego juego=new Juego();
        juego.setNombre(nombre);
        juego.setGenero(genero);
        juego.setPrecio(precio);
        juego.setPuntaje(0.0);
        juego.setUrlImagen(urlImagen);
        juegoRepositorio.save(juego);
    }
    
    @Transactional(readOnly = true)
    public List<Juego> buscarTodos(){
        return juegoRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Juego buscarPorId(String id){
        return  juegoRepositorio.getOne(id);
    }
    
    @Transactional
    public void eliminar(String id){
        juegoRepositorio.deleteById(id);
    }
    
    
    @Transactional
    public List<Juego> buscarPorGenero(String genero){
        List<Juego> juegos =juegoRepositorio.buscarPorGenero(genero);
        return juegos;
    }
    
    @Transactional
    public void modificar( String id, String nombre, String genero, BigDecimal precio, String urlImagen){
        juegoRepositorio.modificar(id, nombre, genero, precio, urlImagen);
    }
    
    @Transactional
    public List<Juego> buscarPorNombre(String nombre){
       return juegoRepositorio.buscarPorNombre(nombre);
    }
    
   
 
   
    
}
