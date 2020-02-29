package ke.co.fargocourier.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import ke.co.fargocourier.model.StandingWeightCharge;

@Repository
public interface StandingWeightChargeRepository extends CrudRepository<StandingWeightCharge, Long> {

	StandingWeightCharge findByName(String name);
	StandingWeightCharge findById(long id);
	@Query("select s from standing_weight_charges s where CONCAT_WS('',s.description,s.id,s.name) like %?1%")
    Page<StandingWeightCharge> findByKeyWord(String keyWord,Pageable pageable);
}
