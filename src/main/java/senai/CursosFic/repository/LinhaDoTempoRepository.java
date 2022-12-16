package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.LinhaDoTempo;

public interface LinhaDoTempoRepository extends PagingAndSortingRepository<LinhaDoTempo, Long> {

	public List<LinhaDoTempo> findByTurmaId(Long id);
	
	@Query("SELECT l FROM LinhaDoTempo l where l.indice = :p and l.turma.id = :t")
	public LinhaDoTempo findByIndice(@Param("p") long indice, @Param("t") Long idTurma);
	
	@Query("SELECT l FROM LinhaDoTempo l where l.indice = :p - 1")
	public LinhaDoTempo removerAcao(@Param("p") long indice);
}
