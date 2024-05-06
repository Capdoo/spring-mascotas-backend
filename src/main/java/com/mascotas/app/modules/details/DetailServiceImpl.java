package com.mascotas.app.modules.details;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl implements DetailService{
	@Autowired
	DetailRepository detailRepository;

	@Override
	public List<DetailEntity> listAllDetails() {
		return detailRepository.findAll();
	}

	@Override
	public DetailEntity createDetail(DetailDto detailDTO) {
		DetailEntity detailEntity = DetailEntity.builder()
				.species(detailDTO.getSpecies())
				.breed(detailDTO.getBreed())
				.build();
		return detailRepository.save(detailEntity);
	}

	@Override
	public DetailEntity readDetail(Long id) {
		return detailRepository.findById(id).orElse(null);
	}

	@Override
	public DetailEntity updateDetail(DetailDto detailDTO) {
		DetailEntity detailEntity = readDetail(detailDTO.getId());
		if (detailEntity == null) return null;

		detailEntity.setSpecies(detailDTO.getSpecies());
		detailEntity.setBreed(detailDTO.getBreed());
		return detailRepository.save(detailEntity);
	}

	@Override
	public DetailEntity deleteDetail(DetailDto detailDTO) {
		DetailEntity detailEntity = readDetail(detailDTO.getId());
		if (detailEntity == null) return null;

		detailEntity.setState("DELETED");
		return detailRepository.save(detailEntity);
	}

	@Override
	public List<DetailEntity> readAllBySpecies(DetailDto detailDTO) {
		return detailRepository.findAllBySpecies(detailDTO.getSpecies());
	}
}