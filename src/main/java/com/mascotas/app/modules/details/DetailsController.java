package com.mascotas.app.modules.details;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.app.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mascotas.app.dto.MessageDTO;
import com.mascotas.app.dto.StringDTO;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/details")
public class DetailsController {

	@Autowired
	DetailServiceImpl detailServiceImpl;
	
	@GetMapping
	public ResponseEntity<Object> readAll(){
		List<DetailModel> listDetails = detailServiceImpl.listAllDetails();
		List<DetailDTO> listDetailsDto = listDetails.stream()
				.map(this::convertDetailEntityToDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDetailsDto);
	}

	@PostMapping
	public ResponseEntity<Object> createDetail(@RequestBody DetailDTO detailDTO, BindingResult bindingResult) throws JsonProcessingException {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
		}
		DetailModel detailModel = detailServiceImpl.createDetail(detailDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.convertDetailEntityToDto(detailModel));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> readDetail(@PathVariable(value = "id") Long id){
		DetailModel detailModel = detailServiceImpl.readDetail(id);
		if (detailModel == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(this.convertDetailEntityToDto(detailModel));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateDetail(@PathVariable(value = "id") Long id, @RequestBody DetailDTO detailDTO){
		DetailModel detailModel = detailServiceImpl.readDetail(id);
		if (detailModel == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
		}
		detailDTO.setId(id);
		DetailModel detailUpdate = detailServiceImpl.updateDetail(detailDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertDetailEntityToDto(detailUpdate));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteDetail(@PathVariable(value = "id") Long id){
		DetailModel detailModel = detailServiceImpl.readDetail(id);
		if (detailModel == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail Not Found");
		}
		DetailDTO detailDTO = DetailDTO.builder()
				.id(id)
				.build();
		DetailModel detailDelete = detailServiceImpl.deleteDetail(detailDTO);
		return ResponseEntity.status(HttpStatus.OK).body(this.convertDetailEntityToDto(detailDelete));
	}
	
	@GetMapping("/species")
	public ResponseEntity<Object> readSpecies(){
		List<DetailModel> listSpecies = detailServiceImpl.listAllDetails();
		List<String> listAllSpecies = listSpecies.stream()
				.map(DetailModel::getSpecies)
				.collect(Collectors.toList());
		return ResponseEntity.accepted().body(listAllSpecies);
	}
	
	@GetMapping("/read/breed")
	public ResponseEntity<Object> readBreedsBySpecies(@RequestParam("species") String especie){
		
		try {
			List<StringDTO> listaRazas = detailServiceImpl.getBreedsBySpecies(especie);
			return new ResponseEntity<Object>(listaRazas, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new MessageDTO("Hubo un problema"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	public DetailDTO convertDetailEntityToDto(DetailModel detailModel){
		return DetailDTO.builder()
				.id(detailModel.getId())
				.species(detailModel.getSpecies())
				.breed(detailModel.getBreed())
				.build();
	}


	private String formatMessage(BindingResult bindingResult) throws JsonProcessingException {
		List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
				.map(err -> {
					Map<String, String> error = new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		ErrorMessageUtil errorMessage = ErrorMessageUtil.builder()
				.code("01")
				.messages(errors).build();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(errorMessage);

		}catch (JsonProcessingException e){
			e.printStackTrace();
		}
		return jsonString;
	}
}











