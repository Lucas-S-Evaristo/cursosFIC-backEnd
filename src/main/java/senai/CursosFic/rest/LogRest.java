package senai.CursosFic.rest;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import senai.CursosFic.model.Log;

import senai.CursosFic.repository.FazerLogRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/log")
public class LogRest {

	@Autowired
	private FazerLogRepository fazerLogRepository;
	
	@Autowired
	HttpServletRequest request;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Log> listarLog() {

		return fazerLogRepository.findAll();
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Log salvarLog(@RequestBody Log log ) {

		Date date = new Date();

		String horaAtual = new SimpleDateFormat("HH:mm").format(date);

		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(date);

		log.setHora(horaAtual);

		log.setData(dataAtual);
		
		log.setJustificativa(log.getJustificativa());
		
			try {
				String token = request.getHeader("Authorization");
				
				token = token.substring(1, token.length() - 1);
				
				// algoritmo para descriptografar
				Algorithm algoritmo = Algorithm.HMAC256(UsuarioRest.SECRET);

				JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRest.EMISSOR).build();
				// linha que vai validar o token
				DecodedJWT jwt = verifier.verify(token);
				// extrair os dados do payload
				Map<String, Claim> payload = jwt.getClaims();

				String nomeUsuario = payload.get("nome_usuario").toString();
				
				String nifUsuario = payload.get("nif_usuario").toString();

				nomeUsuario = nomeUsuario.substring(1, nomeUsuario.length() - 1);
				
				nifUsuario = nifUsuario.substring(1, nifUsuario.length() - 1);

				log.setNomeUsuario(nomeUsuario);
				
				log.setNifUsuario(nifUsuario);
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		return log;
	}
	
	@RequestMapping(value = "/logArea", method = RequestMethod.GET)
	public Iterable<Log> buscarLogsArea(){
		
		return fazerLogRepository.buscarLogArea();
	}
	
	@RequestMapping(value = "/logCurso", method = RequestMethod.GET)
	public Iterable<Log> buscarLogsCurso(){
		
		return fazerLogRepository.buscarLogCurso();
	}
	
	@RequestMapping(value = "/logHorario", method = RequestMethod.GET)
	public Iterable<Log> buscarLogsHorario(){
		
		return fazerLogRepository.buscarLogHorario();
	}
	
	@RequestMapping(value = "/logInstrutor", method = RequestMethod.GET)
	public Iterable<Log> buscarLogsInstrutor(){
		
		return fazerLogRepository.buscarLogInstrutor();
	}
	
	@RequestMapping(value = "/logTurma", method = RequestMethod.GET)
	public Iterable<Log> buscarLogsTurma(){
		
		return fazerLogRepository.buscarLogTurma();
	}
	
	@RequestMapping(value = "/logUsuario", method = RequestMethod.GET)
	public Iterable<Log> buscarLogsUsuario(){
		
		return fazerLogRepository.buscarLogUsuario();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		
		fazerLogRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/listaCadastro", method = RequestMethod.GET)
	public List<Log> listarCadastro(){
		
		return fazerLogRepository.listarCadastro();
	}

}
