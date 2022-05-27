package com.mascotas.app.modules.mascotas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.app.dto.MensajeDTO;


@RestController
@RequestMapping("/mascotas")
public class MascotaController {
	@Autowired
	MascotaService mascotaService;
	
	@PostMapping("/registrar")
	public ResponseEntity<Object> guardar(@RequestBody MascotaDTO mascotaDTO){
		
		try {
			mascotaService.save(mascotaDTO);
			return new ResponseEntity<Object>(new MensajeDTO("Registrado con exito"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/obtener")
	public ResponseEntity<Object> obtener(){
		
		try {
			List<MascotaDTO> listaMascotas = mascotaService.listar();
			return new ResponseEntity<Object>(listaMascotas, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
