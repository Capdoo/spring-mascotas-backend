package com.mascotas.app.modules.shelters;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import com.mascotas.app.modules.partners.PartnerModel;
import com.mascotas.app.modules.pets.PetEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="shelters")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ShelterModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long numberOfPartners;
	private Timestamp registerDate;
	private String contactNumber;
	private String address;
	private String district;
	//Image link
	private String linkImg;
	//Rel to set of partners
	@OneToMany(cascade =  CascadeType.ALL, mappedBy = "shelter")
	private Set<PartnerModel> partners;
	//For pets
	@OneToMany(mappedBy="shelter")
	private Set<PetEntity> pets;
}