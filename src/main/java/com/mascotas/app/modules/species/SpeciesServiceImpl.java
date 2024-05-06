package com.mascotas.app.modules.species;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SpeciesServiceImpl implements SpeciesService{

    @Autowired
    SpeciesRepository speciesRepository;

    @Override
    public List<SpeciesEntity> listAll() {
        return speciesRepository.findAll();
    }
}
