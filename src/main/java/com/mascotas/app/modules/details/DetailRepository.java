package com.mascotas.app.modules.details;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailRepository extends JpaRepository<DetailModel, Long>{

	public List<DetailModel> findAllBySpecies(String species);
	public Optional<DetailModel> findBySpeciesAndBreed(String species, String breed);
	public Boolean existsBySpeciesAndBreed(String species, String breed);
	
}
