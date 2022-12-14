package senai.CursosFic.rest;

import java.net.URI;
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
import senai.CursosFic.model.Horario;
import senai.CursosFic.model.Log;
import senai.CursosFic.repository.LogRepository;
import senai.CursosFic.repository.HorarioRepository;

@RestController
@RequestMapping("/api/horario")
@CrossOrigin
public class HorarioRest {

	@Autowired
	private HorarioRepository repository;

	@Autowired
	private LogRepository fazerLogRepository;

	@Autowired
	public LogRest logRest;

	// API DE CRIAR OS HORARIO
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Horario horario,  HttpServletRequest request) {
		List<Horario> horarios = repository.findAll();
		for (Horario h : horarios ) {
			if(h.getHorario().equals(horario.getHorario())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			
		}
		
		Log log = new Log();

		logRest.salvarLog(log);

		String hora = log.getHora();

		String data = log.getData();

		String nomeUsuario = log.getNomeUsuario();

		String nifUsuario = log.getNifUsuario();

		String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " deletou o seguinte horário: " +  horario.getHorario() + " em " + data
				+ " ás " + hora;

		// emailLog.mandarLog("prateste143@gmail.com", mensagem);
		
		log.setMensagem(mensagem);

		log.setLogsEnum(LogsEnum.CADASTROU);
		
		log.setTipoLog(TipoLog.HORARIO);
		
		log.setInformacaoCadastro(horario.getHorario());
		
		fazerLogRepository.save(log);

		repository.save(horario);

		return ResponseEntity.created(URI.create("/" + horario.getId())).body(horario);
		
		
	}

//API DE LISTAR OS HORARIOS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Horario> listar() {

		return repository.findAll();
	}

	// API DE DELETAR O HORARIO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long idHorario,  HttpServletRequest request) {
		try {
			Log log = new Log();

			logRest.salvarLog(log);

			String hora = log.getHora();

			String data = log.getData();

			String nomeUsuario = log.getNomeUsuario();

			String nifUsuario = log.getNifUsuario();
			
			Horario horario = repository.findById(idHorario).get();

			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " deletou o seguinte horário: " +  horario.getHorario() + " em " + data
					+ " ás " + hora;
			// emailLog.mandarLog("prateste143@gmail.com", mensagem);
			
			log.setMensagem(mensagem);
			
			log.setInformacaoCadastro(horario.getHorario());
			
			log.setLogsEnum(LogsEnum.DELETOU);
			
			log.setTipoLog(TipoLog.HORARIO);

			fazerLogRepository.save(log);

			repository.deleteById(idHorario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR O HORARIO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Horario horario, @PathVariable("id") Long idHorario,  HttpServletRequest request) {

		if (idHorario != horario.getId()) {
			throw new RuntimeException("id não existente!");

		}

		Log log = new Log();

		logRest.salvarLog(log);

		String hora = log.getHora();

		String data = log.getData();

		String nomeUsuario = log.getNomeUsuario();

		String nifUsuario = log.getNifUsuario();

		String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " alterou o seguinte horário: " +  horario.getHorario() + " em " + data
				+ " ás " + hora;
		
		// emailLog.mandarLog("prateste143@gmail.com", mensagem);
		
		log.setMensagem(mensagem);

		log.setLogsEnum(LogsEnum.ALTEROU);
		
		log.setTipoLog(TipoLog.HORARIO);
		
		log.setInformacaoCadastro(horario.getHorario());

		fazerLogRepository.save(log);

		repository.save(horario);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/horario"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
