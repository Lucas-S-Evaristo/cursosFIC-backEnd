package senai.CursosFic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Parametro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String telefone;
	private String endereco;
	@Column(columnDefinition = "LONGTEXT")
	private String logo;
	private Double pontoEquilibrio;
	private int parcelaBoleto;
	private int parcelaCartao;
}
