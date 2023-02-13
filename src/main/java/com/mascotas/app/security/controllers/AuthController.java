package com.mascotas.app.security.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.app.modules.owners.OwnerDTO;
import com.mascotas.app.modules.owners.OwnerService;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserServiceImp;
import com.mascotas.app.utils.ErrorMessageUtil;
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
import com.mascotas.app.security.dto.JwtDTO;
import com.mascotas.app.security.dto.LoginUserDTO;
import com.mascotas.app.security.dto.NewUserDTO;
import com.mascotas.app.security.jwt.JwtProvider;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserServiceImp userServiceImp;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	OwnerService ownerService;
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody NewUserDTO newUserDTO, BindingResult bindingResult) throws IOException{

		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
		}
		if (userServiceImp.existsByUsername(newUserDTO.getUsername())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Already Exists");
		}
		if (userServiceImp.existsByEmail(newUserDTO.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already Exists");
		}

		newUserDTO.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

		UserEntity userCreate = userServiceImp.createUser(newUserDTO);
		ownerService.saveOwner(new OwnerDTO(userCreate.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong fields");
		}
		if(!(userServiceImp.existsByUsernameOrEmail(loginUserDTO.getUsername()))) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
        return Autenticacion(loginUserDTO.getUsername(), loginUserDTO.getPassword());
	}
	
	public ResponseEntity<Object> Autenticacion(String username, String password) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        JwtDTO jwtDto = new JwtDTO(jwt);
			return ResponseEntity.status(HttpStatus.OK).body(jwtDto);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong fields");
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

	private String formatMessage(BindingResult bindingResult) throws JsonProcessingException {
		List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
				.map(err -> {
					Map<String, String> error = new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		ErrorMessageUtil errorMessage = ErrorMessageUtil.builder()
				.code("01")
				.messages(errors).build();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(errorMessage);

		}catch (JsonProcessingException e){
			e.printStackTrace();
		}
		return jsonString;
	}
}











