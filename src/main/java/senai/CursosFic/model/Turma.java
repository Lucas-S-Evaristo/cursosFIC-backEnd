package senai.CursosFic.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	@OneToOne
	private LinhaDoTempo linhaDoTempo;

}
