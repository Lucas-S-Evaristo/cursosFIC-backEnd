package senai.CursosFic.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import Enum.TipoUsuario;
import lombok.Data;

@Entity
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String nif;
	private String senha;
	private TipoUsuario tipoUsuario;

}
