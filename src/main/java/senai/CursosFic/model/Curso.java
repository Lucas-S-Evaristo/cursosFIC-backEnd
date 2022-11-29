package senai.CursosFic.model;

import javax.persistence.Column;
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
	@Column(columnDefinition = "VARCHAR(2048)")
	private String objetivo;
	@Column(columnDefinition = "VARCHAR(2048)")
	private String preRequisito;
	@Column(columnDefinition = "VARCHAR(2048)")
	private String conteudoProgramatico;
	private String sigla;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(255)")
	private TipoAtendimento tipoAtendimento;
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	private Double cargaHoraria;
	@ManyToOne
	private Area area;
	private Double valor;
	@Column(columnDefinition = "VARCHAR(512)")
	private String justificativa;
	
	public String getNivelString() {
		return this.getNivel().toString();
	}
	
	public int getNivelOrdinal() {
		return this.getNivel().ordinal();
	}

	public String getTipoAtendString() {
		return this.getTipoAtendimento().toString();
	}
	
	public int getTipoAtendOrdinal() {
		return this.getTipoAtendimento().ordinal();
	}

}
