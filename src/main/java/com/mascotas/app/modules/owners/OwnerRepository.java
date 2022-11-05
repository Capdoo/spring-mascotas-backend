package com.mascotas.app.modules.owners;

import java.util.List;
import java.util.Optional;

import com.mascotas.app.security.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OwnerRepository extends JpaRepository<OwnerModel, Long>{
	
	List<OwnerModel> findAll();

	Optional<OwnerModel> findByUser(UserModel usuarioModel);

}
