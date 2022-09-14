package senai.CursosFic.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class LinhaDoTempo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private Turma turma;
	private Date dataLimInscricao;
	private Date prazoLimAdiamTurm;
	private Date divulgResult;
	private Date prazoLimMatricula;
	private Date divulgResultSuplUm;
	private Date prazoLimMatrSuplUm;
	private Date divulgResultSuplDois;
	private Date prazoLimMatrSuplDois;
	private Date dataLimAdiamTurma;
	private Date vencSegundaParcela;
	private Date confirmarTurma;
	private Date retiradaSite;
	private Date cobrarEntregaDocum;
	private Date verificarPCDs;
	private Date gerarDiarioEletr;
	private Date montarKitTurma;
	private Date verifQuemFaltouPrimDia;
	private Date iniciarTurma;
	private Date matriculaDefinitiva;
	private Date encerrarTurma;
	private Date escanearDocum;
}
