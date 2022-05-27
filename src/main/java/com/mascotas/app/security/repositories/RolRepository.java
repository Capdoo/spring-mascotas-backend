package com.mascotas.app.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mascotas.app.security.enums.RolNombre;
import com.mascotas.app.security.models.RolModel;


@Repository
public interface RolRepository extends JpaRepository<RolModel, Integer>{
	
	Optional<RolModel> findByRolNombre(RolNombre rolNombre);
	
	
}
