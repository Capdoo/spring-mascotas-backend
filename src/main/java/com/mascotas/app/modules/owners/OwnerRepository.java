package com.mascotas.app.modules.owners;

import java.util.List;
import java.util.Optional;

import com.mascotas.app.security.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OwnerRepository extends JpaRepository<OwnerEntity, Long>{
	
	List<OwnerEntity> findAll();

	Optional<OwnerEntity> findByUser(UserEntity usuarioModel);

}
