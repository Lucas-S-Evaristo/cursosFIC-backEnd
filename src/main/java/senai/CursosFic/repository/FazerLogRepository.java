package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import senai.CursosFic.model.Log;

public interface FazerLogRepository extends PagingAndSortingRepository<Log, Long> {
	
	@Query("SELECT l FROM Log l where l.tipoLog = 0 ORDER BY l.id DESC")
	public List<Log> buscarLogArea();
	
	@Query("SELECT l FROM Log l where l.tipoLog = 1 ORDER BY l.id DESC")
	public List<Log> buscarLogCurso();
	
	@Query("SELECT l FROM Log l where l.tipoLog = 2 ORDER BY l.id DESC")
	public List<Log> buscarLogHorario();
	
	@Query("SELECT l FROM Log l where l.tipoLog = 3 ORDER BY l.id DESC")
	public List<Log> buscarLogInstrutor();
	
	@Query("SELECT l FROM Log l where l.tipoLog = 4 ORDER BY l.id DESC")
	public List<Log> buscarLogTurma();
	
	@Query("SELECT l FROM Log l where l.tipoLog = 5 ORDER BY l.id DESC")
	public List<Log> buscarLogUsuario();
	
	@Query("SELECT l FROM Log l WHERE l.tipoLog = 5 AND l.hora LIKE %:p%")
	public List<Log> pesquisarArea(@Param("p") String parametro);
	
	@Query("SELECT l FROM Log l WHERE l.logsEnum = 0 AND l.tipoLog = 0")
	public List<Log> listarCadastro();

}
