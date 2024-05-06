package com.mascotas.app.modules.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DetailResource {

    @Autowired
    DetailService detailService;

    public ResponseEntity<Object> createDetail(DetailDto detailDTO) {
        DetailEntity detailEntity;

        detailEntity = detailService.createDetail(detailDTO);
        detailDTO = DetailMapper.mapDetailDto(detailEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(detailDTO);

    }

    public ResponseEntity<Object> read() {
        List<DetailEntity> listDetails;
        List<DetailDto> listDetailsDto;

        listDetails = detailService.listAllDetails();
        listDetailsDto = listDetails.stream()
                .map(DetailMapper::mapDetailDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDetailsDto);
    }

    public ResponseEntity<Object> readById(Long id) {
        DetailEntity detailEntity;
        DetailDto detailDto;

        detailEntity = detailService.readDetail(id);
        if (detailEntity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
        }
        detailDto = DetailMapper.mapDetailDto(detailEntity);
        return ResponseEntity.ok().body(detailDto);
    }

    public ResponseEntity<Object> updateDetail(Long id, DetailDto detailDto) {
        DetailEntity detailEntity;
        DetailEntity detailUpdate;

        detailEntity = detailService.readDetail(id);
        if (detailEntity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
        }
        detailDto.setId(id);
        detailUpdate = detailService.updateDetail(detailDto);
        detailDto = DetailMapper.mapDetailDto(detailUpdate);
        return ResponseEntity.ok().body(detailDto);
    }

    public ResponseEntity<Object> deleteDetail(Long id) {
        DetailEntity detailEntity;
        DetailDto detailDto;
        DetailEntity detailDelete;

        detailEntity = detailService.readDetail(id);
        if (detailEntity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
        }
        detailDto = DetailDto.builder()
                .id(id)
                .build();
        detailDelete = detailService.deleteDetail(detailDto);
        detailDto = DetailMapper.mapDetailDto(detailDelete);
        return ResponseEntity.ok().body(detailDto);
    }

    public ResponseEntity<Object> readSpecies() {
        List<DetailEntity> listDetails;
        Set<String> listUniqueSpecies;
        listDetails = detailService.listAllDetails();
        listUniqueSpecies = listDetails.stream()
                .map(DetailEntity::getSpecies)
                .collect(Collectors.toSet());
        return ResponseEntity.ok().body(listUniqueSpecies);
    }

}
