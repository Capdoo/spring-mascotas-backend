package com.mascotas.app.security.controllers;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.UserDTO;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userService;
    @Autowired
    FileUploadService fileUploadService;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Object> listUsers(){

        List<UserEntity> usersDB = userService.findAllUsers();
        List<UserDTO> listUsers = usersDB.stream().map(
                this::convertUserEntityToDTO
        ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listUsers);

    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") Long id){

        if(!userService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        UserEntity userRead = userService.readUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(this.convertUserEntityToDTO(userRead));
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserDTO userDTO){

        if(!userService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        userDTO.setId(id);
        UserEntity userEntity = userService.updateUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(this.convertUserEntityToDTO(userEntity));
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id){
        if(!userService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        UserEntity userEntity = userService.deleteUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(this.convertUserEntityToDTO(userEntity));
    }

    private UserDTO convertUserEntityToDTO(UserEntity userEntity){
        return UserDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .surName(userEntity.getSurName())
                .address(userEntity.getAddress())
                .dni(userEntity.getDni())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .username(userEntity.getUsername())
                .encoded(fileUploadService.convertBytesToEncoded(userEntity.getImage()))
                .state(userEntity.getState()).build();
    }

}
