package com.mascotas.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mascotas.app.security.enums.RolNombre;
import com.mascotas.app.security.models.RolModel;
import com.mascotas.app.security.services.RolService;


//@Component
public class CreateRoles implements CommandLineRunner{

	
	@Autowired
	RolService rolService;
	
	//@Override
	public void run(String... args) throws Exception {
		
		RolModel rolAdmin = new RolModel(RolNombre.ROLE_ADMIN);
		
		RolModel rolUser = new RolModel(RolNombre.ROLE_USER);
		
		rolService.save(rolAdmin);
		rolService.save(rolUser);

	}
	 
	
	
	
	
}
