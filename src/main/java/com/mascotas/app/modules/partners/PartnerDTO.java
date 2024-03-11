package com.mascotas.app.modules.partners;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PartnerDTO {
    private Long id;
    private String registerDate;
    private Long user_id;
}
