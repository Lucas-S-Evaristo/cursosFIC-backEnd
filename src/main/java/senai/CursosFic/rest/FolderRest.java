package senai.CursosFic.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import senai.CursosFic.model.Turma;
import senai.CursosFic.repository.CursoRepository;
import senai.CursosFic.repository.TurmaRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/folder")
public class FolderRest {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TurmaRepository turmaRepository;

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
	public String folderTurma(HttpServletRequest request, HttpServletResponse response) {

		Calendar dataHoje = Calendar.getInstance();

		List<Turma> list = turmaRepository.gerarFolder(dataHoje, Status.ABERTO);

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
			map.put("dataTermino", dataInicio);
			map.put("dataInicio", dataFim);

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
		return "ok";
	}

	@GetMapping(value = "/turmaCsv")
	public String csvTurma(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Calendar dataHoje = Calendar.getInstance();

		List<Turma> list = turmaRepository.gerarFolder(dataHoje, Status.ABERTO);

		// formatando as datas para string
		Calendar calendatDtInicio = Calendar.getInstance();
		Calendar calendarDtTermino = Calendar.getInstance();
		calendatDtInicio = list.get(0).getDataInicio();
		calendarDtTermino = list.get(0).getDataTermino();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataInicio = sdf.format(calendatDtInicio.getTime());
		String dataFim = sdf.format(calendarDtTermino.getTime());

		// pegando o valot total do curso
		Double valorTotal = list.get(0).getCurso().getValor();

		// o valor total do curso divido no numero total de parcelas
		Double valorParcelado = valorTotal / 3;

		// convertendo double para string
		String str = Double.toString(valorParcelado);

		String vTotal = Double.toString(list.get(0).getCurso().getValor());

		String stringValorTotal = "R$ " + vTotal;

		// valor que sera exibido no folder
		String stringValorParcelado = "3 PARCELAS DE R$ " + str;

		JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(list);

		try {

			JasperReport report = JasperCompileManager
					.compileReport(getClass().getResourceAsStream("/relatorios/FolderTurma.jrxml"));

			Map<String, Object> map = new HashMap<>();
			map.put("dataTermino", dataInicio);
			map.put("dataInicio", dataFim);
			map.put("valorParcelado", stringValorParcelado);
			map.put("valorTotal", stringValorTotal);

			String nameCsv = "C:\\Users\\TecDevTarde\\Downloads\\FOLDER_TURMA.xls";

			JasperPrint print = JasperFillManager.fillReport(report, map, dados);

			JRXlsxExporter exporter = new JRXlsxExporter();

			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, nameCsv);
			exporter.exportReport();

			File arquivo = new File(nameCsv);

			OutputStream output = response.getOutputStream();

			Files.copy(arquivo, output);

			output.close();
		} catch (JRException e) {

			System.out.println("catch1");
			e.printStackTrace();

		}
		System.out.println("DEU CERTO");
		return "ok";
	}

}
