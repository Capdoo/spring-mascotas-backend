package com.mascotas.app.modules.pets;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.details.DetailModel;
import com.mascotas.app.modules.details.DetailRepository;
import com.mascotas.app.modules.owners.OwnerModel;
import com.mascotas.app.modules.owners.OwnerRepository;
import com.mascotas.app.utils.FechaUtil;
import com.mascotas.app.utils.StringUtil;

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

	//Lista general
	public List<PetEntity> listAllPets(){
		List<PetEntity> listPets = new ArrayList<>();
		List<OwnerModel> listOwnerDb = ownerRepository.findAll();
		List<Long> listIdPetsByOwner = new ArrayList<>();

		for(OwnerModel p:listOwnerDb){
			for(PetEntity q:p.getPets()){
				listIdPetsByOwner.add(q.getId());
			}
		}
		return petRepository.findAllById(listIdPetsByOwner);

//		for(PetEntity p : petEntityDb) {
//			FechaUtil fechaUtil = new FechaUtil();
//			StringUtil stringUtil = new StringUtil();
//			PetDTO petSingle = this.convertPetEntityToDTO(p);
//			listPets.add(petSingle);
//		}

		//return listPets;
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
		OwnerModel ownerPet = ownerRepository.findByUser(userEntity).get();
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

		//PetEntity petEntity = petRepository.save(newPet);

		return petRepository.save(newPet);
	}


	@Override
	public PetEntity readPet(Long id) {
		//PetEntity petDB = petRepository.findById(id).orElse(null);
		//assert petDB != null;
		return petRepository.findById(id).orElse(null);
	}

	@Override
	public PetEntity updatePet(PetDTO petDTO) {
		PetEntity petDB = petRepository.findById(petDTO.getId()).orElse(null);
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
		OwnerModel ownerModel = ownerRepository.findById(petDTO.getIdOwner()).orElse(null);
		petDB.setOwner(ownerModel);
		String encoded = fileUploadService.obtenerEncoded(petDTO.getEncoded());
		petDB.setImage(fileUploadService.convertEncodedToBytes(encoded));

		PetEntity petEntityUpdate = petRepository.save(petDB);
		return petEntityUpdate;
	}

	@Override
	public PetEntity deletePet(Long id) {
		PetEntity petDB = petRepository.findById(id).orElse(null);
		assert petDB != null;
		petDB.setState("DELETED");
		PetEntity petDeleted = petRepository.save(petDB);

		return petDeleted;
	}

	@Override
	public List<PetEntity> readByOwner(OwnerModel ownerModel) {
		List<PetEntity> petEntityList = petRepository.findAllByOwner(ownerModel);
//		List<PetDTO> returnList = petEntityList.stream().map(
//				this::convertPetEntityToDTO
//		).collect(Collectors.toList());
		return petEntityList;
	}

	//From Entity to DTO
//	private PetDTO convertPetEntityToDTO(PetEntity petEntity){
//		log.info(petEntity.getRegisterDate()+"");
//		System.out.println(petEntity.getRegisterDate());
//		return new PetDTO(
//			petEntity.getId(),
//			petEntity.getName(),
//			petEntity.getGender(),
//			fechaUtil.getStrindDateFromTimestamp(petEntity.getBirthDate()),
//			fechaUtil.getStrindDateFromTimestamp(petEntity.getRegisterDate()),
//			petEntity.getColour(),
//			petEntity.getSpecificBreed(),
//			petEntity.getCharacteristic(),
//			petEntity.getSize(),
//			petEntity.getDetail().getSpecies(),
//			petEntity.getDetail().getBreed(),
//			petEntity.getOwner().getId(),
//			petEntity.getDetail().getId(),
//			fileUploadService.convertBytesToEncoded(petEntity.getImage()),
//			petEntity.getState()
//		);
//	}
}
