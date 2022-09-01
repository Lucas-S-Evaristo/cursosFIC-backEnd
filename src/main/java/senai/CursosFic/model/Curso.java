package senai.CursosFic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.Data;

@Entity
@Data
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String objetivo;
	
	private String preRequisito;
	
	private String conteudoProgramatico;
	
	private String sigla;
	
	private int cargaHoraria;
	
	private Double valor;
	
	@ManyToOne
	private Area area;
}
