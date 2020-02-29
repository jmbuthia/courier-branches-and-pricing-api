package ke.co.fargocourier.service;

import ke.co.fargocourier.model.User;
import ke.co.fargocourier.model.dto.UserDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);
    
	Page<User> findByKeyWord(String keyWord, Pageable pageable);
  
}
