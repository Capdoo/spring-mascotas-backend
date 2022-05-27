package com.mascotas.app.modules.refugios;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.models.UsuarioModel;
import com.mascotas.app.security.repositories.UsuarioRepository;
import com.mascotas.app.utils.FechaUtil;


@Service
public class RefugioService {

	@Autowired
	RefugioRepository refugioRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	FileUploadService fileUploadService;
	
	public void save(RefugioDTO refugioDTO) throws IOException {
		
		//UsuarioModel usuarioRepresentante = usuarioRepository.findById(refugioDTO.getIdRepresentante()).get();
		UsuarioModel usuarioRepresentante = usuarioRepository.findByDni(refugioDTO.getDniRepresentante()).get();

		
		RefugioModel refugioNuevo = new RefugioModel();
			refugioNuevo.setDireccion(refugioDTO.getDireccion());
			refugioNuevo.setDistrito(refugioDTO.getDistrito());
			refugioNuevo.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
			refugioNuevo.setNombre(refugioDTO.getNombre());
			refugioNuevo.setNumeroAsociados(refugioDTO.getNumeroAsociados());
			refugioNuevo.setNumeroContacto(refugioDTO.getNumeroContacto());
			refugioNuevo.setUsuario(usuarioRepresentante);
			
				String encoded = fileUploadService.obtenerEncoded(refugioDTO.getEncoded());
				byte[] imagen = fileUploadService.convertStringToBytes(encoded);
				String url = fileUploadService.fileUpload(imagen);
				
			refugioNuevo.setLinkImg(url);
						
		refugioRepository.save(refugioNuevo);
	}
	
	
	
	
	//Obtener todos
	public List<RefugioDTO> listar(){
		List<RefugioDTO> listaEnviar = new ArrayList<>();
		List<RefugioModel> listaBD = refugioRepository.findAll();

		
		for(RefugioModel p : listaBD) {
			FechaUtil fechaUtil = new FechaUtil();
			RefugioDTO refugioSingle = new RefugioDTO();

				refugioSingle.setId(p.getId());
				refugioSingle.setDireccion(p.getDireccion());
				refugioSingle.setDistrito(p.getDistrito());

					String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
					refugioSingle.setFechaRegistro(fechaRegistro);
				
					//Nuevo
				//refugioSingle.setIdRepresentante(null);
				refugioSingle.setNombre(p.getNombre());
				refugioSingle.setNumeroAsociados(p.getNumeroAsociados());
				refugioSingle.setNumeroContacto(p.getNumeroContacto());
				refugioSingle.setDniRepresentante(p.getUsuario().getDni());

				refugioSingle.setUrlLink(p.getLinkImg());
				
			listaEnviar.add(refugioSingle);
			
		}
		return listaEnviar;
	}
	
	//Obtener por usuario_id
	public List<RefugioDTO> obtenerPorDniUsuario(String dniUsuario){
		List<RefugioDTO> listaEnviar = new ArrayList<>();
		UsuarioModel usuarioModel = usuarioRepository.findByDni(dniUsuario).get();
		
		List<RefugioModel> listaBD = refugioRepository.findAllByUsuario(usuarioModel);
		
		for(RefugioModel p : listaBD) {
			FechaUtil fechaUtil = new FechaUtil();
			RefugioDTO refugioSingle = new RefugioDTO();

				refugioSingle.setId(p.getId());
				refugioSingle.setDireccion(p.getDireccion());
				refugioSingle.setDistrito(p.getDistrito());

					String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
					refugioSingle.setFechaRegistro(fechaRegistro);
				
				//refugioSingle.setIdRepresentante(p.getUsuario().getId());
				refugioSingle.setNombre(p.getNombre());
				refugioSingle.setNumeroAsociados(p.getNumeroAsociados());
				refugioSingle.setNumeroContacto(p.getNumeroContacto());
				refugioSingle.setDniRepresentante(p.getUsuario().getDni());

				refugioSingle.setUrlLink(p.getLinkImg());
				
			listaEnviar.add(refugioSingle);
			
		}
		return listaEnviar;
	}
	
	//Obtener por id
	public RefugioDTO obtenerPorId(int id){

		RefugioModel p = refugioRepository.findById(id).get();

		FechaUtil fechaUtil = new FechaUtil();
		RefugioDTO refugioSingle = new RefugioDTO();

			refugioSingle.setId(p.getId());
			refugioSingle.setDireccion(p.getDireccion());
			refugioSingle.setDistrito(p.getDistrito());

				String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
				refugioSingle.setFechaRegistro(fechaRegistro);
			
			//refugioSingle.setIdRepresentante(p.getUsuario().getId());
			refugioSingle.setNombre(p.getNombre());
			refugioSingle.setNumeroAsociados(p.getNumeroAsociados());
			refugioSingle.setNumeroContacto(p.getNumeroContacto());
			refugioSingle.setDniRepresentante(p.getUsuario().getDni());

			refugioSingle.setUrlLink(p.getLinkImg());

		return refugioSingle;
	}
}
