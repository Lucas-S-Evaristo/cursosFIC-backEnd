package senai.CursosFic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	private String nifUsuario;
	
	@Column(columnDefinition  = "VARCHAR(512)")
	private String justificativa;
	
	private String informacaoCadastro;
	
	private String siglaCurso;
	
	private String codigoTurma;
	
	private String mensagem;
	
	
}
