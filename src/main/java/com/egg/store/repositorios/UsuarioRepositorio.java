/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store.repositorios;

import com.egg.store.entidades.Usuario;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mecha
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
   
    
    //Modifica un Usuario en los parametros que se pasan cuando coincide el campo de DNI
    @Modifying                                                              // , u.fechaNacimiento = :fechaNacimiento
    @Query("UPDATE Usuario u SET u.nombre = :nombre, u.apellido = :apellido  WHERE u.dni = :dni")
    void modificar (@Param ("dni") Long dni, @Param("nombre") String nombre, @Param ("apellido") String apellido);//, @Param("fechaNacimiento") Date fechaNacimiento
    
    //lista todos los usuarios por nombre
    @Query("SELECT u FROM Usuario u WHERE u.nombre= :n")
    List<Usuario> buscarPorNombre(@Param ("n") String nombre);
    
    
    @Query("SELECT u FROM Usuario u WHERE u.usuario = :username")
    Usuario buscarPorNombreDeUsuaruio ( @Param("username") String username);
    
    
}

