package com.mascotas.app.modules.details;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<DetailEntity, Long>{
	List<DetailEntity> findAllBySpecies(String species);
	Optional<DetailEntity> findBySpeciesAndBreed(String species, String breed);
	Boolean existsBySpeciesAndBreed(String species, String breed);
}