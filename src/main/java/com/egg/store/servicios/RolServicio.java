package com.egg.store.servicios;

import com.egg.store.entidades.Rol;
import com.egg.store.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServicio {
    
    @Autowired
    private RolRepositorio rolRepositorio;
    
    @Transactional
    public void crear(String nombre){
        Rol rol=new Rol();
        rol.setNombre(nombre);
        rolRepositorio.save(rol);
    }
}
