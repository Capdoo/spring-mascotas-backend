package com.mascotas.app.modules.pets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
	
	private long id;
	private String name;
	private String gender;
	private String birthDate;
	private String registerDate;
	private String colour;


	private String specificBreed;
	private String characteristic;
	private String size;
	
	//For detail
	private String species;
	private String breed;
	
	private long idOwner;
	private long idDetail;

	//For image
	private String encoded;
	//private String urlLink;

	private String state;


}
