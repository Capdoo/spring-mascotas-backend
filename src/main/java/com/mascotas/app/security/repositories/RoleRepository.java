package com.mascotas.app.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.models.RoleModel;


@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer>{
	
	Optional<RoleModel> findByRoleName(RoleName roleName);
	
	
}
