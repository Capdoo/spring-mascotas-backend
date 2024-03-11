package com.mascotas.app.modules.pets;

import com.mascotas.app.modules.owners.OwnerEntity;

import java.util.List;

public interface PetService {
    public List<PetEntity> listAllPets();

    //crud
    public PetEntity createPet(PetDTO petDTO, String username);
    public PetEntity readPet(Long id);
    public PetEntity updatePet(PetDTO petDTO);
    public PetEntity deletePet(PetDTO petDTO);

    //Find by Owner
    public List<PetEntity> readByOwner(OwnerEntity ownerEntity);

    //Exists By Id
    public Boolean existsById(Long id);
}
