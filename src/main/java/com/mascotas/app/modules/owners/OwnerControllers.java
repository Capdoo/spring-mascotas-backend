package com.mascotas.app.modules.owners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.app.dto.MensajeDTO;
import com.mascotas.app.security.services.UserService;

@RestController
@RequestMapping("/owners")
public class OwnerControllers {

	@Autowired
    OwnerService ownerService;
	
	@Autowired
    UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createOwner(@RequestBody OwnerDTO ownerDTO){
		
		try {
			if(!(userService.existsPorId((int) ownerDTO.getUser_id()))){
				int b = 11;
				return new ResponseEntity<Object>(new MensajeDTO("The user does not exists"), HttpStatus.BAD_REQUEST);
			}

 			if(ownerService.existsOwnerByUserId((int) ownerDTO.getUser_id())){
				int a = 10;
				return new ResponseEntity<Object>(new MensajeDTO("This user is already related to an owner"), HttpStatus.BAD_REQUEST);
			}

			int c = 12;
			
			ownerService.saveOwner(ownerDTO);
			return new ResponseEntity<Object>(new MensajeDTO("Registered successfully"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/read")
	public ResponseEntity<Object> obtener(){
		
		try {
			List<OwnerDTO> listaDuenos = ownerService.listAll();
			return new ResponseEntity<Object>(listaDuenos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
