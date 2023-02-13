package com.mascotas.app.modules.searchs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.modules.pets.PetEntity;


public interface SearchRepository extends JpaRepository<SearchEntity, Long>{
	
	public List<SearchEntity> findAll();
	public List<SearchEntity> findAllByPet(PetEntity petEntity);
	
}
