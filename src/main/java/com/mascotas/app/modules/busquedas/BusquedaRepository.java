package com.mascotas.app.modules.busquedas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.modules.mascotas.MascotaModel;


public interface BusquedaRepository extends JpaRepository<BusquedaModel, Integer>{
	
	public List<BusquedaModel> findAll();
	public List<BusquedaModel> findAllByMascota(MascotaModel mascotaModel);
	
}
