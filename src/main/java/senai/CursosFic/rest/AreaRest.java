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
import senai.CursosFic.model.Area;
import senai.CursosFic.model.Log;
import senai.CursosFic.model.Usuario;
import senai.CursosFic.repository.AreaRepository;
import senai.CursosFic.repository.FazerLogRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/area")
public class AreaRest {

	@Autowired
	private AreaRepository repository;

	@Autowired
	private FazerLogRepository fazerLogRepository;

	@Autowired
	public LogRest logRest;

	// API DE CRIAR AREA

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Area area, HttpServletRequest request) {

		if (area.getNome().equals("")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			
			Log log = new Log();
			
			logRest.salvarLog(log);
			
			log.setLogsEnum(LogsEnum.CADASTROU);
			
			log.setTipoLog(TipoLog.AREA);
			
			log.setInformacaoCadastro(area.getNome());
			
			fazerLogRepository.save(log);

			repository.save(area);

			return ResponseEntity.created(URI.create("/" + area.getId())).body(area);
		}
	}

	// API DE LISTAR AREA
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Area> listar() {

		return repository.findAll();
	}

	// API DE DELETAR A AREA
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id, HttpServletRequest request) {

		try {
			Log log = new Log();

			logRest.salvarLog(log);
			
			Area area = repository.findById(id).get();
			
			log.setInformacaoCadastro(area.getNome());

			log.setLogsEnum(LogsEnum.DELETOU);
			
			log.setTipoLog(TipoLog.AREA);

			fazerLogRepository.save(log);

			repository.deleteById(id);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR A AREA
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Area area, @PathVariable("id") Long id , HttpServletRequest request) {

		if (id != area.getId()) {
			throw new RuntimeException("id não existente!");

		}

		Log log = new Log();

		logRest.salvarLog(log);

		log.setLogsEnum(LogsEnum.ALTEROU);
		
		log.setTipoLog(TipoLog.AREA);
		
		log.setInformacaoCadastro(area.getNome());

		fazerLogRepository.save(log);

		repository.save(area);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/area"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	@RequestMapping(value = "/buscar/", method = RequestMethod.POST)
	public ResponseEntity<?> buscarArea(@RequestBody String parametro){
		List<Area> areas = repository.buscarArea(parametro.replace("\"", ""));
		return ResponseEntity.ok(areas);
	}

}
