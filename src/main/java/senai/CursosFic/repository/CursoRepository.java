package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {

	@Query("SELECT c FROM Curso c " + "WHERE c.nome LIKE %:p% " + "OR c.nivel LIKE %:p% OR c.preRequisito LIKE %:p% "
			+ "OR c.tipoAtendimento LIKE %:p% " + "OR c.area.nome LIKE %:p% OR c.objetivo LIKE %:p% OR c.sigla LIKE %:p% "
					+ "OR c.cargaHoraria LIKE %:p% OR c.valor LIKE %:p% ORDER BY c.nome ASC")
 
	public List<Curso> buscarCurso(@Param("p") String parametro);

	@Query("SELECT c FROM Curso c ORDER BY c.id desc")
	public List<Curso> findAll();
}
