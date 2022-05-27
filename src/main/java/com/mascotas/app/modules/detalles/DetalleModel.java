package com.mascotas.app.modules.detalles;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mascotas.app.modules.mascotas.MascotaModel;


@Entity
@Table(name="detalles")
public class DetalleModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String especie;
	private String raza;

	@OneToOne(cascade =  CascadeType.ALL, mappedBy = "detalle")
	private MascotaModel mascota;

	public DetalleModel() {
		super();
	}

	
	public DetalleModel(int id, String especie, String raza, MascotaModel mascota) {
		super();
		this.id = id;
		this.especie = especie;
		this.raza = raza;
		this.mascota = mascota;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public MascotaModel getMascota() {
		return mascota;
	}

	public void setMascota(MascotaModel mascota) {
		this.mascota = mascota;
	}
		


}
