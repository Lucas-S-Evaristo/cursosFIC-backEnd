package senai.CursosFic.rest;

import java.net.URI;
import java.util.List;

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

import senai.CursosFic.model.Instrutor;
import senai.CursosFic.repository.InstrutorRepository;

@RestController
@RequestMapping("/api/instrutor")
public class InstrutorRest {

	@Autowired
	private InstrutorRepository repository;

	// API DE CRIAR OS Instrutores
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Instrutor instrutor) {

		repository.save(instrutor);

		return ResponseEntity.created(URI.create("/" + instrutor.getId())).body(instrutor);
	}

//API DE LISTAR OS Instrutores
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Instrutor> listar() {

		return repository.findAll();
	}

	// API DE ALTERAR instrutor
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long idInstrutor) {

		repository.deleteById(idInstrutor);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR instrutor
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Instrutor instrutor, @PathVariable("id") Long idInstrutor) {

		if (idInstrutor != instrutor.getId()) {
			throw new RuntimeException("id não existente!");

		}

		repository.save(instrutor);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/instrutor"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	// API BUSCAR INSTRUTO
		  @RequestMapping(value = "/buscar/{nome}",  method = RequestMethod.GET )
		  public List<Instrutor>buscarInstrutor(@PathVariable("nome") String nome){
			  return repository.buscarInstrutor(nome);
		  }

}
