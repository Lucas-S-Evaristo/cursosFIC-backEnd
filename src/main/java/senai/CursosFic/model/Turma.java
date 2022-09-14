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
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar dataInicio;
	@JsonFormat(pattern = "dd-MM-yyyy")
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
	@ManyToOne
	private DiaSemana diaSemana;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar dataLimInscricao;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar confirmarTurma;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar retiradaSite;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar cobrarEntregaDocum;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar verificarPCDs;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar gerarDiarioEletr;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar montarKitTurma;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar verifQuemFaltouPrimDia;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar iniciarTurma;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar matriculaDefinitiva;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar encerrarTurma;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar escanearDocum;

	public void atualizarData(Turma turma) {

		// pegando o valor da data de inicio da requisição
		
		System.out.println("Data colocada:" + turma.getDataInicio());
		
		turma.getDataInicio().add(Calendar.DAY_OF_MONTH, -12);

		System.out.println("doze dias antes: " + turma.getDataInicio());

		turma.setDataLimInscricao(turma.getDataInicio());
	}

}
