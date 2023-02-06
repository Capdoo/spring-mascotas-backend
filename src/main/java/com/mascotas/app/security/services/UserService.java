package com.mascotas.app.security.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.models.UserModel;
import com.mascotas.app.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.modules.owners.OwnerRepository;
import com.mascotas.app.security.dto.UserDTO;

@Service
//Para implementar rollbacks y evitar incoherencia : Concurrencia
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
    OwnerRepository ownerRepository;
	@Autowired
    FileUploadService fileUploadService;
	
	//Obtener
	public List<UserDTO> listar(){
		List<UserDTO> listSend = new ArrayList<>();

		List<UserModel> listModels = userRepository.findAll();
		
		for(UserModel p: listModels) {
			UserDTO userSingle = new UserDTO();

				userSingle.setId(p.getId());
				userSingle.setLastName(p.getLastName());
				userSingle.setSurName(p.getSurName());
				userSingle.setAddress(p.getAddress());
				userSingle.setDni(p.getDni());
				userSingle.setEmail(p.getEmail());
				userSingle.setFirstName(p.getFirstName());
				userSingle.setUsername(p.getUsername());
				userSingle.setPhone(p.getPhone());
				userSingle.setEncoded(fileUploadService.convertBytesToEncoded(p.getImage()));

			listSend.add(userSingle);
		}

		return listSend;
	}

	//Security
	public Optional<UserModel> getByUsernameOrEmail(String usernameOrEmail){
		return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	}

	public Optional<UserModel> getByTokenPassword(String tokenPassword){
		return userRepository.findByTokenPassword(tokenPassword);
	}
	
	public boolean existsByUsername(String nombreUsuario) {
		return userRepository.existsByUsername(nombreUsuario);
	}

	public boolean existsByUsernameOrEmail(String usernameOrEmail) {

		return userRepository.existsByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	}


	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public boolean existsPorId(long id) {
		return userRepository.existsById(id);
	}

	public boolean existsByDni(String dni){ return userRepository.existsByDni(dni);}

	public UserModel save(UserModel usuarioModel) {
		return userRepository.save(usuarioModel);
	}

	public UserDTO getById(long id){
		UserModel p = userRepository.findById(id).get();
		UserDTO userSingle = new UserDTO();

		userSingle.setId(p.getId());
		userSingle.setLastName(p.getLastName());
		userSingle.setSurName(p.getSurName());
		userSingle.setFirstName(p.getFirstName());

		userSingle.setAddress(p.getAddress());
		userSingle.setDni(p.getDni());
		userSingle.setEmail(p.getEmail());
		userSingle.setUsername(p.getUsername());
		userSingle.setPhone(p.getPhone());
		userSingle.setEncoded(fileUploadService.convertBytesToEncoded(p.getImage()));
		return userSingle;
	}

	public void updateUser(long id, UserDTO userDTO){

		UserModel userModel = userRepository.findById(id).get();

			userModel.setLastName(userDTO.getLastName());
			userModel.setSurName(userDTO.getSurName());
			userModel.setFirstName(userDTO.getFirstName());

			userModel.setDni(userDTO.getDni());
			userModel.setEmail(userDTO.getEmail());
			userModel.setAddress(userDTO.getAddress());

			userModel.setUsername(userDTO.getUsername());
			userModel.setPhone(userDTO.getPhone());

		userRepository.save(userModel);

	}

	public void deleteUser(long id){

//		//Get the user
//		UserModel userModel = userRepository.findById(id).get();
//
//		//Si es dueno
//		if(ownerService.existsOwnerByUserId(userModel.getId())){
//			//Eliminar mascotas
//		}
//
//		//Si es propietario de un o unos refugios, eliminar todos
//		if(shelterService.existsRefugioByUserId(userModel.getId())){
//			//
//		}
//
//		userRepository.deleteById(id);
	}
	
}











