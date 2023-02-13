package com.mascotas.app.modules.searchs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.owners.OwnerService;
import com.mascotas.app.modules.pets.PetDTO;
import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetService;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.services.UserService;
import com.mascotas.app.utils.ErrorMessageUtil;
import com.mascotas.app.utils.FechaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mascotas.app.dto.MessageDTO;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/searchs")
public class SearchsController {

	@Autowired
	SearchService searchService;
	@Autowired
	UserService userService;
	@Autowired
	FechaUtil fechaUtil;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	PetService petService;
	@Autowired
	OwnerService ownerService;

	//Should be only admin
	@GetMapping
	public ResponseEntity<Object> readSearchs(){
		List<SearchEntity> listDB = searchService.listAllSearchs();
		List<SearchDTO> listSearchs = listDB.stream().map(
				this::convertSearchEntityToDTO
		).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(listSearchs);
	}

	//Should be owner or shelter partner
	@PostMapping
	public ResponseEntity<Object> createSearch(@RequestBody SearchDTO searchDTO, BindingResult bindingResult) throws JsonProcessingException {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
		}
		SearchEntity searchEntity = searchService.createSearch(searchDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.convertSearchEntityToDTO(searchEntity));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> readById(@PathVariable(value = "id") Long id){
		SearchEntity searchEntity = searchService.readSearch(id);
		if (searchEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search Not Found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(this.convertSearchEntityToDTO(searchEntity));
	}

	@GetMapping("/pet/{id}")
	public ResponseEntity<Object> readByPetId(@PathVariable(value = "id") Long id_pet){
		PetEntity petEntity = petService.readPet(id_pet);
		if (petEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		List<SearchEntity> listSearchEntity = searchService.readAllSearchsByPet(petEntity);
		List<SearchDTO> listSearchDTO = listSearchEntity.stream().map(
				this::convertSearchEntityToDTO
		).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(listSearchDTO);
	}

	//search by owner
	@GetMapping("/owner/{id}")
	public ResponseEntity<Object> readByOwnerId(@PathVariable(value = "id") Long id_owner){
		OwnerEntity ownerEntity = ownerService.readOwnerById(id_owner);
		if (ownerEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
		}
		List<SearchEntity> listSearchEntity = searchService.radAllSearchsByOwner(ownerEntity);
		List<SearchDTO> listSearchDTO = listSearchEntity.stream().map(
				this::convertSearchEntityToDTO
		).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(listSearchDTO);
	}


	//update
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updatePet(@PathVariable(value = "id") Long id, @RequestBody SearchDTO searchDTO){

		PetEntity petEntity = petService.readPet(searchDTO.getPet_id());
		if (petEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}

		searchDTO.setId(id);
		SearchEntity searchUpdate = searchService.updateSearch(searchDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertSearchEntityToDTO(searchUpdate));
	}


	//delete


















	private SearchDTO convertSearchEntityToDTO(SearchEntity searchEntity){
		return SearchDTO.builder()
				.id(searchEntity.getId())
				.address(searchEntity.getAddress())
				.district(searchEntity.getDistrict())
				.phone_a(searchEntity.getPhoneA())
				.phone_b(searchEntity.getPhoneB())
				.pet_id(searchEntity.getPet().getId())
				.name_pet(searchEntity.getPet().getName())
				.species_pet(searchEntity.getPet().getDetail().getSpecies())
				.breed_pet(searchEntity.getPet().getDetail().getBreed())
				.lost_date(fechaUtil.getStrindDateFromTimestamp(searchEntity.getLostDate()))
				.register_date(fechaUtil.getStrindDateFromTimestamp(searchEntity.getRegisterDate()))
				.message(searchEntity.getMessage())
				.build();
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
