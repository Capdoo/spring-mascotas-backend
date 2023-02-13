package com.mascotas.app.modules.pets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.owners.OwnerRepository;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.services.UserService;
import com.mascotas.app.utils.ErrorMessageUtil;
import com.mascotas.app.utils.FechaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
	UserService userService;
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
	public ResponseEntity<Object> createPet(@RequestBody PetDTO petDTO, @RequestHeader("Authorization") String token, BindingResult bindingResult) throws IOException {

		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
		}

		String realToken = token.split(" ")[1];
		String username = jwtProvider.getNombreUsuarioFromToken(realToken);
		if (!userService.existsByUsername(username)){
			return ResponseEntity.notFound().build();
		}
		PetEntity petCreate = petServiceImpl.createPet(petDTO,username);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.convertPetEntityToDTO(petCreate));
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
		if (!ownerRepository.existsById(petDTO.getOwner_id())){
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
		Optional<OwnerEntity> ownerModel = ownerRepository.findById(idOwner);
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

		return PetDTO.builder()
				.id(petEntity.getId())
				.name(petEntity.getName())
				.gender(petEntity.getGender())
				.birthDate(fechaUtil.getStrindDateFromTimestamp(petEntity.getBirthDate()))
				.registerDate(fechaUtil.getStrindDateFromTimestamp(petEntity.getRegisterDate()))
				.colour(petEntity.getColour())
				.characteristic(petEntity.getCharacteristic())
				.size(petEntity.getSize())
				.detail_id(petEntity.getDetail().getId())
				.breed(petEntity.getDetail().getBreed())
				.species(petEntity.getDetail().getSpecies())
				.owner_id(petEntity.getOwner().getId())
				.encoded(fileUploadService.convertBytesToEncoded(petEntity.getImage()))
				.state(petEntity.getState()).build();
	}

	private String formatMessage(BindingResult bindingResult) throws JsonProcessingException {
		List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
				.map(err -> {
					Map<String, String> error = new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		ErrorMessageUtil errorMessage = ErrorMessageUtil.builder()
				.code("01")
				.messages(errors).build();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(errorMessage);

		}catch (JsonProcessingException e){
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
