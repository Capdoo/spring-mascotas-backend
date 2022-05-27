package com.mascotas.app.modules.adopciones;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.modules.busquedas.BusquedaRepository;
import com.mascotas.app.modules.mascotas.MascotaModel;
import com.mascotas.app.modules.mascotas.MascotaRepository;
import com.mascotas.app.utils.FechaUtil;

@Service
public class AdopcionService {


	@Autowired
	BusquedaRepository busquedaRepository;
	
	@Autowired
	MascotaRepository mascotaRepository;
	
	@Autowired
	AdopcionRepository adopcionRepository;

	public void save(AdopcionDTO adopcionDTO) {
		
		FechaUtil fechaUtil = new FechaUtil();
		MascotaModel mascotaSeleccionada = mascotaRepository.findById(adopcionDTO.getMascota_id()).get();
		
		AdopcionModel adopcionNueva = new AdopcionModel();
			adopcionNueva.setDireccion(adopcionDTO.getDireccion());
			adopcionNueva.setDistrito(adopcionDTO.getDistrito());
				
			adopcionNueva.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
			adopcionNueva.setMascota(mascotaSeleccionada);
				
			adopcionNueva.setTelefonoA(adopcionDTO.getTelefonoA());
			adopcionNueva.setTelefonoB(adopcionDTO.getTelefonoB());
				
			adopcionNueva.setMensaje(adopcionDTO.getMensaje());
			adopcionNueva.setMascota(mascotaSeleccionada);
			
			adopcionNueva.setObservacion(adopcionDTO.getObservacion());
	
			adopcionRepository.save(adopcionNueva);
	}
	
	//Obtener todos
	public List<AdopcionDTO> listar(){
		List<AdopcionDTO> listaEnviar = new ArrayList<>();
		List<AdopcionModel> listaBD = adopcionRepository.findAll();

		
		for(AdopcionModel p : listaBD) {
			FechaUtil fechaUtil = new FechaUtil();
			AdopcionDTO adopcionSingle = new AdopcionDTO();

				adopcionSingle.setId(p.getId());
				
				adopcionSingle.setDireccion(p.getDireccion());
				adopcionSingle.setDistrito(p.getDistrito());
				
					
				String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
				adopcionSingle.setFechaRegistro(fechaRegistro);
				adopcionSingle.setMascota_id(p.getMascota().getId());
				
				adopcionSingle.setTelefonoA(p.getTelefonoA());
				adopcionSingle.setTelefonoB(p.getTelefonoB());
				
				adopcionSingle.setMensaje(p.getMensaje());
				adopcionSingle.setObservacion(p.getObservacion());
				
			listaEnviar.add(adopcionSingle);
			
		}
		return listaEnviar;
	}
	
	//Obtener por mascota_id
	public List<AdopcionDTO> obtenerPorMascotaId(int mascotaId){
		List<AdopcionDTO> listaEnviar = new ArrayList<>();
		MascotaModel mascotaSeleccionada = mascotaRepository.findById(mascotaId).get();
		
		List<AdopcionModel> listaBD = adopcionRepository.findAllByMascota(mascotaSeleccionada);
		
		for(AdopcionModel p : listaBD) {
			AdopcionDTO adopcionSingle = new AdopcionDTO();
			FechaUtil fechaUtil = new FechaUtil();

				adopcionSingle.setId(p.getId());
				
				adopcionSingle.setDireccion(p.getDireccion());
				adopcionSingle.setDistrito(p.getDistrito());

					
					String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
					adopcionSingle.setFechaRegistro(fechaRegistro);
					
				adopcionSingle.setMascota_id(p.getMascota().getId());
				adopcionSingle.setTelefonoA(p.getTelefonoA());
				
				adopcionSingle.setTelefonoB(p.getTelefonoB());
				adopcionSingle.setMensaje(p.getMensaje());
				
				adopcionSingle.setObservacion(p.getObservacion());
			listaEnviar.add(adopcionSingle);
			
		}
		return listaEnviar;
	}
	
	//Obtener por id
	public AdopcionDTO obtenerPorId(int id){
		
		AdopcionModel p = adopcionRepository.findById(id).get();
		
		AdopcionDTO adopcionSingle = new AdopcionDTO();
		FechaUtil fechaUtil = new FechaUtil();

			adopcionSingle.setId(p.getId());
			
			adopcionSingle.setDireccion(p.getDireccion());
			adopcionSingle.setDistrito(p.getDistrito());

				
				String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
				adopcionSingle.setFechaRegistro(fechaRegistro);
				
			adopcionSingle.setMascota_id(p.getMascota().getId());
			adopcionSingle.setTelefonoA(p.getTelefonoA());
			
			adopcionSingle.setTelefonoB(p.getTelefonoB());
			adopcionSingle.setMensaje(p.getMensaje());
			
			adopcionSingle.setObservacion(p.getObservacion());

		return adopcionSingle;
	}

}
