package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Turma;

	public interface TurmaRepository extends PagingAndSortingRepository<Turma, Long> {


	@Query("select t from Turma t where year(t.dataInicio) = :valor OR day(t.dataInicio) = :valor OR month(t.dataInicio) = :valor OR year(t.dataTermino) = :valor OR day(t.dataTermino) = :valor OR month(t.dataTermino) = :valor")
	public List<Turma> procurarPorAno(@Param("valor") int ano);
	
	@Query("SELECT t FROM Turma t WHERE t.codigo LIKE %:p% OR t.curso.nome LIKE %:p% OR t.status LIKE %:p% OR t.diaSemana LIKE %:p% OR t.periodo LIKE %:p% OR t.valor LIKE %:p%")
	public List<Turma> buscarTurma(@Param("p") String parametro);
		

}