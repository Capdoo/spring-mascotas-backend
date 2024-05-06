package com.mascotas.app.modules.shelters;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.models.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class ShelterServiceImpl implements ShelterService{

    @Autowired
    ShelterRepository shelterRepository;

    @Autowired
    FileUploadService fileUploadService;

    @Override
    public List<ShelterEntity> listAllShelters() {
        return shelterRepository.findAll();
    }

    @Override
    public ShelterEntity createShelter(ShelterDto shelterDto, UserEntity userEntity) {

        //UsuarioModel usuarioRepresentante = usuarioRepository.findById(refugioDTO.getIdRepresentante()).get();
//        UserEntity usuarioRepresentante = userRepository.findByDni(shelterDTO.getDniMainPartner()).get();

        ShelterEntity refugioNuevo = new ShelterEntity();
        refugioNuevo.setAddress(shelterDto.getAddress());
        refugioNuevo.setDistrict(shelterDto.getDistrict());
        refugioNuevo.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        refugioNuevo.setName(shelterDto.getName());
        refugioNuevo.setNumberOfPartners(shelterDto.getNumberOfPartners());
        refugioNuevo.setContactNumber(shelterDto.getContactNumber());
        //change
//        refugioNuevo.setPartners(null);

        try {
            String encoded = fileUploadService.obtenerEncoded(shelterDto.getEncoded());
            byte[] imagen = fileUploadService.convertEncodedToBytes(encoded);
            String url = fileUploadService.fileUpload(imagen);
            refugioNuevo.setLinkImg(url);
        }catch (Exception e){
            e.printStackTrace();
        }

        return shelterRepository.save(refugioNuevo);
    }

    @Override
    public ShelterEntity readShelter(Long id) {
        return shelterRepository.findById(id).orElse(null);
    }

    @Override
    public ShelterEntity updateShelter(Long id) {
        return null;
    }

    @Override
    public ShelterEntity deleteShelter(Long id) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    /*
  @Autowired
	ShelterRepository shelterRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
    FileUploadService fileUploadService;

	public void saveShelter(ShelterDto shelterDTO) throws IOException {

		//UsuarioModel usuarioRepresentante = usuarioRepository.findById(refugioDTO.getIdRepresentante()).get();
		UserEntity usuarioRepresentante = userRepository.findByDni(shelterDTO.getDniMainPartner()).get();

		ShelterEntity refugioNuevo = new ShelterEntity();
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
	public List<ShelterDto> listAll(){
		List<ShelterDto> listaEnviar = new ArrayList<>();
		List<ShelterEntity> listaBD = shelterRepository.findAll();

		for(ShelterEntity p : listaBD) {
			FechaUtil fechaUtil = new FechaUtil();
			ShelterDto refugioSingle = new ShelterDto();

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

	//Obtener por id
	public ShelterDto getById(long id){

		ShelterEntity p = shelterRepository.findById(id).get();

		FechaUtil fechaUtil = new FechaUtil();
		ShelterDto refugioSingle = new ShelterDto();

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





    */

}
