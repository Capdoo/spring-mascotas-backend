package com.mascotas.app.modules.pets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.app.dto.MessageDTO;


@RestController
@RequestMapping("/pets")
public class PetController {
	@Autowired
	PetService petService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createPet(@RequestBody PetDTO petDTO){
		
		try {
			petService.savePet(petDTO);
			return new ResponseEntity<Object>(new MessageDTO("Pet registered successfully"), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(new MessageDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/read")
	public ResponseEntity<Object> readPets(){
		
		try {
			List<PetDTO> listPets = petService.listAllPets();
			return new ResponseEntity<Object>(listPets, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MessageDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
