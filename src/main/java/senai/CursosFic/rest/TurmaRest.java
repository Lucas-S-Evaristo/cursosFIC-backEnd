package senai.CursosFic.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import senai.CursosFic.Email.JavaMailApp;
import senai.CursosFic.model.Turma;
import senai.CursosFic.repository.CursoRepository;
import senai.CursosFic.repository.TurmaRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/turma")
public class TurmaRest {

	@Autowired
	private TurmaRepository repository;

	@Autowired
	private CursoRepository repositoryCurso;

	@Autowired
	private JavaMailApp javaMailApp = new JavaMailApp();

	// API DE CRIAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Turma turma) {

		// CRIANDO O CODIGO DA TURMA
		Calendar calendar = Calendar.getInstance();
		int anoData = calendar.get(Calendar.YEAR);
		int size = repository.procurarPorAno(anoData).size();
		int numero = size + 1;

		// turma.atualizarData();
		String periodo = turma.getPeriodo().getInicial();

		// pegando o id curso do obj turma, e procurando o curso pelo id informado
		turma.setCurso(repositoryCurso.findById(turma.getCurso().getId()).get());
		String nivel = turma.getCurso().getNivel().getInicial();
		String nomeCurso = turma.getCurso().getNome().substring(0, 1);

		// string com o codigo completo
		String codigo = periodo + nivel + nomeCurso + numero;

		turma.setCodigo(codigo);

		// metodo que atualiza as datas
		turma.atualizarData();

		// se a data de inicio for depois da data de término, retorna um erro ao front
		if (turma.getDataInicio().after(turma.getDataTermino())) {

			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();

			// se a data de inicio for igual a da data de término, retorna um erro ao front
		} else if (turma.getDataInicio().equals(turma.getDataTermino())) {

			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} else {

			String text = "TURMA CADASTRADA COM SUCESSO" + turma.getCodigo();

			// salva a turma
			repository.save(turma);

			// notificação por email
			// javaMailApp.mandarEmail(text);

			return ResponseEntity.created(URI.create("/" + turma.getId())).body(turma);
		}
	}

	// API DE LISTAR AS TURMAS
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Turma> listar() {

		return repository.findAll();
	}

	// API DE DELETAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable("id") Long idTurma) {

		repository.deleteById(idTurma);
		String text = "TURMA DELETADA COM SUCESSO" + idTurma;

		// javaMailApp.mandarEmail(text);

		return ResponseEntity.noContent().build();

	}

	// API DE ALTERAR AS TURMAS
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Turma turma, @PathVariable("id") Long idTurma) {

		if (idTurma != turma.getId()) {
			throw new RuntimeException("id não existente!");

		}
		// manter o codigo da turma após alterar
		Calendar calendar = Calendar.getInstance();
		int anoData = calendar.get(Calendar.YEAR);
		int size = repository.procurarPorAno(anoData).size();
		int numero = size;

		// turma.atualizarData();
		String periodo = turma.getPeriodo().getInicial();

		// pegando o id curso do obj turma, e procurando o curso pelo id informado
		turma.setCurso(repositoryCurso.findById(turma.getCurso().getId()).get());
		String nivel = turma.getCurso().getNivel().getInicial();
		String nomeCurso = turma.getCurso().getNome().substring(0, 1);

		// string com o codigo completo
		String codigo = periodo + nivel + nomeCurso + numero;

		turma.setCodigo(codigo);

		System.out.println("ALTEROUUU!!!!!");

		repository.save(turma);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(URI.create("/api/turma"));

		String text = "TURMA ALTERADA COM SUCESSO" + turma.getCodigo();

		// javaMailApp.mandarEmail(text);

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/buscarTurmaAno/{parametro}", method = RequestMethod.GET)
	public List<Turma> procurarTurmaAno(@PathVariable("parametro") int parametro) {

		return repository.procurarPorAno(parametro);

	}

	@RequestMapping(value = "/findByAll/{p}")
	public Iterable<Turma> findByAll(@PathVariable("p") Calendar parametro) {

		return repository.buscarTurmaDois(parametro);
	}

}
