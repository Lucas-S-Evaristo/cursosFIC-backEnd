package senai.CursosFic.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;

import Enum.Status;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import senai.CursosFic.model.Curso;
import senai.CursosFic.model.Transporte;
import senai.CursosFic.model.Turma;
import senai.CursosFic.repository.CursoRepository;
import senai.CursosFic.repository.ParametroRepository;
import senai.CursosFic.repository.TurmaRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/folder")
public class FolderRest {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private ParametroRepository parametroRepository;

	@GetMapping(value = "/curso/{id}")
	public String folderCursoById(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		Curso curso = new Curso();

		curso = cursoRepository.findById(id).get();

		try {
			JasperReport report = JasperCompileManager
					.compileReport(getClass().getResourceAsStream("/relatorios/FolderCurso.jrxml"));

			Map<String, Object> map = new HashMap<>();

			map.put("nome", curso.getNome());

			map.put("cargaHoraria", curso.getCargaHoraria());

			map.put("objetivo", curso.getObjetivo());

			map.put("preRequisito", curso.getPreRequisito());

			map.put("conteudoProgramatico", curso.getConteudoProgramatico());

			String name = "FOLDER_CURSO.pdf";

			JasperPrint print = JasperFillManager.fillReport(report, map, new JREmptyDataSource());

			JasperExportManager.exportReportToPdfFile(print, name);

			File arquivo = new File(name);

			OutputStream output = response.getOutputStream();

			Files.copy(arquivo, output);

			output.close();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		System.out.println("DEU CERTO");
		return "OK";

	}

	@GetMapping(value = "/turma")
	public ResponseEntity<?> folderTurma(HttpServletRequest request, HttpServletResponse response) {

		Calendar dataHoje = Calendar.getInstance();

		List<Turma> list = turmaRepository.gerarFolder(dataHoje, Status.ABERTO);

		System.out.println("LISTA: " + list);

		if (list.isEmpty()) {

			System.out.println("VAZIAAA");

			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} else {

			// formatando as datas para string
			Calendar calendatDtInicio = Calendar.getInstance();
			Calendar calendarDtTermino = Calendar.getInstance();
			calendatDtInicio = list.get(0).getDataInicio();
			calendarDtTermino = list.get(0).getDataTermino();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dataInicio = sdf.format(calendatDtInicio.getTime());
			String dataFim = sdf.format(calendarDtTermino.getTime());

			JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(list);

			try {

				JasperReport report = JasperCompileManager
						.compileReport(getClass().getResourceAsStream("/relatorios/FolderTurma.jrxml"));

				Map<String, Object> map = new HashMap<>();

				// List<Parametro> list1 = parametroRepository.findAll();

				// if (list1.isEmpty()) {

				// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("LISTA PARAMETRO
				// VAZIA");
				// }

				// System.out.println(">>>>>>>>>>>>>>>>" + list1.get(0).getParcelaBoleto());

				// map.put("parcelasCart", list1.get(0).getParcelaCartao());
				// map.put("parcelasBol", list1.get(0).getParcelaBoleto());
 
				String name = "FOLDER_TURMA.pdf";

				JasperPrint print = JasperFillManager.fillReport(report, map, dados);

				JasperExportManager.exportReportToPdfFile(print, name);

				File arquivo = new File(name);

				OutputStream output = response.getOutputStream();

				Files.copy(arquivo, output);

				output.close();

			} catch (JRException e) {

				System.out.println("catch1");
				e.printStackTrace();

			} catch (IOException e) {

				System.out.println("catch2");
				e.printStackTrace();

			}
			System.out.println("DEU CERTO");
			return ResponseEntity.status(HttpStatus.OK).build();
		}
	}

	@GetMapping(value = "xls")
	public ResponseEntity<Object> relatorioABC(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {

		List<Turma> list = turmaRepository.findAll();

		JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(list);
		Calendar calendar = Calendar.getInstance();

		try {
			JasperReport report = JasperCompileManager.compileReport("src/main/java/relatorios/FolderTurma.jrxml");

			// response.setHeader("Content-Type","application/xml");
			Map<String, Object> map = new HashMap<>();

			// String name = "relatorio.pdf";
			// response.setContentType("application/xls");

			ClassPathResource resource = new ClassPathResource("static");

			// System.out.println(contexto.getRealPath("static"));

			String nomeAleatorio = UUID.randomUUID().toString() + ".xls";

			String nameXml = new File("src\\main\\resources\\static\\folders").getAbsolutePath() + "\\" + nomeAleatorio;

			ClassLoader loader = getClass().getClassLoader();

			JasperPrint print = JasperFillManager.fillReport(report, map, dados);

			// JasperExportManager.exportReportToPdfFile(print, nameXml);

			JRXlsxExporter exporter = new JRXlsxExporter();

			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, nameXml);
			exporter.exportReport();

			/*
			 * criando um arquvio como o nome do relatório File arquivo = new
			 * File(resource.getURI()+"/relatorio.xls");
			 * 
			 * pegando o caminho da requsição OutputStream output =
			 * response.getOutputStream(); mandando o relatório para o front-end para ser
			 * feito o download
			 * 
			 * Files.copy(arquivo, output);
			 */
			System.out.println("nome" + nomeAleatorio);
			Transporte transporte = new Transporte();
			transporte.setNome(nomeAleatorio);

			Thread.sleep(5000);

			return ResponseEntity.ok().body(transporte);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.badRequest().build();
		}

	}

}
