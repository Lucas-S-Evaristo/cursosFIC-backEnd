package senai.CursosFic.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import senai.CursosFic.model.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{

}
