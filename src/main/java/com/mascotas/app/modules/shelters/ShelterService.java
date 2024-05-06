package com.mascotas.app.modules.shelters;

import java.util.List;

import com.mascotas.app.security.models.UserEntity;
import org.springframework.stereotype.Service;

public interface ShelterService {
	List<ShelterEntity> listAllShelters();
	//Crud
	ShelterEntity createShelter(ShelterDto shelterDto, UserEntity userEntity);
	ShelterEntity readShelter(Long id);
	ShelterEntity updateShelter(Long id);
	ShelterEntity deleteShelter(Long id);
	//business
	boolean existsById(Long id);
}