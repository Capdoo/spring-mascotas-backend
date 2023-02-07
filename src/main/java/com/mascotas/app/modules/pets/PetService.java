package com.mascotas.app.modules.pets;

import com.mascotas.app.modules.owners.OwnerModel;

import java.util.List;

public interface PetService {

    public List<PetEntity> listAllPets();

    //crud
    public PetEntity createPet(PetDTO petDTO, String username);
    public PetEntity readPet(Long id);
    public PetEntity updatePet(PetDTO petDTO);
    public PetEntity deletePet(Long id);

    //Find by Owner
    public List<PetEntity> readByOwner(OwnerModel ownerModel);



}
