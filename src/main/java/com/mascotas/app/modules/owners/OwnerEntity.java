package com.mascotas.app.modules.owners;

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

import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.security.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ForeignKey;


@Entity
@Table(name="owners")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OwnerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Timestamp registerDate;
	private Integer numberPets;

	//Secundarios
	private Integer rate;
	//private Long historial_id;

	//User: 1-1
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "OWNER_FK_USER"))
	private UserEntity user;

	//Pets: 1-N
	@OneToMany(mappedBy="owner")
	private Set<PetEntity> pets;

	private String state;

}
