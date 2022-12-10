package senai.CursosFic.rest;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import Enum.AcoesLinhaDoTempo;
import Enum.LogsEnum;
import Enum.TipoLog;
import senai.CursosFic.Email.EmailLog;
import senai.CursosFic.model.LinhaDoTempo;
import senai.CursosFic.model.Log;
import senai.CursosFic.model.Parametro;
import senai.CursosFic.model.Turma;
import senai.CursosFic.repository.CursoRepository;
import senai.CursosFic.repository.HorarioRepository;
import senai.CursosFic.repository.LinhaDoTempoRepository;
import senai.CursosFic.repository.LogRepository;
import senai.CursosFic.repository.ParametroRepository;
import senai.CursosFic.repository.TurmaRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/turma")
public class TurmaRest {

	@Autowired
	private LogRepository fazerLogRepository;

	@Autowired
	public LogRest logRest;

	@Autowired
	private TurmaRepository repository;

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private CursoRepository repositoryCurso;

	@Autowired
	private ParametroRepository parametroRepository;

	@Autowired
	private EmailLog emailLog;

	@Autowired
	private LinhaDoTempoRepository linhaDoTempoRepository;

	// API DE CRIAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Turma turma, HttpServletRequest request) {

		// javaMailApp.mandarEmail(turma);

		if (turma.getNumMinVagas() > turma.getNumMaxVagas()) {

			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}

		// puxa a data atual
		Calendar hoje = Calendar.getInstance();

		hoje.add(Calendar.DAY_OF_WEEK, -1);

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

		turma.atualizarData();
		String periodo = turma.getPeriodo().getInicial();

		// pegando o id curso do obj turma, e procurando o curso pelo id informado
		turma.setCurso(repositoryCurso.findById(turma.getCurso().getId()).get());
		String nivel = turma.getCurso().getNivel().getInicial();
		String nomeCurso = turma.getCurso().getSigla();


		// string com o codigo completo
		String codigo = periodo + nivel + "-" + nomeCurso + "-" + numero;

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

			String hora = log.getHora();

			String data = log.getData();

			String nomeUsuario = log.getNomeUsuario();

			String nifUsuario = log.getNifUsuario();

			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario
					+ " cadastrou uma turma com o seguinte código: " + codigo + " em " + data + " ás " + hora;

			// emailLog.mandarLog("prateste143@gmail.com", mensagem);

			log.setMensagem(mensagem);

			log.setLogsEnum(LogsEnum.CADASTROU);

			log.setTipoLog(TipoLog.TURMA);

			log.setCodigoTurma(codigo);

			fazerLogRepository.save(log);

			// salvar a turma
			repository.save(turma);

