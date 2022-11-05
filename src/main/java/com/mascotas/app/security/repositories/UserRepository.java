package com.mascotas.app.security.repositories;

import java.util.Optional;

import com.mascotas.app.security.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

	Optional<UserModel> findByUsername(String username);
	Optional<UserModel> findById(long id);
	Optional<UserModel> findByDni(String dni);

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);

	boolean existsByDni(String dni);
	
}
