package ke.co.fargocourier.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ke.co.fargocourier.model.Branch;
import ke.co.fargocourier.model.BranchHeadSuburb;
import ke.co.fargocourier.model.Suburb;

@Repository
public interface BranchHeadSuburbRepository extends CrudRepository<BranchHeadSuburb, Long>{
	
	BranchHeadSuburb findById(long id);
	BranchHeadSuburb findBySuburb(Suburb suburb);
	BranchHeadSuburb findByBranch(Branch branch);
	@Query("SELECT b FROM branch_head_suburbs b WHERE b.branch=?1 and b.suburb=?2 ORDER BY b.startDate DESC ")
	Page<BranchHeadSuburb> findByBranchAndSuburbAndStartDateIsLatest(Branch branch,Suburb suburb,Pageable pageable);
	@Query("SELECT b FROM branch_head_suburbs b WHERE b.branch=?1 ORDER BY b.startDate DESC ")
	Page<BranchHeadSuburb> findByBranchAndStartDateIsLatest(Branch branch,Pageable pageable);
}
