package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import senai.CursosFic.model.Parametro;

public interface ParametroRepository extends PagingAndSortingRepository<Parametro, Long> {

	public Parametro findByPontoEquilibrio(Double parametro);
	
	List<Parametro> findAll();
}
