package com.mascotas.app.modules.pets;

import java.util.List;

import com.mascotas.app.modules.owners.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Long>{
	public List<PetEntity> findAll();
	public List<PetEntity> findAllByOwner(OwnerEntity ownerEntity);
}
