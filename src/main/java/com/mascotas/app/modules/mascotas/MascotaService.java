package com.mascotas.app.modules.mascotas;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.modules.detalles.DetalleModel;
import com.mascotas.app.modules.detalles.DetalleRepository;
import com.mascotas.app.modules.duenos.DuenoModel;
import com.mascotas.app.modules.duenos.DuenoRepository;
import com.mascotas.app.utils.FechaUtil;
import com.mascotas.app.utils.StringUtil;


@Service
public class MascotaService {

	
	@Autowired
	MascotaRepository mascotaRepository;
	
	@Autowired
	DuenoRepository duenoRepository;
	
	@Autowired
	DetalleRepository detalleRepository;
	
	@Autowired
	FileUploadService fileUploadService;
	
	public void save(MascotaDTO mascotaDTO) throws IOException {
		
		DuenoModel duenoMascota = duenoRepository.findById(mascotaDTO.getIdDueno()).get();
		FechaUtil fechaUtil = new FechaUtil();
		MascotaModel mascotaNueva = new MascotaModel();
		
			mascotaNueva.setNombre(mascotaDTO.getNombre());
			mascotaNueva.setGenero(mascotaDTO.getGenero());
				Timestamp timeStamp = fechaUtil.obtenerTimeStampDeFecha(mascotaDTO.getFechaNacimiento());
				mascotaNueva.setFechaNacimiento(timeStamp);
			mascotaNueva.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
			mascotaNueva.setColor(mascotaDTO.getColor());
			mascotaNueva.setCaracteristica(mascotaDTO.getCaracteristica());
			mascotaNueva.setTamano(mascotaDTO.getTamaño());
			mascotaNueva.setDueno(duenoMascota);

			if(mascotaDTO.getRazaEspecifica() == null) {
				DetalleModel detalleMascota = detalleRepository.findByEspecieAndRaza(
						mascotaDTO.getEspecie(),
						mascotaDTO.getRaza()).get();
				mascotaNueva.setDetalle(detalleMascota);
				mascotaNueva.setRazaEspecifica(null);
	
			}else {
				mascotaNueva.setRazaEspecifica(mascotaDTO.getEspecie()+"#"+mascotaDTO.getRazaEspecifica());
				mascotaNueva.setDetalle(null);
			}
			
		mascotaNueva.setRefugio(null);
		
				String encoded = fileUploadService.obtenerEncoded(mascotaDTO.getEncoded());
				byte[] imagen = fileUploadService.convertStringToBytes(encoded);
				String url = fileUploadService.fileUpload(imagen);
				
				mascotaNueva.setLinkImg(url);
			
		mascotaRepository.save(mascotaNueva);
	}
	
	//Lista general
	public List<MascotaDTO> listar(){
		List<MascotaDTO> listaMascotas = new ArrayList<>();
		List<MascotaModel> listaBD = mascotaRepository.findAll();
		
		for(MascotaModel p : listaBD) {
			FechaUtil fechaUtil = new FechaUtil();
			StringUtil stringUtil = new StringUtil();
			MascotaDTO mascotaSingle = new MascotaDTO();

				mascotaSingle.setId(p.getId());
			   	mascotaSingle.setNombre(p.getNombre());
				mascotaSingle.setGenero(p.getGenero());
				
		   			String fechaNacimiento = fechaUtil.convertirFecha(p.getFechaNacimiento());
		   			mascotaSingle.setFechaNacimiento(fechaNacimiento);
				
			   		String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
					mascotaSingle.setFechaRegistro(fechaRegistro);
					
				mascotaSingle.setColor(p.getColor());
				mascotaSingle.setCaracteristica(p.getCaracteristica());	
				mascotaSingle.setTamaño(p.getTamano());
				mascotaSingle.setIdDueno(p.getDueno().getId());

				if(p.getRazaEspecifica()==null) {
					mascotaSingle.setEspecie(p.getDetalle().getEspecie());
					mascotaSingle.setRaza(p.getDetalle().getRaza());
					mascotaSingle.setRazaEspecifica(null);
					mascotaSingle.setIdDetalle(p.getDetalle().getId());
				}else {
					mascotaSingle.setEspecie(stringUtil.obtenerEspecieToken(p.getRazaEspecifica()));
					mascotaSingle.setRaza(null);
					mascotaSingle.setRazaEspecifica(stringUtil.obtenerRazaToken(p.getRazaEspecifica()));
				}
				
				mascotaSingle.setUrlLink(p.getLinkImg());
				
			listaMascotas.add(mascotaSingle);
		}
		
		return listaMascotas;
	}
	
	//Lista por id de dueño	
 	
}
