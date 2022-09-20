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

import com.fasterxml.jackson.annotation.JsonCreator;

import senai.CursosFic.model.Curso;
import senai.CursosFic.repository.CursoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/curso")
public class CursoRest {

	@Autowired
	private CursoRepository repository;

	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE  )
	public ResponseEntity<Object> criarCurso(@RequestBody Curso curso){
		try {
		//faz a verificação de campos vazio
		if(curso.getNome().equals("") || curso.getObjetivo().equals("") || curso.getPreRequisito().equals("")
				|| curso.getConteudoProgramatico().equals("")) {
			//envia um status de erro ao front
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}else if(curso.getArea() == null || curso.getTipoAtendimento() == null || curso.getNivel() == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
		}else if(curso.getValor().equals("") || curso.getCargaHoraria() == 0) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
	}else {
		
		
			codigoCurso(curso);
			return ResponseEntity.created(URI.create("/" + curso.getId())).body(curso);
	}
		} catch (Exception e) {
			
			
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		}

	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Curso> listarCurso() {

		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirCurso(@PathVariable("id") Long idCurso) {

		repository.deleteById(idCurso);

		return ResponseEntity.noContent().build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterarCurso(@RequestBody Curso curso, @PathVariable("id") Long idCurso) {

		if (idCurso != curso.getId()) {
			throw new RuntimeException("id inválidado");

		}

		repository.save(curso);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/curso/"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/buscarCurso/{parametro}", method = RequestMethod.GET)
	public List<Curso> procurarCurso(@PathVariable("parametro") String parametro) {

		return repository.buscarCurso(parametro);
	}
	
	public Curso codigoCurso(@RequestBody Curso curso){
		
		String[] verificarEspaco = curso.getNome().split(" ");
		
		
		if(verificarEspaco.length > 1) {
			System.out.println("caiu no primeiro if");
			
			String sigla = verificarEspaco[0].substring(0, 1) + verificarEspaco[1].substring(0,1) + verificarEspaco[2].substring(0,2) ;
			curso.setSigla(sigla.toUpperCase());	
			
			return repository.save(curso);
			
		}else if(verificarEspaco.length > 2){
			System.out.println("caiu no segundo if");
			
			String sigla = verificarEspaco[0].substring(0, 1) + verificarEspaco[1].substring(0,1) + verificarEspaco[2].substring(0,1) + verificarEspaco[3].substring(0,1) ;
			curso.setSigla(sigla.toUpperCase());	
			
			return repository.save(curso);
			
		}else {
			System.out.println("Caiu no terceiro if");
			
			String parte = curso.getNome().substring(0, 3);
			
			curso.setSigla(parte);
			
		return repository.save(curso);
		}
		
	}

}
