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
	
>>>>>>> 9cf8e876ad64757cbdf8599004c7cff2366a0a34
	private String nome;
}
