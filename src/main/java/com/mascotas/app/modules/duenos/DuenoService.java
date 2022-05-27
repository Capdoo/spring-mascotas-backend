package com.mascotas.app.modules.duenos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.security.models.UsuarioModel;
import com.mascotas.app.security.repositories.UsuarioRepository;
import com.mascotas.app.security.services.UsuarioService;
import com.mascotas.app.utils.FechaUtil;

@Service
public class DuenoService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	DuenoRepository duenoRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	public void save(DuenoDTO duenoDTO) {
		
		//Obteniendo al Usuario
		UsuarioModel usuarioObtenido = usuarioRepository.findById(duenoDTO.getUsuario_id()).get();
		
		DuenoModel dueñoModel = new DuenoModel();
			dueñoModel.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
			dueñoModel.setHistorial_id(duenoDTO.getHistorial_id());
			dueñoModel.setNumeroMascotas(duenoDTO.getNumero_mascotas());
			dueñoModel.setRate(duenoDTO.getRate());
			dueñoModel.setUsuario(usuarioObtenido);
			
		duenoRepository.save(dueñoModel);
	}
	
	public List<DuenoDTO> listar(){
		List<DuenoDTO> listaVacia = new ArrayList<>();
		
		//Traer los datos
		List<DuenoModel> listaModelsBD = duenoRepository.findAll();
		

		for(DuenoModel p : listaModelsBD) {
			FechaUtil fechaUtil = new FechaUtil();
			DuenoDTO duenoSingle = new DuenoDTO();
			String fechaRegistro = fechaUtil.convertirFecha(p.getFechaRegistro());
			
				duenoSingle.setId(p.getId());
				duenoSingle.setFechaRegistro(fechaRegistro);
				duenoSingle.setHistorial_id(p.getHistorial_id());
				duenoSingle.setNumero_mascotas(p.getNumeroMascotas());
				duenoSingle.setRate(p.getRate());
				duenoSingle.setUsuario_id(p.getUsuario().getId());
				
			listaVacia.add(duenoSingle);
		}
		
		return listaVacia;
	}
	
	//Util
	public boolean existsDuenoPorUsuarioId(int idUsuario) {
		boolean res = false;
		
		if(usuarioService.existsPorId(idUsuario)) {
			UsuarioModel usuario = usuarioRepository.findById(idUsuario).get();
			
			Optional<DuenoModel> duenoSupuesto = duenoRepository.findByUsuario(usuario);
			
			if(duenoSupuesto.isPresent()) {
				res = true;
			}
		}else {
			res = false;
		}
		return res;
		
	}
}





