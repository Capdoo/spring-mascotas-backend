package com.mascotas.app.modules.owners;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import com.mascotas.app.security.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.utils.FechaUtil;

@Service
public class OwnerService {

	@Autowired
	UserRepository usuarioRepository;
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
    UserServiceImp userServiceImp;
	
	public void saveOwner(OwnerDTO ownerDTO) {
		
		//Obteniendo al Usuario
		int user_id = (int) ownerDTO.getUser_id();
		UserEntity userEntity = usuarioRepository.findById(user_id).get();
		
		OwnerEntity ownerEntity = new OwnerEntity();
			ownerEntity.setRegisterDate(new Timestamp(System.currentTimeMillis()));
			ownerEntity.setHistorial_id(ownerDTO.getHistorial_id());
			ownerEntity.setNumberOfPets(ownerDTO.getNumberOfPets());
			ownerEntity.setRate(ownerDTO.getRate());
			ownerEntity.setUser(userEntity);
			
		ownerRepository.save(ownerEntity);
	}
	
	public List<OwnerDTO> listAll(){
		List<OwnerDTO> sendList = new ArrayList<>();
		
		//Traer los datos
		List<OwnerEntity> listaModelsBD = ownerRepository.findAll();

		for(OwnerEntity p : listaModelsBD) {
			FechaUtil fechaUtil = new FechaUtil();
			OwnerDTO duenoSingle = new OwnerDTO();
			String fechaRegistro = fechaUtil.getStrindDateFromTimestamp(p.getRegisterDate());
			
				duenoSingle.setId(p.getId());
				duenoSingle.setRegisterDate(fechaRegistro);
				duenoSingle.setHistorial_id(p.getHistorial_id());
				duenoSingle.setNumberOfPets(p.getNumberOfPets());
				duenoSingle.setRate(p.getRate());
				duenoSingle.setUser_id(p.getUser().getId());

			sendList.add(duenoSingle);
		}
		
		return sendList;
	}
	
	//Util
	public boolean existsOwnerByUserId(long idUsuario) {
		boolean res = false;
		
		if(userServiceImp.existsById(idUsuario)) {
			UserEntity usuario = usuarioRepository.findById(idUsuario).get();
			
			Optional<OwnerEntity> duenoSupuesto = ownerRepository.findByUser(usuario);
			
			if(duenoSupuesto.isPresent()) {
				res = true;
			}
		}
		return res;
	}

	public OwnerEntity readOwnerById(Long id){
		return ownerRepository.findById(id).orElse(null);
	}

}





