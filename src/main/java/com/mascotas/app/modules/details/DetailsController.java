package com.mascotas.app.modules.details;

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
@RequestMapping("/details")
public class DetailsController {

	@Autowired
	DetailService detailService;
	
	@GetMapping("/read")
	public ResponseEntity<Object> readAll(){
		
		try {
			List<DetailDTO> listDetails = detailService.listAll();
			return new ResponseEntity<Object>(listDetails, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("There has been a problem"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@GetMapping("/read/species")
	public ResponseEntity<Object> readSpecies(){
		
		try {
			List<StringDTO> listaEspecies = detailService.getAllSpecies();
			return new ResponseEntity<Object>(listaEspecies, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/read/breed")
	public ResponseEntity<Object> readBreedsBySpecies(@RequestParam("species") String especie){
		
		try {
			List<StringDTO> listaRazas = detailService.getBreedsBySpecies(especie);
			return new ResponseEntity<Object>(listaRazas, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}











