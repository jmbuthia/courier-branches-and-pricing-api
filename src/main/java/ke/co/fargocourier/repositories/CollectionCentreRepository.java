package ke.co.fargocourier.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ke.co.fargocourier.model.CollectionCentre;

@Repository
public interface CollectionCentreRepository extends CrudRepository<CollectionCentre, Long> {
	CollectionCentre findByName(String name);
	CollectionCentre findById(long id);
	List<CollectionCentre> findByBranchId(long branchId);
	@Query("select c from collection_centres c where CONCAT_WS('',c.description,c.id,c.name) like %?1%")
    Page<CollectionCentre> findByKeyWord(String keyWord,Pageable pageable);
}
