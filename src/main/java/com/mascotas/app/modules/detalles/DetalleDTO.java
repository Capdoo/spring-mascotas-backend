package com.mascotas.app.modules.detalles;

public class DetalleDTO {

	private int id;
	private String especie;
	private String raza;
	
	public DetalleDTO() {
		super();
	}
	
	public DetalleDTO(int id, String especie, String raza) {
		super();
		this.id = id;
		this.especie = especie;
		this.raza = raza;
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
	
	
}
