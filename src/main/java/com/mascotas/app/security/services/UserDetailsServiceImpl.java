package com.mascotas.app.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mascotas.app.security.models.UsuarioModel;
import com.mascotas.app.security.models.UsuarioPrincipalModel;




//Convierte la clase Usuario en Usuario Principal.
//Media entre la clase Usuario y Usuario Principal.
//Es la clase de SpringSecurity especifica
//Para obtener los datos del usuario y sus privilegios

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UsuarioService usuarioService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UsuarioModel usuarioModel = usuarioService.getByNombreUsuario(username).get();
		
		return UsuarioPrincipalModel.build(usuarioModel);
	}

	
	
}
