package com.mascotas.app.modules.details;

import javax.persistence.*;

import com.mascotas.app.modules.pets.PetEntity;

import java.util.Set;


@Entity
@Table(name="details")
public class DetailModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String species;
	private String breed;
	@OneToMany(cascade =  CascadeType.ALL, mappedBy = "detail")
	private Set<PetEntity> pets;

	public DetailModel() {
		super();
	}

	public DetailModel(String species, String breed) {
		this.species = species;
		this.breed = breed;
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

	public Set<PetEntity> getPets() {
		return pets;
	}

	public void setPets(Set<PetEntity> pets) {
		this.pets = pets;
	}
}
