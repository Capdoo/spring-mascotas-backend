package com.mascotas.app.modules.duenos;

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
import com.mascotas.app.security.services.UsuarioService;

@RestController
@RequestMapping("/dueños")
public class DueñoController {

	@Autowired
	DuenoService duenoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping("/registrar")
	public ResponseEntity<Object> guardar(@RequestBody DuenoDTO duenoDTO){
		
		try {
 			if(duenoService.existsDuenoPorUsuarioId(duenoDTO.getUsuario_id())){
				int a = 10;
				return new ResponseEntity<Object>(new MensajeDTO("El usuario ya tiene un dueño asignado"), HttpStatus.BAD_REQUEST);
			}
			
			if(!(usuarioService.existsPorId(duenoDTO.getUsuario_id()))){
				int b = 11;
				return new ResponseEntity<Object>(new MensajeDTO("El usuario aún no existe"), HttpStatus.BAD_REQUEST);
			}
			int c = 12;
			
			duenoService.save(duenoDTO);
			return new ResponseEntity<Object>(new MensajeDTO("Registrado con exito"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/obtener")
	public ResponseEntity<Object> obtener(){
		
		try {
			List<DuenoDTO> listaDuenos = duenoService.listar();
			return new ResponseEntity<Object>(listaDuenos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MensajeDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
