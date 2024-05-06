package com.mascotas.app.modules.pets;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetResource {

    @Autowired
    PetService petService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserService userService;
    @Autowired
    FileUploadService fileUploadService;

    public ResponseEntity<Object> createPet(PetDto petDTO, String token) {
        String realToken = token.split(" ")[1];
        String username = jwtProvider.getNombreUsuarioFromToken(realToken);
        if (!userService.existsByUsername(username)){
            return ResponseEntity.notFound().build();
        }
        PetEntity petCreate = petService.createPet(petDTO, username);
        petDTO = PetMapper.mapPetDto(petCreate);
        petDTO.setEncoded(fileUploadService.convertBytesToEncoded(petCreate.getImage()));
        return ResponseEntity.status(HttpStatus.CREATED).body(petDTO);
    }

    public ResponseEntity<Object> read() {
        List<PetEntity> listPets = petService.listAllPets();
        List<PetDto> sendList = listPets.stream().map(
                PetMapper::mapPetDto
        ).collect(Collectors.toList());
        return ResponseEntity.ok(sendList);

    }

    public ResponseEntity<Object> readPet(Long id) {
        if (!petService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }
        PetEntity petRead = petService.readPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.mapPetDto(petRead));
    }

//    public ResponseEntity<Object> readPetsByOwner(Long idOwner) {
//        OwnerEntity ownerEntity = ownerService.readOwner(idOwner);
//        if(ownerEntity == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
//        }
//        List<PetEntity> petReadByOwner = petService.readByOwner(ownerEntity);
//        List<PetDto> sendList = petReadByOwner.stream().map(
//                PetMapper::mapPetDto
//        ).collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(sendList);
//    }

    public ResponseEntity<Object> updatePet(Long id, PetDto petDto, String token) {
        if (!petService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }

        UserEntity user = userService.readByUsername(jwtProvider.getNombreUsuarioFromToken(
                token.split(" ")[1]
        ));

//        if (!ownerService.existsById(user.getOwner().getId())){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner Not Found");
//        }
        petDto.setId(id);
        PetEntity petUpdate = petService.updatePet(petDto);
        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.mapPetDto(petUpdate));
    }

    public ResponseEntity<Object> deletePet(Long id) {
        if (!petService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }
        PetDto petDTO = new PetDto();
        petDTO.setId(id);
        PetEntity petDelete = petService.deletePet(petDTO);
        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.mapPetDto(petDelete));
    }

}