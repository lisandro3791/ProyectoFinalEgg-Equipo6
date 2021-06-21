package com.egg.store.servicios;

import com.egg.store.entidades.Usuario;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.egg.store.entidades.Comentario;
import com.egg.store.entidades.Juego;
import com.egg.store.repositorios.ComentarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    
    @Transactional
    public void comentar(Usuario usuario, Juego juego, String texto, Date fecha) {
        //Crea un comentario (sin puntuación)
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setJuego(juego);
        comentario.setTexto(texto);
        comentario.setFecha(fecha);

        comentarioRepositorio.save(comentario);

    }

    @Transactional
    public void puntuar(Usuario usuario, Juego juego, Integer puntuacion, String texto, Date fecha) {
        //Crea un comentario con puntuación
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setJuego(juego);
        comentario.setPuntuacion(puntuacion);
        comentario.setTexto(texto);
        comentario.setFecha(fecha);

        comentarioRepositorio.save(comentario);

    }
    
    @Transactional(readOnly = true)
    public List<Comentario> buscarTodos(){
        return comentarioRepositorio.findAll();
    }

}
