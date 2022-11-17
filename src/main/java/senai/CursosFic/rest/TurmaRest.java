package senai.CursosFic.rest;

import java.net.URI;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import senai.CursosFic.Email.JavaMailApp;
import senai.CursosFic.model.Log;
import senai.CursosFic.model.Turma;
import senai.CursosFic.repository.CursoRepository;
import senai.CursosFic.repository.FazerLogRepository;
import senai.CursosFic.repository.HorarioRepository;
import senai.CursosFic.repository.TurmaRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/turma")
public class TurmaRest {
	
	@Autowired
	private FazerLogRepository fazerLogRepository;
	
	@Autowired
	public LogRest logRest;

	@Autowired
	private TurmaRepository repository;

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private CursoRepository repositoryCurso;

	@Autowired
	private JavaMailApp javaMailApp = new JavaMailApp();

	// API DE CRIAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Turma turma,  HttpServletRequest request) {

		// javaMailApp.mandarEmail(turma);

		// puxa a data atual
		Calendar hoje = Calendar.getInstance();

		// validações de campos vazios tipos numéricos
		if (turma.getQtdMatriculas() == 0 || turma.getNumMaxVagas() == 0 || turma.getNumMinVagas() == 0
			) {

			System.out.println("VALIDAÇÂO 1");

			// retorna uma resposta de erro
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();

			// validações de campos vazios de outras entidades
		} else if (turma.getHorarioInicio() == null || turma.getHorarioTermino() == null || turma.getCurso() == null
				|| turma.getAmbiente() == null || turma.getInstrutor() == null) {

			System.out.println("VALIDAÇÂO 2");

			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();

			// validações dos campos restantes
		} else if (turma.getPeriodo() == null || turma.getStatus() == null) {

			System.out.println("VALIDAÇÂO 3");

			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();

		}else if (turma.getNumMinVagas() > turma.getNumMaxVagas()) {

			System.out.println("VALIDAÇÂO 4");

			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).build();

		} else {

			String horario1 = horarioRepository.findById(turma.getHorarioInicio().getId()).get().getHorario();

			String horario2 = horarioRepository.findById(turma.getHorarioTermino().getId()).get().getHorario();

			// convertendo a hora de inicio para LocalTime
			LocalTime horarioInicial = LocalTime.parse(horario1);

			// convertendo a hora de término para LocalTime
			LocalTime horarioFinal = LocalTime.parse(horario2);

			// CRIANDO O CODIGO DA TURMA
			Calendar calendar = Calendar.getInstance();
			int anoData = calendar.get(Calendar.YEAR);
			int size = repository.procurarPorAno(anoData).size();
			int numero = size + 1;

			// turma.atualizarData();
			String periodo = turma.getPeriodo().getInicial();

			// pegando o id curso do obj turma, e procurando o curso pelo id informado
			turma.setCurso(repositoryCurso.findById(turma.getCurso().getId()).get());
			String nivel = turma.getCurso().getNivel().getInicial();
			String nomeCurso = turma.getCurso().getNome().substring(0, 1);

			// string com o codigo completo
			String codigo = periodo + nivel + nomeCurso + numero;

			turma.setCodigo(codigo);

			// metodo que atualiza as datas
			turma.atualizarData();

			// verificando se a data de inicio não é depois da data de término
			if (turma.getDataInicio().after(turma.getDataTermino())) {

				return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();

				// verificando se a data de inicio não é igual da data de término
			} else if (turma.getDataInicio().equals(turma.getDataTermino())) {

				return ResponseEntity.status(HttpStatus.CONFLICT).build();

				// verificando se a data de inicio não é antes do dia atual
			} else if (turma.getDataInicio().before(hoje)) {

				System.out.println("ANTES HOJE");

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

				// verificando se a hora de inicio não é depois que a hora de término
			} else if (horarioInicial.isAfter(horarioFinal)) {

				System.out.println("IF HORARIOSSSS!!!!!!");

				return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();

			} else if (horarioInicial.equals(horarioFinal)) {

				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

			} else {
				
				
				
				Log log = new Log();
				
				logRest.salvarLog(log);
				
				log.setLogsEnum(LogsEnum.CADASTROU);
				
				log.setTipoLog(TipoLog.TURMA);
				
				log.setInformacaoCadastro(codigo);
				
				fazerLogRepository.save(log);

				// salvar a turma
				repository.save(turma);

				return ResponseEntity.created(URI.create("/" + turma.getId())).body(turma);

			}

		}
	}

	// API DE LISTAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Turma> listar() {

		return repository.findAll();
	}

	// API DE DELETAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long idTurma,  HttpServletRequest request) {
		
		Log log = new Log();
		
		logRest.salvarLog(log);
		
		log.setLogsEnum(LogsEnum.DELETOU);
		
		log.setTipoLog(TipoLog.TURMA);
		
		fazerLogRepository.save(log);

		repository.deleteById(idTurma);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Turma turma, @PathVariable("id") Long idTurma,  HttpServletRequest request) {

		if (idTurma != turma.getId()) {
			throw new RuntimeException("id não existente!");

		}
		// puxa a data atual
		Calendar hoje = Calendar.getInstance();

		// validações de campos vazios tipos numéricos
		if (turma.getNumMaxVagas() == 0 || turma.getNumMinVagas() == 0) {

			System.out.println("VALIDAÇÂO 1");

			// retorna uma resposta de erro
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();

			// validações de campos vazios de outras entidades
		} else if (turma.getHorarioInicio() == null || turma.getHorarioTermino() == null || turma.getCurso() == null
				|| turma.getAmbiente() == null || turma.getInstrutor() == null) {

			System.out.println("VALIDAÇÂO 2");

			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();

			// validações dos campos restantes
		} else if (turma.getPeriodo() == null || turma.getStatus() == null) {

			System.out.println("VALIDAÇÂO 3");

			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();

		} else {

			String horario1 = horarioRepository.findById(turma.getHorarioInicio().getId()).get().getHorario();

			String horario2 = horarioRepository.findById(turma.getHorarioTermino().getId()).get().getHorario();

			// convertendo a hora de inicio para LocalTime
			LocalTime horarioInicial = LocalTime.parse(horario1);

			// convertendo a hora de término para LocalTime
			LocalTime horarioFinal = LocalTime.parse(horario2);

			if (horarioInicial.isAfter(horarioFinal)) {

				System.out.println("IF HORARIOSSSS!!!!!!");

				return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();

			} else if (horarioInicial.equals(horarioFinal)) {

				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

			} else {

				Calendar calendar = Calendar.getInstance();
				int anoData = calendar.get(Calendar.YEAR);
				int size = repository.procurarPorAno(anoData).size();
				int numero = size;

				// turma.atualizarData();
				String periodo = turma.getPeriodo().getInicial();

				// pegando o id curso do obj turma, e procurando o curso pelo id informado
				turma.setCurso(repositoryCurso.findById(turma.getCurso().getId()).get());
				String nivel = turma.getCurso().getNivel().getInicial();
				String nomeCurso = turma.getCurso().getNome().substring(0, 1);

				// string com o codigo completo
				String codigo = periodo + nivel + nomeCurso + numero;

				turma.setCodigo(codigo);
				
				Double valorCurso = repositoryCurso.findById(turma.getCurso().getId()).get().getValor();
				
				Double cargaHorariaCurso = repositoryCurso.findById(turma.getCurso().getId()).get().getCargaHoraria();
				
				Double podeSerLancado = turma.getQtdMatriculas() * valorCurso / cargaHorariaCurso; 
				
				System.out.println("pode ser lançada " + podeSerLancado);
				
				System.out.println("valor Curso " + valorCurso);
				
				System.out.println("Carga Horaria Curso " + cargaHorariaCurso);
				
				if(podeSerLancado >= 190) {
					
					System.out.println("IF 1");
					
					turma.setPodeSerLancado(true);
					
					Log log = new Log();
					
					logRest.salvarLog(log);
					
					log.setLogsEnum(LogsEnum.ALTEROU);
					
					log.setTipoLog(TipoLog.TURMA);
					
					log.setInformacaoCadastro(codigo);
					
					fazerLogRepository.save(log);

					repository.save(turma);

					HttpHeaders headers = new HttpHeaders();

					headers.setLocation(URI.create("/api/turma"));

					return new ResponseEntity<Void>(headers, HttpStatus.OK);
					
					
				}else {
					
					System.out.println("IF 2");
					
					Log log = new Log();
					
					logRest.salvarLog(log);
					
					log.setLogsEnum(LogsEnum.ALTEROU);
					
					log.setTipoLog(TipoLog.TURMA);
					
					log.setInformacaoCadastro(codigo);
					
					fazerLogRepository.save(log);

					repository.save(turma);

					HttpHeaders headers = new HttpHeaders();

					headers.setLocation(URI.create("/api/turma"));

					return new ResponseEntity<Void>(headers, HttpStatus.OK);
				}
				
				
			}
		}
	}

	@RequestMapping(value = "/buscarTurmaAno/{parametro}", method = RequestMethod.GET)
	public List<Turma> procurarTurmaAno(@PathVariable("parametro") int parametro) {

		return repository.procurarPorAno(parametro);

	}

	@RequestMapping(value = "/findByAll/{p}")
	public Iterable<Turma> findByAll(@PathVariable("p") Calendar parametro) {

		return repository.buscarTurmaDois(parametro);
	}

}
