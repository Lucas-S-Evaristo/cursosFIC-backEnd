package senai.CursosFic.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import senai.CursosFic.model.Log;

public interface FazerLogRepository extends PagingAndSortingRepository<Log, Long> {

}
