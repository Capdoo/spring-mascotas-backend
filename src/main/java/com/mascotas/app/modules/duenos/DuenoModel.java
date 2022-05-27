package com.mascotas.app.modules.duenos;

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

import com.mascotas.app.modules.mascotas.MascotaModel;
import com.mascotas.app.security.models.UsuarioModel;

import javax.persistence.ForeignKey;


@Entity
@Table(name="dueños")
public class DuenoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Timestamp fechaRegistro;
	private int numeroMascotas;
	
	//Secundarios
	private int rate;
	private int historial_id;
	
	//Id del usuario (OneToOne)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "DUEÑO_FK_EMPLEADO"))
	private UsuarioModel usuario;
	
	
	//Para mascotas
	@OneToMany(mappedBy="dueno")
	private Set<MascotaModel> mascotas;
	
	
	public DuenoModel() {
		super();
	}

	
	public DuenoModel(int id, UsuarioModel usuario, Set<MascotaModel> mascotas, Timestamp fechaRegistro,
			int numeroMascotas, int rate, int historial_id) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.mascotas = mascotas;
		this.fechaRegistro = fechaRegistro;
		this.numeroMascotas = numeroMascotas;
		this.rate = rate;
		this.historial_id = historial_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UsuarioModel getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}
	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public int getNumeroMascotas() {
		return numeroMascotas;
	}
	public void setNumeroMascotas(int numeroMascotas) {
		this.numeroMascotas = numeroMascotas;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getHistorial_id() {
		return historial_id;
	}
	public void setHistorial_id(int historial_id) {
		this.historial_id = historial_id;
	}

	public Set<MascotaModel> getMascotas() {
		return mascotas;
	}

	public void setMascotas(Set<MascotaModel> mascotas) {
		this.mascotas = mascotas;
	}
	
	
}
