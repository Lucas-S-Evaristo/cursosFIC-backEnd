package senai.CursosFic.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import Enum.Status;
import senai.CursosFic.model.Turma;

public interface TurmaRepository extends PagingAndSortingRepository<Turma, Long> {

	@Query("select t from Turma t where year(t.dataInicio) = :valor OR day(t.dataInicio) = :valor OR month(t.dataInicio) = :valor OR year(t.dataTermino) = :valor OR day(t.dataTermino) = :valor OR month(t.dataTermino) = :valor")
	public List<Turma> procurarPorAno(@Param("valor") int ano);
	
	 @Query("SELECT t FROM Turma t WHERE t.codigo LIKE %:p% OR t.curso.nome LIKE %:p% OR t.status LIKE %:p% OR t.diasDaTurma LIKE %:p% OR t.periodo LIKE %:p% OR t.instrutor.nome LIKE %:p% OR t.ambiente.nome LIKE %:p% OR t.qtdMatriculas LIKE %:p% OR t.numMaxVagas LIKE %:p% OR t.numMinVagas LIKE %:p% ")
	   public List<Turma> buscarTurma(@Param("p") String parametro);

	 @Query("SELECT t FROM Turma t WHERE t.dataInicio = :pa OR t.dataTermino = :pa")
	    public List<Turma> buscarTurmaDois(@Param("pa") Calendar parametro);

	@Query("SELECT t FROM Turma t WHERE t.dataInicio > :dataHoje AND t.status = :status")
	public List<Turma> gerarFolder(@Param("dataHoje") Calendar dataHoje, @Param("status")Status status);
	
	@Query("SELECT t FROM Turma t WHERE t.status = 0")
	public List<Turma> selectTurmaAberta();

	@Query("SELECT t FROM Turma t ORDER BY t.id DESC")
	public List<Turma> findAll();
	
	 @Query("SELECT t FROM Turma t WHERE t.dataLimInscricao =:pa OR t.confirmarTurma =:pa OR t.retiradaSite = :pa  OR t.verificarPCDs = :pa OR t.gerarDiarioEletr = :pa OR t.montarKitTurma = :pa OR t.verifQuemFaltouPrimDia = :pa OR t.iniciarTurma = :pa OR t.iniciarTurma = :pa OR t.matriculaDefinitiva = :pa OR t.encerrarTurma = :pa OR t.escanearDocum = :pa OR t.cobrarEntregaDocum = :pa")
	    public List<Turma> buscarTurmaTarefa(@Param("pa") Calendar parametro);

	
}