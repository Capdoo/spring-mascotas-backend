package com.mascotas.app.modules.detalles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DetalleRepository extends JpaRepository<DetalleModel, Integer>{

	
	public List<DetalleModel> findAll();
	public List<DetalleModel> findAllByEspecie(String especie);
	public Optional<DetalleModel> findByEspecieAndRaza(String especie, String raza);
	
}
