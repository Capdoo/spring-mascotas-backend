package com.mascotas.app.utils;

import com.mascotas.app.modules.details.DetailEntity;
import com.mascotas.app.modules.details.DetailRepository;
import com.mascotas.app.security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.models.RoleEntity;
import com.mascotas.app.security.services.RoleService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateRolesUtil implements CommandLineRunner{

	@Autowired
	RoleService roleService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	DetailRepository detailRepository;

	@Override
	public void run(String... args) throws Exception {

		RoleEntity roleGuest = new RoleEntity(RoleName.ROLE_GUEST);
		RoleEntity rolePublisher = new RoleEntity(RoleName.ROLE_PUBLISHER);
		RoleEntity roleOwner = new RoleEntity(RoleName.ROLE_OWNER);
		RoleEntity roleShelterOwner = new RoleEntity(RoleName.ROLE_SHELTER_OWNER);
		RoleEntity roleAdmin = new RoleEntity(RoleName.ROLE_ADMIN);

		if(!roleRepository.existsByRoleName(RoleName.ROLE_GUEST)){
			roleService.save(roleGuest);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_PUBLISHER)){
			roleService.save(rolePublisher);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_OWNER)){
			roleService.save(roleOwner);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_SHELTER_OWNER)){
			roleService.save(roleShelterOwner);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_ADMIN)){
			roleService.save(roleAdmin);
		}

		String[] listBreedsCats = new String[]{
				"Abyssinian", "American Shorthair", "Bengal", "Birman", "Bombay", "British Shorthair",
				"Burmese", "Chartreux", "Exotic Shorthair", "Himalayan", "Maine Coon", "Nebelung",
				"Norwegian Forest", "Persian", "Ragdoll", "Russian Blue", "Scottish Fold", "Siamese",
				"Siberian", "Tonkinese"
		};

		String[] listBreedsDogs = new String[]{
				"Australian shepherd", "Beagle", "Bernese mountain dog", "Boston terrier", "Boxer",
				"Bulldog", "Cane corso", "Cavalier King Charles spaniel", "Dachshund", "Doberman pinscher",
				"French bulldog", "German shepherd", "German shorthaired pointer", "Golden retriever",
				"Great Dane", "Havanese", "Labrador retriever", "Miniature schnauzer", "Pembroke Welsh corgi",
				"Pomeranian", "Poodle", "Rottweiler", "Shih Tzu", "Siberian husky", "Yorkshire terrier"
		};

		String[] listBreedsRabbits = new String[]{
				"Californian", "Dutch", "Dwarf Papillon", "Flemish Giant", "French Lop", "Holland Lop",
				"Lionhead", "Mini Lop", "Mini Rex", "Netherland Dwarf"
		};

		List<DetailEntity> listDetails = new ArrayList<>();
		String catName = "Cat";
		for(String p:listBreedsCats){
			if(!detailRepository.existsBySpeciesAndBreed(catName, p)){
				DetailEntity detailEntity = DetailEntity.builder()
						.species(catName)
						.breed(p)
						.state("CREATED")
						.build();
				listDetails.add(detailEntity);
			}
		}
		String dogName = "Dog";
		for(String q:listBreedsDogs){
			if(!detailRepository.existsBySpeciesAndBreed(dogName, q)){
				DetailEntity detailEntity = DetailEntity.builder()
						.species(dogName)
						.breed(q)
						.state("CREATED")
						.build();
				listDetails.add(detailEntity);
			}
		}
		String rabbitName = "Rabbit";
		for(String q:listBreedsRabbits){
			if(!detailRepository.existsBySpeciesAndBreed(rabbitName, q)){
				DetailEntity detailEntity = DetailEntity.builder()
						.species(rabbitName)
						.breed(q)
						.state("CREATED")
						.build();
				listDetails.add(detailEntity);
			}
		}
		detailRepository.saveAll(listDetails);
	}
}