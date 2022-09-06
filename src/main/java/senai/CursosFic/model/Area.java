package senai.CursosFic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Area {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
	private int id;
=======

	private Long id;
	
>>>>>>> 476105bdd47615fc6964238902e7a4300234fc8d
	private String nome;
}
