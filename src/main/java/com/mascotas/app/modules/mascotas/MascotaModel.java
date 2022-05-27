package com.mascotas.app.modules.mascotas;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mascotas.app.modules.busquedas.BusquedaModel;
import com.mascotas.app.modules.detalles.DetalleModel;
import com.mascotas.app.modules.duenos.DuenoModel;
import com.mascotas.app.modules.refugios.RefugioModel;

@Entity
@Table(name="mascotas")
public class MascotaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private String genero;
	private Timestamp fechaNacimiento;
	private Timestamp fechaRegistro;
	private String color;
	//Especificado por el usuario
	private String razaEspecifica;
	private String caracteristica;
	private String tamano;
	
	//link de imagen
	private String linkImg;
	
	@ManyToOne
	@JoinColumn(name="dueño_id",referencedColumnName = "id", nullable=true)
	private DuenoModel dueno;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detalle_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "MASCOTA_FK_DETALLE"))
	private DetalleModel detalle;
	
	//Para busquedas
	@OneToMany(mappedBy="mascota")
	private Set<BusquedaModel> busquedas;
	
	//Para adopciones
	@OneToMany(mappedBy="mascota")
	private Set<BusquedaModel> adopciones;
	
	//Refugios
	@ManyToOne
	@JoinColumn(name="refugio_id",referencedColumnName = "id", nullable=true)
	private RefugioModel refugio;

	
	public MascotaModel() {
		super();
	}

	public MascotaModel(int id, String nombre, String genero, Timestamp fechaNacimiento, Timestamp fechaRegistro,
			String color, String razaEspecifica, String caracteristica, String tamano, DuenoModel dueno,
			DetalleModel detalle, Set<BusquedaModel> busquedas, RefugioModel refugio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
		this.color = color;
		this.razaEspecifica = razaEspecifica;
		this.caracteristica = caracteristica;
		this.tamano = tamano;
		this.dueno = dueno;
		this.detalle = detalle;
		this.busquedas = busquedas;
		this.refugio = refugio;
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


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}

	

	public Timestamp getFechaNacimiento() {
		return fechaNacimiento;
	}




	public void setFechaNacimiento(Timestamp fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}




	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public DuenoModel getDueno() {
		return dueno;
	}


	public void setDueno(DuenoModel dueno) {
		this.dueno = dueno;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public DetalleModel getDetalle() {
		return detalle;
	}

	public void setDetalle(DetalleModel detalle) {
		this.detalle = detalle;
	}




	public String getRazaEspecifica() {
		return razaEspecifica;
	}

	public void setRazaEspecifica(String razaEspecifica) {
		this.razaEspecifica = razaEspecifica;
	}

	//Busquedas
	
	public Set<BusquedaModel> getBusquedas() {
		return busquedas;
	}

	public void setBusquedas(Set<BusquedaModel> busquedas) {
		this.busquedas = busquedas;
	}

	//Refugios

	public RefugioModel getRefugio() {
		return refugio;
	}

	public void setRefugio(RefugioModel refugio) {
		this.refugio = refugio;
	}



	public Set<BusquedaModel> getAdopciones() {
		return adopciones;
	}

	public void setAdopciones(Set<BusquedaModel> adopciones) {
		this.adopciones = adopciones;
	}
	
	
	//Imagen link
	public String getLinkImg() {
		return linkImg;
	}

	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}

}
