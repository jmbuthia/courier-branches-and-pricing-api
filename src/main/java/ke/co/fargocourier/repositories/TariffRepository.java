package ke.co.fargocourier.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ke.co.fargocourier.model.Suburb;
import ke.co.fargocourier.model.Tariff;

@Repository
public interface TariffRepository extends CrudRepository<Tariff, Long> {
	Tariff findById(long id);
	Tariff findByDescription(String name);
	@Query("SELECT tariffs FROM tariffs tariffs WHERE "
			+ "tariffs.fromSuburbId=?1 "
			+ "and tariffs.toSuburbId=?2 "
			+ "and tariffs.reviewDate < now() or tariffs.reviewDate = now() "
			+ " ORDER BY tariffs.reviewDate DESC")
	Page<Tariff> findByFromSuburbIdAndToSuburbIdAndReviewDateIsLatest(Suburb fromSuburbId,Suburb toSuburbId,Pageable pageable);
	//TariffZone findByIdTariffZoneAndITariffZone(long fromBranch,long toBranch);
}
