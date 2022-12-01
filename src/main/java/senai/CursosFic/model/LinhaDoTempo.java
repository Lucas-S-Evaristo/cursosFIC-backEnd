package senai.CursosFic.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;

import Enum.AcoesLinhaDoTempo;
import lombok.Data;

@Data
@Entity
public class LinhaDoTempo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar dataRealizada;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar dataPrevista;
	@Enumerated(EnumType.STRING)
	private AcoesLinhaDoTempo acoesLinhaDoTempo;
	// faz o cascade de turma para linha do tempo, fazendo com que a "turma mande na linha do tempo"
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne
	private Turma turma;
	
	private String nifUsuario;

	private String hora;
	
	private String data;
	
	private String nomeUsuario;
	
	public boolean getStatus() {
		if (dataRealizada == null) {
			return false;
		}
		return true;
	}
	
	public String getAcoesdaLinhaDoTempo() {
		return this.getAcoesLinhaDoTempo().toString();
	}

}
