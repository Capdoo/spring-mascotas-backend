package com.mascotas.app.modules.busquedas;

public class BusquedaDTO {

	private int id;
	private String direccion;
	private String distrito;
	private String telefonoA;
	private String telefonoB;
	private int mascotaId;
	
	private String nombreMascota;
	private String especieMascota;
	private String razaMascota;
	
	//Fecha de usuario
	private String fechaPerdida;
	private String fechaRegistro;
	
	//Mensaje
	private String mensaje;
	
	//Imagen
	private String encoded;
	private String urlLink;

	public BusquedaDTO() {
		super();
	}

	
	//cambiable
	public BusquedaDTO(int id, String direccion, String distrito, String telefonoA, String telefonoB, int mascotaId,
			String fechaPerdida, String fechaRegistro, String mensaje) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.distrito = distrito;
		this.telefonoA = telefonoA;
		this.telefonoB = telefonoB;
		this.mascotaId = mascotaId;
		this.fechaPerdida = fechaPerdida;
		this.fechaRegistro = fechaRegistro;
		this.mensaje = mensaje;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
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

	public int getMascotaId() {
		return mascotaId;
	}

	public void setMascotaId(int mascotaId) {
		this.mascotaId = mascotaId;
	}

	public String getFechaPerdida() {
		return fechaPerdida;
	}

	public void setFechaPerdida(String fechaPerdida) {
		this.fechaPerdida = fechaPerdida;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public String getNombreMascota() {
		return nombreMascota;
	}


	public void setNombreMascota(String nombreMascota) {
		this.nombreMascota = nombreMascota;
	}


	public String getEspecieMascota() {
		return especieMascota;
	}


	public void setEspecieMascota(String especieMascota) {
		this.especieMascota = especieMascota;
	}


	public String getRazaMascota() {
		return razaMascota;
	}


	public void setRazaMascota(String razaMascota) {
		this.razaMascota = razaMascota;
	}


	//Imagen
	public String getEncoded() {
		return encoded;
	}


	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}


	public String getUrlLink() {
		return urlLink;
	}


	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	//
	
	
}
