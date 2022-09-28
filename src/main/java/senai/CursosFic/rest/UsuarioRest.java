package senai.CursosFic.rest;

import java.net.URI;
import java.util.List;

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

import Enum.TipoUsuario;
import senai.CursosFic.model.Usuario;
import senai.CursosFic.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/usuario")
public class UsuarioRest {

	@Autowired
	private UsuarioRepository repository;

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

	// API BUSCAR INSTRUTO
	@RequestMapping(value = "/buscar/{nome}", method = RequestMethod.GET)
	public List<Usuario> buscarUsuario(@PathVariable("nome") String nome) {
		return repository.buscarUsuario(nome);
	}

}
