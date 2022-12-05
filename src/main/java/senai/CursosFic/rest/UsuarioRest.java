package senai.CursosFic.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.UnionSubclass;
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
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import Enum.LogsEnum;
import Enum.TipoLog;
import Enum.TipoUsuario;
import senai.CursosFic.Email.JavaEmailDaSenha;
import senai.CursosFic.model.Log;
import senai.CursosFic.model.TokenJWT;
import senai.CursosFic.model.Usuario;
import senai.CursosFic.repository.LogRepository;
import senai.CursosFic.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/usuario")
public class UsuarioRest implements HandlerInterceptor {

	@Autowired
	private JavaEmailDaSenha email;

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private LogRepository fazerLogRepository;

	public static final String EMISSOR = "3M1SSORS3CR3t0";

	public static final String SECRET = "S3Cr3t0CUrS0F1C";

	@Autowired
	public LogRest logRest;

	// API DE CRIAR OS USUARIOS
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario, Log log, HttpServletRequest request) {

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
		if (usuario.getNome().equals("") || usuario.getEmail().equals("") || usuario.getNif().equals("")
				|| usuario.getTipoUsuario() == null) {
			// envia um status de erro ao front

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usuario);

		} else {

			//pega o nif do usuario como a senha de primeiro acesso
			usuario.setSenha(usuario.getNif());
			
			
			//se a lista estiver vazia, não irá gerar nenhum log, pois não tem usuario cadastrado
			if(list.size() == 0) {
				
				repository.save(usuario);
				
			return ResponseEntity.created(URI.create("/" + usuario.getId())).body(usuario);
			
			}else {
				//percorre a lista de usuario e verifica se existe 
				for(Usuario u : list) {
					
					String tipo = u.getTipoUsuarioString();
					//se existir apenas o tipo opp e secretaria, não ira gerar nenhum log
					if(tipo.equals("Opp") || tipo.equals("Secretaria")) {
						
						repository.save(usuario);
						
						return ResponseEntity.created(URI.create("/" + usuario.getId())).body(usuario);
					}
					
				}
			
			Log log2 = new Log();

			logRest.salvarLog(log);
			
			logRest.salvarLog(log2);

			//puxa a hora da ação efetuada 
			String hora = log2.getHora();
			//puxa a data da ação efetuada
			String data = log2.getData();

			//puxa o nome do usuario logado na sessao
			String nomeUsuario = log2.getNomeUsuario();

			//pega o nif do usuario logado
			String nifUsuario = log2.getNifUsuario();
			//passa uma mensagem que é usada como parametro pra buscar, assim diferenciar o tipo de busca
			String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " cadastrou um usuário chamado " +  usuario.getNome() +  " e o seu nif é, " + usuario.getNif() + " no dia " + data
					+ " ás " + hora;

			// emailLog.mandarLog("prateste143@gmail.com", mensagem);
			
			//seta a mensagem no log
			log.setMensagem(mensagem);

			repository.save(usuario);

			//setar o tipo de acao que o usuario esta fazendo
			log.setLogsEnum(LogsEnum.CADASTROU);
			//setar em que entidade que o usuario esta mexendo
			log.setTipoLog(TipoLog.USUARIO);

			//passa o nif pra informação
			log.setInformacaoCadastro(usuario.getNif());

			fazerLogRepository.save(log);

			return ResponseEntity.created(URI.create("/" + usuario.getId())).body(usuario);
			}
		}
	}

	// API DE LISTAR OS USUARIOS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Usuario> listarUsuario() {

		return repository.findAll();
	}

	// API DE DELETAR USUARIO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario, HttpServletRequest request) {

		Log log = new Log();

		logRest.salvarLog(log);

		String hora = log.getHora();

		String data = log.getData();

		String nomeUsuario = log.getNomeUsuario();

		String nifUsuario = log.getNifUsuario();
		
		Usuario usuario = repository.findById(idUsuario).get();

		String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " deletou um usuário chamado " +  usuario.getNome() +  " e o seu nif é, " + usuario.getNif() + " no dia " + data
				+ " ás " + hora;
		// emailLog.mandarLog("prateste143@gmail.com", mensagem);
		
		log.setMensagem(mensagem);

		log.setInformacaoCadastro(usuario.getNif());

		log.setLogsEnum(LogsEnum.DELETOU);

		log.setTipoLog(TipoLog.USUARIO);

		repository.deleteById(idUsuario);

		fazerLogRepository.save(log);

		// RETORNO SEM CORPO
		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR USUARIO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterarUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario,
			HttpServletRequest request) {

		if (idUsuario != usuario.getId()) {
			throw new RuntimeException("id não existente!");

		}
		
		Log log = new Log();

		logRest.salvarLog(log);

		String hora = log.getHora();

		String data = log.getData();

		String nomeUsuario = log.getNomeUsuario();

		String nifUsuario = log.getNifUsuario();

		String mensagem = "O usuário " + nomeUsuario + " com o Nif " + nifUsuario + " alterou um usuário chamado " +  usuario.getNome() +  " e o seu nif é, " + usuario.getNif() + " no dia " + data
				+ " ás " + hora;

		// emailLog.mandarLog("prateste143@gmail.com", mensagem);
		
		log.setMensagem(mensagem);

		log.setLogsEnum(LogsEnum.ALTEROU);

		log.setTipoLog(TipoLog.USUARIO);

		log.setInformacaoCadastro(usuario.getNif());

		fazerLogRepository.save(log);
		
		String senha = repository.findById(idUsuario).get().getSenha();
		
		usuario.setSenhaSemHash(senha);
		
		//verificar se ja foi redefinido a senha
		boolean verd = repository.findById(idUsuario).get().isRedefinirSenha();
		
		if(verd == true) {
			
			usuario.setRedefinirSenha(true);
			
			repository.save(usuario);
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.setLocation(URI.create("/api/usuario"));

			return new ResponseEntity<Void>(headers, HttpStatus.OK);

			
		}else {
			
			usuario.setRedefinirSenha(false);
			
			repository.save(usuario);
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.setLocation(URI.create("/api/usuario"));

			return new ResponseEntity<Void>(headers, HttpStatus.OK);

		}
			}

	@RequestMapping(value = "/tipo/{tipo}", method = RequestMethod.GET)
	public List<Usuario> getUsuariosByTipo(@PathVariable("idTipo") TipoUsuario tipo) {
		return repository.findByTipoUsuario(tipo);
	}

	// API BUSCAR USUARIO
	@RequestMapping(value = "/buscar/", method = RequestMethod.POST)
	public ResponseEntity<?> buscarUsuario(@RequestBody String nome) {
		
		List<Usuario> usuarios = repository.buscarUsuario(nome.replace("\"", ""));

		return ResponseEntity.ok().body(usuarios);
	}

	// api para logar o usuário
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logar(@RequestBody Usuario usuario) {

		// trás uma lista de usuários
		List<Usuario> list = repository.findAll();
		
		//se não tiver nenhum usuario cadastrado ele redireciona para o primeiro cadastro
		if(list.size() == 0) {
			
			//se o nif for igual admin da acesso para cadastrar, se não retorna inautorizado
			if(usuario.getNif().equals("admin")){
				
				return ResponseEntity.status(308).build();
				
				
			}else {
				
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			
		}else {
			//for pra verificar se existe algum usuario do tipo master cadastrado no banco
			// caso exista, o nif = admin não irá mais funcionar
			for(Usuario usuario2 : list) {
				
				String tipo = usuario2.getTipoUsuarioString();
				
						if(tipo.equals("Master")) {

						 break;
							
						}else {
							
							if(usuario.getNif().equals("admin")){
								
								return ResponseEntity.status(308).build();		
							}
						}
					}
			
		for (Usuario u : list) {

			// verifica se o nif digitado é igual ao do banco de dados
			if (u.getNif().equals(usuario.getNif()) && u.getSenha().equals(usuario.getSenha())) {

				// Adicionar valores para o token
				Map<String, Object> payload = new HashMap<String, Object>();

				payload.put("id_usuario", u.getId());

				payload.put("nome_usuario", u.getNome());

				String tipo = u.getTipoUsuarioString();

				payload.put("usuario", u.toString());

				payload.put("tipo_usuario", tipo);

				payload.put("nif_usuario", u.getNif());

				Calendar expiracao = Calendar.getInstance();

				// expirar sessão do usuario que estiver logado depois de uma hora
				expiracao.add(Calendar.HOUR, 1);

				// algoritmo para assinar o token
				Algorithm algorithm = Algorithm.HMAC256(SECRET);

				// gerar o token
				TokenJWT tokenJwt = new TokenJWT();

				tokenJwt.setToken(JWT.create().withPayload(payload).withIssuer(EMISSOR).sign(algorithm));

				//se redefinir a senha estiver como falsa, significa que a senha do usuario não foi redefinida, ou
				//ela foi alterada.
				if (u.isRedefinirSenha() == false) {

					return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).body(u);

				} else {

					return ResponseEntity.ok(tokenJwt);
				}

			}
		}

		}

		return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
	}

	//verifica se o email do usuario existe no banco e manda uma nova senha provisoria pra ele
	@RequestMapping(value = "/verificarParametro", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verificarparametro(@RequestBody Usuario usuario) {

		List<Usuario> lista = repository.findAll();

		for (Usuario u : lista) {

			if (u.getEmail().equals(usuario.getEmail())) {
				String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				// create random string builder
				StringBuilder sb = new StringBuilder();

				// create an object of Random class
				Random random = new Random();

				// specify length of random string
				int length = 7;

				for (int i = 0; i < length; i++) {

					// generate random index number
					int index = random.nextInt(alphabet.length());

					// get character specified by index
					// from the string
					char randomChar = alphabet.charAt(index);

					// append the character to string builder
					sb.append(randomChar);
				}

				String randomString = sb.toString();
				u.setSenha(randomString);
				u.setRedefinirSenha(false);
				System.out.println("Random String is: " + randomString);
				System.out.println("nova semha: " + u.getSenha());
				repository.save(u);
				email.mandarEmail(u.getEmail(), randomString);

				return ResponseEntity.status(HttpStatus.OK).build();
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
	}

	@RequestMapping(value = "/redefinirSenha/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> redefinirSenha(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario) {

		usuario.setRedefinirSenha(true);

		repository.save(usuario);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/usuario"));

		return new ResponseEntity<Void>(headers, HttpStatus.OK);

	}

}
