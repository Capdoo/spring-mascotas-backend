package com.mascotas.app.modules.mascotas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MascotaRepository extends JpaRepository<MascotaModel, Integer>{

	public List<MascotaModel> findAll();
	
}
