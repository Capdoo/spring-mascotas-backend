package com.mascotas.app.modules.details;

import javax.persistence.*;

import com.mascotas.app.modules.pets.PetEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name="details")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String species;

	private String breed;
//
//	@OneToMany(cascade =  CascadeType.ALL, mappedBy = "detail")
//	private Set<PetEntity> pets;

	private String state;
}