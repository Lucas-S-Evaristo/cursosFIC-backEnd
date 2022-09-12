package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Instrutor;

public interface InstrutorRepository extends PagingAndSortingRepository<Instrutor, Long> {
	@Query("SELECT t FROM Instrutor t WHERE t.nome LIKE %:p% ORDER BY t.nome ASC")
	public List<Instrutor>buscrInstrutor(@Param("p") String parameter);

	@Query("SELECT t FROM Instrutor t WHERE t.nome LIKE %:p% ORDER BY t.nome ASC")
	public List<Instrutor>buscarInstrutor(@Param("p") String parameter);
}
