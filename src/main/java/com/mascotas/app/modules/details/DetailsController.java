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
	DetailResource detailResource;
	

	@PostMapping(value = "/create")
	public ResponseEntity<Object> createDetail(@RequestBody DetailDto detailDTO, BindingResult bindingResult) throws JsonProcessingException {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
		}
		return this.detailResource.createDetail(detailDTO);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<Object> readAll(){
		return this.detailResource.read();
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<Object> readDetail(@PathVariable(value = "id") Long id){
		return this.detailResource.readById(id);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateDetail(@PathVariable(value = "id") Long id, @RequestBody DetailDto detailDto){
		return this.detailResource.updateDetail(id, detailDto);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteDetail(@PathVariable(value = "id") Long id){
		return this.detailResource.deleteDetail(id);
	}

	@GetMapping("/read/species")
	public ResponseEntity<Object> readSpecies(){
		return this.detailResource.readSpecies();
	}

	@GetMapping("/read/breeds/{species}")
	public ResponseEntity<Object> readBreedsBySpecies(@PathVariable(value = "species") String species){
		DetailDto detailDTO = DetailDto.builder()
				.species(species)
				.build();
		List<DetailEntity> listDetailsBySpecies = detailService.readAllBySpecies(detailDTO);
		List<String> listAllBreedsBySpecies = listDetailsBySpecies.stream()
				.map(DetailEntity::getBreed)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listAllBreedsBySpecies);
	}

	public DetailDto convertDetailEntityToDto(DetailEntity detailEntity){
		return DetailDto.builder()
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