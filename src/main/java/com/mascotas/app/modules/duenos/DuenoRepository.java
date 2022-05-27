package com.mascotas.app.modules.duenos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.security.models.UsuarioModel;


public interface DuenoRepository extends JpaRepository<DuenoModel, Integer>{
	
	List<DuenoModel> findAll();
	//boolean existsByUsuario(UsuarioModel usuarioModel);
	Optional<DuenoModel> findByUsuario(UsuarioModel usuarioModel);

}
