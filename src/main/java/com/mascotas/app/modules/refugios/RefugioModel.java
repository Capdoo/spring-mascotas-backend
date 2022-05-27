package com.mascotas.app.modules.refugios;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mascotas.app.modules.mascotas.MascotaModel;
import com.mascotas.app.security.models.UsuarioModel;

@Entity
@Table(name="refugios")
public class RefugioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private int numeroAsociados;
	private Timestamp fechaRegistro;
	private String numeroContacto;
	private String direccion;
	private String distrito;

	//link de imagen
	private String linkImg;
	
	//Id del representante (OneToOne)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "REFUGIO_FK_USUARIO"))
	private UsuarioModel usuario;
	
	//Para mascotas
	@OneToMany(mappedBy="refugio")
	private Set<MascotaModel> mascotas;
	
	public RefugioModel() {
		super();
	}

	public RefugioModel(int id, String nombre, int numeroAsociados, Timestamp fechaRegistro,
			String numeroContacto, String direccion, String distrito, UsuarioModel usuario,
			Set<MascotaModel> mascotas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numeroAsociados = numeroAsociados;
		this.fechaRegistro = fechaRegistro;
		this.numeroContacto = numeroContacto;
		this.direccion = direccion;
		this.distrito = distrito;
		this.usuario = usuario;
		this.mascotas = mascotas;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
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

	public Set<MascotaModel> getMascotas() {
		return mascotas;
	}

	public void setMascotas(Set<MascotaModel> mascotas) {
		this.mascotas = mascotas;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	//
	public String getLinkImg() {
		return linkImg;
	}

	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}
	
}

