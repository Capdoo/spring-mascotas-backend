package com.mascotas.app.modules.pets;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.utils.FechaUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class PetMapper {

    public static PetDto mapPetDto(PetEntity petEntity) {
        return PetDto.builder()
                .id(petEntity.getId())
                .name(petEntity.getName())
                .gender(petEntity.getGender())
                .birthDate(FechaUtil.getStrindDateFromTimestamp(petEntity.getBirthDate()))
                .registerDate(FechaUtil.getStrindDateFromTimestamp(petEntity.getRegisterDate()))
                .colour(petEntity.getColour())
                .characteristic(petEntity.getCharacteristic())
                .size(petEntity.getSize())
//                .detail_id(petEntity.getDetail().getId())
//                .breed(petEntity.getDetail().getBreed())
//                .species(petEntity.getDetail().getSpecies())
//                .owner_id(petEntity.getOwner().getId())
//                .encoded(fileUploadService.convertBytesToEncoded(petEntity.getImage()))
                .state(petEntity.getState()).build();
    }
}
