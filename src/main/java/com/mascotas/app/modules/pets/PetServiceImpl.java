package com.mascotas.app.modules.pets;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.details.DetailModel;
import com.mascotas.app.modules.details.DetailRepository;
import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.owners.OwnerRepository;
import com.mascotas.app.utils.FechaUtil;

@Slf4j
@Service
public class PetServiceImpl implements PetService{

	@Autowired
	PetRepository petRepository;
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	DetailRepository detailRepository;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	FechaUtil fechaUtil;

	public List<PetEntity> listAllPets(){
		return petRepository.findAll();
//		List<PetEntity> listPets = new ArrayList<>();
//		List<OwnerEntity> listOwnerDb = ownerRepository.findAll();
//		List<Long> listIdPetsByOwner = new ArrayList<>();
//
//		for(OwnerEntity p:listOwnerDb){
//			for(PetEntity q:p.getPets()){
//				listIdPetsByOwner.add(q.getId());
//			}
//		}
//		return petRepository.findAllById(listIdPetsByOwner);
	}

	@Override
	public PetEntity createPet(PetDTO petDTO, String username) {

		PetEntity newPet = new PetEntity();
		newPet.setName(petDTO.getName());
		newPet.setGender(petDTO.getGender());
		newPet.setBirthDate(fechaUtil.getTimestampFromStringDate(petDTO.getBirthDate()));
		newPet.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		newPet.setColour(petDTO.getColour());
		newPet.setCharacteristic(petDTO.getCharacteristic());
		newPet.setSize(petDTO.getSize());

		UserEntity userEntity = userRepository.findByUsername(username).get();
		OwnerEntity ownerPet = ownerRepository.findByUser(userEntity).get();
		newPet.setOwner(ownerPet);

		DetailModel petDetail = detailRepository.findBySpeciesAndBreed(
				petDTO.getSpecies(),
				petDTO.getBreed()).get();
		newPet.setDetail(petDetail);
		newPet.setShelter(null);

		String encoded = fileUploadService.obtenerEncoded(petDTO.getEncoded());
		byte[] image = fileUploadService.convertEncodedToBytes(encoded);
		newPet.setImage(image);
		newPet.setState("CREATED");

		return petRepository.save(newPet);
	}

	@Override
	public PetEntity readPet(Long id) {
		return petRepository.findById(id).orElse(null);
	}

	@Override
	public PetEntity updatePet(PetDTO petDTO) {
		PetEntity petDB = readPet(petDTO.getId());
		assert petDB != null;
		petDB.setName(petDTO.getName());
		petDB.setGender(petDTO.getGender());
		petDB.setBirthDate(fechaUtil.getTimestampFromStringDate(petDTO.getBirthDate()));
		//petDB.setRegisterDate(fechaUtil.getTimestampFromStringDate(petDTO.getRegisterDate()));
		petDB.setColour(petDTO.getColour());
		petDB.setSpecificBreed(petDTO.getSpecificBreed());
		petDB.setCharacteristic(petDTO.getCharacteristic());
		petDB.setSize(petDTO.getSize());
		DetailModel detailModel = detailRepository.findBySpeciesAndBreed(petDTO.getSpecies(), petDTO.getBreed()).orElse(null);
		petDB.setDetail(detailModel);
		OwnerEntity ownerEntity = ownerRepository.findById(petDTO.getOwner_id()).orElse(null);
		petDB.setOwner(ownerEntity);
		String encoded = fileUploadService.obtenerEncoded(petDTO.getEncoded());
		petDB.setImage(fileUploadService.convertEncodedToBytes(encoded));

		return petRepository.save(petDB);
	}

	@Override
	public PetEntity deletePet(PetDTO petDTO) {
		PetEntity petDB = readPet(petDTO.getId());
		assert petDB != null;
		petDB.setState("DELETED");
		return petRepository.save(petDB);
	}

	@Override
	public List<PetEntity> readByOwner(OwnerEntity ownerEntity) {
		return petRepository.findAllByOwner(ownerEntity);
	}

	@Override
	public Boolean existsById(Long id) {
		return petRepository.existsById(id);
	}

}
