package com.mascotas.app.modules.adopciones;

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
import com.mascotas.app.dto.StringDTO;


@RestController
@RequestMapping("/adopciones")
public class AdopcionController {

	@Autowired
	AdopcionService adopcionService;
	
	@PostMapping("/registrar")
	public ResponseEntity<Object> guardar(@RequestBody AdopcionDTO adopcionDTO){
		
		try {
			adopcionService.save(adopcionDTO);
			return new ResponseEntity<Object>(new MensajeDTO("Registrado con exito"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/obtener")
	public ResponseEntity<Object> obtener(){
		
		try {
			List<AdopcionDTO> listaAdopciones = adopcionService.listar();
			return new ResponseEntity<Object>(listaAdopciones, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/obtener/mascota")
	public ResponseEntity<Object> obtenerPorMascotaId(@RequestBody StringDTO stringDTO){
		
		try {
			int idMascota = Integer.parseInt(stringDTO.getData());
			List<AdopcionDTO> listaAdopciones = adopcionService.obtenerPorMascotaId(idMascota);
			return new ResponseEntity<Object>(listaAdopciones, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/obtenerPorId")
	public ResponseEntity<Object> obtenerPorId(@RequestBody StringDTO stringDTO){
		
		try {
			int idMascota = Integer.parseInt(stringDTO.getData());
			AdopcionDTO adopcion = adopcionService.obtenerPorId(idMascota);
			return new ResponseEntity<Object>(adopcion, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}
