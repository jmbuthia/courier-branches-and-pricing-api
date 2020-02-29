package ke.co.fargocourier.repositories;

import ke.co.fargocourier.model.User;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User saveAndFlush(User user);
    @Query("select u from users u where CONCAT_WS('',u.username,u.id,u.firstname,u.lastname) like %?1%")
    //@Query("from users where firstname like %?1%")
    Page<User> findByKeyWord(String keyWord,Pageable pageable);

}
