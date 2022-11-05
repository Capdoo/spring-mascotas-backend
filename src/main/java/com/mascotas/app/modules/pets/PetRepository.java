package com.mascotas.app.modules.pets;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PetRepository extends JpaRepository<PetModel, Long>{

	public List<PetModel> findAll();
	
}
