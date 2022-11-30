package senai.CursosFic.rest;

import java.net.URI;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import senai.CursosFic.model.LinhaDoTempo;
import senai.CursosFic.repository.LinhaDoTempoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/linhaDoTempo")
public class LinhaDoTempoRest {

	@Autowired
	private LinhaDoTempoRepository repository;

	// API DE LISTAR OS USUARIOS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<LinhaDoTempo> lista() {

		return repository.findAll();
	}

	// API DE AVANÇAR AÇÕES
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> addAction(@PathVariable("id") Long id) {

		LinhaDoTempo linhaDoTempo = new LinhaDoTempo();

		linhaDoTempo = repository.findById(id).get();

		if (id != linhaDoTempo.getId()) {
			throw new RuntimeException("id não existente!");
		}

		Calendar hoje = Calendar.getInstance();

		linhaDoTempo.setDataRealizada(hoje);

		repository.save(linhaDoTempo);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/linhaDoTempo"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
