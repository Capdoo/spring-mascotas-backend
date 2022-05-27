package com.mascotas.app.modules.refugios;

public class RefugioDTO {

	private int id;
	
	private String direccion;
	private String distrito;
	private String nombre;
	private int numeroAsociados;
	private String numeroContacto;
	
	private String fechaRegistro;
	
	private int idRepresentante;
	private String dniRepresentante;

	//Para imagen
	private String encoded;
	private String urlLink;

	public RefugioDTO() {
		super();
	}

	public RefugioDTO(String direccion, String distrito, String nombre, int numeroAsociados, String numeroContacto,
			int idRepresentante) {
		super();
		this.direccion = direccion;
		this.distrito = distrito;
		this.nombre = nombre;
		this.numeroAsociados = numeroAsociados;
		this.numeroContacto = numeroContacto;
		this.idRepresentante = idRepresentante;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumeroAsociados() {
		return numeroAsociados;
	}

	public void setNumeroAsociados(int numeroAsociados) {
		this.numeroAsociados = numeroAsociados;
	}

	public String getNumeroContacto() {
		return numeroContacto;
	}

	public void setNumeroContacto(String numeroContacto) {
		this.numeroContacto = numeroContacto;
	}

	public int getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(int idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getDniRepresentante() {
		return dniRepresentante;
	}

	public void setDniRepresentante(String dniRepresentante) {
		this.dniRepresentante = dniRepresentante;
	}
	//

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
	
	
}
