package com.mascotas.app.modules.adopciones;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mascotas.app.modules.mascotas.MascotaModel;

@Entity
@Table(name="adopciones")
public class AdopcionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String mensaje;
	//En caso el animal este enfermo
	private String observacion;
	
	private String telefonoA;
	private String telefonoB;
	
	private String direccion;
	private String distrito;
	
	private Timestamp fechaRegistro;
	
	@ManyToOne
	@JoinColumn(name="mascota_id",referencedColumnName = "id", nullable=false)
	private MascotaModel mascota;

	public AdopcionModel() {
		super();
	}
		
	public AdopcionModel(int id, String mensaje, String observacion, String telefonoA, String telefonoB,
			String direccion, String distrito, Timestamp fechaRegistro, MascotaModel mascota) {
		super();
		this.id = id;
		this.mensaje = mensaje;
		this.observacion = observacion;
		this.telefonoA = telefonoA;
		this.telefonoB = telefonoB;
		this.direccion = direccion;
		this.distrito = distrito;
		this.fechaRegistro = fechaRegistro;
		this.mascota = mascota;
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

	public MascotaModel getMascota() {
		return mascota;
	}

	public void setMascota(MascotaModel mascota) {
		this.mascota = mascota;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}



}













