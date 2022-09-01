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

import senai.CursosFic.model.FormaPagamento;
import senai.CursosFic.repository.FormaPagamentoRepository;

@RestController
@RequestMapping("/api/formaPagamento")
public class FormaPagamentoRest {

	@Autowired
	private FormaPagamentoRepository repository;

	// API DE CRIAR FORMAS DE PAGAMENTO
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody FormaPagamento formaPagamento) {

		repository.save(formaPagamento);

		return ResponseEntity.created(URI.create("/" + formaPagamento.getId())).body(formaPagamento);
	}

	// API DE LISTAR AS FORMAS DE PAGAMENTO
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<FormaPagamento> listar() {

		return repository.findAll();
	}

	// API DE DELETAR AS FORMAS DE PAGAMENTO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR AS FORMAS DE PAGAMENTO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody FormaPagamento formaPagamento, @PathVariable("id") Long id) {

		if (id != formaPagamento.getId()) {
			throw new RuntimeException("id não existente!");

		}

		repository.save(formaPagamento);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/formaPagamento"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
