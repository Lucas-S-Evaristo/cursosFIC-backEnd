package senai.CursosFic.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import Enum.TipoUsuario;
import senai.CursosFic.model.TokenJWT;
import senai.CursosFic.model.Usuario;
import senai.CursosFic.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/usuario")
public class UsuarioRest {

	@Autowired
	private UsuarioRepository repository;
	
	public static final String EMISSOR = "3M1SSORS3CR3t0";
	
	public static final String SECRET = "S3Cr3t0CUrS0F1C";


	// API DE CRIAR OS USUARIOS
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario) {

		System.out.println();
		List<Usuario> list = repository.findAll();

		// percorre todos os usuarios e verifica se o email ja existe no bd
		for (Usuario u : list) {
			if (u.getEmail().equals(usuario.getEmail())) {

				return ResponseEntity.status(HttpStatus.CONFLICT).body(usuario.getEmail());

			}

			else if (u.getNif().equals(usuario.getNif())) {

				return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(usuario.getNif());
			}
		}

		
		

		// faz a verificação de campos vazio
		if (usuario.getNome().equals("") || usuario.getEmail().equals("")
				|| usuario.getNif().equals("") || usuario.getTipoUsuario() == null) {
			// envia um status de erro ao front
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usuario);
			
			
			
		} else {

			usuario.setSenha(usuario.getNif());
			repository.save(usuario);
			return ResponseEntity.created(URI.create("/" + usuario.getId())).body(usuario);

		}
	}

	// API DE LISTAR OS USUARIOS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Usuario> listarUsuario() {

		return repository.findAll();
	}

	// API DE DELETAR USUARIO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario) {

		repository.deleteById(idUsuario);
		System.out.println("DELETEI");

		// RETORNO SEM CORPO
		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR USUARIO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterarUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario) {

		if (idUsuario != usuario.getId()) {
			throw new RuntimeException("id não existente!");

		}

		repository.save(usuario);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/usuario"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/tipo/{tipo}", method = RequestMethod.GET)
	public List<Usuario> getUsuariosByTipo(@PathVariable("idTipo") TipoUsuario tipo) {
		return repository.findByTipoUsuario(tipo);
	}
	
	// API BUSCAR USUARIO
	@RequestMapping(value = "/buscar/{nome}",  method = RequestMethod.GET )
	  public List<Usuario>buscarUsuario(@PathVariable("nome") String nome){
		  return repository.buscarUsuario(nome);
	  }

	//api para logar o usuário
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenJWT> logar(@RequestBody Usuario usuario){
		
		//trás uma lista de usuários
		List<Usuario> list = repository.findAll();
		
		
		//for pra percorrer a lista de usuários
		for(Usuario u : list) {
			
			//verifica se o nif digitado é igual ao do banco de dados
			if(u.getNif().equals(usuario.getNif()) && u.getSenha().equals(usuario.getSenha())) {
				System.out.println("ENTROU LOGAR" + "\n");
				
				//Adicionar valores para o token
				Map<String, Object> payload = new HashMap<String, Object>();
				
				payload.put("id_usuario", u.getId());
				
				System.out.println("id_usuario " + u.getId());
				
				payload.put("nome_usuario", u.getNome());

				System.out.println("nome_usuario " + u.getNome());
				
				String tipo = u.getTipoUsuario().toString();
				
				System.out.println("tipo_usuario " + tipo);
				
				payload.put("tipo_usuario", tipo);
				
				Calendar expiracao = Calendar.getInstance();
				
				//expirar sessão do usuario que estiver logado depois de uma hora
				expiracao.add(Calendar.HOUR, 1);
				
				// algoritmo para assinar o token
				Algorithm algorithm = Algorithm.HMAC256(SECRET);
				
				// gerar o token
				TokenJWT tokenJwt = new TokenJWT();
				
				tokenJwt.setToken(JWT.create().withPayload(payload).withIssuer(EMISSOR).withExpiresAt(expiracao.getTime()).sign(algorithm));
				
				return ResponseEntity.ok(tokenJwt);
				
			}
		
		}
		System.out.println("NÃO AUTORIZADO" + "\n");
		
		return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
	}


}
