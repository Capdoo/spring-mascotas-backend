package com.mascotas.app.modules.pets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.owners.OwnerModel;
import com.mascotas.app.modules.owners.OwnerRepository;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.services.UserServiceImp;
import com.mascotas.app.utils.FechaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/pets")
public class PetController {

	@Autowired
	PetServiceImpl petServiceImpl;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
    UserServiceImp userServiceImp;
	@Autowired
	PetRepository petRepository;
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	FechaUtil fechaUtil;
	@Autowired
	FileUploadService fileUploadService;

	@GetMapping
	public ResponseEntity<Object> listPets(){
		List<PetEntity> listPets = petServiceImpl.listAllPets();
		List<PetDTO> sendList = listPets.stream().map(
				this::convertPetEntityToDTO
		).collect(Collectors.toList());
		return ResponseEntity.ok(sendList);
	}

	@PostMapping
	public ResponseEntity<Object> createPet(@RequestBody PetDTO petDTO, @RequestHeader("Authorization") String token) throws IOException {
		String realToken = token.split(" ")[1];
		String username = jwtProvider.getNombreUsuarioFromToken(realToken);
		if (!userServiceImp.existsByUsername(username)){
			return ResponseEntity.notFound().build();
		}
		PetEntity petCreate = petServiceImpl.createPet(petDTO,username);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertPetEntityToDTO(petCreate));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getPet(@PathVariable(value = "id") Long id){
		if (!petRepository.existsById(id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		PetEntity petRead = petServiceImpl.readPet(id);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertPetEntityToDTO(petRead));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updatePet(@PathVariable(value = "id") Long id, @RequestBody PetDTO petDTO){
		if (!petRepository.existsById(id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		if (!ownerRepository.existsById(petDTO.getIdOwner())){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
		}
		petDTO.setId(id);
		PetEntity petUpdate = petServiceImpl.updatePet(petDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertPetEntityToDTO(petUpdate));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deletePet(@PathVariable(value = "id") Long id){
		if (!petRepository.existsById(id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		PetDTO petDTO = new PetDTO();
		petDTO.setId(id);
		PetEntity petDelete = petServiceImpl.deletePet(petDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertPetEntityToDTO(petDelete));
	}

	@GetMapping(value = "/owner/{id}")
	public ResponseEntity<Object> readByOwner(@PathVariable(value = "id") Long idOwner){
		Optional<OwnerModel> ownerModel = ownerRepository.findById(idOwner);
		if(ownerModel.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
		}
		List<PetEntity> petReadByOwner = petServiceImpl.readByOwner(ownerModel.get());
		List<PetDTO> sendList = petReadByOwner.stream().map(
				this::convertPetEntityToDTO
		).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(sendList);
	}

	//To send DTO
	private PetDTO convertPetEntityToDTO(PetEntity petEntity){
		log.info(petEntity.getRegisterDate()+"");
		System.out.println(petEntity.getRegisterDate());
		return new PetDTO(
				petEntity.getId(),
				petEntity.getName(),
				petEntity.getGender(),
				fechaUtil.getStrindDateFromTimestamp(petEntity.getBirthDate()),
				fechaUtil.getStrindDateFromTimestamp(petEntity.getRegisterDate()),
				petEntity.getColour(),
				petEntity.getSpecificBreed(),
				petEntity.getCharacteristic(),
				petEntity.getSize(),
				petEntity.getDetail().getSpecies(),
				petEntity.getDetail().getBreed(),
				petEntity.getOwner().getId(),
				petEntity.getDetail().getId(),
				fileUploadService.convertBytesToEncoded(petEntity.getImage()),
				petEntity.getState()
		);
	}
	
}
