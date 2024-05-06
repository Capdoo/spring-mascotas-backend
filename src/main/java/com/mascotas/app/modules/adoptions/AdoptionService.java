package com.mascotas.app.modules.adoptions;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AdoptionService {
	List<AdoptionEntity> listAllAdoptions();
	//Crud
	AdoptionEntity createAdoption(AdoptionDto adoptionDto);
	AdoptionEntity readAdoption(Long id);
	AdoptionEntity updateAdoption(AdoptionDto adoptionDto);
	AdoptionEntity deleteAdoption(AdoptionDto adoptionDto);
	//Business
	List<AdoptionEntity> listAllByPetId(Long petId);
	boolean existsById(Long id);
}