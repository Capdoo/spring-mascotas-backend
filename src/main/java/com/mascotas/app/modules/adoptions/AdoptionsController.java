package com.mascotas.app.modules.adoptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mascotas.app.dto.MessageDTO;

@RestController
@RequestMapping("/adoptions")
public class AdoptionsController {

	@Autowired
	AdoptionResource adoptionResource;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createAdoption(@RequestBody AdoptionDto adoptionDTO){
		return this.adoptionResource.createAdoption(adoptionDTO);
	}
	
	@GetMapping
	public ResponseEntity<Object> read(){
		return this.adoptionResource.read();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> readAdoption(@PathVariable(value = "id") Long id){
		return this.adoptionResource.readAdoption(id);
	}

	@GetMapping(value = "/pet/{id}")
	public ResponseEntity<Object> readAdoptionsByPetId(@PathVariable(value = "id") Long id){
		return adoptionResource.readAdoptionsByPetId(id);
	}

}
