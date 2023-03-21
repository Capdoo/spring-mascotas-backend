package com.mascotas.app.security.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.partners.PartnerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Username can not be empty")
	@Column(unique = true, nullable = false)
	private String username;

	@NotEmpty(message = "Dni cannot be empty")
	@Size(min = 8, max = 8, message = "Dni length should be 8")
	@Column(name = "dni", unique = true, length = 8, nullable = false)
	private String dni;

	@NotEmpty(message = "First Name can not be empty")
	@Column(name="first_name", nullable = false)
	private String firstName;

	@NotEmpty(message = "Last Name can not be empty")
	@Column(name="last_name", nullable = false)
	private String lastName;

	@NotEmpty(message = "Phone can not be empty")
	@Column(name="phone")
	private String phone;

	@NotEmpty(message = "Address can not be empty")
	@Column(name="address")
	private String address;

	@NotEmpty(message = "Email can not be empty")
	@Email(message = "Email is not valid")
	@Column(unique = true, nullable = false)
	private String email;

	@NotEmpty(message = "Password can not be empty")
	private String password;

	@Lob
	@Column(length = 16777215)
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] image;

	@Column(name="token_password")
	private String tokenPassword;

	//Referenced owner
	@OneToOne(cascade =  CascadeType.ALL, mappedBy = "user")
	private OwnerEntity owner;

	//Referenced partner
	@OneToOne(cascade =  CascadeType.ALL, mappedBy = "user")
	private PartnerModel partnerModel;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="user_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
	private Set<RoleEntity> roles = new HashSet<>();

	private String state;

}

























