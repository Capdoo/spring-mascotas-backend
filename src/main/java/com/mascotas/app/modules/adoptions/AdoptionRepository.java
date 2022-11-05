package com.mascotas.app.modules.adoptions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.modules.pets.PetModel;


public interface AdoptionRepository extends JpaRepository<AdoptionModel, Long>{

	public List<AdoptionModel> findAll();
	public List<AdoptionModel> findAllByPet(PetModel petModel);

	public boolean existsByPet(PetModel petModel);
}
