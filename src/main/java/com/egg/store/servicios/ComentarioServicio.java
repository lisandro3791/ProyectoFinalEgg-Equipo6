package com.egg.store.servicios;

import com.egg.store.entidades.Usuario;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.egg.store.entidades.Comentario;
import com.egg.store.repositorios.ComentarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Transactional
    public void crear(Usuario usuario, String texto, Date fecha) {
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setTexto(texto);
        comentario.setFecha(fecha);

        comentarioRepositorio.save(comentario);

    }
    
    @Transactional(readOnly = true)
    public List<Comentario> buscarTodos(){
        return comentarioRepositorio.findAll();
    }

}
