package ke.co.fargocourier.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ke.co.fargocourier.model.AdditionalCharge;
import ke.co.fargocourier.model.Suburb;

@Repository
public interface AdditionalChargeRepository extends CrudRepository<AdditionalCharge, Long> {

	
	AdditionalCharge findById(long id);
	AdditionalCharge findBySuburbFacilitatingAdditionalChargeId(Suburb suburb);
	AdditionalCharge findBySuburbWithAdditionalChargeId(Suburb suburb);
	@Query("SELECT a FROM additional_charges a "
			+ "WHERE a.suburbWithAdditionalChargeId=?1 "
			+ "and a.reviewDate < now() or a.reviewDate = now()"
			+ "ORDER BY a.reviewDate DESC")
	Page<AdditionalCharge> findLatestSuburbAdditionalCharge(Suburb suburbWithAdditionalCharge,Pageable pageable);
}
