package com.mascotas.app.modules.breed;

import com.mascotas.app.modules.species.SpeciesEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BreedService {
    List<BreedEntity> listAll();
    List<BreedEntity> listAllBySpecies(SpeciesEntity speciesEntity);
}