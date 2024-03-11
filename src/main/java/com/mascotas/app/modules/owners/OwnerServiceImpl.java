package com.mascotas.app.modules.owners;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import com.mascotas.app.security.services.UserService;
import com.mascotas.app.security.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.utils.FechaUtil;

@Service
public class OwnerServiceImpl implements OwnerService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
    UserServiceImp userServiceImp;
	@Autowired
	UserService userService;

	@Override
	public List<OwnerEntity> listAllOwners() {
		return ownerRepository.findAll();
	}

	@Override
	public OwnerEntity createOwner(OwnerDTO ownerDTO) {
		UserEntity userEntity = userService.readUser(ownerDTO.getUser_id());
		if (userEntity == null){
			return null;
		}
		OwnerEntity ownerEntity = OwnerEntity.builder()
				.registerDate(new Timestamp(System.currentTimeMillis()))
				.numberPets(0)
				.rate(0)
				.user(userEntity)
				.state("CREATED").build();
		return ownerRepository.save(ownerEntity);
	}

	@Override
	public OwnerEntity readOwner(Long id) {
		return ownerRepository.findById(id).orElse(null);
	}

	@Override
	public OwnerEntity updateOwner(OwnerDTO ownerDTO) {
		OwnerEntity ownerEntity = readOwner(ownerDTO.getId());
		ownerEntity.setRate(ownerDTO.getRate());
		return ownerRepository.save(ownerEntity);
	}

	@Override
	public OwnerEntity deleteOwner(OwnerDTO ownerDTO) {
		OwnerEntity ownerEntity = readOwner(ownerDTO.getId());
		ownerEntity.setState("DELETED");
		return ownerRepository.save(ownerEntity);
	}
}