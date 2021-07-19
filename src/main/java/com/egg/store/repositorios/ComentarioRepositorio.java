/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store.repositorios;

import com.egg.store.entidades.Comentario;
import com.egg.store.entidades.Juego;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String> {
    
    @Query("SELECT c FROM Comentario c WHERE c.juego = :juego ")
    List<Comentario> buscarPorJuego(@Param("juego")Juego juego);
    
    
    @Modifying
    @Query("UPDATE Comentario c  SET c.texto=:texto WHERE c.id=:id")
    void modificar(@Param("id") String id,@Param("texto") String texto);
    
    @Modifying
    @Query("DELETE FROM Comentario c WHERE c.juego =:juego ")
    void borrarComentario(@Param("juego")Juego juego);
    
    
}
