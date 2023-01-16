package com.mascotas.app.modules.shelters;

import java.util.List;

import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mascotas.app.dto.MessageDTO;

@RestController
@RequestMapping("/shelters")

public class ShelterController {

	@Autowired
    ShelterService shelterService;
	@Autowired
	UserService userService;

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@PostMapping("/create")
	public ResponseEntity<Object> createShelter(@RequestBody ShelterDTO shelterDTO){
		try {
			shelterService.saveShelter(shelterDTO);
			return new ResponseEntity<Object>(new MessageDTO("Shelter registered successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new MessageDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@PostMapping("/create/partner")
	public ResponseEntity<Object> createPartner(@RequestBody ShelterDTO shelterDTO){
		try {
			shelterService.saveShelter(shelterDTO);
			return new ResponseEntity<Object>(new MessageDTO("Shelter registered successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new MessageDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/read")
	public ResponseEntity<Object> readShelters(){
		try {
			List<ShelterDTO> listaRefugios = shelterService.listAll();
			return new ResponseEntity<Object>(listaRefugios, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new MessageDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/read/single")
	public ResponseEntity<Object> readById(@RequestParam long id){
		try {
			ShelterDTO busqueda = shelterService.getById(id);
			return new ResponseEntity<Object>(busqueda, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new MessageDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
	}

	/*
	@GetMapping("/read/user")
	public ResponseEntity<Object> readByDniUser(@RequestParam String dni){
		if(!userService.existsByDni(dni)){
			return new ResponseEntity<Object>(new MessageDTO("User not found"), HttpStatus.BAD_REQUEST);
		}
		List<ShelterDTO> listaRefugios = shelterService.getByDniUser(dni);
		return new ResponseEntity<Object>(listaRefugios, HttpStatus.OK);
	}*/
}
