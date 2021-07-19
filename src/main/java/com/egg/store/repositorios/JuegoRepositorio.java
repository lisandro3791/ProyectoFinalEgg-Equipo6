
package com.egg.store.repositorios;

import com.egg.store.entidades.Juego;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegoRepositorio extends JpaRepository <Juego,String>{
    
    @Query("SELECT j FROM Juego j WHERE j.genero like :genero")
    List<Juego> buscarPorGenero(@Param("genero")String genero);
    
    
    @Modifying
    @Query("UPDATE Juego j  SET j.nombre=:nombre,j.genero=:genero,j.precio=:precio,j.urlImagen=:urlImagen WHERE j.id=:id")
    void modificar(@Param("id") String id,@Param("nombre") String nombre,@Param("genero") String genero,@Param("precio") BigDecimal precio,@Param("urlImagen") String urlImagen);
    
    
    @Query("SELECT j FROM Juego j WHERE j.nombre like :nombre")
    List<Juego> buscarPorNombre(@Param("nombre")String nombre); 
    
    @Modifying
    @Query("UPDATE Juego j  SET j.mostrar=:mostrar WHERE j.id=:id")
    void modificarMostrar(@Param("id") String id,@Param("mostrar") boolean mostrar);
    
    
}
