package com.mascotas.app.security.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mascotas.app.modules.duenos.DuenoModel;
import com.mascotas.app.modules.refugios.RefugioModel;


@Entity
@Table(name="usuarios")
public class UsuarioModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	@Column(unique = true)
	private String nombreUsuario;
	
	@Column(name="dni")
	private String dni;
	
	@Column(name="apellido_paterno")
	private String apellidoPaterno;
	
	@Column(name="apellido_materno")
	private String apellidoMaterno;
	
	@Column(name="telefono")
	private String telefono;
	
	@Column(name="direccion")
	private String direccion;
	
	
	
	private String email;
	
	private String password;
	
	//
	private String linkImg;	
	
	//Dueño referenciado
	@OneToOne(cascade =  CascadeType.ALL,mappedBy = "usuario")
	private DuenoModel dueno;
	
	//Refugio
	@OneToOne(cascade =  CascadeType.ALL,mappedBy = "usuario")
	private RefugioModel refugio;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usuario_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
	private Set<RolModel> roles = new HashSet<>();

	public UsuarioModel() {
		super();
	}

	
	
	

	public UsuarioModel(String nombre, String nombreUsuario, String email, String password) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
	}





	public UsuarioModel(String nombre, String nombreUsuario, String dni, String apellidoPaterno,
			String apellidoMaterno, String telefono, String direccion, String email, String password, Set<RolModel> roles) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.dni = dni;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.telefono = telefono;
		this.direccion = direccion;
		this.email = email;
		this.password = password;
		this.roles = roles;
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RolModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolModel> roles) {
		this.roles = roles;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public DuenoModel getDueno() {
		return dueno;
	}

	public void setDueño(DuenoModel dueño) {
		this.dueno = dueño;
	}


//

	public String getLinkImg() {
		return linkImg;
	}

	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	} 

	//
	
	
}

























