package com.mascotas.app.modules.shelters;

import java.util.List;

import com.mascotas.app.security.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShelterRepository extends JpaRepository<ShelterModel, Long>{

	public List<ShelterModel> findAll();
	//
	// public List<ShelterModel> findAllByPar(UserModel userModel);

	//public boolean existsByUsuario();
}
