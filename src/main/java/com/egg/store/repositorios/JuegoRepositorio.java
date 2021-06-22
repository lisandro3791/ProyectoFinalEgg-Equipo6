
package com.egg.store.repositorios;

import com.egg.store.entidades.Juego;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegoRepositorio extends JpaRepository <Juego,String>{
    
    @Query("SELECT j FROM Juego j WHERE j.genero = :genero ")
    List<Juego> buscarPorGenero(@Param("genero")String genero);
}
