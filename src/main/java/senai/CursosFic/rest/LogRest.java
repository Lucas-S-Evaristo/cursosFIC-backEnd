package senai.CursosFic.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import senai.CursosFic.model.Log;
import senai.CursosFic.model.Usuario;
import senai.CursosFic.repository.FazerLogRepository;

@RestController
@RequestMapping("/api/log")
public class LogRest {
	
	@Autowired
	private FazerLogRepository fazerLogRepository;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar (@RequestBody Log log){
		
		Usuario usuario = new Usuario();
		
		System.out.println(usuario);
		
		
		fazerLogRepository.save(log);
		
		return ResponseEntity.created(URI.create("/" + log.getId())).body(log);
	}
	
}
