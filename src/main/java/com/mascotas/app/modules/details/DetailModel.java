package com.mascotas.app.modules.details;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mascotas.app.modules.pets.PetModel;


@Entity
@Table(name="details")
public class DetailModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String species;
	private String breed;

	@OneToOne(cascade =  CascadeType.ALL, mappedBy = "detail")
	private PetModel pet;

	public DetailModel() {
		super();
	}

	public DetailModel(long id, String species, String breed, PetModel pet) {
		this.id = id;
		this.species = species;
		this.breed = breed;
		this.pet = pet;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public PetModel getPet() {
		return pet;
	}

	public void setPet(PetModel pet) {
		this.pet = pet;
	}
}
