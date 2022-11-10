package senai.CursosFic.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import senai.CursosFic.model.Log;

import senai.CursosFic.repository.FazerLogRepository;

@RestController
@RequestMapping("/api/log")
public class LogRest {
	
	@Autowired
	private FazerLogRepository fazerLogRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Log> listarLog() {

		return fazerLogRepository.findAll();
	}

	
	}
	
