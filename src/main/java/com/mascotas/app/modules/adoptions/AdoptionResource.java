package com.mascotas.app.modules.adoptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdoptionResource {

    @Autowired
    AdoptionService adoptionService;

    public ResponseEntity<Object> createAdoption(AdoptionDto adoptionDto) {
        AdoptionEntity adoptionEntity;
        adoptionEntity = adoptionService.createAdoption(adoptionDto);
        return ResponseEntity.ok(AdoptionMapper.mapAdoptionDto(adoptionEntity));

    }

    public ResponseEntity<Object> read() {
        List<AdoptionEntity> listAdoptions = adoptionService.listAllAdoptions();
        List<AdoptionDto> sendList = listAdoptions.stream().map(
                AdoptionMapper::mapAdoptionDto
        ).collect(Collectors.toList());
        return ResponseEntity.ok(sendList);
    }

    public ResponseEntity<Object> readAdoption(Long id) {
        if (!adoptionService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }
        AdoptionEntity adoptionEntity = adoptionService.readAdoption(id);
        return ResponseEntity.ok(adoptionEntity);
    }

    public ResponseEntity<Object> readAdoptionsByPetId(long petId) {
        List<AdoptionEntity> listAdoptions = adoptionService.listAllByPetId(petId);
        List<AdoptionDto> sendList = listAdoptions.stream().map(
                AdoptionMapper::mapAdoptionDto
        ).collect(Collectors.toList());
        return ResponseEntity.ok(sendList);
    }
}