package com.mascotas.app.security.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.app.dto.MensajeDTO;
import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.JwtDTO;
import com.mascotas.app.security.dto.LoginUsuarioDTO;
import com.mascotas.app.security.dto.NuevoUsuarioDTO;
import com.mascotas.app.security.enums.RolNombre;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.models.RolModel;
import com.mascotas.app.security.models.UsuarioModel;
import com.mascotas.app.security.services.RolService;
import com.mascotas.app.security.services.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@PostMapping("/nuevo")
	public ResponseEntity<Object> nuevo(@RequestBody NuevoUsuarioDTO nuevoUsuarioDTO, BindingResult bindingResult) throws IOException{
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}
		
		if (usuarioService.existsByNombreUsuario(nuevoUsuarioDTO.getNombreUsuario())) {
			return new ResponseEntity(new MensajeDTO("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
	
		}
		if (usuarioService.existsByEmail(nuevoUsuarioDTO.getEmail())) {
			return new ResponseEntity(new MensajeDTO("El email ya existe"), HttpStatus.BAD_REQUEST);
	
		}
		
		
		/*
		UsuarioModel usuarioModel = new UsuarioModel(
										nuevoUsuarioDTO.getNombre(),
										nuevoUsuarioDTO.getNombreUsuario(),
										nuevoUsuarioDTO.getEmail(),
										passwordEncoder.encode(nuevoUsuarioDTO.getPassword())
									);
		*/
		UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setApellidoPaterno(nuevoUsuarioDTO.getApellidoPaterno());
			usuarioModel.setApellidoMaterno(nuevoUsuarioDTO.getApellidoMaterno());
			usuarioModel.setNombre(nuevoUsuarioDTO.getNombre());
			usuarioModel.setDireccion(nuevoUsuarioDTO.getDireccion());
			usuarioModel.setDni(nuevoUsuarioDTO.getDni());
			usuarioModel.setEmail(nuevoUsuarioDTO.getEmail());
			usuarioModel.setNombreUsuario(nuevoUsuarioDTO.getNombreUsuario());
			usuarioModel.setPassword(passwordEncoder.encode(nuevoUsuarioDTO.getPassword()));
			usuarioModel.setTelefono(nuevoUsuarioDTO.getTelefono());
		
				String encoded = fileUploadService.obtenerEncoded(nuevoUsuarioDTO.getEncoded());
				byte[] imagen = fileUploadService.convertStringToBytes(encoded);
				String url = fileUploadService.fileUpload(imagen);
			
				usuarioModel.setLinkImg(url);
			
		
		
		Set<RolModel> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		if (nuevoUsuarioDTO.getRoles().contains("admin")) {
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		}
		
		if (nuevoUsuarioDTO.getRoles().contains("rept")) {
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_REPT).get());
		}
		
		usuarioModel.setRoles(roles);
		usuarioService.save(usuarioModel);
		
		return new ResponseEntity(new MensajeDTO("Usuario guardado"), HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginUsuarioDTO loginUsuarioDTO, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}
		
		if(!(usuarioService.existsByNombreUsuario(loginUsuarioDTO.getNombreUsuario()))) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}
		
        return Autenticacion(loginUsuarioDTO.getNombreUsuario(), loginUsuarioDTO.getPassword());
		
	}
	
	public ResponseEntity<Object> Autenticacion(String username, String password) {
		
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        UserDetails userDetails = (UserDetails)authentication.getPrincipal(); 
	        JwtDTO jwtDto = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
	        return new ResponseEntity(jwtDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}

	}
	
}











