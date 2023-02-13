package com.mascotas.app.modules.searchs;

import com.mascotas.app.modules.owners.OwnerEntity;
import com.mascotas.app.modules.pets.PetEntity;

import java.util.List;

public interface SearchService {

    public List<SearchEntity> listAllSearchs();
    //crud
    public SearchEntity createSearch(SearchDTO searchDTO);
    public SearchEntity readSearch(Long id);
    public SearchEntity updateSearch(SearchDTO searchDTO);
    public SearchEntity deleteSearch(SearchDTO searchDTO);

    //business rules
    public List<SearchEntity> readAllSearchsByPet(PetEntity petEntity);
    public List<SearchEntity> radAllSearchsByOwner(OwnerEntity ownerEntity);

    //public Boolean existsById();

    //Not ready
    //public List<SearchEntity> radAllSearchsByShelter(ShelterModel shelterModel);

}
