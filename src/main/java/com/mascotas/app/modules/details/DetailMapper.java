package com.mascotas.app.modules.details;

public class DetailMapper {

    public static DetailDto mapDetailDto(DetailEntity detailEntity){
        return DetailDto.builder()
                .id(detailEntity.getId())
                .species(detailEntity.getSpecies())
                .breed(detailEntity.getBreed())
                .state(detailEntity.getState())
                .build();
    }
}
