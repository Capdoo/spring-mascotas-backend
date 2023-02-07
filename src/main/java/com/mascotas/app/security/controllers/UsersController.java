package com.mascotas.app.security.controllers;

import com.mascotas.app.dto.MessageDTO;
import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.UserDTO;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserServiceImp;
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
    UserServiceImp userServiceImp;
    @Autowired
    FileUploadService fileUploadService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Object> listUsers(){

        List<UserEntity> usersDB = userServiceImp.findAllUsers();
        List<UserDTO> listUsers = usersDB.stream().map(
                this::convertUserEntityToDTO
        ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listUsers);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") Long id){

        if(!userServiceImp.existsById(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
        }
        UserEntity userRead = userServiceImp.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(this.convertUserEntityToDTO(userRead));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestParam int id, @RequestBody UserDTO userDTO){

        if(!userServiceImp.existsById(id)){
            return new ResponseEntity<Object>(new MessageDTO("User not found"), HttpStatus.BAD_REQUEST);
        }

        userServiceImp.updateUser(id, userDTO);
        return new ResponseEntity<Object>(new MessageDTO("User updated successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestParam int id){

        if(!userServiceImp.existsById(id)){
            return new ResponseEntity<Object>(new MessageDTO("User not found"), HttpStatus.BAD_REQUEST);
        }

        userServiceImp.deleteUser(id);
        return new ResponseEntity<Object>(new MessageDTO("User deleted successfully"), HttpStatus.OK);
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
                .encoded(fileUploadService.convertBytesToEncoded(userEntity.getImage())).build();
    }

}
