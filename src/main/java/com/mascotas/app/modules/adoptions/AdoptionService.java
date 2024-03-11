package com.mascotas.app.modules.adoptions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.modules.searches.SearchRepository;
import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetRepository;
import com.mascotas.app.utils.FechaUtil;

@Service
public class AdoptionService {
	@Autowired
	PetRepository petRepository;
	
	@Autowired
	AdoptionRepository adoptionRepository;

	public void saveAdoption(AdoptionDTO adoptionDTO) {
		FechaUtil fechaUtil = new FechaUtil();
		PetEntity selectedPet = petRepository.findById(adoptionDTO.getPet_id()).get();
		AdoptionModel newAdoption = new AdoptionModel();
			newAdoption.setAddress(adoptionDTO.getAddress());
			newAdoption.setDistrict(adoptionDTO.getDistrict());
			newAdoption.setRegisterDate(new Timestamp(System.currentTimeMillis()));
			newAdoption.setPet(selectedPet);
			newAdoption.setPhoneA(adoptionDTO.getPhoneA());
			newAdoption.setPhoneB(adoptionDTO.getPhoneB());
			newAdoption.setMessage(adoptionDTO.getMessage());
			newAdoption.setPet(selectedPet);
			newAdoption.setObservation(adoptionDTO.getObservation());
		adoptionRepository.save(newAdoption);
	}
	
	//Obtener todos
	public List<AdoptionDTO> listAllAdoptions(){
		List<AdoptionDTO> sendList = new ArrayList<>();
		List<AdoptionModel> listaBD = adoptionRepository.findAll();
		for(AdoptionModel p : listaBD) {
			FechaUtil fechaUtil = new FechaUtil();
			AdoptionDTO singleAdoption = new AdoptionDTO();
				singleAdoption.setId(p.getId());
				singleAdoption.setAddress(p.getAddress());
				singleAdoption.setDistrict(p.getDistrict());
				String registerDate = fechaUtil.getStrindDateFromTimestamp(p.getRegisterDate());
				singleAdoption.setRegisterDate(registerDate);
				singleAdoption.setPet_id(p.getPet().getId());
				singleAdoption.setPhoneA(p.getPhoneA());
				singleAdoption.setPhoneB(p.getPhoneB());
				singleAdoption.setMessage(p.getMessage());
				singleAdoption.setObservation(p.getObservation());
			sendList.add(singleAdoption);
		}
		return sendList;
	}
	
	//Obtener por mascota_id
	public List<AdoptionDTO> getByPetId(long mascotaId){
		List<AdoptionDTO> sendList = new ArrayList<>();
		PetEntity mascotaSeleccionada = petRepository.findById(mascotaId).get();
		List<AdoptionModel> listaBD = adoptionRepository.findAllByPet(mascotaSeleccionada);
		for(AdoptionModel p : listaBD) {
			AdoptionDTO adopcionSingle = new AdoptionDTO();
			FechaUtil fechaUtil = new FechaUtil();
				adopcionSingle.setId(p.getId());
				adopcionSingle.setAddress(p.getAddress());
				adopcionSingle.setDistrict(p.getDistrict());

					String registerDate = fechaUtil.getStrindDateFromTimestamp(p.getRegisterDate());
					adopcionSingle.setRegisterDate(registerDate);
					
				adopcionSingle.setPet_id(p.getPet().getId());
				adopcionSingle.setPhoneA(p.getPhoneA());
				adopcionSingle.setPhoneB(p.getPhoneB());
				adopcionSingle.setMessage(p.getMessage());
				adopcionSingle.setObservation(p.getObservation());
			sendList.add(adopcionSingle);
			
		}
		return sendList;
	}
	
	//Obtener por id
	public AdoptionDTO getById(long id){
		AdoptionModel p = adoptionRepository.findById(id).get();
		AdoptionDTO adopcionSingle = new AdoptionDTO();
		FechaUtil fechaUtil = new FechaUtil();
			adopcionSingle.setId(p.getId());
			adopcionSingle.setAddress(p.getAddress());
			adopcionSingle.setDistrict(p.getDistrict());

				String fechaRegistro = fechaUtil.getStrindDateFromTimestamp(p.getRegisterDate());
				adopcionSingle.setRegisterDate(fechaRegistro);
				
			adopcionSingle.setPet_id(p.getPet().getId());
			adopcionSingle.setPhoneA(p.getPhoneA());
			adopcionSingle.setPhoneB(p.getPhoneB());
			adopcionSingle.setMessage(p.getMessage());
			adopcionSingle.setObservation(p.getObservation());
		return adopcionSingle;
	}
}
