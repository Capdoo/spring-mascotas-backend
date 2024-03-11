package com.mascotas.app.modules.owners;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OwnerDTO {
	private Long id;
	private String register_date;
	private Long historial_id;
	private Integer number_pets;
	private Integer rate;
	private Long user_id;
	private String state;
	public OwnerDTO(Long user_id){
		this.user_id = user_id;
	}
}
