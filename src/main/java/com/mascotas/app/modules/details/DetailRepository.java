package com.mascotas.app.modules.details;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<DetailEntity, Long>{
	public List<DetailEntity> findAllBySpecies(String species);
	public Optional<DetailEntity> findBySpeciesAndBreed(String species, String breed);
	public Boolean existsBySpeciesAndBreed(String species, String breed);
}
