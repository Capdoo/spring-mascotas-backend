package com.mascotas.app.modules.refugios;

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
@RequestMapping("/refugios")

public class RefugioController {

	@Autowired
	RefugioService refugioService;
	
	@PostMapping("/registrar")
	public ResponseEntity<Object> guardar(@RequestBody RefugioDTO refugioDTO){
		
		try {
			refugioService.save(refugioDTO);
			return new ResponseEntity<Object>(new MensajeDTO("Registrado con exito"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/obtener")
	public ResponseEntity<Object> obtener(){
		
		try {
			List<RefugioDTO> listaRefugios = refugioService.listar();
			return new ResponseEntity<Object>(listaRefugios, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/obtener/usuario")
	public ResponseEntity<Object> obtenerPorDniUsuario(@RequestBody StringDTO stringDTO){
		
		try {
			List<RefugioDTO> listaRefugios = refugioService.obtenerPorDniUsuario(stringDTO.getData());
			return new ResponseEntity<Object>(listaRefugios, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/obtenerPorId")
	public ResponseEntity<Object> obtenerPorId(@RequestBody StringDTO stringDTO){
		
		try {
			int idRefugio = Integer.parseInt(stringDTO.getData());
			RefugioDTO busqueda = refugioService.obtenerPorId(idRefugio);
			return new ResponseEntity<Object>(busqueda, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
}
