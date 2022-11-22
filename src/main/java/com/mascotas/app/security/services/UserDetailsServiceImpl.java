package com.mascotas.app.security.services;

import com.mascotas.app.security.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mascotas.app.security.models.UsuarioPrincipalModel;

//Convierte la clase Usuario en Usuario Principal.
//Media entre la clase Usuario y Usuario Principal.
//Es la clase de SpringSecurity especifica
//Para obtener los datos del usuario y sus privilegios

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserModel userModel = userService.getByUsernameOrEmail(usernameOrEmail).get();
		return UsuarioPrincipalModel.build(userModel);
	}
}
