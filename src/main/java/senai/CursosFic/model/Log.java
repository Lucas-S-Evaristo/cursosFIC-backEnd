package senai.CursosFic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import Enum.LogsEnum;
import Enum.TipoLog;
import lombok.Data;

@Data
@Entity
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomeUsuario;
	
	private String hora;
	
	private String data;
	
	private LogsEnum logsEnum;
	
	private TipoLog tipoLog;
	
	private String mensagem;
	
	private String nifUsuario;
	
	private String justificativa;
	
	private String informacaoCadastro;
	
	private String informacaoCadastroDois;
	
	
}
