package com.mascotas.app.modules.adopciones;

public class AdopcionDTO {
	
	private int id;
	
	private String mensaje;
	//En caso el animal este enfermo
	private String observacion;
	
	private String telefonoA;
	private String telefonoB;
	
	private String direccion;
	private String distrito;
	
	private String fechaRegistro;
	private int mascota_id;

	public AdopcionDTO() {
		super();
	}
	
	public AdopcionDTO(int id, String mensaje, String observacion, String telefonoA, String telefonoB, String direccion,
			String distrito, int mascota_id) {
		super();
		this.id = id;
		this.mensaje = mensaje;
		this.observacion = observacion;
		this.telefonoA = telefonoA;
		this.telefonoB = telefonoB;
		this.direccion = direccion;
		this.distrito = distrito;
		this.mascota_id = mascota_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTelefonoA() {
		return telefonoA;
	}

	public void setTelefonoA(String telefonoA) {
		this.telefonoA = telefonoA;
	}

	public String getTelefonoB() {
		return telefonoB;
	}

	public void setTelefonoB(String telefonoB) {
		this.telefonoB = telefonoB;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public int getMascota_id() {
		return mascota_id;
	}

	public void setMascota_id(int mascota_id) {
		this.mascota_id = mascota_id;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


}
