package com.egg.store.servicios;

import com.egg.store.entidades.Usuario;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.egg.store.entidades.Comentario;
import com.egg.store.entidades.Juego;
import com.egg.store.repositorios.ComentarioRepositorio;
import com.egg.store.repositorios.JuegoRepositorio;
import com.egg.store.repositorios.UsuarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    
    @Autowired// va autowired?
    private UsuarioServicio usuarioServicio;
    
    @Autowired// va autowired?
    private JuegoServicio juegoServicio;
    
    @Transactional
    public void comentar(Long usuarioId, String juegoId, Integer puntuacion, String texto) {
        //Crea un comentario con puntuación
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuarioServicio.buscarPorId(usuarioId));
        comentario.setJuego(juegoServicio.buscarPorId(juegoId));
        comentario.setPuntuacion(puntuacion);
        comentario.setTexto(texto);
        comentario.setFecha(new Date());

        comentarioRepositorio.save(comentario);

    }
    
    @Transactional(readOnly = true)
    public List<Comentario> buscarTodos(){
        return comentarioRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Comentario buscarPorId(String id){
        return  comentarioRepositorio.getOne(id);
    }
    
    @Transactional
    public void eliminar(String id){
        comentarioRepositorio.deleteById(id);
    }
    
    @Transactional
    public List<Comentario> buscarPorJuego(String juegoId){
        Juego juego = juegoServicio.buscarPorId(juegoId);
        return comentarioRepositorio.buscarPorJuego(juego);
    }
    
    @Transactional
    public void modificar( String id, String texto){
        comentarioRepositorio.modificar(id, texto);
    }
}
