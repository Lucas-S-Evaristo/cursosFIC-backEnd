package senai.CursosFic.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.checkerframework.checker.units.qual.C;
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

import Enum.LogsEnum;
import Enum.TipoLog;
import senai.CursosFic.Email.EmailLog;
import senai.CursosFic.model.Curso;
import senai.CursosFic.model.Erro;
import senai.CursosFic.model.Log;
import senai.CursosFic.repository.CursoRepository;
import senai.CursosFic.repository.FazerLogRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/curso")
public class CursoRest {

	@Autowired
	private CursoRepository repository;

	@Autowired
	private FazerLogRepository fazerLogRepository;

	@Autowired
	public LogRest logRest;
	
	@Autowired
	private EmailLog emailLog;

	// método pra criar cursos
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarCurso(@RequestBody Curso curso, HttpServletRequest request) {

		try {
			
				Log log = new Log();

				logRest.salvarLog(log);
				
				String hora = log.getHora();

				String data = log.getData();

				String nomeUsuario = log.getNomeUsuario();

				String nifUsuario = log.getNifUsuario();

				String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " cadastrou um curso em " + data
						+ " ás " + hora;

				//emailLog.mandarLog("prateste143@gmail.com", mensagem);

				log.setLogsEnum(LogsEnum.CADASTROU);

				log.setTipoLog(TipoLog.CURSO);

				log.setInformacaoCadastro(curso.getNome());

				// salva o curso através desse método que faz a criação automática da sigla do
				// curso
				codigoCurso(curso);

				log.setSiglaCurso(curso.getSigla());

				fazerLogRepository.save(log);

				return ResponseEntity.created(URI.create("/" + curso.getId())).body(curso);
			
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		}

	}

	// método pra listagem de curso
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Curso> listarCurso() {

		return repository.findAll();
	}

	// método pra excluir algum curso
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirCurso(@PathVariable("id") Long idCurso, @RequestBody String justificativa) {
		try {

			justificativa = justificativa.substring(1, justificativa.length() - 1);

			Log log = new Log();

			logRest.salvarLog(log);

			Curso curso = repository.findById(idCurso).get();
			
			String hora = log.getHora();

			String data = log.getData();

			String nomeUsuario = log.getNomeUsuario();

			String nifUsuario = log.getNifUsuario();

			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " deletou um curso em " + data
					+ " ás " + hora;

			//emailLog.mandarLog("prateste143@gmail.com", mensagem);

			log.setInformacaoCadastro(curso.getNome());

			log.setSiglaCurso(curso.getSigla());

			log.setLogsEnum(LogsEnum.DELETOU);

			log.setTipoLog(TipoLog.CURSO);

			log.setJustificativa(justificativa);

			fazerLogRepository.save(log);

			repository.deleteById(idCurso);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		return ResponseEntity.noContent().build();

	}

	// método pra alterar algum curso
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterarCurso(@RequestBody Curso curso, @PathVariable("id") Long idCurso,
			HttpServletRequest request) {

		if (idCurso.longValue() != curso.getId().longValue()) {
			throw new RuntimeException("id inválidado");

		}

		Log log = new Log();

		logRest.salvarLog(log);

		log.setLogsEnum(LogsEnum.ALTEROU);
		
		String hora = log.getHora();

		String data = log.getData();

		String nomeUsuario = log.getNomeUsuario();

		String nifUsuario = log.getNifUsuario();

		String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " deletou um curso em " + data
				+ " ás " + hora;

		//emailLog.mandarLog("prateste143@gmail.com", mensagem);

		log.setTipoLog(TipoLog.CURSO);

		log.setInformacaoCadastro(curso.getNome());

		log.setJustificativa(curso.getJustificativa());

		codigoCurso(curso);

		log.setSiglaCurso(curso.getSigla());

		fazerLogRepository.save(log);

		repository.save(curso);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/curso/"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	// método que busca parâmetros do curso
	@RequestMapping(value = "/buscarCurso/", method = RequestMethod.POST)
	public ResponseEntity<?> procurarCurso(@RequestBody String parametro) {
		List<Curso> cursos = repository.buscarCurso(parametro.replace("\"", ""));

		return ResponseEntity.ok().body(cursos);
	}

	// método que cria automáticamente a sigla do curso
	public ResponseEntity<Curso> codigoCurso(@RequestBody Curso curso) {

		String semPreposicao = curso.getNome().replace(" de ", " ");

		semPreposicao = semPreposicao.replace(" da ", " ");

		String[] nomeEspaco = semPreposicao.split(" ");

		if (nomeEspaco.length == 1) {

			if (nomeEspaco[0].length() == 1) {
				
				String sigla = nomeEspaco[0].substring(0, 1);
				curso.setSigla(sigla.toUpperCase());

				return ResponseEntity.ok(repository.save(curso));

			}else if(nomeEspaco[0].length() == 2) {

				String sigla = nomeEspaco[0].substring(0, 2);
				curso.setSigla(sigla.toUpperCase());

				return ResponseEntity.ok(repository.save(curso));
				
			}else {
				
				String sigla = nomeEspaco[0].substring(0, 3);
				curso.setSigla(sigla.toUpperCase());

				return ResponseEntity.ok(repository.save(curso));
			}

		} else if (nomeEspaco.length == 2) {

			if (nomeEspaco[1].length() == 1) {

				String sigla = nomeEspaco[0].substring(0, 2) + nomeEspaco[1].substring(0, 1);
				curso.setSigla(sigla.toUpperCase());

				return ResponseEntity.ok(repository.save(curso));
				
			}else {
				
				String sigla = nomeEspaco[0].substring(0, 2) + nomeEspaco[1].substring(0, 2);
				curso.setSigla(sigla.toUpperCase());

				return ResponseEntity.ok(repository.save(curso));
			}

		}else if(nomeEspaco.length == 3) {

				String sigla = nomeEspaco[0].substring(0, 2) + nomeEspaco[1].substring(0, 1) + nomeEspaco[2].substring(0, 1);
				curso.setSigla(sigla.toUpperCase());

				return ResponseEntity.ok(repository.save(curso));
			
		}else {
			
			String sigla = nomeEspaco[0].substring(0, 1) + nomeEspaco[1].substring(0, 1) + nomeEspaco[2].substring(0, 1) + nomeEspaco[3].substring(0, 1);
			curso.setSigla(sigla.toUpperCase());

			return ResponseEntity.ok(repository.save(curso));
				
			}
		}		
	}
