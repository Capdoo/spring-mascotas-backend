package com.mascotas.app.modules.details;

import java.util.List;

public interface DetailService {
    public List<DetailEntity> listAllDetails();
    public DetailEntity createDetail(DetailDTO detailDTO);
    public DetailEntity readDetail(Long id);
    public DetailEntity updateDetail(DetailDTO detailDTO);
    public DetailEntity deleteDetail(DetailDTO detailDTO);
    //business
    public List<DetailEntity> readAllBySpecies(DetailDTO detailDTO);
}
