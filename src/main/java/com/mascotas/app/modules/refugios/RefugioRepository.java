package com.mascotas.app.modules.refugios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.security.models.UsuarioModel;


public interface RefugioRepository extends JpaRepository<RefugioModel, Integer>{

	public List<RefugioModel> findAll();
	public List<RefugioModel> findAllByUsuario(UsuarioModel usuarioModel);
}
