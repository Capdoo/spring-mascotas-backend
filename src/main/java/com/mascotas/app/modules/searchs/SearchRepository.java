package com.mascotas.app.modules.searchs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.modules.pets.PetEntity;


public interface SearchRepository extends JpaRepository<SearchModel, Long>{
	
	public List<SearchModel> findAll();
	public List<SearchModel> findAllByPet(PetEntity petEntity);
	
}
