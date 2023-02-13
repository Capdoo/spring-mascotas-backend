package com.mascotas.app.modules.searchs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SearchDTO {

	private Long id;
	private String address;
	private String district;
	private String phone_a;
	private String phone_b;

	//Pet
	private Long pet_id;
	private String name_pet;
	private String species_pet;
	private String breed_pet;


	//Fecha de usuario
	private String lost_date;
	private String register_date;
	
	//Mensaje
	private String message;

}
