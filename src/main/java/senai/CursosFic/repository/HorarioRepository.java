package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import senai.CursosFic.model.Horario;

public interface HorarioRepository extends PagingAndSortingRepository<Horario, Long> {
	
	@Query("SELECT h FROM Horario h ORDER BY h.id desc")
	public List<Horario> findAll();

}
