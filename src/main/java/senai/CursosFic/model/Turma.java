package senai.CursosFic.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import Enum.Periodo;
import Enum.Status;
import lombok.Data;

@Data
@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int qtdMatriculas;
	@ManyToOne
	private Instrutor instrutor;
	@ManyToOne
	private Horario horarioInicio;
	@ManyToOne
	private Horario horarioTermino;
	private Date dataInicio;
	private Date dataTermino;
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
	
	public void codigoTurma (String codigo) {
		Curso curso = new Curso();
		
		System.out.println(curso.getNivel());
	}

}
