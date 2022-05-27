package com.mascotas.app.modules.duenos;

public class DuenoDTO {
	int id;
	String fechaRegistro;
	int historial_id;
	int numero_mascotas;
	int rate;
	int usuario_id;
	

	public DuenoDTO() {
		super();
	}
	
	public DuenoDTO(int id, String fechaRegistro, int historial_id, int numero_mascotas, int rate, int usuario_id) {
		super();
		this.id = id;
		this.fechaRegistro = fechaRegistro;
		this.historial_id = historial_id;
		this.numero_mascotas = numero_mascotas;
		this.rate = rate;
		this.usuario_id = usuario_id;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public int getHistorial_id() {
		return historial_id;
	}
	public void setHistorial_id(int historial_id) {
		this.historial_id = historial_id;
	}
	public int getNumero_mascotas() {
		return numero_mascotas;
	}
	public void setNumero_mascotas(int numero_mascotas) {
		this.numero_mascotas = numero_mascotas;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
