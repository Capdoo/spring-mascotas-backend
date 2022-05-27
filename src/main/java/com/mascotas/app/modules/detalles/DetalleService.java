package com.mascotas.app.modules.detalles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.dto.StringDTO;

@Service
public class DetalleService {

	@Autowired
	DetalleRepository detalleRepository;
	
	//No existe registrar, son datos fijos
	
	//Obtener todos
	public List<DetalleDTO> listar(){
		List<DetalleDTO> listaDetalles = new ArrayList<>();
		List<DetalleModel> detallesBD = detalleRepository.findAll();
		

		for(DetalleModel p : detallesBD) {
			//Single
			DetalleDTO detalleSingle = new DetalleDTO();
				detalleSingle.setId(p.getId());
				detalleSingle.setEspecie(p.getEspecie());
				detalleSingle.setRaza(p.getRaza());
			
			listaDetalles.add(detalleSingle);
		}
		
		return listaDetalles;
	}
	
	public List<StringDTO> obtenerEspecies(){
		List<StringDTO> listaEspecies = new ArrayList<>();
		List<DetalleModel> detallesBD = detalleRepository.findAll();
		
		
		for(DetalleModel p : detallesBD) {
			StringDTO stringDTOSingle = new StringDTO();
			
			if(!(existeEspecieEnLista(listaEspecies, p.getEspecie()))) {
				stringDTOSingle.setData(p.getEspecie());
				
				listaEspecies.add(stringDTOSingle);
			}
		}
		
		return listaEspecies;
	}
	
	public boolean existeEspecieEnLista(List<StringDTO> listaEspecies, String especie) {
		boolean res = false;
		
		for(StringDTO p : listaEspecies) {
			if(especie.equals(p.getData().toString())) {
				res = true;
			}
		}
		return res;
	}
	
	//Obtener Razas Por Especie
	public List<StringDTO> obtenerRazasPorEspecie(String especie){
		List<StringDTO> listaRazas = new ArrayList<>();

		List<DetalleModel> detallesBD = detalleRepository.findAllByEspecie(especie);
		
		for(DetalleModel p : detallesBD) {
			StringDTO stringSingle = new StringDTO();
				stringSingle.setData(p.getRaza());	
			listaRazas.add(stringSingle);
		}
		
		return listaRazas;
	}
	
	
}










