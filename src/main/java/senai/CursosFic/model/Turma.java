package senai.CursosFic.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import Enum.DiaSemana;
import Enum.Periodo;
import Enum.Status;
import lombok.Data;

@Data
@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int qtdMatriculas;
	@ManyToOne
	private Instrutor instrutor;
	@ManyToOne
	private Horario horarioInicio;
	@ManyToOne
	private Horario horarioTermino;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar dataInicio;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar dataTermino;
	@ManyToOne
	private Curso curso;
	@Enumerated(EnumType.STRING)
	private Periodo periodo;
	private String codigo;
	private Double valor;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private Ambiente ambiente;
	private int numMaxVagas;
	@Enumerated(EnumType.STRING)
	private DiaSemana diaSemana;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar dataLimInscricao;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar confirmarTurma;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar retiradaSite;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar cobrarEntregaDocum;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar verificarPCDs;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar gerarDiarioEletr;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar montarKitTurma;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar verifQuemFaltouPrimDia;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar iniciarTurma;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar matriculaDefinitiva;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar encerrarTurma;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Calendar escanearDocum;
	private boolean simEnao;

	// turma.getDataInicio().add(Calendar.DAY_OF_MONTH, -12);

	public void atualizarData() {

		// clonando as datas a partir das datas de inicio e terminio
		Calendar dataLimInsc = (Calendar) dataInicio.clone();
		Calendar confirmTurma = (Calendar) dataInicio.clone();
		Calendar retiradaTurmaSite = (Calendar) dataInicio.clone();
		Calendar entregaDocs = (Calendar) dataInicio.clone();
		Calendar verficaPcds = (Calendar) dataInicio.clone();
		Calendar gerarDiario = (Calendar) dataInicio.clone();
		Calendar montarKit = (Calendar) dataInicio.clone();
		Calendar verifQuemFaltouPrimDia = (Calendar) dataInicio.clone();
		Calendar iniciarTurma = (Calendar) dataInicio.clone();
		Calendar matriculaDefinitiva = (Calendar) dataInicio.clone();
		Calendar encerrarTurma = (Calendar) dataTermino.clone();
		Calendar escanearDocum = (Calendar) dataTermino.clone();

		dataLimInsc.add(Calendar.DAY_OF_WEEK, -12);
		confirmTurma.add(Calendar.DAY_OF_WEEK, -5);
		entregaDocs.add(Calendar.DAY_OF_WEEK, -3);
		verficaPcds.add(Calendar.DAY_OF_WEEK, -7);
		gerarDiario.add(Calendar.DAY_OF_WEEK, -1);
		montarKit.add(Calendar.DAY_OF_WEEK, -2);
		verifQuemFaltouPrimDia.add(Calendar.DAY_OF_WEEK, 1);
		iniciarTurma.add(Calendar.DAY_OF_WEEK, 3);
		matriculaDefinitiva.add(Calendar.DAY_OF_WEEK, 7);
		encerrarTurma.add(Calendar.DAY_OF_WEEK, 1);
		escanearDocum.add(Calendar.DAY_OF_WEEK, 15);

		// passando
		setConfirmarTurma(confirmTurma);
		setDataLimInscricao(dataLimInsc);
		setRetiradaSite(retiradaTurmaSite);
		setCobrarEntregaDocum(entregaDocs);
		setVerificarPCDs(verficaPcds);
		setGerarDiarioEletr(gerarDiario);
		setMontarKitTurma(montarKit);
		setVerifQuemFaltouPrimDia(verifQuemFaltouPrimDia);
		setIniciarTurma(iniciarTurma);
		setMatriculaDefinitiva(matriculaDefinitiva);
		setEncerrarTurma(encerrarTurma);
		setEscanearDocum(escanearDocum);

	}

}