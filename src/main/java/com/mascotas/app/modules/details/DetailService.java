package com.mascotas.app.modules.details;

import java.util.List;

public interface DetailService {
    public List<DetailEntity> listAllDetails();
    public DetailEntity createDetail(DetailDto detailDTO);
    public DetailEntity readDetail(Long id);
    public DetailEntity updateDetail(DetailDto detailDTO);
    public DetailEntity deleteDetail(DetailDto detailDTO);
    //business
    public List<DetailEntity> readAllBySpecies(DetailDto detailDTO);
}