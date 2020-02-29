package ke.co.fargocourier.service.impl;


import ke.co.fargocourier.model.Role;
import ke.co.fargocourier.model.User;
import ke.co.fargocourier.model.dto.UserDto;
import ke.co.fargocourier.repositories.RoleRepository;
import ke.co.fargocourier.repositories.UserRepository;
import ke.co.fargocourier.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),  user.getPassword(), user.isEnabled(), 
				user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), getAuthority(user));
		//return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		 user.getRoles().forEach(role -> {
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
		//return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
    public User save(UserDto user) {
	    
		User initialUser=new User();
		
		System.out.println("normal password = "+user.getPassword()+"----------------------------------------------------------------");
		
		System.out.println("decoded password"+bcryptEncoder.encode(user.getPassword())+"-------------------------------------------------------------------------------------------");
		
		initialUser.setFirstname(user.getFirstname());
    	initialUser.setLastname(user.getLastname());
    	initialUser.setPassword(bcryptEncoder.encode(user.getPassword()));   	
    	initialUser.setUsername(user.getUsername());
    	initialUser.setProfile(user.getProfile());
    	initialUser.setDateOfBirth(user.getDateOfBirth());
    	initialUser.setAccountNonExpired(user.isAccountNonExpired());
    	initialUser.setAccountNonLocked(user.isAccountNonLocked());
    	initialUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
    	initialUser.setEnabled(user.isEnabled());
    	
		
		Set<Role> roles=new HashSet<>();
		for (Role role : user.getRoles()) {
			if(roleRepository.findByName(role.getName())==null){
				//no role added
			}else {
					
			roles.add(roleRepository.findByName(role.getName()));
			}
		}
		
		
		initialUser.setRoles(roles);
        return userRepository.saveAndFlush(initialUser);
    }

	@Override
	public Page<User> findByKeyWord(String keyWord, Pageable pageable) {
		
		return userRepository.findByKeyWord(keyWord,pageable);
	}

	
}
