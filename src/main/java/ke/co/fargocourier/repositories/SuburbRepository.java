package ke.co.fargocourier.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ke.co.fargocourier.model.Branch;
import ke.co.fargocourier.model.Suburb;


@Repository
public interface SuburbRepository extends CrudRepository<Suburb, Long> {
	Suburb findByName(String name);
	Suburb findByNameAndBranch(String name,Branch branch);
	Suburb findById(long id);
	@Query("select s from suburbs s where CONCAT_WS('',s.description,s.id,s.name) like %?1%")
    Page<Suburb> findByKeyWord(String keyWord,Pageable pageable);
}
