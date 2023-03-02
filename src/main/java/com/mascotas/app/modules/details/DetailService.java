package com.mascotas.app.modules.details;

import java.util.List;

public interface DetailService {

    public List<DetailModel> listAllDetails();
    public DetailModel createDetail(DetailDTO detailDTO);
    public DetailModel readDetail(Long id);
    public DetailModel updateDetail(DetailDTO detailDTO);
    public DetailModel deleteDetail(DetailDTO detailDTO);

    public List<DetailModel> readBySpecies(DetailDTO detailDTO);

    //Business
    public Boolean existsById(Long id);


}
