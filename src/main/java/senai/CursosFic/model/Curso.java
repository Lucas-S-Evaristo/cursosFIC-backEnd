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


import org.hibernate.annotations.ManyToAny;

>>>>>>> 476105bdd47615fc6964238902e7a4300234fc8d

import Enum.Nivel;
import Enum.TipoAtendimento;
import lombok.Data;

@Entity
@Data
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
	private int id;
=======
	private Long id;
>>>>>>> 476105bdd47615fc6964238902e7a4300234fc8d
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
	
>>>>>>> 476105bdd47615fc6964238902e7a4300234fc8d

}
