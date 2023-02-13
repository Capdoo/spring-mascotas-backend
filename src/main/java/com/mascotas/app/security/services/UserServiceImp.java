package com.mascotas.app.security.services;

import java.util.*;

import javax.transaction.Transactional;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.NewUserDTO;
import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.models.RoleEntity;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.security.dto.UserDTO;

@Service
//Para implementar rollbacks y evitar incoherencia : Concurrencia
@Transactional
public class UserServiceImp implements UserService{
	@Autowired
	RoleService roleService;
	@Autowired
	UserRepository userRepository;
	@Autowired
    FileUploadService fileUploadService;
//	@Autowired
//	PasswordEncoder passwordEncoder;

	@Override
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity createUser(NewUserDTO newUserDTO) {

		Set<RoleEntity> roles = new HashSet<>();
		roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
		if (newUserDTO.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
		}
		if (newUserDTO.getRoles().contains("rept")) {
			roles.add(roleService.getByRoleName(RoleName.ROLE_REPT).get());
		}

		String encoded = "";
		if(newUserDTO.getEncoded() == null){
			encoded = fileUploadService.getEncodedDefault();
		}else{
			encoded = fileUploadService.obtenerEncoded(newUserDTO.getEncoded());
		}
		byte[] image = fileUploadService.convertEncodedToBytes(encoded);

		UserEntity createUserEntity = UserEntity.builder()
				.username(newUserDTO.getUsername())
				.dni(newUserDTO.getDni())
				.firstName(newUserDTO.getFirstName())
				.lastName(newUserDTO.getLastName())
				.surName(newUserDTO.getSurName())
				.phone(newUserDTO.getPhone())
				.address(newUserDTO.getAddress())
				.email(newUserDTO.getEmail())
				.password(newUserDTO.getPassword())
				.image(image)
				.roles(roles)
				.state("CREATED").build();
		return userRepository.save(createUserEntity);
	}

	@Override
	public UserEntity readUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserEntity updateUser(UserDTO userDTO) {

		UserEntity userEntity = readUser(userDTO.getId());
		userEntity.setLastName(userDTO.getLastName());
		userEntity.setSurName(userDTO.getSurName());
		userEntity.setFirstName(userDTO.getFirstName());
		userEntity.setDni(userDTO.getDni());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setAddress(userDTO.getAddress());
		userEntity.setUsername(userDTO.getUsername());
		userEntity.setPhone(userDTO.getPhone());
		userEntity.setImage(fileUploadService.convertEncodedToBytes(userDTO.getEncoded()));

		return userRepository.save(userEntity);
	}

	@Override
	public UserEntity deleteUser(UserDTO userDTO) {
		UserEntity userDB = readUser(userDTO.getId());
		userDB.setState("DELETED");
		return userRepository.save(userDB);
	}

	@Override
	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}


	//Security
	public Optional<UserEntity> getByUsernameOrEmail(String usernameOrEmail){
		return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	}

	public Optional<UserEntity> getByTokenPassword(String tokenPassword){
		return userRepository.findByTokenPassword(tokenPassword);
	}

	@Override
	public boolean existsByUsername(String nombreUsuario) {
		return userRepository.existsByUsername(nombreUsuario);
	}

	public boolean existsByUsernameOrEmail(String usernameOrEmail) {
		return userRepository.existsByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	}


	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}



	public void deleteUser(long id){
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










