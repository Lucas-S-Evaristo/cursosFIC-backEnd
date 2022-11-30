package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import senai.CursosFic.model.LinhaDoTempo;

public interface LinhaDoTempoRepository extends PagingAndSortingRepository<LinhaDoTempo, Long> {

	public List<LinhaDoTempo> findByTurmaId(Long id);
}
