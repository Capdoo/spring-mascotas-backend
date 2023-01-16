package com.mascotas.app.security.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import com.mascotas.app.modules.owners.OwnerDTO;
import com.mascotas.app.modules.owners.OwnerModel;
import com.mascotas.app.modules.owners.OwnerService;
import com.mascotas.app.security.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.app.dto.MessageDTO;
import com.mascotas.app.files.FileService;
import com.mascotas.app.security.dto.JwtDTO;
import com.mascotas.app.security.dto.LoginUserDTO;
import com.mascotas.app.security.dto.NewUserDTO;
import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.models.RoleModel;

import com.mascotas.app.security.services.RoleService;
import com.mascotas.app.security.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	FileService fileService;
	@Autowired
	OwnerService ownerService;
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody NewUserDTO newUserDTO, BindingResult bindingResult) throws IOException{
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new MessageDTO("Wrong fields"), HttpStatus.BAD_REQUEST);
		}
		
		if (userService.existsByUsername(newUserDTO.getUsername())) {
			return new ResponseEntity(new MessageDTO("Username already in use"), HttpStatus.BAD_REQUEST);
	
		}
		if (userService.existsByEmail(newUserDTO.getEmail())) {
			return new ResponseEntity(new MessageDTO("Email already in use"), HttpStatus.BAD_REQUEST);
	
		}

		UserModel userModel = new UserModel();
			userModel.setUsername(newUserDTO.getUsername());
			userModel.setDni(newUserDTO.getDni());
			userModel.setFirstName(newUserDTO.getFirstName());
			userModel.setLastName(newUserDTO.getLastName());
			userModel.setSurName(newUserDTO.getSurName());
			userModel.setPhone(newUserDTO.getPhone());
			userModel.setAddress(newUserDTO.getAddress());
			userModel.setEmail(newUserDTO.getEmail());
			userModel.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

			Set<RoleModel> roles = new HashSet<>();
			roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
			if (newUserDTO.getRoles().contains("admin")) {
				roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
			}
			if (newUserDTO.getRoles().contains("rept")) {
				roles.add(roleService.getByRoleName(RoleName.ROLE_REPT).get());
			}

			String encoded = "";
			if(newUserDTO.getEncoded() == null){
				encoded = fileService.getEncodedDefault();
			}else{
				encoded = fileService.obtenerEncoded(newUserDTO.getEncoded());
			}
			byte[] image = fileService.convertEncodedToBytes(encoded);

			userModel.setImage(image);
			userModel.setRoles(roles);

		UserModel userRegistered = userService.save(userModel);
		OwnerModel ownerModel = new OwnerModel(
				userRegistered
		);

		ownerService.saveOwner(
				new OwnerDTO(
						userRegistered.getId()
				)
		);

		return new ResponseEntity(userRegistered, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new MessageDTO("Wrong fields1"), HttpStatus.BAD_REQUEST);
		}
		if(!(userService.existsByUsernameOrEmail(loginUserDTO.getUsername()))) {
			return new ResponseEntity(new MessageDTO("Wrong fields2"), HttpStatus.BAD_REQUEST);
		}
        return Autenticacion(loginUserDTO.getUsername(), loginUserDTO.getPassword());
	}
	
	public ResponseEntity<Object> Autenticacion(String username, String password) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        JwtDTO jwtDto = new JwtDTO(jwt);
	        return new ResponseEntity(jwtDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity(new MessageDTO("Wrong fields"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<Object> refreshToken(@RequestBody JwtDTO jwtDTO) throws ParseException {
		try {
			String token = jwtProvider.refreshToken(jwtDTO);
			JwtDTO jwt = new JwtDTO(token);
			return new ResponseEntity<Object>(jwt, HttpStatus.OK);

		}catch (Exception e){
			return new ResponseEntity<Object>(new MessageDTO(e.getMessage()), HttpStatus.OK);
		}
	}
}











