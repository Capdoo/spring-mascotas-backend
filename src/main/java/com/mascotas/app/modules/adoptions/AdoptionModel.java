package com.mascotas.app.modules.adoptions;

import java.sql.Timestamp;

import javax.persistence.*;

import com.mascotas.app.modules.pets.PetEntity;

@Entity
@Table(name="adoptions")
public class AdoptionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String message;
	//In case the pet is sick
	private String observation;
	private String phoneA;
	private String phoneB;
	private String address;
	private String district;
	private Timestamp registerDate;
	
	@ManyToOne
	@JoinColumn(name="pet_id", referencedColumnName = "id", nullable=false, foreignKey = @ForeignKey(name = "ADOPTION_FK_PET"))
	private PetEntity pet;

	public AdoptionModel() {
		super();
	}
		
	public AdoptionModel(long id, String message, String observation, String phoneA, String phoneB,
			String address, String district, Timestamp registerDate, PetEntity pet) {
		super();
		this.id = id;
		this.message = message;
		this.observation = observation;
		this.phoneA = phoneA;
		this.phoneB = phoneB;
		this.address = address;
		this.district = district;
		this.registerDate = registerDate;
		this.pet = pet;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getPhoneA() {
		return phoneA;
	}

	public void setPhoneA(String phoneA) {
		this.phoneA = phoneA;
	}

	public String getPhoneB() {
		return phoneB;
	}

	public void setPhoneB(String phoneB) {
		this.phoneB = phoneB;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}


	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public PetEntity getPet() {
		return pet;
	}

	public void setPet(PetEntity pet) {
		this.pet = pet;
	}
}













