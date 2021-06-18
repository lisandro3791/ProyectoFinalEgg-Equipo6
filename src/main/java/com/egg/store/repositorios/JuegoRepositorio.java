
package com.egg.store.repositorios;

import com.egg.store.entidades.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegoRepositorio extends JpaRepository <Juego,String>{
    
    
}
