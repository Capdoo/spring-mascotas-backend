package com.mascotas.app.modules.pets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.mascotas.app.modules.owners.OwnerModel;
import com.mascotas.app.modules.owners.OwnerRepository;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import com.mascotas.app.dto.MessageDTO;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pets")
public class PetController {

	@Autowired
	PetServiceImpl petServiceImpl;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserService userService;
	@Autowired
	PetRepository petRepository;
	@Autowired
	OwnerRepository ownerRepository;

	@GetMapping
	public ResponseEntity<Object> listPets(){
		List<PetDTO> listPets = petServiceImpl.listAllPets();
		return ResponseEntity.ok(listPets);
	}

	@PostMapping
	public ResponseEntity<Object> createPet(@RequestBody PetDTO petDTO, @RequestHeader("Authorization") String token) throws IOException {
		String realToken = token.split(" ")[1];
		String username = jwtProvider.getNombreUsuarioFromToken(realToken);

		if (!userService.existsByUsername(username)){
			return ResponseEntity.notFound().build();
		}

		PetDTO petCreate = petServiceImpl.createPet(petDTO,username);
		return ResponseEntity.status(HttpStatus.OK).body(petCreate);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getPet(@PathVariable(value = "id") Long id){
		if (!petRepository.existsById(id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		PetDTO petDTO = petServiceImpl.readPet(id);
		return ResponseEntity.status(HttpStatus.OK).body(petDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updatePet(@PathVariable(value = "id") Long id, @RequestBody PetDTO petDTO){
		if (!petRepository.existsById(id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}

		if (!ownerRepository.existsById(petDTO.getIdOwner())){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
		}

		petDTO.setId(id);
		PetDTO petDTOUpdate = petServiceImpl.updatePet(petDTO);
		return ResponseEntity.status(HttpStatus.OK).body(petDTOUpdate);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deletePet(@PathVariable(value = "id") Long id){
		if (!petRepository.existsById(id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
		}
		PetDTO petDTODelete = petServiceImpl.deletePet(id);
		return ResponseEntity.status(HttpStatus.OK).body(petDTODelete);
	}

	@GetMapping(value = "/owner/{id}")
	public ResponseEntity<Object> readByOwner(@PathVariable(value = "id") Long idOwner){
		Optional<OwnerModel> ownerModel = ownerRepository.findById(idOwner);
		if(ownerModel.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
		}
		List<PetDTO> petDTOList = petServiceImpl.readByOwner(ownerModel.get());
		return ResponseEntity.status(HttpStatus.OK).body(petDTOList);
	}
	
}
