package com.mascotas.app.modules.searchs;

import java.sql.Timestamp;
import java.util.List;

import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.pets.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetRepository;
import com.mascotas.app.utils.FechaUtil;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	SearchRepository searchRepository;
	@Autowired
	PetRepository petRepository;
	@Autowired
    FileUploadService fileUploadService;
	@Autowired
	FechaUtil fechaUtil;
	@Autowired
	PetService petService;

	@Override
	public List<SearchEntity> listAllSearchs() {
		return searchRepository.findAll();
	}

	@Override
	public SearchEntity createSearch(SearchDTO searchDTO) {

		PetEntity petEntityDB = petService.readPet(searchDTO.getPet_id());
		if (petEntityDB == null){
			return null;
		}
		SearchEntity searchEntity = SearchEntity.builder()
				.address(searchDTO.getAddress())
				.district(searchDTO.getDistrict())
				.registerDate(new Timestamp(System.currentTimeMillis()))
				.lostDate(fechaUtil.getTimestampFromStringDate(searchDTO.getLost_date()))
				.phoneA(searchDTO.getPhone_a())
				.phoneB(searchDTO.getPhone_b())
				.message(searchDTO.getMessage())
				.pet(petEntityDB).build();
		return searchRepository.save(searchEntity);
	}

	@Override
	public SearchEntity readSearch(Long id) {
		return searchRepository.findById(id).orElse(null);
	}

	@Override
	public SearchEntity updateSearch(SearchDTO searchDTO) {

		PetEntity petEntityDB = petService.readPet(searchDTO.getPet_id());
		if (petEntityDB == null){
			return null;
		}
		SearchEntity searchEntity = readSearch(searchDTO.getPet_id());
		searchEntity.setAddress(searchDTO.getAddress());
		searchEntity.setDistrict(searchDTO.getDistrict());
		searchEntity.setRegisterDate(fechaUtil.getTimestampFromStringDate(searchDTO.getRegister_date()));
		searchEntity.setLostDate(fechaUtil.getTimestampFromStringDate(searchDTO.getLost_date()));
		searchEntity.setPhoneA(searchDTO.getPhone_a());
		searchEntity.setPhoneB(searchDTO.getPhone_b());
		searchEntity.setMessage(searchDTO.getMessage());
		return searchRepository.save(searchEntity);
	}

	@Override
	public SearchEntity deleteSearch(SearchDTO searchDTO) {
		return null;
	}

	@Override
	public List<SearchEntity> readAllSearchsByPet(PetEntity petEntity) {
		PetEntity petEntityDB = petService.readPet(petEntity.getId());
		if (petEntityDB == null){
			return null;
		}
		return searchRepository.findAllByPet(petEntityDB);
	}

	@Override
	public List<SearchEntity> radAllSearchsByOwner(OwnerEntity ownerEntity) {
		return null;
	}




}



























