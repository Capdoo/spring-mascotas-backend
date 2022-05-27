package com.mascotas.app.modules.adopciones;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.modules.mascotas.MascotaModel;


public interface AdopcionRepository extends JpaRepository<AdopcionModel, Integer>{

	public List<AdopcionModel> findAll();
	public List<AdopcionModel> findAllByMascota(MascotaModel mascotaModel);
	
}
