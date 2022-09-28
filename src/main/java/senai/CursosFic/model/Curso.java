package senai.CursosFic.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

<<<<<<< HEAD
=======

>>>>>>> c2824697e40c4d9c776127304b72e9088a0cb416
import Enum.Nivel;
import Enum.TipoAtendimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
<<<<<<< HEAD
=======
	
	public void vetorID() {
		
		int id[] = new int[this.id.intValue()];
		System.out.println(id);
	}
	
>>>>>>> c2824697e40c4d9c776127304b72e9088a0cb416

}
