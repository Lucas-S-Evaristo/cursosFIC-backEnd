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

import senai.CursosFic.model.Curso;
import senai.CursosFic.repository.CursoRepository;

@RestController
@RequestMapping("/api/curso")
public class CursoRest {
	
	@Autowired
	private CursoRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE  )
	public ResponseEntity<Object> criarCurso(@RequestBody Curso curso){
		
		repository.save(curso);
		
		return ResponseEntity.created(URI.create("/" + curso.getId())).body(curso);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Curso> listarCurso(){
		
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirCurso(@PathVariable("id") Long idCurso){
		
		repository.deleteById(idCurso);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterarCurso(@RequestBody Curso curso, @PathVariable("id") Long idCurso){
		
		if(idCurso != curso.getId()) {
			throw new RuntimeException("id inválidado");
			
		}
		
		repository.save(curso);
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(URI.create("/api/curso/"));
		
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
