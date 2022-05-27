package com.mascotas.app.modules.detalles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.app.dto.MensajeDTO;
import com.mascotas.app.dto.StringDTO;


@RestController
@RequestMapping("/detalles")
public class DetalleController {

	
	@Autowired
	DetalleService detalleService;
	
	@GetMapping("/obtener")
	public ResponseEntity<Object> obtener(){
		
		try {
			List<DetalleDTO> listaDetalles = detalleService.listar();
			return new ResponseEntity<Object>(listaDetalles, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@GetMapping("/especies")
	public ResponseEntity<Object> obtenerEspecies(){
		
		try {
			List<StringDTO> listaEspecies = detalleService.obtenerEspecies();
			return new ResponseEntity<Object>(listaEspecies, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/razas")
	public ResponseEntity<Object> obtenerRazasPorEspecie(@RequestParam("especie") String especie){
		
		try {
			List<StringDTO> listaRazas = detalleService.obtenerRazasPorEspecie(especie);
			return new ResponseEntity<Object>(listaRazas, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}











