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

import senai.CursosFic.model.DiaSemana;
import senai.CursosFic.repository.DiaSemanaRepository;

@RestController
@RequestMapping("/api/diaSemana")
public class DiaSemanaRest {

	@Autowired
	private DiaSemanaRepository repository;

	// API DE CRIAR OS DIAS
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody DiaSemana diaSemana) {

		repository.save(diaSemana);

		return ResponseEntity.created(URI.create("/" + diaSemana.getId())).body(diaSemana);
	}

	// API DE LISTAR OS DIAS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<DiaSemana> listar() {

		return repository.findAll();
	}

	// API DE DELETAR O DIA
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR O DIA
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody DiaSemana diaSemana, @PathVariable("id") Long id) {

		if (id != diaSemana.getId()) {
			throw new RuntimeException("id não existente!");

		}

		repository.save(diaSemana);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/diaSemana"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
