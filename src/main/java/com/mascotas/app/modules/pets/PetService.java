package com.mascotas.app.modules.pets;

import com.mascotas.app.modules.owners.OwnerModel;

import java.util.List;

public interface PetService {

    public List<PetDTO> listAllPets();

    //crud
    public PetDTO createPet(PetDTO petDTO, String username);
    public PetDTO readPet(Long id);
    public PetDTO updatePet(PetDTO petDTO);
    public PetDTO deletePet(Long id);

    //Find by Owner
    public List<PetDTO> readByOwner(OwnerModel ownerModel);



}
