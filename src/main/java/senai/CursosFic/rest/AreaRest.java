package senai.CursosFic.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import senai.CursosFic.model.Area;

import senai.CursosFic.repository.AreaRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/area")
public class AreaRest {

	@Autowired
	private AreaRepository repository;

	// API DE CRIAR AREA
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Area area) {


		if(area.getNome().equals("")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}else {

		repository.save(area);

		return ResponseEntity.created(URI.create("/" + area.getId())).body(area);
		}
	}

	// API DE LISTAR AREA
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Area> listar() {

		return repository.findAll();
	}

	// API DE DELETAR A AREA
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR A AREA
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Area area, @PathVariable("id") Long id) {

		if (id != area.getId()) {
			throw new RuntimeException("id não existente!");

		}

		repository.save(area);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/area"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/buscar/{parametro}", method = RequestMethod.GET)
	public List<Area> procurarArea(@PathVariable("parametro") String parametro){
		
		return repository.buscarArea(parametro);
	}


}
