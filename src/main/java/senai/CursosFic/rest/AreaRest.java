package senai.CursosFic.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import senai.CursosFic.model.Area;
import senai.CursosFic.model.Curso;
import senai.CursosFic.repository.AreaRepository;

@RestController
@RequestMapping("/api/area")
public class AreaRest {
	
	@Autowired
	private AreaRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarArea(@RequestBody Area area){
		
		repository.save(area);
		
		return ResponseEntity.created(URI.create("/" + area.getId())).body(area);
		
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Area> listarArea(){
		
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirArea(@PathVariable("id") Long idArea){
		
		repository.deleteById(idArea);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterarArea(@RequestBody Area area, @PathVariable("id") Long idArea){
		
		if(idArea != area.getId()) {
			throw new RuntimeException("id inválido!");
			
		}
		
		repository.save(area);
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(URI.create("/api/area/"));
		
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}


}
