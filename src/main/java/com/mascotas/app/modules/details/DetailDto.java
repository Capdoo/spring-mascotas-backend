package com.mascotas.app.modules.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DetailDto {
	private Long id;
	private String species;
	private String breed;
	private String state;
}
