package com.mascotas.app.security.services;

import com.mascotas.app.security.dto.NewUserDTO;
import com.mascotas.app.security.dto.UserDTO;
import com.mascotas.app.security.models.UserEntity;

import java.util.List;

public interface UserService {

    public List<UserEntity> findAllUsers();

    //crud
    public UserEntity createUser(NewUserDTO newUserDTO);

    public UserEntity readUser(Long id);
    public UserEntity updateUser(UserDTO userDTO);
    public UserEntity deleteUser(UserDTO userDTO);

    public boolean existsById(Long id);
    public boolean existsByUsername(String username);

}
