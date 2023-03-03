package com.mascotas.app.modules.shelters;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import com.mascotas.app.security.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;

import com.mascotas.app.utils.FechaUtil;

@Service
public class ShelterService {

	@Autowired
	ShelterRepository shelterRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
    FileUploadService fileUploadService;
	@Autowired
    UserServiceImp userServiceImp;

	public void saveShelter(ShelterDTO shelterDTO) throws IOException {
		
		//UsuarioModel usuarioRepresentante = usuarioRepository.findById(refugioDTO.getIdRepresentante()).get();
		UserEntity usuarioRepresentante = userRepository.findByDni(shelterDTO.getDniMainPartner()).get();

		ShelterModel refugioNuevo = new ShelterModel();
			refugioNuevo.setAddress(shelterDTO.getAddress());
			refugioNuevo.setDistrict(shelterDTO.getDistrict());
			refugioNuevo.setRegisterDate(new Timestamp(System.currentTimeMillis()));
			refugioNuevo.setName(shelterDTO.getName());
			refugioNuevo.setNumberOfPartners(shelterDTO.getNumberOfPartners());
			refugioNuevo.setContactNumber(shelterDTO.getContactNumber());
			//change
			refugioNuevo.setPartners(null);

			try {
				String encoded = fileUploadService.obtenerEncoded(shelterDTO.getEncoded());
				byte[] imagen = fileUploadService.convertEncodedToBytes(encoded);
				String url = fileUploadService.fileUpload(imagen);
				refugioNuevo.setLinkImg(url);
			}catch (Exception e){
				e.printStackTrace();
			}

		shelterRepository.save(refugioNuevo);
	}
	

	//Obtener todos
	public List<ShelterDTO> listAll(){
		List<ShelterDTO> listaEnviar = new ArrayList<>();
		List<ShelterModel> listaBD = shelterRepository.findAll();

		for(ShelterModel p : listaBD) {
			FechaUtil fechaUtil = new FechaUtil();
			ShelterDTO refugioSingle = new ShelterDTO();

				refugioSingle.setId(p.getId());
				refugioSingle.setAddress(p.getAddress());
				refugioSingle.setDistrict(p.getDistrict());

					String fechaRegistro = fechaUtil.getStrindDateFromTimestamp(p.getRegisterDate());
					refugioSingle.setRegisterDate(fechaRegistro);
				
					//Nuevo
				//refugioSingle.setIdRepresentante(null);
				refugioSingle.setName(p.getName());
				refugioSingle.setNumberOfPartners(p.getNumberOfPartners());
				refugioSingle.setContactNumber(p.getContactNumber());

				//refugioSingle.setDniMainPartner(p.getUser().getDni());
				refugioSingle.setDniMainPartner(null);

				refugioSingle.setUrlLink(p.getLinkImg());
				
			listaEnviar.add(refugioSingle);
			
		}
		return listaEnviar;
	}

	/*
	//Obtener por dni user
	public List<ShelterDTO> getByDniUser(String dniUser){
		List<ShelterDTO> listSend = new ArrayList<>();
		UserEntity usuarioModel = userRepository.findByDni(dniUser).get();
		
		List<ShelterModel> listaDB = shelterRepository.findAllByUser(usuarioModel);
		
		for(ShelterModel p : listaDB) {
			FechaUtil fechaUtil = new FechaUtil();
			ShelterDTO refugioSingle = new ShelterDTO();

				refugioSingle.setId(p.getId());
				refugioSingle.setAddress(p.getAddress());
				refugioSingle.setDistrict(p.getDistrict());

					String fechaRegistro = fechaUtil.convertirFecha(p.getRegisterDate());
					refugioSingle.setRegisterDate(fechaRegistro);
				
				//refugioSingle.setIdRepresentante(p.getUsuario().getId());
				refugioSingle.setName(p.getName());
				refugioSingle.setNumberOfPartners(p.getNumberOfPartners());
				refugioSingle.setContactNumber(p.getContactNumber());

				//refugioSingle.setDniMainPartner(p.getUser().getDni());
				refugioSingle.setDniMainPartner(null);

				refugioSingle.setUrlLink(p.getLinkImg());

			listSend.add(refugioSingle);
			
		}
		return listSend;
	}*/
	
	//Obtener por id
	public ShelterDTO getById(long id){

		ShelterModel p = shelterRepository.findById(id).get();

		FechaUtil fechaUtil = new FechaUtil();
		ShelterDTO refugioSingle = new ShelterDTO();

			refugioSingle.setId(p.getId());
			refugioSingle.setAddress(p.getAddress());
			refugioSingle.setDistrict(p.getDistrict());

				String fechaRegistro = fechaUtil.getStrindDateFromTimestamp(p.getRegisterDate());
				refugioSingle.setRegisterDate(fechaRegistro);
			
			//refugioSingle.setIdRepresentante(p.getUsuario().getId());
			refugioSingle.setName(p.getName());
			refugioSingle.setNumberOfPartners(p.getNumberOfPartners());
			refugioSingle.setContactNumber(p.getContactNumber());

		//refugioSingle.setDniMainPartner(p.getUser().getDni());
		refugioSingle.setDniMainPartner(null);

			refugioSingle.setUrlLink(p.getLinkImg());

		return refugioSingle;
	}

	//Util
	/*
	public boolean existsRefugioByUserId(long idUsuario){
		boolean res = false;

		if(userServiceImp.existsPorId(idUsuario)) {
			UserEntity usuario = userRepository.findById(idUsuario).get();

			List<ShelterModel> shelterModel = shelterRepository.findAllByUser(usuario);

			if(!shelterModel.isEmpty()) {
				res = true;
			}
		}
		return res;
	}*/
}
