package com.mascotas.app.modules.owners;

import java.util.List;

public interface OwnerService {

    public List<OwnerEntity> listAllOwners();
    public OwnerEntity createOwner(OwnerDTO ownerDTO);
    public OwnerEntity readOwner(Long id);
    public OwnerEntity updateOwner(OwnerDTO ownerDTO);
    public OwnerEntity deleteOwner(OwnerDTO ownerDTO);

    //business rules
}
