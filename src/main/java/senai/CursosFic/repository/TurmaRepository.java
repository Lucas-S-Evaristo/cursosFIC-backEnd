package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Turma;

public interface TurmaRepository extends PagingAndSortingRepository<Turma, Long> {
<<<<<<< HEAD

	@Query("select t from Turma t where year(t.dataInicio) = :year")
	public List<Turma> procurarPorAno(@Param("year") int ano);
=======
	@Query("SELECT t FROM Turma t WHERE t.codigo LIKE %:p% OR t.curso LIKE %:p%")
	public List<Turma> buscarTurma(@Param("p") String parametro);
>>>>>>> c2824697e40c4d9c776127304b72e9088a0cb416
}
