package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{


@Query("SELECT c FROM Curso c WHERE c.nome LIKE %:p% OR c.objetivo LIKE %:p% OR c.area.nome LIKE %:p% ORDER BY c.nome ASC")
public List<Curso> buscarCurso(@Param("p") String parametro);
}
