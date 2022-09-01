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
import senai.CursosFic.model.Ambiente;
import senai.CursosFic.repository.AmbienteRepository;

@RestController
@RequestMapping("/api/ambiente")
public class AmbienteRest {

	@Autowired
	private AmbienteRepository repository;

	// API DE CRIAR OS AMBIENTES
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Ambiente ambiente) {

		repository.save(ambiente);

		return ResponseEntity.created(URI.create("/" + ambiente.getId())).body(ambiente);
	}

	// API DE LISTAR OS AMBIENTES
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Ambiente> listar() {

		return repository.findAll();
	}

	// API DE ALTERAR O AMBIENTE
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR O AMBIENTE
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Ambiente ambiente, @PathVariable("id") Long idUsuario) {

		if (idUsuario != ambiente.getId()) {
			throw new RuntimeException("id não existente!");

		}

		repository.save(ambiente);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/ambiente"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
