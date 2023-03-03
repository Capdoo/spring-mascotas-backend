package com.mascotas.app.modules.details;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.app.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/details")
public class DetailsController {

	@Autowired
	DetailService detailService;
	@Autowired
	DetailServiceImpl detailServiceImpl;
	
	@GetMapping
	public ResponseEntity<Object> readAll(){
		List<DetailEntity> listDetails = detailService.listAllDetails();
		List<DetailDTO> listDetailsDto = listDetails.stream()
				.map(this::convertDetailEntityToDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDetailsDto);
	}

	@PostMapping
	public ResponseEntity<Object> createDetail(@RequestBody DetailDTO detailDTO, BindingResult bindingResult) throws JsonProcessingException {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
		}
		DetailEntity detailEntity = detailService.createDetail(detailDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.convertDetailEntityToDto(detailEntity));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> readDetail(@PathVariable(value = "id") Long id){
		DetailEntity detailEntity = detailService.readDetail(id);
		if (detailEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
		}
		return ResponseEntity.ok().body(this.convertDetailEntityToDto(detailEntity));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateDetail(@PathVariable(value = "id") Long id, @RequestBody DetailDTO detailDTO){
		DetailEntity detailEntity = detailService.readDetail(id);
		if (detailEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
		}
		detailDTO.setId(id);
		DetailEntity detailUpdate = detailService.updateDetail(detailDTO);
		return ResponseEntity.ok().body(this.convertDetailEntityToDto(detailUpdate));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteDetail(@PathVariable(value = "id") Long id){
		DetailEntity detailEntity = detailService.readDetail(id);
		if (detailEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
		}
		DetailDTO detailDTO = DetailDTO.builder()
				.id(id)
				.build();
		DetailEntity detailDelete = detailService.deleteDetail(detailDTO);
		return ResponseEntity.ok().body(this.convertDetailEntityToDto(detailDelete));
	}

	/*
	@GetMapping("/breed")
	public ResponseEntity<Object> readBreeds(){
		List<DetailModel> listSpecies = detailServiceImpl.listAllDetails();
		List<String> listAllSpecies = listSpecies.stream()
				.map(DetailModel::getBreed)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listAllSpecies);
	}*/

	@GetMapping("/species")
	public ResponseEntity<Object> readSpecies(){
		List<DetailEntity> listDetails = detailService.listAllDetails();
		Set<String> listUniqueSpecies = listDetails.stream()
				.map(DetailEntity::getSpecies)
				.collect(Collectors.toSet());
		return ResponseEntity.ok().body(listUniqueSpecies);
	}

	@GetMapping("/breeds/{species}")
	public ResponseEntity<Object> readBreedsBySpecies(@PathVariable(value = "species") String species){
		DetailDTO detailDTO = DetailDTO.builder()
				.species(species)
				.build();
		List<DetailEntity> listDetailsBySpecies = detailService.readAllBySpecies(detailDTO);
		List<String> listAllBreedsBySpecies = listDetailsBySpecies.stream()
				.map(DetailEntity::getBreed)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listAllBreedsBySpecies);
	}

	public DetailDTO convertDetailEntityToDto(DetailEntity detailEntity){
		return DetailDTO.builder()
				.id(detailEntity.getId())
				.species(detailEntity.getSpecies())
				.breed(detailEntity.getBreed())
				.state(detailEntity.getState())
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











