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

import senai.CursosFic.model.Turma;
import senai.CursosFic.repository.TurmaRepository;

@RestController
@RequestMapping("/api/turma")
public class TurmaRest {

	@Autowired
	private TurmaRepository repository;

	// API DE CRIAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Turma turma) {
		/*// faz a verificação de campos vazio
				if (curso.getNome().equals("") || curso.getObjetivo().equals("") || curso.getPreRequisito().equals("")
						|| curso.getSigla().equals("") || curso.getConteudoProgramatico().equals("") || curso.getNivel() == null
						|| curso.getArea() == null || curso.getTipoAtendimento() == null) {
					// envia um status de erro ao front
					return ResponseEntity.status(HttpStatus.CONFLICT).build();

				} else if (curso.getValor() == null || curso.getCargaHoraria() == 0) {
					return ResponseEntity.status(HttpStatus.CONFLICT).build();

				} else {
					repository.save(curso);

					return ResponseEntity.created(URI.create("/" + curso.getId())).body(curso);
				}*/
		
		
		// LOGICA DAS DATAS DAS TURMAS AQUIIIIIIIIIIIIIIIIIIIII

		repository.save(turma);

		return ResponseEntity.created(URI.create("/" + turma.getId())).body(turma);
	}

	//API DE LISTAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Turma> listar() {

		return repository.findAll();
	}

	// API DE DELETAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long idTurma) {

		repository.deleteById(idTurma);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Turma turma, @PathVariable("id") Long idTurma) {

		if (idTurma != turma.getId()) {
			throw new RuntimeException("id não existente!");

		}

		repository.save(turma);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/instrutor"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	@RequestMapping(value = "/buscarTurma/{parametro}", method = RequestMethod.GET)
	public List<Turma> procurarCurso(@PathVariable("parametro") String parametro) {

		return repository.buscarTurma(parametro);
	}

}