			LinhaDoTempo linhaDoTempo1 = new LinhaDoTempo();
			linhaDoTempo1.setTurma(turma);
			linhaDoTempo1.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.DATA_LIM_INSC);
			linhaDoTempo1.setDataPrevista(turma.getDataLimInscricao());
			linhaDoTempo1.setIndice(1);

			LinhaDoTempo linhaDoTempo4 = new LinhaDoTempo();
			linhaDoTempo4.setTurma(turma);
			linhaDoTempo4.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.VERI_PCDS);
			linhaDoTempo4.setDataPrevista(turma.getVerificarPCDs());
			linhaDoTempo4.setIndice(2);
			

			LinhaDoTempo linhaDoTempo3 = new LinhaDoTempo();
			linhaDoTempo3.setTurma(turma);
			linhaDoTempo3.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.COBRAR_ENTREG_DOC);
			linhaDoTempo3.setDataPrevista(turma.getCobrarEntregaDocum());
			linhaDoTempo3.setIndice(3);

			LinhaDoTempo linhaDoTempo10 = new LinhaDoTempo();
			linhaDoTempo10.setTurma(turma);
			linhaDoTempo10.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.CONFIRM_TUR);
			linhaDoTempo10.setDataPrevista(turma.getConfirmarTurma());
			linhaDoTempo10.setIndice(4);

			LinhaDoTempo linhaDoTempo5 = new LinhaDoTempo();
			linhaDoTempo5.setTurma(turma);
			linhaDoTempo5.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.GERAR_DIAR_ELETR);
			linhaDoTempo5.setDataPrevista(turma.getGerarDiarioEletr());
			linhaDoTempo5.setIndice(5);

			LinhaDoTempo linhaDoTempo6 = new LinhaDoTempo();
			linhaDoTempo6.setTurma(turma);
			linhaDoTempo6.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.MONTAR_KIT_TURMA);
			linhaDoTempo6.setDataPrevista(turma.getMontarKitTurma());
			linhaDoTempo6.setIndice(6);

			LinhaDoTempo linhaDoTempo12 = new LinhaDoTempo();
			linhaDoTempo12.setTurma(turma);
			linhaDoTempo12.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.VER_QUEM_FALT);
			linhaDoTempo12.setDataPrevista(turma.getVerifQuemFaltouPrimDia());
			linhaDoTempo12.setIndice(7);
			
			LinhaDoTempo linhaDoTempo7 = new LinhaDoTempo();
			linhaDoTempo7.setTurma(turma);
			linhaDoTempo7.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.INICIAR_TURM);
			linhaDoTempo7.setDataPrevista(turma.getIniciarTurma());
			linhaDoTempo7.setIndice(8);

			LinhaDoTempo linhaDoTempo8 = new LinhaDoTempo();
			linhaDoTempo8.setTurma(turma);
			linhaDoTempo8.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.MATRCUL_DEFINITIV);
			linhaDoTempo8.setDataPrevista(turma.getMatriculaDefinitiva());
			linhaDoTempo8.setIndice(9);

			LinhaDoTempo linhaDoTempo9 = new LinhaDoTempo();
			linhaDoTempo9.setTurma(turma);
			linhaDoTempo9.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.ENCERRAR_TURMA);
			linhaDoTempo9.setDataPrevista(turma.getEncerrarTurma());
			linhaDoTempo9.setIndice(10);
			
			LinhaDoTempo linhaDoTempo11 = new LinhaDoTempo();
			linhaDoTempo11.setTurma(turma);
			linhaDoTempo11.setAcoesLinhaDoTempo(AcoesLinhaDoTempo.ESCANER_DOC);
			linhaDoTempo11.setDataPrevista(turma.getEscanearDocum());
			linhaDoTempo11.setIndice(11);
			
			
			linhaDoTempoRepository.save(linhaDoTempo1);

			linhaDoTempoRepository.save(linhaDoTempo4);

			linhaDoTempoRepository.save(linhaDoTempo3);

			linhaDoTempoRepository.save(linhaDoTempo10);

			linhaDoTempoRepository.save(linhaDoTempo5);

			linhaDoTempoRepository.save(linhaDoTempo6);

			linhaDoTempoRepository.save(linhaDoTempo12);

			linhaDoTempoRepository.save(linhaDoTempo7);

			linhaDoTempoRepository.save(linhaDoTempo8);

			linhaDoTempoRepository.save(linhaDoTempo9);

			linhaDoTempoRepository.save(linhaDoTempo11);

			return ResponseEntity.created(URI.create("/" + turma.getId())).body(turma);

		}

	}

	// API DE LISTAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Turma> listar() {

		return repository.findAll();
	}

	// API DE DELETAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long idTurma, @RequestBody String justificativa) {

		justificativa = justificativa.substring(1, justificativa.length() - 1);

		Log log = new Log();

		logRest.salvarLog(log);

		log.setLogsEnum(LogsEnum.DELETOU);

		Turma turma = repository.findById(idTurma).get();

		log.setCodigoTurma(turma.getCodigo());

		String hora = log.getHora();

		String data = log.getData();

		String nomeUsuario = log.getNomeUsuario();

		String nifUsuario = log.getNifUsuario();

		String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario
				+ " deletou uma turma com o seguinte código: " + turma.getCodigo() + " em " + data + " ás " + hora;
		// emailLog.mandarLog("prateste143@gmail.com", mensagem);

		log.setMensagem(mensagem);

		log.setJustificativa(justificativa);

		log.setTipoLog(TipoLog.TURMA);

		fazerLogRepository.save(log);

		repository.deleteById(idTurma);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Turma turma, @PathVariable("id") Long idTurma) {

		turma.atualizarData();
		List<LinhaDoTempo> list = linhaDoTempoRepository.findByTurmaId(turma.getId());

		if (idTurma != turma.getId()) {
			throw new RuntimeException("id não existente!");

		}

		if (turma.getNumMinVagas() > turma.getNumMaxVagas()) {

			return ResponseEntity.status(405).build();

		} else if (turma.getQtdMatriculas() > turma.getNumMaxVagas()) {

			return ResponseEntity.status(409).build();
		}
		// puxa a data atual
		Calendar hoje = Calendar.getInstance();

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

			turma.atualizarData();
			String periodo = turma.getPeriodo().getInicial();

			// pegando o id curso do obj turma, e procurando o curso pelo id informado
			turma.setCurso(repositoryCurso.findById(turma.getCurso().getId()).get());
			String nivel = turma.getCurso().getNivel().getInicial();
			String nomeCurso = turma.getCurso().getSigla();


			// string com o codigo completo
			String codigo = periodo + nivel + "-" + nomeCurso + "-" + numero;

			turma.setCodigo(codigo);
		
			Log log = new Log();

			logRest.salvarLog(log);

			String hora = log.getHora();

			String data = log.getData();

			log.setJustificativa(turma.getJustificativa());

			String nomeUsuario = log.getNomeUsuario();

			String nifUsuario = log.getNifUsuario();

			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario
					+ " alterou uma turma com o seguinte código: " + codigo + " em " + data + " ás " + hora;

			// emailLog.mandarLog("prateste143@gmail.com", mensagem);

			log.setMensagem(mensagem);

			log.setLogsEnum(LogsEnum.ALTEROU);

			log.setTipoLog(TipoLog.TURMA);

			log.setCodigoTurma(codigo);

			pontoEquilibrio(turma, idTurma);

			// procurado a ação pelo id e modificando ela 
			LinhaDoTempo doTempo1 = linhaDoTempoRepository.findByIndice(1, idTurma);
			doTempo1.setDataPrevista(turma.getDataLimInscricao());
	

			LinhaDoTempo doTempo2 = linhaDoTempoRepository.findByIndice(2, idTurma);
			doTempo2.setDataPrevista(turma.getVerificarPCDs());
	

			LinhaDoTempo doTempo3 = linhaDoTempoRepository.findByIndice(3, idTurma);
			doTempo3.setDataPrevista(turma.getCobrarEntregaDocum());
	

			LinhaDoTempo doTempo4 = linhaDoTempoRepository.findByIndice(4, idTurma);
			doTempo4.setDataPrevista(turma.getConfirmarTurma());
	

			LinhaDoTempo doTempo5 = linhaDoTempoRepository.findByIndice(5, idTurma);
			doTempo5.setDataPrevista(turma.getGerarDiarioEletr());
	

			LinhaDoTempo doTempo6 = linhaDoTempoRepository.findByIndice(6, idTurma);
			doTempo6.setDataPrevista(turma.getMontarKitTurma());


			LinhaDoTempo doTempo7 = linhaDoTempoRepository.findByIndice(7, idTurma);
			doTempo7.setDataPrevista(turma.getVerifQuemFaltouPrimDia());
	

			LinhaDoTempo doTempo8 = linhaDoTempoRepository.findByIndice(8, idTurma);
			doTempo8.setDataPrevista(turma.getIniciarTurma());
		

			LinhaDoTempo doTempo9 = linhaDoTempoRepository.findByIndice(9, idTurma);
			doTempo9.setDataPrevista(turma.getVerificarPCDs());
		

			LinhaDoTempo doTempo10 = linhaDoTempoRepository.findByIndice(10, idTurma);
			doTempo10.setDataPrevista(turma.getVerificarPCDs());
	

			LinhaDoTempo doTempo11 = linhaDoTempoRepository.findByIndice(11, idTurma);
			doTempo11.setDataPrevista(turma.getVerificarPCDs());
	

			fazerLogRepository.save(log);
			linhaDoTempoRepository.save(doTempo1);
			linhaDoTempoRepository.save(doTempo2);
			linhaDoTempoRepository.save(doTempo3);
			linhaDoTempoRepository.save(doTempo4);
			linhaDoTempoRepository.save(doTempo5);
			linhaDoTempoRepository.save(doTempo6);
			linhaDoTempoRepository.save(doTempo7);
			linhaDoTempoRepository.save(doTempo8);
			linhaDoTempoRepository.save(doTempo9);
			linhaDoTempoRepository.save(doTempo10);
			linhaDoTempoRepository.save(doTempo11);
			repository.save(turma);

			HttpHeaders headers = new HttpHeaders();

			headers.setLocation(URI.create("/api/turma"));

			return new ResponseEntity<Void>(headers, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/buscarTurmaAno/", method = RequestMethod.POST)
	public ResponseEntity<?> procurarTurmaAno(@RequestBody String parametro) {

		List<Turma> turmas = repository.buscarTurma(parametro.replace("\"", ""));

		return ResponseEntity.ok().body(turmas);
	}

	@RequestMapping(value = "/buscarData/", method = RequestMethod.POST)
	public ResponseEntity<?> buscarData(@RequestBody String parametro) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar data = Calendar.getInstance();
		try {
			data.setTime(sdf.parse(parametro.replace("\"", "")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Turma> turmas = repository.buscarTurmaDois(data);

		return ResponseEntity.ok().body(turmas);
	}

	@RequestMapping(value = "/buscarDataTarefa/", method = RequestMethod.POST)
	public ResponseEntity<?> buscarDataTarefa(@RequestBody String parametro) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar data = Calendar.getInstance();
		try {
			data.setTime(sdf.parse(parametro.replace("\"", "")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Turma> turmas = repository.buscarTurmaTarefa(data);

		return ResponseEntity.ok().body(turmas);
	}

	@RequestMapping(value = "/findByAll/{p}")
	public Iterable<Turma> findByAll(@PathVariable("p") Calendar parametro) {

		return repository.buscarTurmaDois(parametro);
	}

	@RequestMapping(value = "qtdMatricula/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> acrescentarValor(@PathVariable("id") Long id) {

		Turma turma = repository.findById(id).get();

		int qtdMatricula = turma.getQtdMatriculas();

		qtdMatricula++;

		if (qtdMatricula > turma.getNumMaxVagas()) {

			return ResponseEntity.status(409).build();
		}

		turma.setQtdMatriculas(qtdMatricula);

		pontoEquilibrio(turma, id);

		repository.save(turma);

		return ResponseEntity.ok(qtdMatricula);

	}

	@RequestMapping(value = "diminuirQtdMatricula/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> diminuirValor(@PathVariable("id") Long id) {

		Turma turma = repository.findById(id).get();

		int qtdMatricula = turma.getQtdMatriculas();

		qtdMatricula--;

		if (qtdMatricula < 0) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}

		turma.setQtdMatriculas(qtdMatricula);

		pontoEquilibrio(turma, id);

		repository.save(turma);

		return ResponseEntity.ok(qtdMatricula);

	}

	public Double pontoEquilibrio(Turma turma, Long id) {

		Double valorCurso = repositoryCurso.findById(turma.getCurso().getId()).get().getValor();

		Double cargaHorariaCurso = repositoryCurso.findById(turma.getCurso().getId()).get().getCargaHoraria();

		Double podeSerLancado = turma.getQtdMatriculas() * valorCurso / cargaHorariaCurso;

		List<Parametro> lista = parametroRepository.findAll();

		for (Parametro pa : lista) {

			if (podeSerLancado >= pa.getPontoEquilibrio()) {

				turma.setPontoEquilibrio(pa.getPontoEquilibrio());

				turma.setPodeSerLancado(true);

				return podeSerLancado;

			} else {

				turma.setPodeSerLancado(false);

				turma.setPontoEquilibrio(pa.getPontoEquilibrio());

				return podeSerLancado;
			}
		}

		return (double) 0;

	}

	@RequestMapping(value = "/BuscarTG/{p}")
	public List<Turma> BuscarTurmaGeral(@PathVariable("p") String parametro) {
		return repository.buscarTurma(parametro);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<LinhaDoTempo>> getId(@PathVariable("id") Long id) {

		Turma turma = new Turma();

		turma = repository.findById(id).get();

		System.out.println(turma.getId());

		List<LinhaDoTempo> list = linhaDoTempoRepository.findByTurmaId(turma.getId());

		System.out.println(list);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/turma"));

		return ResponseEntity.created(URI.create("/" + turma.getId())).body(list);

	}

}
