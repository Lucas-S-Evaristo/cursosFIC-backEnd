package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Area;

public interface AreaRepository extends PagingAndSortingRepository<Area, Long> {
<<<<<<< HEAD
=======
	
@Query("SELECT a FROM Area a WHERE a.nome LIKE %:p% ORDER BY a.nome ASC")
public List<Area> buscarArea(@Param("p") String parametro);
>>>>>>> c2824697e40c4d9c776127304b72e9088a0cb416

}
