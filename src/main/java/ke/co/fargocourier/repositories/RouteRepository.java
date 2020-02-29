package ke.co.fargocourier.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ke.co.fargocourier.model.Route;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
	Route findByName(String name);
	Route findById(long id);
	@Query("select r from routes r where CONCAT_WS('',r.description,r.id,r.name) like %?1%")
    Page<Route> findByKeyWord(String keyWord,Pageable pageable);

}
