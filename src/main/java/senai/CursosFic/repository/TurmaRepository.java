package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Turma;

public interface TurmaRepository extends PagingAndSortingRepository<Turma, Long> {

	@Query("select t from Turma t where year(t.dataInicio) = :year")
	public List<Turma> procurarPorAno(@Param("year") int ano);
}
