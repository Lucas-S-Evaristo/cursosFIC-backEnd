package senai.CursosFic.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import Enum.Nivel;
import Enum.TipoAtendimento;
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
	@Enumerated(EnumType.STRING)
	private TipoAtendimento tipoAtendimento;
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	private int cargaHoraria;
	@ManyToOne
	private Area area;
	private Double valor;

}
