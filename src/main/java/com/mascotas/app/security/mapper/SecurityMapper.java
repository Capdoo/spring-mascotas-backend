package com.mascotas.app.security.mapper;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.UserDTO;
import com.mascotas.app.security.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityMapper {

    @Autowired
    static FileUploadService fileUploadService;

    public static UserDTO mapUserDto(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setAddress(userEntity.getAddress());
        userDTO.setDni(userEntity.getDni());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEncoded(fileUploadService.convertBytesToEncoded(userEntity.getImage()));
        userDTO.setState(userEntity.getState());
        return userDTO;
    }

}
