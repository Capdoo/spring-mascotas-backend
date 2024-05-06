package com.mascotas.app.modules.adoptions;

import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetRepository;
import com.mascotas.app.modules.pets.PetService;
import com.mascotas.app.utils.FechaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AdoptionServiceImpl implements AdoptionService{

    @Autowired
    PetRepository petRepository;

    @Autowired
    AdoptionRepository adoptionRepository;

    @Autowired
    PetService petService;

    public List<AdoptionEntity> listAllAdoptions(){
        return adoptionRepository.findAll();
    }

    @Override
    public AdoptionEntity createAdoption(AdoptionDto adoptionDto) {
        PetEntity selectedPet = petRepository.findById(adoptionDto.getPet_id()).get();
        AdoptionEntity newAdoption = new AdoptionEntity();
        newAdoption.setAddress(adoptionDto.getAddress());
        newAdoption.setDistrict(adoptionDto.getDistrict());
        newAdoption.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        newAdoption.setPet(selectedPet);
        newAdoption.setPhoneA(adoptionDto.getPhoneA());
        newAdoption.setPhoneB(adoptionDto.getPhoneB());
        newAdoption.setMessage(adoptionDto.getMessage());
        newAdoption.setPet(selectedPet);
        newAdoption.setObservation(adoptionDto.getObservation());
        return adoptionRepository.save(newAdoption);
    }

    @Override
    public AdoptionEntity readAdoption(Long id) {
        return adoptionRepository.findById(id).orElse(null);
    }

    @Override
    public AdoptionEntity updateAdoption(AdoptionDto adoptionDto) {
        return null;
    }

    @Override
    public AdoptionEntity deleteAdoption(AdoptionDto adoptionDto) {
        return null;
    }

    @Override
    public List<AdoptionEntity> listAllByPetId(Long petId) {
        PetEntity petEntity = petService.readPet(petId);
        if (petEntity == null) {
            return null;
        }
        return adoptionRepository.findAllByPet(petEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }



    //Obtener todos




    //Obtener por mascota_id

    //Obtener por mascota_id
    public List<AdoptionEntity> listAllByPetId(long petId){
        PetEntity mascotaSeleccionada = petRepository.findById(petId).get();
        return adoptionRepository.findAllByPet(mascotaSeleccionada);
    }



}
