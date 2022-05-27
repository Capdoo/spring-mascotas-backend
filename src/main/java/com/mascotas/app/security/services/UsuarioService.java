package com.mascotas.app.security.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.modules.duenos.DuenoRepository;
import com.mascotas.app.security.dto.UsuarioDTO;
import com.mascotas.app.security.models.UsuarioModel;
import com.mascotas.app.security.repositories.UsuarioRepository;


@Service
//Para implementar rollbacks y evitar incoherencia : Concurrencia
@Transactional
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	DuenoRepository duenoRepository;
	
	//Obtener
	public List<UsuarioDTO> listar(){
		List<UsuarioDTO> listaEnviar = new ArrayList<>();

		List<UsuarioModel> listaModels = usuarioRepository.findAll();
		
		for(UsuarioModel p: listaModels) {
			UsuarioDTO usuarioSingle = new UsuarioDTO();

				usuarioSingle.setId(p.getId());
				usuarioSingle.setApellidoMaterno(p.getApellidoMaterno());
				usuarioSingle.setApellidoPaterno(p.getApellidoPaterno());
				usuarioSingle.setDireccion(p.getDireccion());
				usuarioSingle.setDni(p.getDni());
				usuarioSingle.setEmail(p.getEmail());
				usuarioSingle.setNombres(p.getNombre());
				usuarioSingle.setNombreUsuario(p.getNombreUsuario());
				usuarioSingle.setTelefono(p.getTelefono());
				
				
				usuarioSingle.setUrlLink(p.getLinkImg());
				
			listaEnviar.add(usuarioSingle);
		}

		return listaEnviar;
	}
	
	//Para el JWT
	public int obtenerIdPorUsername(String nombreUsuario) {
		UsuarioModel usuarioModel =  usuarioRepository.findByNombreUsuario(nombreUsuario).get();
		return usuarioModel.getId();
	}
	
	//Seguridad
	public Optional<UsuarioModel> getByNombreUsuario(String nombreUsuario){
		return usuarioRepository.findByNombreUsuario(nombreUsuario);
	}
	
	public boolean existsByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.existsByNombreUsuario(nombreUsuario);
	}
	
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
	public boolean existsPorId(int id) {
		return usuarioRepository.existsById(id);
	}
	
	public void save(UsuarioModel usuarioModel) {
		usuarioRepository.save(usuarioModel);
	}
	
}











