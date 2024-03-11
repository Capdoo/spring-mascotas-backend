package com.mascotas.app.modules.owners;

import java.util.List;
import java.util.stream.Collectors;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserService;
import com.mascotas.app.utils.FechaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/owners")
public class OwnerControllers {
	@Autowired
	OwnerService ownerService;
	@Autowired
	UserService userService;
	@Autowired
	FechaUtil fechaUtil;

	//read all
	@GetMapping
	public ResponseEntity<Object> readOwners(){
		List<OwnerEntity> listOwnersDB = ownerService.listAllOwners();
		List<OwnerDTO> listOwners = listOwnersDB.stream().map(
				this::convertOwnerEntityToDTO
		).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(listOwners);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateOwner(@PathVariable(value = "id") Long id, @RequestBody OwnerDTO ownerDTO){
		UserEntity userEntity = ownerService.readOwner(id).getUser();
		if (userEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
		}
		ownerDTO.setId(id);
		OwnerEntity ownerUpdate = ownerService.updateOwner(ownerDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertOwnerEntityToDTO(ownerUpdate));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteOwner(@PathVariable(value = "id") Long id){
		UserEntity userEntity = ownerService.readOwner(id).getUser();
		if (userEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
		}
		OwnerDTO ownerDTO = new OwnerDTO();
		ownerDTO.setId(id);
		OwnerEntity ownerDelete = ownerService.deleteOwner(ownerDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertOwnerEntityToDTO(ownerDelete));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> readById(@PathVariable(value = "id") Long id){
		OwnerEntity ownerEntity = ownerService.readOwner(id);
		if (ownerEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(this.convertOwnerEntityToDTO(ownerEntity));
	}

	private OwnerDTO convertOwnerEntityToDTO(OwnerEntity ownerEntity){
		return OwnerDTO.builder()
				.id(ownerEntity.getId())
				.register_date(fechaUtil.getStrindDateFromTimestamp(ownerEntity.getRegisterDate()))
				.historial_id(99L)
				.number_pets(ownerEntity.getNumberPets())
				.rate(ownerEntity.getRate())
				.user_id(ownerEntity.getUser().getId())
				.state(ownerEntity.getState()).build();
	}
}