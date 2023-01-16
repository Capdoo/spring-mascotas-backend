package com.mascotas.app.modules.partners;

import com.mascotas.app.modules.owners.OwnerDTO;
import com.mascotas.app.modules.owners.OwnerModel;
import com.mascotas.app.modules.owners.OwnerRepository;
import com.mascotas.app.security.models.UserModel;
import com.mascotas.app.security.repositories.UserRepository;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartnerService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    UserService userService;

    public PartnerModel savePartner(PartnerDTO partnerDTO) {
        //Gettin' user
        Long user_id =  partnerDTO.getUser_id();
        UserModel userModel = userRepository.findById(user_id).get();

        PartnerModel partnerModel = new PartnerModel();
        partnerModel.setUser(userModel);
        partnerModel.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        return partnerRepository.save(partnerModel);
    }
    /*
    public List<PartnerDTO> listAll(){
        List<PartnerDTO> sendList = new ArrayList<>();
    }*/



}
