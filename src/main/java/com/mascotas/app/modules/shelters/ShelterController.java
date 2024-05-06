package com.mascotas.app.modules.shelters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mascotas.app.dto.MessageDTO;

@RestController
@RequestMapping("/shelters")
public class ShelterController {

    @Autowired
    ShelterResource shelterResource;

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@PostMapping("/create")
	public ResponseEntity<Object> createShelter(@RequestBody ShelterDto shelterDTO){
        return this.shelterResource.createShelter(shelterDTO);
	}

	@GetMapping("/read")
	public ResponseEntity<Object> readShelters(){
        return this.shelterResource.readShelters();
	}

	@GetMapping("/read/single")
    public ResponseEntity<Object> readById(@PathVariable(value = "id") Long shelterId) {
        return this.shelterResource.readById(shelterId);
    }
}