
package com.egg.store.repositorios;

import com.egg.store.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, String>{
    
    
    
}
