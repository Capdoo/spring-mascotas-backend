package com.mascotas.app.modules.partners;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import com.mascotas.app.security.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PartnerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    UserServiceImp userServiceImp;

    public PartnerModel savePartner(PartnerDTO partnerDTO) {
        //Gettin' user
        Long user_id =  partnerDTO.getUser_id();
        UserEntity userEntity = userRepository.findById(user_id).get();

        PartnerModel partnerModel = new PartnerModel();
        partnerModel.setUser(userEntity);
        partnerModel.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        return partnerRepository.save(partnerModel);
    }
}