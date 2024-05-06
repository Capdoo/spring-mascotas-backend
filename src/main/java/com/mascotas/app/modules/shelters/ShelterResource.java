package com.mascotas.app.modules.shelters;

import com.mascotas.app.dto.MessageDTO;
import com.mascotas.app.security.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShelterResource {

    @Autowired
    ShelterService shelterService;

    public ResponseEntity<Object> createShelter(@RequestBody ShelterDto shelterDto){
        //Pendiente
        UserEntity userEntity = new UserEntity();
        ShelterEntity shelterEntity = shelterService.createShelter(shelterDto, userEntity);
        shelterDto = ShelterMapper.mapShelterDto(shelterEntity);
        return new ResponseEntity<Object>(shelterDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> readShelters(){
        List<ShelterEntity> listSheltersEntities = shelterService.listAllShelters();
        List<ShelterDto> listSheltersDto = listSheltersEntities.stream()
                .map(ShelterMapper::mapShelterDto)
                .collect(Collectors.toList());
        return new ResponseEntity<Object>(listSheltersDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> readById(@RequestParam long id) {
        ShelterEntity shelterEntity = shelterService.readShelter(id);
        ShelterDto shelterDto = ShelterMapper.mapShelterDto(shelterEntity);
        return new ResponseEntity<Object>(shelterDto, HttpStatus.OK);
    }
}