package com.mascotas.app.modules.owners;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.security.models.UserEntity;

import javax.persistence.ForeignKey;


@Entity
@Table(name="owners")
public class OwnerModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Timestamp registerDate;
	private int numberOfPets;
	//Secundarios
	private int rate;
	private long historial_id;
	//Idx user (OneToOne)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "OWNER_FK_USER"))
	private UserEntity user;
	//Para mascotas
	@OneToMany(mappedBy="owner")
	private Set<PetEntity> pets;
	
	
	public OwnerModel() {
		super();
	}

	public OwnerModel(UserEntity user) {
		super();
		this.user = user;
	}

	public OwnerModel(long id, Timestamp registerDate, int numberOfPets, int rate, long historial_id, UserEntity user, Set<PetEntity> pets) {
		this.id = id;
		this.registerDate = registerDate;
		this.numberOfPets = numberOfPets;
		this.rate = rate;
		this.historial_id = historial_id;
		this.user = user;
		this.pets = pets;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}


	public int getNumberOfPets() {
		return numberOfPets;
	}

	public void setNumberOfPets(int numberOfPets) {
		this.numberOfPets = numberOfPets;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public long getHistorial_id() {
		return historial_id;
	}

	public void setHistorial_id(long historial_id) {
		this.historial_id = historial_id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Set<PetEntity> getPets() {
		return pets;
	}

	public void setPets(Set<PetEntity> pets) {
		this.pets = pets;
	}
}
