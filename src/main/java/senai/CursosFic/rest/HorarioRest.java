package senai.CursosFic.rest;

import java.net.URI;

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
import senai.CursosFic.repository.FazerLogRepository;
import senai.CursosFic.repository.HorarioRepository;

@RestController
@RequestMapping("/api/horario")
@CrossOrigin
public class HorarioRest {

	@Autowired
	private HorarioRepository repository;

	@Autowired
	private FazerLogRepository fazerLogRepository;

	@Autowired
	public LogRest logRest;

	// API DE CRIAR OS HORARIO
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Horario horario) {

		Log log = new Log();

		logRest.salvarLog(log);

		log.setLogsEnum(LogsEnum.CADASTROU);
		
		log.setTipoLog(TipoLog.HORARIO);

		fazerLogRepository.save(log);

		repository.save(horario);

		return ResponseEntity.created(URI.create("/" + horario.getId())).body(horario);
	}

//API DE LISTAR OS HORARIOS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Horario> listar() {

		return repository.findAll();
	}

	// API DE DELETAR O HORARIO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long idHorario) {
		try {
			Log log = new Log();

			logRest.salvarLog(log);

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
	public ResponseEntity<Void> alterar(@RequestBody Horario horario, @PathVariable("id") Long idHorario) {

		if (idHorario != horario.getId()) {
			throw new RuntimeException("id não existente!");

		}

		Log log = new Log();

		logRest.salvarLog(log);

		log.setLogsEnum(LogsEnum.ALTEROU);
		
		log.setTipoLog(TipoLog.HORARIO);

		fazerLogRepository.save(log);

		repository.save(horario);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/horario"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
