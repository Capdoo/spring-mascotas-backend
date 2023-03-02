package com.mascotas.app.modules.details;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.dto.StringDTO;

@Service
public class DetailServiceImpl implements DetailService{

	@Autowired
	DetailRepository detailRepository;

	@Override
	public List<DetailModel> listAllDetails() {
		return detailRepository.findAll();
	}

	@Override
	public DetailModel createDetail(DetailDTO detailDTO) {
		DetailModel detailModel = DetailModel.builder()
				.species(detailDTO.getSpecies())
				.breed(detailDTO.getBreed())
				.build();
		return detailRepository.save(detailModel);
	}

	@Override
	public DetailModel readDetail(Long id) {
		return detailRepository.findById(id).orElse(null);
	}

	@Override
	public DetailModel updateDetail(DetailDTO detailDTO) {
		DetailModel detailModel = readDetail(detailDTO.getId());
		if (detailModel == null) return null;

		detailModel.setSpecies(detailDTO.getSpecies());
		detailModel.setBreed(detailDTO.getBreed());
		return detailRepository.save(detailModel);
	}

	@Override
	public DetailModel deleteDetail(DetailDTO detailDTO) {
		DetailModel detailModel = readDetail(detailDTO.getId());
		if (detailModel == null) return null;

		detailModel.setState("DELETED");
		return detailRepository.save(detailModel);
	}

	@Override
	public List<DetailModel> readBySpecies(DetailDTO detailDTO) {
		return detailRepository.findAllBySpecies(detailDTO.getSpecies());
	}

	@Override
	public Boolean existsById(Long id) {
		return detailRepository.existsById(id);
	}




	public List<StringDTO> getAllSpecies(){
		List<StringDTO> listaEspecies = new ArrayList<>();
		List<DetailModel> detallesBD = detailRepository.findAll();
		
		
		for(DetailModel p : detallesBD) {
			StringDTO stringDTOSingle = new StringDTO();
			
			if(!(existsSpeciesInList(listaEspecies, p.getSpecies()))) {
				stringDTOSingle.setData(p.getSpecies());
				
				listaEspecies.add(stringDTOSingle);
			}
		}
		
		return listaEspecies;
	}
	
	public boolean existsSpeciesInList(List<StringDTO> listaEspecies, String especie) {

		boolean res = false;
		
		for(StringDTO p : listaEspecies) {
			if(especie.equals(p.getData().toString())) {
				res = true;
			}
		}
		return res;
	}
	
	//Obtener Razas Por Especie
	public List<StringDTO> getBreedsBySpecies(String especie){
		List<StringDTO> listaRazas = new ArrayList<>();

		List<DetailModel> detallesBD = detailRepository.findAllBySpecies(especie);
		
		for(DetailModel p : detallesBD) {
			StringDTO stringSingle = new StringDTO();
				stringSingle.setData(p.getBreed());
			listaRazas.add(stringSingle);
		}
		
		return listaRazas;
	}














}










