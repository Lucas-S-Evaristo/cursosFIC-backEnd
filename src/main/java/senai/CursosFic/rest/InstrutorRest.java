package senai.CursosFic.rest;

import java.net.URI;
import java.util.Arrays;
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
import senai.CursosFic.model.Instrutor;
import senai.CursosFic.model.Log;
import senai.CursosFic.repository.LogRepository;
import senai.CursosFic.repository.InstrutorRepository;

@RestController
@RequestMapping("/api/instrutor")
@CrossOrigin
public class InstrutorRest {

	@Autowired
	private InstrutorRepository repository;
	
	@Autowired
	private LogRepository fazerLogRepository;
	
	@Autowired
	public LogRest logRest;

	// API DE CRIAR OS Instrutores
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Instrutor instrutor,  HttpServletRequest request) {

		if (instrutor.getNome().equals("")) {
			// envia um status de erro ao front
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} else {
			
			Log log = new Log();

			logRest.salvarLog(log);

			String hora = log.getHora();

			String data = log.getData();

			String nomeUsuario = log.getNomeUsuario();

			String nifUsuario = log.getNifUsuario();

			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " cadastrou um instrutor chamado " +  instrutor.getNome() + " em " + data
					+ " ás " + hora;

			// emailLog.mandarLog("prateste143@gmail.com", mensagem);
			
			log.setMensagem(mensagem);

			
			log.setLogsEnum(LogsEnum.CADASTROU);
			
			log.setTipoLog(TipoLog.INSTRUTOR);
			
			log.setInformacaoCadastro(instrutor.getNome());
			
			fazerLogRepository.save(log);
			
			repository.save(instrutor);

			return ResponseEntity.created(URI.create("/" + instrutor.getId())).body(instrutor);
		}

	}

//API DE LISTAR OS Instrutores
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Instrutor> listar() {

		return repository.findAll();
	}

	// API DE ALTERAR instrutor

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id,  HttpServletRequest request) {

		try {
			
			Log log = new Log();

			logRest.salvarLog(log);

			String hora = log.getHora();

			String data = log.getData();

			String nomeUsuario = log.getNomeUsuario();

			String nifUsuario = log.getNifUsuario();
			
			Instrutor instrutor = repository.findById(id).get();

			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " deletou um instrutor chamado " +  instrutor.getNome() + " em " + data
					+ " ás " + hora;
			// emailLog.mandarLog("prateste143@gmail.com", mensagem);
			
			log.setMensagem(mensagem);

			log.setInformacaoCadastro(instrutor.getNome());
			
			log.setLogsEnum(LogsEnum.DELETOU);
			
			log.setTipoLog(TipoLog.INSTRUTOR);
			
			fazerLogRepository.save(log);
			
			repository.deleteById(id);
		} catch (Exception e) {
			// envia um status de erro ao front
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR instrutor
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Instrutor instrutor, @PathVariable("id") Long idInstrutor,  HttpServletRequest request) {

		if (idInstrutor.longValue() != instrutor.getId().longValue()) {
			throw new RuntimeException("id não existente!");

		} else if (instrutor.getNome().equals("")) {
			// envia um status de erro ao front
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} else {
			
			Log log = new Log();

			logRest.salvarLog(log);

			String hora = log.getHora();

			String data = log.getData();

			String nomeUsuario = log.getNomeUsuario();

			String nifUsuario = log.getNifUsuario();

			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " alterou um instrutor chamado " +  instrutor.getNome() + " em " + data
					+ " ás " + hora;

			// emailLog.mandarLog("prateste143@gmail.com", mensagem);
			
			log.setMensagem(mensagem);

			
			log.setLogsEnum(LogsEnum.ALTEROU);
			
			log.setTipoLog(TipoLog.INSTRUTOR);
			
			log.setInformacaoCadastro(instrutor.getNome());
			
			fazerLogRepository.save(log);

			repository.save(instrutor);

			HttpHeaders headers = new HttpHeaders();

			headers.setLocation(URI.create("/api/instrutor"));

			return new ResponseEntity<Void>(headers, HttpStatus.OK);
		}
	}

	 // API BUSCAR INSTRUTO
    @RequestMapping(value = "/buscar/", method = RequestMethod.POST)
    public  ResponseEntity<?>  buscarInstrutor(@RequestBody String nome) {
        
        List<Instrutor> instrutors = repository.buscarInstrutor(nome.replace("\"", ""));
     
        return ResponseEntity.ok().body(instrutors);
    }

}