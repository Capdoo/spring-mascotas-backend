package com.mascotas.app.modules.pets;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import com.mascotas.app.modules.adoptions.AdoptionModel;
import com.mascotas.app.modules.searchs.SearchEntity;
import com.mascotas.app.modules.details.DetailModel;
import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.shelters.ShelterModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name="pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String gender;
	private Timestamp birthDate;
	private Timestamp registerDate;
	private String colour;
	//Especificado por el usuario
	private String specificBreed;
	private String characteristic;
	private String size;
	private String state;
	//link de imagen
	@Lob
	@Column(length = 16777215)
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] image;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "PET_FK_OWNER"))
	private OwnerEntity owner;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PET_FK_DETAIL"))
	private DetailModel detail;

	//Shelters
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shelter_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "PET_FK_SHELTER"))
	private ShelterModel shelter;


	//For search
	//errr
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "search_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PET_FK_SEARCH"))
//	private SearchEntity search;

	@OneToOne(cascade =  CascadeType.ALL,mappedBy = "pet")
	private SearchEntity search;


	/*
	@OneToMany(cascade =  CascadeType.ALL, mappedBy="pet")
	private Set<SearchEntity> searchs;
	*/

	//For pets
	@OneToMany(cascade =  CascadeType.ALL, mappedBy="pet")
	private Set<AdoptionModel> adoptions;

}
