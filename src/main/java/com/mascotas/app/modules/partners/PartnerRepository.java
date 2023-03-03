package com.mascotas.app.modules.partners;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<PartnerModel, Long> {
    List<PartnerModel> findAll();
    Optional<PartnerModel> findByUser(PartnerModel partnerModel);
}
