package com.mascotas.app.modules.searches;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.owners.OwnerServiceImpl;
import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetRepository;
import com.mascotas.app.modules.pets.PetService;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.services.UserService;
import com.mascotas.app.utils.ErrorMessageUtil;
import com.mascotas.app.utils.FechaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/searchs")
public class SearchesController {

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
	OwnerServiceImpl ownerServiceImpl;
	@Autowired
	PetRepository petRepository;

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
		if (!petService.existsById(searchDTO.getPetId())){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		//Later: save according owner or shelter pet related
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

	//update
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updatePet(@PathVariable(value = "id") Long id, @RequestBody SearchDTO searchDTO){

		PetEntity petEntity = searchService.readSearch(id).getPet();
		if (petEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet No Longer Exists");
		}

		searchDTO.setId(id);
		SearchEntity searchUpdate = searchService.updateSearch(searchDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertSearchEntityToDTO(searchUpdate));
	}

	//TODO
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deletePet(@PathVariable(value = "id") Long id){
		SearchEntity searchEntity = searchService.readSearch(id);
		if (searchEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search Not Found");
		}
		SearchDTO searchDTO = new SearchDTO();
		searchDTO.setId(id);
		searchEntity = searchService.deleteSearch(searchDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertSearchEntityToDTO(searchEntity));
	}

	//search by pet
	@GetMapping("/pet/{id}")
	public ResponseEntity<Object> readByPetId(@PathVariable(value = "id") Long pet_id){
		if (!petService.existsById(pet_id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		PetEntity petEntity = petService.readPet(pet_id);
		if (!searchService.existsByPet(petEntity)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet With No Search");
		}
		SearchEntity searchByPet = searchService.readSearchByPet(petEntity);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertSearchEntityToDTO(searchByPet));
	}

	//search by owner
	@GetMapping("/owner/{id}")
	public ResponseEntity<Object> readByOwnerId(@PathVariable(value = "id") Long owner_id){
		OwnerEntity ownerEntity = ownerServiceImpl.readOwner(owner_id);
		if (ownerEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
		}
		List<SearchEntity> listSearchEntity = searchService.radAllSearchsByOwner(ownerEntity);
		List<SearchDTO> listSearchDTO = listSearchEntity.stream().map(
				this::convertSearchEntityToDTO
		).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(listSearchDTO);
	}














	private SearchDTO convertSearchEntityToDTO(SearchEntity searchEntity){
		return SearchDTO.builder()
				.id(searchEntity.getId())
				.address(searchEntity.getAddress())
				.district(searchEntity.getDistrict())
				.phoneA(searchEntity.getPhoneA())
				.phoneB(searchEntity.getPhoneB())
				.petId(searchEntity.getPet().getId())
				.namePet(searchEntity.getPet().getName())
				.speciesPet(searchEntity.getPet().getDetail().getSpecies())
				.breedPet(searchEntity.getPet().getDetail().getBreed())
				.lostDate(fechaUtil.getStrindDateFromTimestamp(searchEntity.getLostDate()))
				.registerDate(fechaUtil.getStrindDateFromTimestamp(searchEntity.getRegisterDate()))
				.message(searchEntity.getMessage())
				.encoded(fileUploadService.convertBytesToEncoded(searchEntity.getPet().getImage()))
				.state(searchEntity.getState())
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
