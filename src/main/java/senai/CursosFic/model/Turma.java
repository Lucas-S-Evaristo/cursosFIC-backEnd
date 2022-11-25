	package senai.CursosFic.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import Enum.Periodo;
import Enum.SimNao;
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
	@Temporal(TemporalType.DATE)
	private Calendar dataInicio;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar dataTermino;
	@ManyToOne
	private Curso curso;
	@Enumerated(EnumType.STRING)
	private Periodo periodo;
	private String codigo;

	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private Ambiente ambiente;
	private int numMaxVagas;
	private int numMinVagas;

	private String diasDaTurma;
	
	private boolean podeSerLancado;
	
	private String justificativa;
	
	private Double pontoEquilibrio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar dataLimInscricao;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar confirmarTurma;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar retiradaSite;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar cobrarEntregaDocum;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar verificarPCDs;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar gerarDiarioEletr;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar montarKitTurma;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar verifQuemFaltouPrimDia;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar iniciarTurma;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar matriculaDefinitiva;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar encerrarTurma;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar escanearDocum;

	private SimNao simEnao;

	public void codigoTurma(String codigo) {
		Curso curso = new Curso();

		System.out.println(curso.getNivel());
	}

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
		montarKit.add(Calendar.DAY_OF_WEEK, -1);
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

	public String getParcelas() {

		

		return "3x de R$ " + String.format("%.2f", curso.getValor() / 3);

	}

	public String getTotal() {


		return "R$ " + String.format("%.2f", curso.getValor());

	}

	public String getCargaHoraria() {

		return String.valueOf(curso.getCargaHoraria() + " Horas");

	}
	
	public String getDatasInicio() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		return String.valueOf(sdf.format(dataInicio.getTime()));

	}
	
	public String getDatasTermino() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		return String.valueOf(sdf.format(dataTermino.getTime()));

	}

}