package com.mascotas.app.modules.pets;

import java.sql.Timestamp;
import java.util.List;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.details.DetailEntity;
import com.mascotas.app.modules.details.DetailRepository;
import com.mascotas.app.utils.FechaUtil;

@Slf4j
@Service
public class PetServiceImpl implements PetService{
	@Autowired
	PetRepository petRepository;
	@Autowired
	DetailRepository detailRepository;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	UserRepository userRepository;

	public List<PetEntity> listAllPets(){
		return petRepository.findAll();
	}

	@Override
	public PetEntity createPet(PetDto petDTO, String username) {

		PetEntity createPet = new PetEntity();
		createPet.setName(petDTO.getName());
		createPet.setGender(petDTO.getGender());
		createPet.setBirthDate(FechaUtil.getTimestampFromStringDate(petDTO.getBirthDate()));
		createPet.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		createPet.setColour(petDTO.getColour());
		createPet.setCharacteristic(petDTO.getCharacteristic());
		createPet.setSize(petDTO.getSize());

		UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
		if (userEntity == null) {
			return null;
		}
		createPet.setUser(userEntity);

		DetailEntity petDetail = detailRepository.findBySpeciesAndBreed(
				petDTO.getSpecies(),
				petDTO.getBreed()).orElse(null);
		if (petDetail == null) {
			return null;
		}

//		createPet.setDetail(petDetail);
		createPet.setShelter(null);

		String encoded = fileUploadService.obtenerEncoded(petDTO.getEncoded());
		byte[] image = fileUploadService.convertEncodedToBytes(encoded);
		createPet.setImage(image);
		createPet.setState("CREATED");

		return petRepository.save(createPet);
	}

	@Override
	public PetEntity readPet(Long id) {
		return petRepository.findById(id).orElse(null);
	}

	@Override
	public PetEntity updatePet(PetDto petDTO) {
		PetEntity petDB = readPet(petDTO.getId());
		assert petDB != null;
		petDB.setName(petDTO.getName());
		petDB.setGender(petDTO.getGender());
		petDB.setBirthDate(FechaUtil.getTimestampFromStringDate(petDTO.getBirthDate()));
		//petDB.setRegisterDate(fechaUtil.getTimestampFromStringDate(petDTO.getRegisterDate()));
		petDB.setColour(petDTO.getColour());
		petDB.setSpecificBreed(petDTO.getSpecificBreed());
		petDB.setCharacteristic(petDTO.getCharacteristic());
		petDB.setSize(petDTO.getSize());
//		DetailEntity detailEntity = detailRepository.findBySpeciesAndBreed(petDTO.getSpecies(), petDTO.getBreed()).orElse(null);
//		petDB.setDetail(detailEntity);

		String encoded = fileUploadService.obtenerEncoded(petDTO.getEncoded());
		petDB.setImage(fileUploadService.convertEncodedToBytes(encoded));

		return petRepository.save(petDB);
	}

	@Override
	public PetEntity deletePet(PetDto petDTO) {
		PetEntity petDB = readPet(petDTO.getId());
		assert petDB != null;
		petDB.setState("DELETED");
		return petRepository.save(petDB);
	}

	@Override
	public Boolean existsById(Long id) {
		return petRepository.existsById(id);
	}

}