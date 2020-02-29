package ke.co.fargocourier.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ke.co.fargocourier.model.Branch;
import ke.co.fargocourier.model.CollectionCentre;

@Repository
public interface BranchRepository extends CrudRepository<Branch, Long>{
	Branch findByName(String name);
	Branch findById(long id);
	@Query("select b from branches b where CONCAT_WS('',b.description,b.id,b.name) like %?1%")
    Page<Branch> findByKeyWord(String keyWord,Pageable pageable);
	//@Query("SELECT b FROM branches b INNER JOIN collection_centres c WHERE b.name like %?1% or c.name like %?1%")
	//@Query("SELECT b FROM branches b INNER JOIN collection_centres c WHERE CONCAT_WS('',b.name,c.name) like %?1%")
	//@Query("SELECT b FROM branches b JOIN collection_centres c ON b.id= c.branch WHERE b.name like %?1% or c.name like %?1%")
	@Query("SELECT c FROM branches b JOIN collection_centres c ON b.id= c.branch WHERE CONCAT_WS('',b.name,c.name) like %?1%")
	Page<CollectionCentre> findBranchAndCollectionCentreBYKeyWord(String keyWord,Pageable pageable);
}
