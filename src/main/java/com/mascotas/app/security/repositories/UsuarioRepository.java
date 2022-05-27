package com.mascotas.app.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mascotas.app.security.models.UsuarioModel;



@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer>{

	Optional<UsuarioModel> findByNombreUsuario(String nombreUsuario);
	Optional<UsuarioModel> findById(int id);
	Optional<UsuarioModel> findByDni(String dni);

	
	boolean existsByNombreUsuario(String nombreUsuario);
	boolean existsByEmail(String email);
	
}
