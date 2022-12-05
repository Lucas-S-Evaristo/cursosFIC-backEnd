package senai.CursosFic.rest;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import senai.CursosFic.model.LinhaDoTempo;
import senai.CursosFic.repository.LinhaDoTempoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/linhaDoTempo")
public class LinhaDoTempoRest {

	@Autowired
	private LinhaDoTempoRepository repository;

	@Autowired
	HttpServletRequest request;

	// API DE LISTAR OS USUARIOS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<LinhaDoTempo> lista() {

		return repository.findAll();
	}

	// API DE AVANÇAR AÇÕES
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> addAction(@PathVariable("id") Long id) {
		
		System.out.println("ENTREEEU");

		LinhaDoTempo linhaDoTempo = new LinhaDoTempo();
		
		
		
		System.out.println("id: " + id);

		linhaDoTempo = repository.findById(id).get();

		if (id != linhaDoTempo.getId()) {
			throw new RuntimeException("id não existente!");
		}

		Calendar hoje = Calendar.getInstance();

		linhaDoTempo.setDataRealizada(hoje);
		
		Date date = new Date();

		String horaAtual = new SimpleDateFormat("HH:mm").format(date);

		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(date);
		
		linhaDoTempo.setHora(horaAtual);
		
		linhaDoTempo.setData(dataAtual);
		
		String token = request.getHeader("Authorization");
		
		token = token.substring(1, token.length() - 1);
		
		// algoritmo para descriptografar
		Algorithm algoritmo = Algorithm.HMAC256(UsuarioRest.SECRET);

		JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRest.EMISSOR).build();
		// linha que vai validar o token
		DecodedJWT jwt = verifier.verify(token);
		// extrair os dados do payload
		Map<String, Claim> payload = jwt.getClaims();
		
		String nifUsuario = payload.get("nif_usuario").toString();
		
		System.out.println("nif usuario: " + nifUsuario);
		
		nifUsuario = nifUsuario.substring(1, nifUsuario.length() - 1);
		
		String nomeUsuario = payload.get("nome_usuario").toString();
		
		nomeUsuario = nomeUsuario.substring(1, nomeUsuario.length() - 1);
		
		linhaDoTempo.setNifUsuario(nifUsuario);
		
		linhaDoTempo.setNomeUsuario(nomeUsuario);
		
		System.out.println("nome Usu: " + nomeUsuario);
		
		
		repository.save(linhaDoTempo);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/linhaDoTempo"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

}
