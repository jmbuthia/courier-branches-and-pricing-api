package ke.co.fargocourier.controller;

import ke.co.fargocourier.error.ApiError;
import ke.co.fargocourier.model.AdditionalCharge;
import ke.co.fargocourier.model.Branch;
import ke.co.fargocourier.model.BranchHeadSuburb;
import ke.co.fargocourier.model.CollectionCentre;
import ke.co.fargocourier.model.Role;
import ke.co.fargocourier.model.Route;
import ke.co.fargocourier.model.StandingWeightCharge;
import ke.co.fargocourier.model.Suburb;
import ke.co.fargocourier.model.Tariff;
import ke.co.fargocourier.model.User;
import ke.co.fargocourier.model.dto.AdditionalChargeDto;
import ke.co.fargocourier.model.dto.BranchDto;
import ke.co.fargocourier.model.dto.BranchHeadSuburbDto;
import ke.co.fargocourier.model.dto.Charge;
import ke.co.fargocourier.model.dto.CollectionCentreDto;
import ke.co.fargocourier.model.dto.CostingBranchJson;
import ke.co.fargocourier.model.dto.CostingSuburbJson;
import ke.co.fargocourier.model.dto.RouteDto;
import ke.co.fargocourier.model.dto.StandingWeightChargeDto;
import ke.co.fargocourier.model.dto.SuburbDto;
import ke.co.fargocourier.model.dto.TariffDto;
import ke.co.fargocourier.model.dto.UserDto;
import ke.co.fargocourier.model.dto.UserSignupDto;
import ke.co.fargocourier.repositories.AdditionalChargeRepository;
import ke.co.fargocourier.repositories.BranchHeadSuburbRepository;
import ke.co.fargocourier.repositories.BranchRepository;
import ke.co.fargocourier.repositories.CollectionCentreRepository;
import ke.co.fargocourier.repositories.RoleRepository;
import ke.co.fargocourier.repositories.RouteRepository;
import ke.co.fargocourier.repositories.StandingWeightChargeRepository;
import ke.co.fargocourier.repositories.SuburbRepository;
import ke.co.fargocourier.repositories.TariffRepository;
import ke.co.fargocourier.repositories.UserRepository;
import ke.co.fargocourier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    BranchRepository branchRepository;
    
    @Autowired
    RouteRepository routeRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    SuburbRepository suburbRepository;
    
    @Autowired
    CollectionCentreRepository collectionCentreRepository;
    
    @Autowired
    StandingWeightChargeRepository standingWeightChargeRepository;
    
    @Autowired
    BranchHeadSuburbRepository branchHeadSuburbRepository; 
    
    @Autowired
    TariffRepository tariffRepository;
    
    @Autowired
    AdditionalChargeRepository additionalChargeRepository;
    
    @Autowired
   	private BCryptPasswordEncoder bcryptEncoder;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }

  //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/suburbs", method = RequestMethod.GET)
    public List<Suburb> listSuburb(){
        return (List<Suburb>) suburbRepository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/collection-centres", method = RequestMethod.GET)
    public List<CollectionCentre> listCollectionCentres(){
        return (List<CollectionCentre>) collectionCentreRepository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branch-head-suburbs", method = RequestMethod.GET)
    public List<BranchHeadSuburb> listBranchHeadSuburbs(){
        return (List<BranchHeadSuburb>) branchHeadSuburbRepository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/additional-charges", method = RequestMethod.GET)
    public List<AdditionalCharge> listAdditionalCharges(){
        return (List<AdditionalCharge>) additionalChargeRepository.findAll();
    }
  
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/routes", method = RequestMethod.GET)
    public List<Route> listRoutes(){
        return (List<Route>) routeRepository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/roles", method = RequestMethod.GET)
    public List<Role> listRoles(){
        return (List<Role>) roleRepository.findAll();
    }
  
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/standing-weight-charges", method = RequestMethod.GET)
    public List<StandingWeightCharge> listStandingWeightCharges(){
        return (List<StandingWeightCharge>) standingWeightChargeRepository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branches", method = RequestMethod.GET)
    public List<Branch> listBranches(){
        return (List<Branch>) branchRepository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/tariffs", method = RequestMethod.GET)
    public List<Tariff> listTariffs(){
        return (List<Tariff>) tariffRepository.findAll();
    }
    
    //search by username, firstname,lastname,id
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users/search", method = RequestMethod.GET)
    public Page<User> listUsersByKeyWord(
    		@RequestParam Optional<String> keyword,
    		@RequestParam Optional<Integer> page,
    		@RequestParam Optional<Integer> size,
    		@RequestParam Optional<String> arrangeIn,
    		@RequestParam Optional<String> sortBy){
    	
    	//to solve errors where user input page as negative or size as zero and also sortby as name that is not a field in the object
    	
    	
    	
    	
    	Direction direction=Direction.ASC;
    	if(arrangeIn.isPresent()) {
    		
    		if(arrangeIn.get().equals("DESC")) {
    			direction=Direction.DESC;
    		}
    	}
    	
    	
    	Pageable searchedByKeyWord =  PageRequest.of(page.orElse(0), size.orElse(50), direction, sortBy.orElse("id"));
		//Pageable sortedByName =  PageRequest.of(0, 3, Direction.ASC,Sort.by("name"));
        //return userService.findByFirstname(firstname.orElse("_"),);
    	return userService.findByKeyWord(keyword.orElse("_"), searchedByKeyWord);
    }
    
    //search by id,description,name
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/routes/search", method = RequestMethod.GET)
    public Page<Route> listRoutesByKeyWord(
    		@RequestParam Optional<String> keyword,
    		@RequestParam Optional<Integer> page,
    		@RequestParam Optional<Integer> size,
    		@RequestParam Optional<String> arrangeIn,
    		@RequestParam Optional<String> sortBy){
    	
    	
    	
    	Direction direction=Direction.ASC;
    	if(arrangeIn.isPresent()) {
    		
    		if(arrangeIn.get().equals("DESC")) {
    			direction=Direction.DESC;
    		}
    	}
    	
    	
    	Pageable searchedByKeyWord =  PageRequest.of(page.orElse(0), size.orElse(50), direction, sortBy.orElse("id"));
		//Pageable sortedByName =  PageRequest.of(0, 3, Direction.ASC,Sort.by("name"));
        //return userService.findByFirstname(firstname.orElse("_"),);
    	return routeRepository.findByKeyWord(keyword.orElse("_"), searchedByKeyWord);
    }

    //search by id,description,name
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branches/search", method = RequestMethod.GET)
    public Page<Branch> listBrancesByKeyWord(
    		@RequestParam Optional<String> keyword,
    		@RequestParam Optional<Integer> page,
    		@RequestParam Optional<Integer> size,
    		@RequestParam Optional<String> arrangeIn,
    		@RequestParam Optional<String> sortBy){
    	
    	
    	
    	Direction direction=Direction.ASC;
    	if(arrangeIn.isPresent()) {
    		
    		if(arrangeIn.get().equals("DESC")) {
    			direction=Direction.DESC;
    		}
    	}
    	
    	
    	Pageable searchedByKeyWord =  PageRequest.of(page.orElse(0), size.orElse(50), direction, sortBy.orElse("id"));
    	return branchRepository.findByKeyWord(keyword.orElse("_"), searchedByKeyWord);
    }

    
    //search by id,description,name
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/suburbs/search", method = RequestMethod.GET)
    public Page<Suburb> listSuburbsByKeyWord(
    		@RequestParam Optional<String> keyword,
    		@RequestParam Optional<Integer> page,
    		@RequestParam Optional<Integer> size,
    		@RequestParam Optional<String> arrangeIn,
    		@RequestParam Optional<String> sortBy){
    	
    	
    	
    	Direction direction=Direction.ASC;
    	if(arrangeIn.isPresent()) {
    		
    		if(arrangeIn.get().equals("DESC")) {
    			direction=Direction.DESC;
    		}
    	}
    	
    	
    	Pageable searchedByKeyWord =  PageRequest.of(page.orElse(0), size.orElse(50), direction, sortBy.orElse("id"));
    	return suburbRepository.findByKeyWord(keyword.orElse("_"), searchedByKeyWord);
    }
    
    

    //search by id,description,name
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/collection-centres/search", method = RequestMethod.GET)
    public Page<CollectionCentre> listCollectionCentresByKeyWord(
    		@RequestParam Optional<String> keyword,
    		@RequestParam Optional<Integer> page,
    		@RequestParam Optional<Integer> size,
    		@RequestParam Optional<String> arrangeIn,
    		@RequestParam Optional<String> sortBy){
    	
    	
    	
    	Direction direction=Direction.ASC;
    	if(arrangeIn.isPresent()) {
    		
    		if(arrangeIn.get().equals("DESC")) {
    			direction=Direction.DESC;
    		}
    	}
    	
    	
    	Pageable searchedByKeyWord =  PageRequest.of(page.orElse(0), size.orElse(50), direction, sortBy.orElse("id"));
    	return collectionCentreRepository.findByKeyWord(keyword.orElse("_"), searchedByKeyWord);
    }
    

    //search by id,description,name
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/standing-weight-charges/search", method = RequestMethod.GET)
    public Page<StandingWeightCharge> listStandingWeightChargesByKeyWord(
    		@RequestParam Optional<String> keyword,
    		@RequestParam Optional<Integer> page,
    		@RequestParam Optional<Integer> size,
    		@RequestParam Optional<String> arrangeIn,
    		@RequestParam Optional<String> sortBy){
    	
    	
    	
    	Direction direction=Direction.ASC;
    	if(arrangeIn.isPresent()) {
    		
    		if(arrangeIn.get().equals("DESC")) {
    			direction=Direction.DESC;
    		}
    	}
    	
    	
    	Pageable searchedByKeyWord =  PageRequest.of(page.orElse(0), size.orElse(50), direction, sortBy.orElse("id"));
    	return standingWeightChargeRepository.findByKeyWord(keyword.orElse("_"), searchedByKeyWord);
    }
    

    //search by id,description,name
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branches-or-collection-centres/search", method = RequestMethod.GET)
    public Object listBranchOrCollectionCentresByKeyWord(
    		@RequestParam Optional<String> keyword,
    		@RequestParam Optional<Integer> page,
    		@RequestParam Optional<Integer> size,
    		@RequestParam Optional<String> arrangeIn,
    		@RequestParam Optional<String> sortBy){
    	
    	Page<CollectionCentre> obj;
    	
    	List<Object> object=new ArrayList<Object>();
    	
    	Direction direction=Direction.ASC;
    	if(arrangeIn.isPresent()) {
    		
    		if(arrangeIn.get().equals("DESC")) {
    			direction=Direction.DESC;
    		}
    	}
    	
    	
    	Pageable searchedByKeyWord =  PageRequest.of(page.orElse(0), size.orElse(50), direction, sortBy.orElse("id"));
    	obj= branchRepository.findBranchAndCollectionCentreBYKeyWord(keyword.orElse("_"), searchedByKeyWord);

 
    	Set<CollectionCentre> myObject=obj.toSet();
    	
    	for (CollectionCentre collectionCentre : myObject) {
    		
    		if(keyword.isPresent()){
    			
    			CharSequence charSequence=keyword.get().toUpperCase();
    			String collectionCentreName=collectionCentre.getName().toUpperCase();
    			if(collectionCentreName.contains(charSequence)) {
        			
    			object.add(collectionCentre);
    		}else {
    		
    			object.add(collectionCentre.getBranch());
    		}
    		
    		}else {
    			object.add(collectionCentre);
    		}
		}
    		object=object.stream().distinct().collect(Collectors.toList());
    	
    	return object;
    }
    
    

    //search by id,description,name
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/collection-centres-of-branch/{id}", method = RequestMethod.GET)
    public List<CollectionCentre> listCollectionCentresOfBranch(@PathVariable(value = "id") Long id){
       	return collectionCentreRepository.findByBranchId(id);
    }
    
    
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Object getOne(@PathVariable(value = "id") Long id){
    	
       // return userService.findById(id);
        User user=null;
        
        try {
        	user=userService.findById(id);
        
		}catch (Exception e) {
				//System.out.println(e);		
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No user with id = "+id+" that exist. ", errors));
    	
		}

		return user;

        
        
        
    }

    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteUser(@PathVariable(value = "id") Long id){
    	
    	try {
			userRepository.deleteById(id);
        
		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
    	
		}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}

		return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(new ApiError(HttpStatus.OK, "User with id = "+id+" is deleted successfully","Success"));
	
       
    }
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/route/{id}", method = RequestMethod.GET)
    public Object getOneRoute(@PathVariable(value = "id") Long id){
    	
        Route route;
        
        try {
        	route=routeRepository.findById(id).get();
        	
        
		}catch (Exception e) {
			//System.out.println("***********************"+e);		
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Route with id = "+id+" that exist. ", errors));
    	
		}

		return route;

        
        
        
        
    }
    
  //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/branch/{id}", method = RequestMethod.GET)
    public Object getOneBranch(@PathVariable(value = "id") Long id){
    	
    	Branch branch;
    	 try {
    		 branch= branchRepository.findById(id).get();
         	
         
 		}catch (Exception e) {
 						
 			List<String> errors=new ArrayList<String>();
     		errors.add(e.getMessage());
     		return ResponseEntity
     	            .status(HttpStatus.FORBIDDEN)
     	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Branch with id = "+id+" that exist. ", errors));
     	
 		}

 		return branch;

        
    }
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/branch/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteBranch(@PathVariable(value = "id") Long id){
    	
    	
    	 try {
    		branchRepository.deleteById(id);
         	
         
 		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
			errors.add(e.getMessage());
			return ResponseEntity
			        .status(HttpStatus.FORBIDDEN)
			        .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
			
			}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}
			
			return ResponseEntity
			    .status(HttpStatus.OK)
			    .body(new ApiError(HttpStatus.OK, "Branch with id = "+id+" is deleted successfully","Success"));


        
    }
  //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/suburb/{id}", method = RequestMethod.GET)
    public Object getOneSuburb(@PathVariable(value = "id") Long id){
    	Suburb suburb;
        
        
        try {
        	suburb= suburbRepository.findById(id).get();
        	
        
		}catch (Exception e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Suburb with id = "+id+" that exist. ", errors));
    	
		}

		return suburb;
    }
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/suburb/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteSuburb(@PathVariable(value = "id") Long id){
    	
    	
    	 try {
    		suburbRepository.deleteById(id);
         	
         
 		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
			errors.add(e.getMessage());
			return ResponseEntity
			        .status(HttpStatus.FORBIDDEN)
			        .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
			
			}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}
			
			return ResponseEntity
			    .status(HttpStatus.OK)
			    .body(new ApiError(HttpStatus.OK, "Suburb with id = "+id+" is deleted successfully","Success"));


        
    }
   //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/collection-centre/{id}", method = RequestMethod.GET)
    public Object getOneCollectionCentre(@PathVariable(value = "id") Long id){
        
        
        CollectionCentre collectionCentre;
        try {
        	collectionCentre= collectionCentreRepository.findById(id).get();
        	
        
		}catch (Exception e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Collection Centre with id = "+id+" that exist. ", errors));
    	
		}

		return collectionCentre;
    }
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/collection-centre/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteCollectionCentre(@PathVariable(value = "id") Long id){
    	
    	
    	 try {
    		 collectionCentreRepository.deleteById(id);
         	
         
 		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
			errors.add(e.getMessage());
			return ResponseEntity
			        .status(HttpStatus.FORBIDDEN)
			        .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
			
			}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}
			
			return ResponseEntity
			    .status(HttpStatus.OK)
			    .body(new ApiError(HttpStatus.OK, " Collection Centre with id = "+id+" is deleted successfully","Success"));


        
    }
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/standing-weight-charge/{id}", method = RequestMethod.GET)
    public Object getOneStandingWeightCharge(@PathVariable(value = "id") Long id){
        
        StandingWeightCharge standingWeightCharge;
        try {
        	standingWeightCharge= standingWeightChargeRepository.findById(id).get();
        	
        
		}catch (Exception e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Standing Weight Charge with id = "+id+" that exist. ", errors));
    	
		}

		return standingWeightCharge;
    }
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/standing-weight-charge/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteStandingWeightCharge(@PathVariable(value = "id") Long id){
    	
    	
    	 try {
    		 standingWeightChargeRepository.deleteById(id);
         	
         
 		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
			errors.add(e.getMessage());
			return ResponseEntity
			        .status(HttpStatus.FORBIDDEN)
			        .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
			
			}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}
			
			return ResponseEntity
			    .status(HttpStatus.OK)
			    .body(new ApiError(HttpStatus.OK, " Standing Weight Charge with id = "+id+" is deleted successfully","Success"));


        
    }
    
    
  //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/branch-head-suburb/{id}", method = RequestMethod.GET)
    public Object getOneSBranchHeadSuburb(@PathVariable(value = "id") Long id){
       
        BranchHeadSuburb branchHeadSuburb;
        try {
        	branchHeadSuburb= branchHeadSuburbRepository.findById(id).get();
        	
        
		}catch (Exception e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Branch Head Suburb with id = "+id+" that exist. ", errors));
    	
		}

		return branchHeadSuburb;
    }
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/branch-head-suburb/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteBranchHeadSuburb(@PathVariable(value = "id") Long id){
    	
    	
    	 try {
    		 branchHeadSuburbRepository.deleteById(id);
         	
         
 		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
			errors.add(e.getMessage());
			return ResponseEntity
			        .status(HttpStatus.FORBIDDEN)
			        .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
			
			}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}
			
			return ResponseEntity
			    .status(HttpStatus.OK)
			    .body(new ApiError(HttpStatus.OK, " Branch Head Suburb with id = "+id+" is deleted successfully","Success"));


        
    }
    
    
  //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/additional-charge/{id}", method = RequestMethod.GET)
    public Object getOneAdditionalCharge(@PathVariable(value = "id") Long id){
       
        AdditionalCharge additionalCharge;
        try {
        	additionalCharge= additionalChargeRepository.findById(id).get();
        	
        
		}catch (Exception e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Additional Charge with id = "+id+" that exist. ", errors));
    	
		}

		return additionalCharge;
    }
    
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/additional-charge/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteAdditionalCharge(@PathVariable(value = "id") Long id){
    	
    	
    	 try {
    		 additionalChargeRepository.deleteById(id);
         	
         
 		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
			errors.add(e.getMessage());
			return ResponseEntity
			        .status(HttpStatus.FORBIDDEN)
			        .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
			
			}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}
			
			return ResponseEntity
			    .status(HttpStatus.OK)
			    .body(new ApiError(HttpStatus.OK, " Additional Charge with id = "+id+" is deleted successfully","Success"));


        
    }
    
    
  //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/tariff/{id}", method = RequestMethod.GET)
    public Object getOneTariff(@PathVariable(value = "id") Long id){
       
        Tariff tariff;
        try {
        	tariff= tariffRepository.findById(id).get();
        	
        
		}catch (Exception e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Tariff with id = "+id+" that exist. ", errors));
    	
		}

		return tariff;
    }
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public Object getOneRole(@PathVariable(value = "id") Long id){
      
        Role role;
        try {
        	role= roleRepository.findById(id).get();
        	
        
		}catch (Exception e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "No Role with id = "+id+" that exist. ", errors));
    	
		}

		return role;
       
    }
    
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/route/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiError> deleteRoute(@PathVariable(value = "id") Long id){
    	
    	try {
			routeRepository.deleteById(id);
        
		}catch (EmptyResultDataAccessException e) {
						
			List<String> errors=new ArrayList<String>();
    		errors.add(e.getMessage());
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors));
    	
		}catch (DataIntegrityViolationException e) {
			//System.out.println(e.getCause().getMessage());	
	List<String> errors=new ArrayList<String>();
	errors.add(e.getCause().getMessage());
	return ResponseEntity
	        .status(HttpStatus.FORBIDDEN)
	        .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
	
	}

		return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(new ApiError(HttpStatus.OK, "Route with id = "+id+" is deleted successfully","Success"));
	
       
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveUser(@RequestBody UserSignupDto user){
    	
    	System.out.println("in signup method");
    	System.out.println(user);
    	//{""\"error\":\"Dublicate Entry\",\"message\":\"User with username= "+user.getUsername()+" already exists\"}"
    	

    	User initialUser=new User();
    	initialUser.setLastname(user.getLastname());
    	initialUser.setPassword(bcryptEncoder.encode(user.getPassword()));  
    	initialUser.setFirstname(user.getFirstname());
    	initialUser.setUsername(user.getUsername());
    	initialUser.setProfile(user.getProfile());
    	initialUser.setDateOfBirth(user.getDateOfBirth());
    	initialUser.setAccountNonExpired(user.isAccountNonExpired());
    	initialUser.setAccountNonLocked(user.isAccountNonLocked());
    	initialUser.setPhone(user.getPhone());
    	initialUser.setPhoneVerified(user.isPhoneVerified());
    	initialUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
    	initialUser.setEnabled(user.isEnabled());
    	
    	Set<Role> defaultRoles=new HashSet<Role>();
    	Role defaultRole =new Role();
    	defaultRole.setName("USER");
    	defaultRoles.add(defaultRole);    	
    	initialUser.setRoles(defaultRoles);
    	
    	if(user.getBranch()!=null) {
    		initialUser.setBranch(user.getBranch());
    	}
    	
    	
    	
    	
    	if(userRepository.findByUsername(user.getUsername())==null) {
    		
    		if(user.getBranch()!=null && (branchRepository.findByName(user.getBranch().getName())!=null
    				|| branchRepository.findById(user.getBranch().getId())!=null)) {
    			if(branchRepository.findById(user.getBranch().getId())!=null) {
    				initialUser.setBranch(branchRepository.findById(user.getBranch().getId()));
    			}else {
    			
    			initialUser.setBranch(branchRepository.findByName(user.getBranch().getName()));
    			}
    		}
    		
    		Set<Role> adminroles=new HashSet<>();
    		for (Role role : initialUser.getRoles()) {
    			adminroles.add(this.roleRepository.findByName(role.getName()));
    		}
    		initialUser.setRoles(adminroles);
    		
    		return userRepository.saveAndFlush(initialUser);
    	}else {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		//errors.add("User violation");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "User with username= "+user.getUsername()+" already exists", errors));
    	}
    	
      
    }
    
    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value="/user", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateUser(@RequestBody UserDto user){
    	
    	System.out.println("in update user method");
    	System.out.println(user);
    	//{""\"error\":\"Dublicate Entry\",\"message\":\"User with username= "+user.getUsername()+" already exists\"}"
    	

    	User savedUser=null;
    	User initialUser=new User();
    	initialUser.setId(user.getId());
    	initialUser.setLastname(user.getLastname());
    	initialUser.setFirstname(user.getFirstname());
    	initialUser.setUsername(user.getUsername());
    	initialUser.setProfile(user.getProfile());
    	initialUser.setDateOfBirth(user.getDateOfBirth());
    	initialUser.setAccountNonExpired(user.isAccountNonExpired());
    	initialUser.setAccountNonLocked(user.isAccountNonLocked());
    	initialUser.setPhone(user.getPhone());
    	initialUser.setPhoneVerified(user.isPhoneVerified());
    	initialUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
    	initialUser.setEnabled(user.isEnabled());   	
    	initialUser.setRoles(user.getRoles());
    	initialUser.setBranch(user.getBranch());
    	
    	
    	
    	
    	
    	if(userRepository.findById(user.getId())!=null) {
    		
    		if(user.getPassword()==null) {
        		initialUser.setPassword(bcryptEncoder.encode(userService.findById(user.getId()).getPassword()));  
        	}else {
        		initialUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        	}
    		
    		if(user.getBranch()!=null && (branchRepository.findByName(user.getBranch().getName())!=null
    				|| branchRepository.findById(user.getBranch().getId())!=null)) {
    			
    		}else {

        		List<String> errors=new ArrayList<String>();
        		errors.add("Branch doesnt exist");
        		//errors.add("User violation");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no branch with id or name = "+user.getBranch().getName() +" that exists", errors));
        	
    		}
    		try {
				savedUser=userRepository.save(initialUser);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
    		
    		return savedUser;
    	}else {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Unknown user");
    		//errors.add("User violation");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "User with Id = "+user.getId()+" doesn't exists", errors));
    	}
    	
      
    }
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/user", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object addUser(@RequestBody UserDto user){
    	
    	System.out.println("in signup method");
    	System.out.println(user);
    	//{""\"error\":\"Dublicate Entry\",\"message\":\"User with username= "+user.getUsername()+" already exists\"}"
    	

    	User initialUser=new User();
    	initialUser.setLastname(user.getLastname());
    	initialUser.setPassword(bcryptEncoder.encode(user.getPassword()));  
    	initialUser.setFirstname(user.getFirstname());
    	initialUser.setUsername(user.getUsername());
    	initialUser.setProfile(user.getProfile());
    	initialUser.setDateOfBirth(user.getDateOfBirth());
    	initialUser.setAccountNonExpired(user.isAccountNonExpired());
    	initialUser.setAccountNonLocked(user.isAccountNonLocked());
    	initialUser.setPhone(user.getPhone());
    	initialUser.setPhoneVerified(user.isPhoneVerified());
    	initialUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
    	initialUser.setEnabled(user.isEnabled());
    	initialUser.setRoles(user.getRoles());
    	if(user.getBranch()!=null) {
    		initialUser.setBranch(user.getBranch());
    	}
    	
    	
    	
    	
	if(userRepository.findByUsername(user.getUsername())==null) {
    		
    		if(user.getBranch()!=null && (branchRepository.findByName(user.getBranch().getName())!=null
    				|| branchRepository.findById(user.getBranch().getId())!=null)) {
    			if(branchRepository.findById(user.getBranch().getId())!=null) {
    				initialUser.setBranch(branchRepository.findById(user.getBranch().getId()));
    			}else {
    			
    			initialUser.setBranch(branchRepository.findByName(user.getBranch().getName()));
    			}
    		}
    		
    		Set<Role> adminroles=new HashSet<>();
    		for (Role role : initialUser.getRoles()) {
    			adminroles.add(this.roleRepository.findByName(role.getName()));
    		}
    		initialUser.setRoles(adminroles);
    		
    		return userRepository.saveAndFlush(initialUser);
    	}else {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		//errors.add("User violation");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "User with username= "+user.getUsername()+" already exists", errors));
    	}
    	
      
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/route", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveRoute(@RequestBody RouteDto routeDto){
    	
    	System.out.println("in save route method");
    	System.out.println(routeDto);
    	//{""\"error\":\"Dublicate Entry\",\"message\":\"User with username= "+user.getUsername()+" already exists\"}"
    	
    	Route route=new Route();
    	route.setDescription(routeDto.getDescription());
    	route.setName(routeDto.getName());
    	
    	if(routeRepository.findByName(route.getName())==null) {
    		
    		return routeRepository.save(route);
    	}else {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Route with  name= "+route.getName()+" already exists", errors));
    	}
    	
      
    }
    
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/route", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateRoute(@RequestBody RouteDto routeDto){
    	
    	System.out.println("in update route method");
    	System.out.println(routeDto);
    	
    	Route savedRoute=null;
    	
    	Route route=new Route();
    	route.setId(routeDto.getId());
    	route.setDescription(routeDto.getDescription());
    	route.setName(routeDto.getName());
    	
    	if(routeRepository.findById(route.getId())!=null) {
    		
    		try {
    			savedRoute=routeRepository.save(route);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
    		
    		return savedRoute;
    	}else {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Unknown route");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Route with  id = "+route.getId()+" doesn't exists", errors));
    	}
    	
      
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branch", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveBranch(@RequestBody BranchDto branchDto){
    	
    	System.out.println("in save Branch method");
    	System.out.println(branchDto);
    	//{""\"error\":\"Dublicate Entry\",\"message\":\"User with username= "+user.getUsername()+" already exists\"}"
    	
    	Branch savedbranch=null;
    	
    	Branch branch=new Branch();
    	branch.setDescription(branchDto.getDescription());
    	branch.setGpsLocation(branchDto.getGpsLocation());
    	branch.setImageUrl(branchDto.getImageUrl());
    	branch.setName(branchDto.getName());
    	branch.setPhone(branchDto.getPhone());
    	branch.setPhysicalLocation(branchDto.getPhysicalLocation());
    	branch.setRoute(branchDto.getRoute());
    	
    	if(branchRepository.findByName(branch.getName())==null) {
    		
    		if(routeRepository.findByName(branch.getRoute().getName())!=null ||
    				routeRepository.findById(branch.getRoute().getId())!=null) {
    			if(routeRepository.findByName(branch.getRoute().getName())!=null) {
    			branch.setRoute(routeRepository.findByName(branch.getRoute().getName()));
    			}else {
    				branch.setRoute(routeRepository.findById(branch.getRoute().getId()));
				}
    			
    			try {
    				savedbranch=branchRepository.save(branch);
    			}catch (DataIntegrityViolationException e) {
    				
    				List<String> errors=new ArrayList<String>();
            		errors.add(e.getRootCause().getMessage());
            		return ResponseEntity
            	            .status(HttpStatus.FORBIDDEN)
            	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
            	
				}
    			
    			return savedbranch;
    		}else {
    			List<String> errors=new ArrayList<String>();
        		errors.add("No such Route exists");
        		return ResponseEntity
        	            .status(HttpStatus.NOT_FOUND)
        	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no route with  name= "+branch.getRoute().getName()+" that exist. ", errors));
        	
    			
    		}
    		
    		
    	}else {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Branch with  name= "+branch.getName()+" already exists", errors));
    	}
    	
      
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branch", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateBranch(@RequestBody BranchDto branchDto){
    	
    	System.out.println("in update Branch method");
    	System.out.println(branchDto);
   
    	
    	Branch savedbranch=null;
    	
    	Branch branch=new Branch();
    	branch.setId(branchDto.getId());
    	branch.setDescription(branchDto.getDescription());
    	branch.setGpsLocation(branchDto.getGpsLocation());
    	branch.setImageUrl(branchDto.getImageUrl());
    	branch.setName(branchDto.getName());
    	branch.setPhone(branchDto.getPhone());
    	branch.setPhysicalLocation(branchDto.getPhysicalLocation());
    	branch.setRoute(branchDto.getRoute());
    	
    	if(branchRepository.findById(branch.getId())!=null) {
    		
    		if(routeRepository.findByName(branch.getRoute().getName())!=null ||
    				routeRepository.findById(branch.getRoute().getId())!=null) {
    			if(routeRepository.findByName(branch.getRoute().getName())!=null) {
    			branch.setRoute(routeRepository.findByName(branch.getRoute().getName()));
    			}else {
    				branch.setRoute(routeRepository.findById(branch.getRoute().getId()));
				}
    			
    			try {
    				savedbranch=branchRepository.save(branch);
    			}catch (DataIntegrityViolationException e) {
    				
    				List<String> errors=new ArrayList<String>();
            		errors.add(e.getRootCause().getMessage());
            		return ResponseEntity
            	            .status(HttpStatus.FORBIDDEN)
            	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
            	
				}
    			
    			return savedbranch;
    		}else {
    			List<String> errors=new ArrayList<String>();
        		errors.add("No such Route exists");
        		return ResponseEntity
        	            .status(HttpStatus.NOT_FOUND)
        	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no route with  name= "+branch.getRoute().getName()+" that exist. ", errors));
        	
    			
    		}
    		
    		
    	}else {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Unknown branch");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Branch with  id = "+branch.getId()+" doesn't exist", errors));
    	}
    	
      
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/suburb", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveSuburb(@RequestBody SuburbDto suburbDto){
    	
    	System.out.println("in save Suburb method");
    	System.out.println(suburbDto);
    	//{""\"error\":\"Dublicate Entry\",\"message\":\"User with username= "+user.getUsername()+" already exists\"}"
    	
    	Suburb savedSuburb=null;
    	
    	Suburb suburb=new Suburb();
    	suburb.setDescription(suburbDto.getDescription());
    	suburb.setName(suburbDto.getName());
    	suburb.setDateCreated(suburbDto.getDateCreated());
    	suburb.setBranch(suburbDto.getBranch());
    	suburb.setStandingWeightCharge(suburbDto.getStandingWeightCharge());
    	
    	Branch branch=null;
    	
    	if(branchRepository.findByName(suburbDto.getBranch().getName())==null
    		
    			&& branchRepository.findById(suburbDto.getBranch().getId())==null) {
    		
    		
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Branch exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no branch with  name = "+suburbDto.getBranch().getName()+" or id = "+suburbDto.getBranch().getId()+" that exist. ", errors));
    	
    		
    	}else {
    	
    		
    		if(branchRepository.findByName(suburbDto.getBranch().getName())!=null) {
    			
    			branch=branchRepository.findByName(suburbDto.getBranch().getName());
    		}else {
    			
				branch=branchRepository.findById(suburbDto.getBranch().getId());
				
			}
    		
    	}
    	StandingWeightCharge standingWeightCharge=null;
    	if(
    			standingWeightChargeRepository.findByName(suburbDto.getStandingWeightCharge().getName())==null
    			&&
    			standingWeightChargeRepository.findById(suburbDto.getStandingWeightCharge().getId())==null) {
    		
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("No such standing weight charge exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, 
    	            		"There is no standingWeightCharge with  name or id = "
    	            		+ ""+suburbDto.getStandingWeightCharge().getName()+""+suburbDto.getStandingWeightCharge().getId()+" that exist. ", errors));
    	
    		
    	}
    	else {
    		if(standingWeightChargeRepository.findByName(suburbDto.getStandingWeightCharge().getName())!=null) {
    			standingWeightCharge=standingWeightChargeRepository.findByName(suburbDto.getStandingWeightCharge().getName());
    		}else {
    			standingWeightCharge=standingWeightChargeRepository.findById(suburbDto.getStandingWeightCharge().getId());
    		}
    	}
    	if(suburbRepository.findByNameAndBranch(suburb.getName(),branch)==null) {
    	
    		//System.out.println("in suburbRepository.findByNameAndBranch(suburb.getName(),suburbDto.getBranch())==null method");
    		try {
    			suburb.setBranch(branch);
    			suburb.setStandingWeightCharge(standingWeightCharge);
    			savedSuburb=suburbRepository.save(suburb);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedSuburb;
		
			
    	}else {
    		

    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Suburb with  name= "+suburb.getName()+" already exists in branch "+branch.getName(), errors));
    	
			
		}
    	
      
    }
   
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/suburb", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateSuburb(@RequestBody SuburbDto suburbDto){
    	
    	System.out.println("in update Suburb method");
    	System.out.println(suburbDto);
    	
    	
    	Suburb savedSuburb=null;
    	
    	Suburb suburb=new Suburb();
    	suburb.setId(suburbDto.getId());
    	suburb.setDescription(suburbDto.getDescription());
    	suburb.setName(suburbDto.getName());
    	suburb.setDateCreated(suburbDto.getDateCreated());
    	suburb.setBranch(suburbDto.getBranch());
    	suburb.setStandingWeightCharge(suburbDto.getStandingWeightCharge());
    	
    	Branch branch=null;
    	
    	if(branchRepository.findByName(suburbDto.getBranch().getName())==null
    		
    			&& branchRepository.findById(suburbDto.getBranch().getId())==null) {
    		
    		
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Branch exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no branch with  name = "+suburbDto.getBranch().getName()+" or id = "+suburbDto.getBranch().getId()+" that exist. ", errors));
    	
    		
    	}else {
    	
    		
    		if(branchRepository.findByName(suburbDto.getBranch().getName())!=null) {
    			
    			branch=branchRepository.findByName(suburbDto.getBranch().getName());
    		}else {
    			
				branch=branchRepository.findById(suburbDto.getBranch().getId());
				
			}
    		
    	}
    	StandingWeightCharge standingWeightCharge=null;
    	if(
    			standingWeightChargeRepository.findByName(suburbDto.getStandingWeightCharge().getName())==null
    			&&
    			standingWeightChargeRepository.findById(suburbDto.getStandingWeightCharge().getId())==null) {
    		
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("No such standing weight charge exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, 
    	            		"There is no standingWeightCharge with  name or id = "
    	            		+ ""+suburbDto.getStandingWeightCharge().getName()+""+suburbDto.getStandingWeightCharge().getId()+" that exist. ", errors));
    	
    		
    	}
    	else {
    		if(standingWeightChargeRepository.findByName(suburbDto.getStandingWeightCharge().getName())!=null) {
    			standingWeightCharge=standingWeightChargeRepository.findByName(suburbDto.getStandingWeightCharge().getName());
    		}else {
    			standingWeightCharge=standingWeightChargeRepository.findById(suburbDto.getStandingWeightCharge().getId());
    		}
    	}
    	if(suburbRepository.findById(suburb.getId())!=null) {
    	
    		
    		try {
    			suburb.setBranch(branch);
    			suburb.setStandingWeightCharge(standingWeightCharge);
    			savedSuburb=suburbRepository.save(suburb);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedSuburb;
		
			
    	}else {
    		

    		List<String> errors=new ArrayList<String>();
    		errors.add("Unknown Suburb");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Suburb with  id= "+suburb.getId()+" doesn't exist", errors));
    	
			
		}
    	
      
    }
   
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/standing-weight-charge", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveStandingWeightCharge(@RequestBody StandingWeightChargeDto standingWeightChargeDto){
    	
    	System.out.println("in save StandingWeightCharge method");
    	System.out.println(standingWeightChargeDto);
    	//{""\"error\":\"Dublicate Entry\",\"message\":\"User with username= "+user.getUsername()+" already exists\"}"
    	
    	
    	StandingWeightCharge savedStandingWeightCharge=null;
    	
    	
    	StandingWeightCharge standingWeightCharge=new StandingWeightCharge();
    	standingWeightCharge.setDateUpdated(standingWeightChargeDto.getDateUpdated());
    	standingWeightCharge.setName(standingWeightChargeDto.getName());
    	standingWeightCharge.setDescription(standingWeightChargeDto.getDescription());
    	standingWeightCharge.setExtraPerKgAmount(standingWeightChargeDto.getExtraPerKgAmount());
    	standingWeightCharge.setStandingMaxWeightCharged(standingWeightChargeDto.getStandingMaxWeightCharged());
    	standingWeightCharge.setStandingMaxWeightNotCharged(standingWeightChargeDto.getStandingMaxWeightNotCharged());
    
    	
    	if(standingWeightChargeRepository.findByName(standingWeightCharge.getName())!=null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Standing Weight Charge with  name= "+standingWeightCharge.getName()+" already exists.", errors));
    	
    		
    	}else if(standingWeightCharge.getExtraPerKgAmount()<0) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("standing weight charge can't be negative");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, 
    	            		"standing weight charge can't be negative ", errors));
    	
    		
    	}else if(standingWeightCharge.getStandingMaxWeightCharged()>standingWeightCharge.getStandingMaxWeightNotCharged()) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("weight not charge should be greater that weight charged");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, 
    	            		"weight not charge should be greater that weight charged ", errors));
    	
    		
    	}else {
    	
    		//System.out.println("in suburbRepository.findByNameAndBranch(suburb.getName(),suburbDto.getBranch())==null method");
    		try {
    			
    			savedStandingWeightCharge=standingWeightChargeRepository.save(standingWeightCharge);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedStandingWeightCharge;
		
			
    	}
    	
      
    }
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/standing-weight-charge", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateStandingWeightCharge(@RequestBody StandingWeightChargeDto standingWeightChargeDto){
    	
    	System.out.println("in update StandingWeightCharge method");
    	System.out.println(standingWeightChargeDto);
    	
    	
    	
    	StandingWeightCharge savedStandingWeightCharge=null;
    	
    	
    	StandingWeightCharge standingWeightCharge=new StandingWeightCharge();
    	standingWeightCharge.setId(standingWeightChargeDto.getId());
    	standingWeightCharge.setDateUpdated(standingWeightChargeDto.getDateUpdated());
    	standingWeightCharge.setName(standingWeightChargeDto.getName());
    	standingWeightCharge.setDescription(standingWeightChargeDto.getDescription());
    	standingWeightCharge.setExtraPerKgAmount(standingWeightChargeDto.getExtraPerKgAmount());
    	standingWeightCharge.setStandingMaxWeightCharged(standingWeightChargeDto.getStandingMaxWeightCharged());
    	standingWeightCharge.setStandingMaxWeightNotCharged(standingWeightChargeDto.getStandingMaxWeightNotCharged());
    
    	
    	if(standingWeightChargeRepository.findById(standingWeightCharge.getId())==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Unknown Standing Weight Charge");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Standing Weight Charge with  id = "+standingWeightCharge.getId()+" doesn't exist.", errors));
    	
    		
    	}else if(standingWeightCharge.getExtraPerKgAmount()<0) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("standing weight charge can't be negative");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, 
    	            		"standing weight charge can't be negative ", errors));
    	
    		
    	}else if(standingWeightCharge.getStandingMaxWeightCharged()>standingWeightCharge.getStandingMaxWeightNotCharged()) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("weight not charge should be greater that weight charged");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, 
    	            		"weight not charge should be greater that weight charged ", errors));
    	
    		
    	}else {
    	
    		//System.out.println("in suburbRepository.findByNameAndBranch(suburb.getName(),suburbDto.getBranch())==null method");
    		try {
    			
    			savedStandingWeightCharge=standingWeightChargeRepository.save(standingWeightCharge);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedStandingWeightCharge;
		
			
    	}
    	
      
    }
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/collection-centre", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveCollectionCentre(@RequestBody CollectionCentreDto collectionCentre){
    	
    	System.out.println("in save collectionCentre method");
    	System.out.println(collectionCentre);
    	    	
    	
    	CollectionCentre savedCollectionCentre=null;
    	
    	
    	CollectionCentre myCollectionCentre=new CollectionCentre();
    	myCollectionCentre.setBranch(collectionCentre.getBranch());
    	myCollectionCentre.setDescription(collectionCentre.getDescription());
    	myCollectionCentre.setGpsLocation(collectionCentre.getGpsLocation());
    	myCollectionCentre.setImageUrl(collectionCentre.getImageUrl());
    	myCollectionCentre.setName(collectionCentre.getName());
    	myCollectionCentre.setPhone(collectionCentre.getPhone());
    	myCollectionCentre.setPhysicalLocation(collectionCentre.getPhysicalLocation());
        
    	
    	Branch branch=null;
    	
    	if(collectionCentreRepository.findByName(myCollectionCentre.getName())!=null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Collection Centre with  name= "+myCollectionCentre.getName()+" already exists.", errors));
    	
    		
    	}else {
    	
 
    	
    	if(branchRepository.findByName(myCollectionCentre.getBranch().getName())==null
    		
    			&& branchRepository.findById(myCollectionCentre.getBranch().getId())==null) {
    		
    		
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Branch exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no branch with  name = "+myCollectionCentre.getBranch().getName()+" or id = "+myCollectionCentre.getBranch().getId()+" that exist. ", errors));
    	
    		
    	}else {
    	
    		
    		if(branchRepository.findByName(myCollectionCentre.getBranch().getName())!=null) {
    			
    			branch=branchRepository.findByName(myCollectionCentre.getBranch().getName());
    		}else {
    			
				branch=branchRepository.findById(myCollectionCentre.getBranch().getId());
				
			}
    		
    		

    		try {
    			myCollectionCentre.setBranch(branch);
    			
    			savedCollectionCentre=collectionCentreRepository.save(myCollectionCentre);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedCollectionCentre;
		
			
    	
    		
    	}
    	
    	
    }
    	
   
    	
      
    }
  
 
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/collection-centre", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateCollectionCentre(@RequestBody CollectionCentreDto collectionCentre){
    	
    	System.out.println("in update collectionCentre method");
    	System.out.println(collectionCentre);
    	    	
    	
    	CollectionCentre savedCollectionCentre=null;
    	
    	
    	CollectionCentre myCollectionCentre=new CollectionCentre();
    	myCollectionCentre.setId(collectionCentre.getId());
    	myCollectionCentre.setBranch(collectionCentre.getBranch());
    	myCollectionCentre.setDescription(collectionCentre.getDescription());
    	myCollectionCentre.setGpsLocation(collectionCentre.getGpsLocation());
    	myCollectionCentre.setImageUrl(collectionCentre.getImageUrl());
    	myCollectionCentre.setName(collectionCentre.getName());
    	myCollectionCentre.setPhone(collectionCentre.getPhone());
    	myCollectionCentre.setPhysicalLocation(collectionCentre.getPhysicalLocation());
        
    	
    	Branch branch=null;
    	
    	if(collectionCentreRepository.findById(myCollectionCentre.getId())==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Unknown Collection Centre");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Collection Centre with  id = "+myCollectionCentre.getId()+" doesn't exist.", errors));
    	
    		
    	}else {
    	
 
    	
    	if(branchRepository.findByName(myCollectionCentre.getBranch().getName())==null
    		
    			&& branchRepository.findById(myCollectionCentre.getBranch().getId())==null) {
    		
    		
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Branch exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no branch with  name = "+myCollectionCentre.getBranch().getName()+" or id = "+myCollectionCentre.getBranch().getId()+" that exist. ", errors));
    	
    		
    	}else {
    	
    		
    		if(branchRepository.findByName(myCollectionCentre.getBranch().getName())!=null) {
    			
    			branch=branchRepository.findByName(myCollectionCentre.getBranch().getName());
    		}else {
    			
				branch=branchRepository.findById(myCollectionCentre.getBranch().getId());
				
			}
    		
    		

    		try {
    			myCollectionCentre.setBranch(branch);
    			
    			savedCollectionCentre=collectionCentreRepository.save(myCollectionCentre);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedCollectionCentre;
		
			
    	
    		
    	}
    	
    	
    }
    	
   
    	
      
    }
  
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branch-head-suburb", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveBranchHeadSuburb(@RequestBody BranchHeadSuburbDto branchHeadSuburb){
    	
    	System.out.println("in save branchHeadSuburb method");
    	System.out.println(branchHeadSuburb);
    	    	
    	
    	BranchHeadSuburb savedBranchHeadSuburb=null;
    	
    	
    	BranchHeadSuburb myBranchHeadSuburb=new BranchHeadSuburb();
    	myBranchHeadSuburb.setBranch(branchHeadSuburb.getBranch());
    	myBranchHeadSuburb.setDescription(branchHeadSuburb.getDescription());
    	myBranchHeadSuburb.setStartDate(branchHeadSuburb.getStartDate());
    	myBranchHeadSuburb.setSuburb(branchHeadSuburb.getSuburb());
        
    	
    	Branch branch=null;
    	Suburb suburb=null;
    	
    	if(branchRepository.findByName(myBranchHeadSuburb.getBranch().getName())!=null
    			|| branchRepository.findById(myBranchHeadSuburb.getBranch().getId())!=null) {
    		

    		if(branchRepository.findByName(myBranchHeadSuburb.getBranch().getName())!=null) {
    			
    			branch=branchRepository.findByName(myBranchHeadSuburb.getBranch().getName());
    		}else {
    			
				branch=branchRepository.findById(myBranchHeadSuburb.getBranch().getId());
				
			}
    		
    		
    	}else {

    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Branch exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no branch with  name = "+myBranchHeadSuburb.getBranch().getName()+" or id = "+myBranchHeadSuburb.getBranch().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	
    	if(suburbRepository.findByName(myBranchHeadSuburb.getSuburb().getName())!=null
    			|| suburbRepository.findById(myBranchHeadSuburb.getSuburb().getId())!=null) {
    		


    		if(suburbRepository.findByName(myBranchHeadSuburb.getSuburb().getName())!=null) {
    			
    			suburb=suburbRepository.findByName(myBranchHeadSuburb.getSuburb().getName());
    		}else {
    			
				suburb=suburbRepository.findById(myBranchHeadSuburb.getSuburb().getId());
				
			}
    		
    		
    	}else {


    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myBranchHeadSuburb.getSuburb().getName()+" or id = "+myBranchHeadSuburb.getSuburb().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	
    	
    	
    	
    if(branchHeadSuburbRepository.findByBranch(branch)!=null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Branch  with name= "+branch.getName()+" or with id = "+branch.getId()+" already exists.", errors));
    	
    		
    	}else if(branchHeadSuburbRepository.findBySuburb(suburb)!=null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Dublicate Entry");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Suburb  with name= "+suburb.getName()+" or with id = "+suburb.getId()+" already exists.", errors));
    	
    		
    	}
    	
    	else {
    	
    		
    		

    		try {
    			myBranchHeadSuburb.setBranch(branch);
    			myBranchHeadSuburb.setSuburb(suburb);
    			
    			savedBranchHeadSuburb=branchHeadSuburbRepository.save(myBranchHeadSuburb);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedBranchHeadSuburb;
		
			
    	
    	
    	
    }
    	
   
    	
      
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/branch-head-suburb", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateBranchHeadSuburb(@RequestBody BranchHeadSuburbDto branchHeadSuburb){
    	
    	System.out.println("in update branchHeadSuburb method");
    	System.out.println(branchHeadSuburb);
    	    	
    	
    	BranchHeadSuburb savedBranchHeadSuburb=null;
    	
    	
    	BranchHeadSuburb myBranchHeadSuburb=new BranchHeadSuburb();
    	myBranchHeadSuburb.setId(branchHeadSuburb.getId());
    	myBranchHeadSuburb.setBranch(branchHeadSuburb.getBranch());
    	myBranchHeadSuburb.setDescription(branchHeadSuburb.getDescription());
    	myBranchHeadSuburb.setStartDate(branchHeadSuburb.getStartDate());
    	myBranchHeadSuburb.setSuburb(branchHeadSuburb.getSuburb());
        
    	
    	Branch branch=null;
    	Suburb suburb=null;
    	
    	if(branchRepository.findByName(myBranchHeadSuburb.getBranch().getName())!=null
    			|| branchRepository.findById(myBranchHeadSuburb.getBranch().getId())!=null) {
    		

    		if(branchRepository.findByName(myBranchHeadSuburb.getBranch().getName())!=null) {
    			
    			branch=branchRepository.findByName(myBranchHeadSuburb.getBranch().getName());
    		}else {
    			
				branch=branchRepository.findById(myBranchHeadSuburb.getBranch().getId());
				
			}
    		
    		
    	}else {

    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Branch exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no branch with  name = "+myBranchHeadSuburb.getBranch().getName()+" or id = "+myBranchHeadSuburb.getBranch().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	
    	if(suburbRepository.findByName(myBranchHeadSuburb.getSuburb().getName())!=null
    			|| suburbRepository.findById(myBranchHeadSuburb.getSuburb().getId())!=null) {
    		


    		if(suburbRepository.findByName(myBranchHeadSuburb.getSuburb().getName())!=null) {
    			
    			suburb=suburbRepository.findByName(myBranchHeadSuburb.getSuburb().getName());
    		}else {
    			
				suburb=suburbRepository.findById(myBranchHeadSuburb.getSuburb().getId());
				
			}
    		
    		
    	}else {


    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myBranchHeadSuburb.getSuburb().getName()+" or id = "+myBranchHeadSuburb.getSuburb().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	
    	
    	
    	
    if(branchHeadSuburbRepository.findById(branchHeadSuburb.getId())==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("Unknown Branch Head Suburb");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Branch  head suburb with id = "+branchHeadSuburb.getId()+" doesn't exist.", errors));
    	
    		
    	}
    	
    	else {
    	
    		if(branch.getName().equals(suburb.getBranch().getName())) {
    			
    		try {
    			myBranchHeadSuburb.setBranch(branch);
    			myBranchHeadSuburb.setSuburb(suburb);
    			
    			savedBranchHeadSuburb=branchHeadSuburbRepository.save(myBranchHeadSuburb);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedBranchHeadSuburb;
		
			
    	
    	}else {
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("Suburb should belong to that Branch");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "Branch  doesn't have suburb with name = "+suburb.getName()+" that exist.", errors));
    	
    }
    	
    	}
    	
      
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/additional-charge", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveAdditionalCharge(@RequestBody AdditionalChargeDto additionalChargeDto){
    	
    	System.out.println("in save additionalCharge method");
    	System.out.println(additionalChargeDto);
    	    	
    	
    	AdditionalCharge savedAdditionalChargeDto=null;
    	
    	
    	AdditionalCharge myAdditionalCharge=new AdditionalCharge();
    	myAdditionalCharge.setAmount(additionalChargeDto.getAmount());
    	myAdditionalCharge.setDescription(additionalChargeDto.getDescription());
    	myAdditionalCharge.setReviewDate(additionalChargeDto.getReviewDate());
    	myAdditionalCharge.setSuburbFacilitatingAdditionalChargeId(additionalChargeDto.getSuburbFacilitatingAdditionalChargeId());
    	myAdditionalCharge.setSuburbWithAdditionalChargeId(additionalChargeDto.getSuburbWithAdditionalChargeId());
    	
        
    	
    	Suburb suburbFacilitatingAdditionalCharge=null;
    	Suburb suburbWithAdditionalCharge=null;
    	    	
    	
    	if(suburbRepository.findByName(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName())!=null
    			|| suburbRepository.findById(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getId())!=null) {
    		


    		if(suburbRepository.findByName(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName())!=null) {
    			
    			suburbFacilitatingAdditionalCharge=suburbRepository.findByName(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName());
    		}else {
    			
    			suburbFacilitatingAdditionalCharge=suburbRepository.findById(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getId());
				
			}
    		
    		
    	}else {


    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName()+" or id = "+myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	if(suburbRepository.findByName(myAdditionalCharge.getSuburbWithAdditionalChargeId().getName())!=null
    			|| suburbRepository.findById(myAdditionalCharge.getSuburbWithAdditionalChargeId().getId())!=null) {
    		


    		if(suburbRepository.findByName(myAdditionalCharge.getSuburbWithAdditionalChargeId().getName())!=null) {
    			
    			suburbWithAdditionalCharge=suburbRepository.findByName(myAdditionalCharge.getSuburbWithAdditionalChargeId().getName());
    		}else {
    			
    			suburbWithAdditionalCharge=suburbRepository.findById(myAdditionalCharge.getSuburbWithAdditionalChargeId().getId());
				
			}
    		
    		
    	}else {


    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myAdditionalCharge.getSuburbWithAdditionalChargeId().getName()+" or id = "+myAdditionalCharge.getSuburbWithAdditionalChargeId().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	  if(!suburbFacilitatingAdditionalCharge.getBranch().equals(suburbWithAdditionalCharge.getBranch())) {
      		List<String> errors=new ArrayList<String>();
      		errors.add("Suburbs not from the same branch.");
      		return ResponseEntity
      	            .status(HttpStatus.FORBIDDEN)
      	          .body(new ApiError(HttpStatus.FORBIDDEN, "Suburbs must be from the same branch.", errors));
      	
      		
      	} 
    	  
    	  else if(!	branchHeadSuburbRepository.findByBranchAndSuburbAndStartDateIsLatest(suburbFacilitatingAdditionalCharge.getBranch(), 
        			suburbFacilitatingAdditionalCharge, PageRequest.of(0, 1)).get().iterator().next().getBranch().equals(suburbFacilitatingAdditionalCharge.getBranch())) {
      	
      		
        		List<String> errors=new ArrayList<String>();
        		errors.add("Suburb not head of branch.");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Suburb facilitating additional charge"
        	            		+ " with name = "+suburbFacilitatingAdditionalCharge.getName()+""
        	            				+ " must be head of branch with name = "+suburbFacilitatingAdditionalCharge.getBranch().getName(), errors));
        	
        		
        	} 
    	else {
    	
    		if(myAdditionalCharge.getAmount()<0) {

        		List<String> errors=new ArrayList<String>();
        		errors.add("Amount less than required.");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Ammount should not be less than zero. ", errors));
        	
        		
    		}else {

    		try {
    			myAdditionalCharge.setSuburbFacilitatingAdditionalChargeId(suburbFacilitatingAdditionalCharge);
    			myAdditionalCharge.setSuburbWithAdditionalChargeId(suburbWithAdditionalCharge);
    			
    			savedAdditionalChargeDto=additionalChargeRepository.save(myAdditionalCharge);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedAdditionalChargeDto;
		
			
    	
    	}
    	
    }
    	
   
    	
      
    }
    
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/additional-charge", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object updateAdditionalCharge(@RequestBody AdditionalChargeDto additionalChargeDto){
    	
    	System.out.println("in update additionalCharge method");
    	System.out.println(additionalChargeDto);
    	    	
    	
    	AdditionalCharge savedAdditionalChargeDto=null;
    	
    	
    	AdditionalCharge myAdditionalCharge=new AdditionalCharge();
    	myAdditionalCharge.setId(additionalChargeDto.getId());
    	myAdditionalCharge.setAmount(additionalChargeDto.getAmount());
    	myAdditionalCharge.setDescription(additionalChargeDto.getDescription());
    	myAdditionalCharge.setReviewDate(additionalChargeDto.getReviewDate());
    	myAdditionalCharge.setSuburbFacilitatingAdditionalChargeId(additionalChargeDto.getSuburbFacilitatingAdditionalChargeId());
    	myAdditionalCharge.setSuburbWithAdditionalChargeId(additionalChargeDto.getSuburbWithAdditionalChargeId());
    	
        
    	
    	Suburb suburbFacilitatingAdditionalCharge=null;
    	Suburb suburbWithAdditionalCharge=null;
    	    	
    	
    	if(suburbRepository.findByName(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName())!=null
    			|| suburbRepository.findById(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getId())!=null) {
    		


    		if(suburbRepository.findByName(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName())!=null) {
    			
    			suburbFacilitatingAdditionalCharge=suburbRepository.findByName(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName());
    		}else {
    			
    			suburbFacilitatingAdditionalCharge=suburbRepository.findById(myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getId());
				
			}
    		
    		
    	}else {


    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getName()+" or id = "+myAdditionalCharge.getSuburbFacilitatingAdditionalChargeId().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	if(suburbRepository.findByName(myAdditionalCharge.getSuburbWithAdditionalChargeId().getName())!=null
    			|| suburbRepository.findById(myAdditionalCharge.getSuburbWithAdditionalChargeId().getId())!=null) {
    		


    		if(suburbRepository.findByName(myAdditionalCharge.getSuburbWithAdditionalChargeId().getName())!=null) {
    			
    			suburbWithAdditionalCharge=suburbRepository.findByName(myAdditionalCharge.getSuburbWithAdditionalChargeId().getName());
    		}else {
    			
    			suburbWithAdditionalCharge=suburbRepository.findById(myAdditionalCharge.getSuburbWithAdditionalChargeId().getId());
				
			}
    		
    		
    	}else {


    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myAdditionalCharge.getSuburbWithAdditionalChargeId().getName()+" or id = "+myAdditionalCharge.getSuburbWithAdditionalChargeId().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	  if(!suburbFacilitatingAdditionalCharge.getBranch().equals(suburbWithAdditionalCharge.getBranch())) {
      		List<String> errors=new ArrayList<String>();
      		errors.add("Suburbs not from the same branch.");
      		return ResponseEntity
      	            .status(HttpStatus.FORBIDDEN)
      	          .body(new ApiError(HttpStatus.FORBIDDEN, "Suburbs must be from the same branch.", errors));
      	
      		
      	} 
    	  
    	  else if(!	branchHeadSuburbRepository.findByBranchAndSuburbAndStartDateIsLatest(suburbFacilitatingAdditionalCharge.getBranch(), 
        			suburbFacilitatingAdditionalCharge, PageRequest.of(0, 1)).get().iterator().next().getBranch().equals(suburbFacilitatingAdditionalCharge.getBranch())) {
      	
      		
        		List<String> errors=new ArrayList<String>();
        		errors.add("Suburb not head of branch.");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Suburb facilitating additional charge"
        	            		+ " with name = "+suburbFacilitatingAdditionalCharge.getName()+""
        	            				+ " must be head of branch with name = "+suburbFacilitatingAdditionalCharge.getBranch().getName(), errors));
        	
        		
        	} 
    	else {
    	
    		if(additionalChargeRepository.findById(myAdditionalCharge.getId())==null) {
    			

        		List<String> errors=new ArrayList<String>();
        		errors.add("Unknown Additional charge.");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Additional Charge with id = "+myAdditionalCharge.getId()+" doesn't exist.", errors));
        	
    			
    		}else {
    			if(myAdditionalCharge.getAmount()<0) {

            		List<String> errors=new ArrayList<String>();
            		errors.add("Amount less than required.");
            		return ResponseEntity
            	            .status(HttpStatus.FORBIDDEN)
            	            .body(new ApiError(HttpStatus.FORBIDDEN, "Ammount should not be less than zero. ", errors));
            	
            		
        		}else {

    		try {
    			myAdditionalCharge.setSuburbFacilitatingAdditionalChargeId(suburbFacilitatingAdditionalCharge);
    			myAdditionalCharge.setSuburbWithAdditionalChargeId(suburbWithAdditionalCharge);
    			
    			savedAdditionalChargeDto=additionalChargeRepository.save(myAdditionalCharge);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedAdditionalChargeDto;
		
			
    	
    		}
    	
    }
    	
    }
    	
      
    }
    
    
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/tariff", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public Object saveTariff(@RequestBody TariffDto tariffDto){
    	
    	System.out.println("in save Tariff method");
    	System.out.println(tariffDto);
    	    	
    	
    	Tariff savedTariffDto=null;
    	
    	
    	Tariff myTariff=new Tariff();
    	myTariff.setAmount(tariffDto.getAmount());
    	myTariff.setDescription(tariffDto.getDescription());
    	myTariff.setFromSuburbId(tariffDto.getFromSuburbId());
    	myTariff.setReviewDate(tariffDto.getReviewDate());
    	myTariff.setToSuburbId(tariffDto.getToSuburbId());
    
    	
        
    	
    	Suburb fromSuburb=null;
    	Suburb toSuburb=null;
    	AdditionalCharge additionalChargeToSuburb=null;
    	AdditionalCharge additionalChargeFromSuburb=null;
    	    	
    	
    	if(suburbRepository.findByName(myTariff.getFromSuburbId().getName())!=null
    			|| suburbRepository.findById(myTariff.getFromSuburbId().getId())!=null) {
    		

    		if(suburbRepository.findByName(myTariff.getFromSuburbId().getName())!=null) {
    			
    			fromSuburb=suburbRepository.findByName(myTariff.getFromSuburbId().getName());
    		}else {
    			
    			fromSuburb=suburbRepository.findById(myTariff.getFromSuburbId().getId());
				
			}
    		
    		
    	}else {

    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myTariff.getFromSuburbId().getName()+" or id = "+myTariff.getFromSuburbId().getId()+" that exist. ", errors));
    	
    		
    	}
    	
    	if(suburbRepository.findByName(myTariff.getToSuburbId().getName())!=null
    			|| suburbRepository.findById(myTariff.getToSuburbId().getId())!=null) {
    		
    		

    		if(suburbRepository.findByName(myTariff.getToSuburbId().getName())!=null) {
    			
    			toSuburb=suburbRepository.findByName(myTariff.getToSuburbId().getName());
    		}else {
    			
    			toSuburb=suburbRepository.findById(myTariff.getToSuburbId().getId());
				
			}
    		
    		
    	}else {


    		List<String> errors=new ArrayList<String>();
    		errors.add("No such Suburb exists");
    		return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body(new ApiError(HttpStatus.NOT_FOUND, "There is no suburb with  name = "+myTariff.getToSuburbId().getName()+" or id = "+myTariff.getToSuburbId().getId()+" that exist. ", errors));
    	
    		
    	}
    
    
    	
    	if(!additionalChargeRepository.findLatestSuburbAdditionalCharge(toSuburb, PageRequest.of(0, 1)).getContent().isEmpty()) {
    	
    		
    		
    		
    		additionalChargeToSuburb=additionalChargeRepository.findLatestSuburbAdditionalCharge(toSuburb, PageRequest.of(0, 1)).get().iterator().next();
    		     	
    	}
    	if(!additionalChargeRepository.findLatestSuburbAdditionalCharge(fromSuburb, PageRequest.of(0, 1)).getContent().isEmpty()) {
    		
    	
	
    		additionalChargeFromSuburb=additionalChargeRepository.findLatestSuburbAdditionalCharge(fromSuburb, PageRequest.of(0, 1)).get().iterator().next();
       	 
    	}
    	
    
    	  
    	  
    	  if(additionalChargeToSuburb!=null) {
    		  
    		  if(additionalChargeToSuburb.getAmount()>0) {
    			  
    	      		List<String> errors=new ArrayList<String>();
    	      		errors.add("Suburb has additional Charge.");
    	      		return ResponseEntity
    	      	            .status(HttpStatus.FORBIDDEN)
    	      	          .body(new ApiError(HttpStatus.FORBIDDEN, "Suburb with additional charge will be calculated automatically from the branch serving it.", errors));
    	      	
    	      		
    		  }
    		  
    		
      	} 
    	  if(additionalChargeFromSuburb!=null) {
    		  if(additionalChargeFromSuburb.getAmount()>0) {
    			  
    	      		List<String> errors=new ArrayList<String>();
    	      		errors.add("Suburb has additional Charge.");
    	      		return ResponseEntity
    	      	            .status(HttpStatus.FORBIDDEN)
    	      	          .body(new ApiError(HttpStatus.FORBIDDEN, "Suburb with additional charge will be calculated automatically from the branch serving it.", errors));
    	      	
    	      		
    		  }
    		  
    		
      	} 
    	  
    
    	  
    	  if(myTariff.getAmount()<0) {
      	
      		
        		List<String> errors=new ArrayList<String>();
        		errors.add("Tariff amount less than required.");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Tariff amount must be greater than zero", errors));
        	
        		
        	} 
    	else {
    	

    		try {
    			myTariff.setFromSuburbId(fromSuburb);
    			myTariff.setToSuburbId(toSuburb);
    			
    			savedTariffDto=tariffRepository.save(myTariff);
			}catch (DataIntegrityViolationException e) {
				
				List<String> errors=new ArrayList<String>();
        		errors.add(e.getRootCause().getMessage());
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, e.getRootCause().getMessage(), errors));
        	
			}
			
			return savedTariffDto;
		
			
    	
    	
    	
    }
    	
   
    	
      
    }
    
    
    
    
    /*

    //search amount from suburb to suburb
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/amount-from-suburb-to-suburb", method = RequestMethod.GET)
    public Object amountFromSuburbToSuburb(@RequestParam Optional<Double> weight,
    		@RequestBody Suburb fromSuburb,
    		@RequestBody Suburb toSuburb,
    		@RequestBody Optional<Volume> volume){
    	
    	System.out.println("inside");
    	
    	
    	
    	double packageWeight;
    	double totalAmount;
    	
    	Charge charge=new Charge();

    	if(suburbRepository.findByName(fromSuburb.getName())!=null
    		
    			|| suburbRepository.findById(fromSuburb.getId())!=null) {
    		
    		if(suburbRepository.findByName(fromSuburb.getName())!=null) {
    			fromSuburb=suburbRepository.findByName(fromSuburb.getName());
    		}else {
    			fromSuburb=suburbRepository.findById(fromSuburb.getId());
    		}
    		
    	}else {
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("Suburb not found!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "From Suburb with id = "+fromSuburb.getId()+" or name = "+fromSuburb.getName()+" doesn't exist.", errors));
    	
    		
    		
    	}
    	
    	if(suburbRepository.findByName(toSuburb.getName())!=null
        		
    			|| suburbRepository.findById(toSuburb.getId())!=null) {
    		
    		if(suburbRepository.findByName(toSuburb.getName())!=null) {
    			toSuburb=suburbRepository.findByName(toSuburb.getName());
    		}else {
    			toSuburb=suburbRepository.findById(toSuburb.getId());
    		}
    		
    	}else {
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("Suburb not found!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "From Suburb with id = "+toSuburb.getId()+" or name = "+toSuburb.getName()+" doesn't exist.", errors));
    	
    		
    		
    	}
    	
    	double deadWeight = 0;
    	if(weight.isPresent()){
    		if(weight.get()<0) {

        		List<String> errors=new ArrayList<String>();
        		errors.add("Abnormal weight!");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Weight can not be negative.", errors));
        	
    		}else {
    			deadWeight=weight.get();
    			charge.setDeadWeight(deadWeight);
    		}
    		
    	}
    	double volumetricWeight = 0;
    	
      	if(volume.isPresent()){
    		if(volume.get().getVolumetricWeight()<0) {

        		List<String> errors=new ArrayList<String>();
        		errors.add("Abnormal Volume!");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Volume can not be negative.", errors));
        	
    		}else {
    			volumetricWeight=volume.get().getVolumetricWeight();
    			charge.setVolumetricWeight(volumetricWeight);
    		}
    		
    	}
      	
      	if(deadWeight>volumetricWeight) {
      		packageWeight=deadWeight;
      		charge.setDeadWeightUsed(true);
      		charge.setVolumetricWeightUsed(false);
      	}else {
      		packageWeight=volumetricWeight;
      		charge.setDeadWeightUsed(false);
      		charge.setVolumetricWeightUsed(true);
      	}
    	
    	Suburb suburbHeadOfFromSuburb=branchHeadSuburbRepository.findByBranchAndStartDateIsLatest(fromSuburb.getBranch(),  PageRequest.of(0, 1)).get().findFirst().get().getSuburb();
    	Suburb suburbHeadOfToSuburb=branchHeadSuburbRepository.findByBranchAndStartDateIsLatest(toSuburb.getBranch(),  PageRequest.of(0, 1)).get().findFirst().get().getSuburb();
    	
    	
    	if(suburbHeadOfFromSuburb==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Branch Head Suburb Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no suburb Defined to be the head in branch with name = "+fromSuburb.getBranch().getName(), errors));
    	
    	}
    	
    	if(suburbHeadOfToSuburb==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Branch Head Suburb Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no suburb Defined to be the head in branch with name = "+toSuburb.getBranch().getName(), errors));
    	
    	}
    	
    	AdditionalCharge additionalChargeInFromSuburb=additionalChargeRepository.findLatestSuburbAdditionalCharge(fromSuburb, PageRequest.of(0, 1)).getContent().iterator().next();
    	AdditionalCharge additionalChargeInToSuburb=additionalChargeRepository.findLatestSuburbAdditionalCharge(toSuburb, PageRequest.of(0, 1)).getContent().iterator().next();
    	
    	double additionalChargeSuburbHeadOfFromSuburbAmount = 0;
    	double additionalChargeSuburbHeadOfToSuburbAmount = 0;
    	Tariff tariff=null;
    	Suburb suburbFacilitatingAdditionalChargeInFromSuburb=null;
    	Suburb suburbFacilitatingAdditionalChargeInToSuburb=null;
    	
    	if(additionalChargeInFromSuburb!=null) {
    		additionalChargeSuburbHeadOfFromSuburbAmount=additionalChargeInFromSuburb.getAmount();
    		if(additionalChargeSuburbHeadOfFromSuburbAmount<=0) {
    			suburbFacilitatingAdditionalChargeInFromSuburb=suburbHeadOfFromSuburb;
    		}else {
    			suburbFacilitatingAdditionalChargeInFromSuburb=additionalChargeInFromSuburb.getSuburbFacilitatingAdditionalChargeId();
    		}
    		
    	}else {
    		suburbFacilitatingAdditionalChargeInFromSuburb=suburbHeadOfFromSuburb;
    	}
    	
    	if(additionalChargeInToSuburb!=null) {
    		additionalChargeSuburbHeadOfToSuburbAmount=additionalChargeInToSuburb.getAmount();
    		
    		if(additionalChargeSuburbHeadOfToSuburbAmount<=0) {
    			suburbFacilitatingAdditionalChargeInToSuburb=suburbHeadOfToSuburb;
    		}else {
    			suburbFacilitatingAdditionalChargeInToSuburb=additionalChargeInToSuburb.getSuburbFacilitatingAdditionalChargeId();
    		}
    	}else {
    		suburbFacilitatingAdditionalChargeInToSuburb=suburbHeadOfToSuburb;
    	}
    	
    
    	
    	tariff=tariffRepository.findByFromSuburbIdAndToSuburbIdAndReviewDateIsLatest(suburbFacilitatingAdditionalChargeInFromSuburb.getId(),suburbFacilitatingAdditionalChargeInToSuburb.getId(), PageRequest.of(0, 1)).getContent().iterator().next();
    	    	
    	if(tariff==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Tariff Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no tariff Defined to be used in the suburb with name = "+suburbFacilitatingAdditionalChargeInFromSuburb.getName()+" or "+suburbFacilitatingAdditionalChargeInToSuburb.getName(), errors));
    	
    	}
    	
    
    	
    	
    	double extraPerKgAmount = 0;
    	
    	if(packageWeight>suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightCharged()) {
    		double totalweight;
    		if(packageWeight>suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightNotCharged()) {
    			totalweight=suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightNotCharged();
    		}else {
    			totalweight=packageWeight;
    		}
    		double extraweight=totalweight-suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightCharged();
    	
    		extraPerKgAmount=extraweight*suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getExtraPerKgAmount();
    	
    	}
    	
    	
    	
    	
    	
    	totalAmount=additionalChargeSuburbHeadOfFromSuburbAmount+additionalChargeSuburbHeadOfToSuburbAmount+tariff.getAmount()+extraPerKgAmount;
    	charge.setAdditionalCharge(additionalChargeSuburbHeadOfToSuburbAmount);
    	charge.setCollectionCharge(additionalChargeSuburbHeadOfFromSuburbAmount);
    	charge.setExtraKgCharge(extraPerKgAmount);
    	charge.setTariffCharge(tariff.getAmount());
    	charge.setTotalCost(totalAmount);
    	
    	
    	return charge;
    }
    
    
    */
    
    
    
    
    

    //search amount from suburb to suburb
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/amount-from-suburb-to-suburb", method = RequestMethod.GET)
    public Object amountFromSuburbToSuburb(	@RequestBody CostingSuburbJson costingJson ){
    	
    	System.out.println("inside amountFromSuburbToSuburb function");
    	
    	
    	
    	double packageWeight = 0;
    	double totalAmount;
    	
    	Charge charge=new Charge();
    	Suburb fromSuburb=null;
    	Suburb toSuburb=null;

    	
    	
    	
    	if(suburbRepository.findByName(costingJson.getFromSuburb().getName())!=null
    		
    			|| suburbRepository.findById(costingJson.getFromSuburb().getId())!=null) {
    		
    		if(suburbRepository.findByName(costingJson.getFromSuburb().getName())!=null) {
    			fromSuburb=suburbRepository.findByName(costingJson.getFromSuburb().getName());
    		}else {
    			fromSuburb=suburbRepository.findById(costingJson.getFromSuburb().getId());
    		}
    		
    	}else {
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("Suburb not found!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "From Suburb with id = "+costingJson.getFromSuburb().getId()+" or name = "+costingJson.getFromSuburb().getName()+" doesn't exist.", errors));
    	
    		
    		
    	}
    	
    	if(suburbRepository.findByName(costingJson.getToSuburb().getName())!=null
        		
    			|| suburbRepository.findById(costingJson.getToSuburb().getId())!=null) {
    		
    		if(suburbRepository.findByName(costingJson.getToSuburb().getName())!=null) {
    			toSuburb=suburbRepository.findByName(costingJson.getToSuburb().getName());
    		}else {
    			toSuburb=suburbRepository.findById(costingJson.getToSuburb().getId());
    		}
    		
    	}else {
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("Suburb not found!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "From Suburb with id = "+costingJson.getToSuburb().getId()+" or name = "+costingJson.getToSuburb().getName()+" doesn't exist.", errors));
    	
    		
    		
    	}
    	
    
    	
    	double deadWeight = 0;
    	try {
    		if(costingJson.getWeight().isPresent()){
        		System.out.println("Wiight not present");
        		if(costingJson.getWeight().get()<0) {

            		List<String> errors=new ArrayList<String>();
            		errors.add("Abnormal weight!");
            		return ResponseEntity
            	            .status(HttpStatus.FORBIDDEN)
            	            .body(new ApiError(HttpStatus.FORBIDDEN, "Weight can not be negative.", errors));
            	
        		}else {
        			
        			deadWeight=costingJson.getWeight().get();
        			charge.setDeadWeight(deadWeight);
        		
        		}
        		
        	}
    	}catch (Exception e) {
			//System.out.println(e);
		}
    	
    	
    	
    
    	double volumetricWeight = 0;
    	try {
      	if(costingJson.getVolume().isPresent()){
    		if(costingJson.getVolume().get().getVolumetricWeight()<0) {

        		List<String> errors=new ArrayList<String>();
        		errors.add("Abnormal Volume!");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Volume can not be negative.", errors));
        	
    		}else {
    			volumetricWeight=costingJson.getVolume().get().getVolumetricWeight();
    			charge.setVolumetricWeight(volumetricWeight);
    		
    		}
    		
    	}
      	
    }catch (Exception e) {
		
	}
      	
      	if(deadWeight>volumetricWeight) {
      		packageWeight=deadWeight;
      		charge.setDeadWeightUsed(true);
      		charge.setVolumetricWeightUsed(false);
      		
      	}
      	
     	if(volumetricWeight>deadWeight) {
     		packageWeight=volumetricWeight;
      		charge.setDeadWeightUsed(false);
      		charge.setVolumetricWeightUsed(true);
      		
      	}
    	
    	Suburb suburbHeadOfFromSuburb=branchHeadSuburbRepository.findByBranchAndStartDateIsLatest(fromSuburb.getBranch(),  PageRequest.of(0, 1)).get().findFirst().get().getSuburb();
    	Suburb suburbHeadOfToSuburb=branchHeadSuburbRepository.findByBranchAndStartDateIsLatest(toSuburb.getBranch(),  PageRequest.of(0, 1)).get().findFirst().get().getSuburb();
    	
    	
    	if(suburbHeadOfFromSuburb==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Branch Head Suburb Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no suburb Defined to be the head in branch with name = "+fromSuburb.getBranch().getName(), errors));
    	
    	}
    	
    	if(suburbHeadOfToSuburb==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Branch Head Suburb Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no suburb Defined to be the head in branch with name = "+toSuburb.getBranch().getName(), errors));
    	
    	}
    	
    	
    	AdditionalCharge additionalChargeInFromSuburb=null;
    	AdditionalCharge additionalChargeInToSuburb=null;
    	if(!additionalChargeRepository.findLatestSuburbAdditionalCharge(fromSuburb, PageRequest.of(0, 1)).isEmpty()) {
    		additionalChargeInFromSuburb=additionalChargeRepository.findLatestSuburbAdditionalCharge(fromSuburb, PageRequest.of(0, 1)).getContent().iterator().next();
        	
    	}
    	if(!additionalChargeRepository.findLatestSuburbAdditionalCharge(toSuburb, PageRequest.of(0, 1)).isEmpty()) {
    		additionalChargeInToSuburb=additionalChargeRepository.findLatestSuburbAdditionalCharge(toSuburb, PageRequest.of(0, 1)).getContent().iterator().next();
        	
    	}
    	
    	
    	double additionalChargeSuburbHeadOfFromSuburbAmount = 0;
    	double additionalChargeSuburbHeadOfToSuburbAmount = 0;
    	Tariff tariff=null;
    	Suburb suburbFacilitatingAdditionalChargeInFromSuburb=null;
    	Suburb suburbFacilitatingAdditionalChargeInToSuburb=null;
    	
    	if(additionalChargeInFromSuburb!=null) {
    		
    		additionalChargeSuburbHeadOfFromSuburbAmount=additionalChargeInFromSuburb.getAmount();
    		if(additionalChargeSuburbHeadOfFromSuburbAmount<=0) {
    			suburbFacilitatingAdditionalChargeInFromSuburb=suburbHeadOfFromSuburb;
    		}else {
    			suburbFacilitatingAdditionalChargeInFromSuburb=additionalChargeInFromSuburb.getSuburbFacilitatingAdditionalChargeId();
    		}
    		
    	}else {
    		suburbFacilitatingAdditionalChargeInFromSuburb=suburbHeadOfFromSuburb;
    	}
    	
    	if(additionalChargeInToSuburb!=null) {
    		additionalChargeSuburbHeadOfToSuburbAmount=additionalChargeInToSuburb.getAmount();
    		
    		if(additionalChargeSuburbHeadOfToSuburbAmount<=0) {
    			suburbFacilitatingAdditionalChargeInToSuburb=suburbHeadOfToSuburb;
    		}else {
    			suburbFacilitatingAdditionalChargeInToSuburb=additionalChargeInToSuburb.getSuburbFacilitatingAdditionalChargeId();
    		}
    	}else {
    		suburbFacilitatingAdditionalChargeInToSuburb=suburbHeadOfToSuburb;
    	}
    	
    	
    	if(!tariffRepository.findByFromSuburbIdAndToSuburbIdAndReviewDateIsLatest(suburbFacilitatingAdditionalChargeInFromSuburb,suburbFacilitatingAdditionalChargeInToSuburb, PageRequest.of(0, 1)).isEmpty()  	) {
    		tariff=tariffRepository.findByFromSuburbIdAndToSuburbIdAndReviewDateIsLatest(suburbFacilitatingAdditionalChargeInFromSuburb,suburbFacilitatingAdditionalChargeInToSuburb, PageRequest.of(0, 1)).getContent().iterator().next();
      	  
    	}
    	
    	
    	
    	  	
    	if(tariff==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Tariff Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no tariff Defined to be used in the suburb with name = "+suburbFacilitatingAdditionalChargeInFromSuburb.getName()+" or "+suburbFacilitatingAdditionalChargeInToSuburb.getName(), errors));
    	
    	}
    	
    
    	
    	
    	double extraPerKgAmount = 0;
    	
    	if(packageWeight>suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightCharged()) {
    		double totalweight;
    		if(packageWeight>suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightNotCharged()) {
    			totalweight=suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightNotCharged();
    		}else {
    			totalweight=packageWeight;
    		}
    		double extraweight=totalweight-suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getStandingMaxWeightCharged();
    	
    		extraPerKgAmount=extraweight*suburbFacilitatingAdditionalChargeInToSuburb.getStandingWeightCharge().getExtraPerKgAmount();
    	
    	}
    	
    	
    	
    	
    	
    	totalAmount=additionalChargeSuburbHeadOfFromSuburbAmount+additionalChargeSuburbHeadOfToSuburbAmount+tariff.getAmount()+extraPerKgAmount;
    	charge.setAdditionalCharge(additionalChargeSuburbHeadOfToSuburbAmount);
    	charge.setCollectionCharge(additionalChargeSuburbHeadOfFromSuburbAmount);
    	charge.setExtraKgCharge(extraPerKgAmount);
    	charge.setTariffCharge(tariff.getAmount());
    	charge.setTotalCost(totalAmount);
    	
    	
    	return charge;
    	
    	
    	
    	
    }
    
    

    //search amount from branch to branch
    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/amount-from-branch-to-branch", method = RequestMethod.GET)
    public Object amountFromBranchToBranch(	@RequestBody CostingBranchJson costingBranchJson ){
    	
    	System.out.println("inside amountFromBranchToBranch function");
    	
    	
    	
    	double packageWeight = 0;
    	double totalAmount;
    	
    	Charge charge=new Charge();
    	 Branch fromBranch=null;
    	 Branch toBranch=null;

    	
    	
    	
    	if(branchRepository.findByName(costingBranchJson.getFromBranch().getName())!=null
    		
    			|| branchRepository.findById(costingBranchJson.getFromBranch().getId())!=null) {
    		
    		if(branchRepository.findByName(costingBranchJson.getFromBranch().getName())!=null) {
    			fromBranch=branchRepository.findByName(costingBranchJson.getFromBranch().getName());
    		}else {
    			fromBranch=branchRepository.findById(costingBranchJson.getFromBranch().getId());
    		}
    		
    	}else {
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("Branch not found!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "From Branch with id = "+costingBranchJson.getFromBranch().getId()+" or name = "+costingBranchJson.getFromBranch().getName()+" doesn't exist.", errors));
    	
    		
    		
    	}
    	
    	
    	if(branchRepository.findByName(costingBranchJson.getToBranch().getName())!=null
    		
    			|| branchRepository.findById(costingBranchJson.getToBranch().getId())!=null) {
    		
    		if(branchRepository.findByName(costingBranchJson.getToBranch().getName())!=null) {
    			toBranch=branchRepository.findByName(costingBranchJson.getToBranch().getName());
    		}else {
    			toBranch=branchRepository.findById(costingBranchJson.getToBranch().getId());
    		}
    		
    	}else {
    		
    		List<String> errors=new ArrayList<String>();
    		errors.add("Branch not found!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "To Branch with id = "+costingBranchJson.getToBranch().getId()+" or name = "+costingBranchJson.getToBranch().getName()+" doesn't exist.", errors));
    	
    		
    		
    	}
    	
    	
    
    	
    	double deadWeight = 0;
    	try {
    		if(costingBranchJson.getWeight().isPresent()){
        		System.out.println("Wiight not present");
        		if(costingBranchJson.getWeight().get()<0) {

            		List<String> errors=new ArrayList<String>();
            		errors.add("Abnormal weight!");
            		return ResponseEntity
            	            .status(HttpStatus.FORBIDDEN)
            	            .body(new ApiError(HttpStatus.FORBIDDEN, "Weight can not be negative.", errors));
            	
        		}else {
        			
        			deadWeight=costingBranchJson.getWeight().get();
        			charge.setDeadWeight(deadWeight);
        		
        		}
        		
        	}
    	}catch (Exception e) {
			//System.out.println(e);
		}
    	
    	
    	
    
    	double volumetricWeight = 0;
    	try {
      	if(costingBranchJson.getVolume().isPresent()){
    		if(costingBranchJson.getVolume().get().getVolumetricWeight()<0) {

        		List<String> errors=new ArrayList<String>();
        		errors.add("Abnormal Volume!");
        		return ResponseEntity
        	            .status(HttpStatus.FORBIDDEN)
        	            .body(new ApiError(HttpStatus.FORBIDDEN, "Volume can not be negative.", errors));
        	
    		}else {
    			volumetricWeight=costingBranchJson.getVolume().get().getVolumetricWeight();
    			charge.setVolumetricWeight(volumetricWeight);
    		
    		}
    		
    	}
      	
    }catch (Exception e) {
		
	}
      	
      	if(deadWeight>volumetricWeight) {
      		packageWeight=deadWeight;
      		charge.setDeadWeightUsed(true);
      		charge.setVolumetricWeightUsed(false);
      		
      	}
      	
     	if(volumetricWeight>deadWeight) {
     		packageWeight=volumetricWeight;
      		charge.setDeadWeightUsed(false);
      		charge.setVolumetricWeightUsed(true);
      		
      	}
    	
    	Suburb suburbHeadOfFromBranch=branchHeadSuburbRepository.findByBranchAndStartDateIsLatest(fromBranch,  PageRequest.of(0, 1)).get().findFirst().get().getSuburb();
    	Suburb suburbHeadOfToBranch=branchHeadSuburbRepository.findByBranchAndStartDateIsLatest(toBranch,  PageRequest.of(0, 1)).get().findFirst().get().getSuburb();
    	
    	
    	if(suburbHeadOfFromBranch==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Branch Head Suburb Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no suburb Defined to be the head in branch with name = "+fromBranch.getName(), errors));
    	
    	}
    	
    	if(suburbHeadOfToBranch==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Branch Head Suburb Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no suburb Defined to be the head in branch with name = "+toBranch.getName(), errors));
    	
    	}
    	
    	
    	AdditionalCharge additionalChargeInFromBranch=null;
    	AdditionalCharge additionalChargeInToBranch=null;
    	if(!additionalChargeRepository.findLatestSuburbAdditionalCharge(suburbHeadOfFromBranch, PageRequest.of(0, 1)).isEmpty()) {
    		additionalChargeInFromBranch=additionalChargeRepository.findLatestSuburbAdditionalCharge(suburbHeadOfFromBranch, PageRequest.of(0, 1)).getContent().iterator().next();
        	
    	}
    	if(!additionalChargeRepository.findLatestSuburbAdditionalCharge(suburbHeadOfToBranch, PageRequest.of(0, 1)).isEmpty()) {
    		additionalChargeInToBranch=additionalChargeRepository.findLatestSuburbAdditionalCharge(suburbHeadOfToBranch, PageRequest.of(0, 1)).getContent().iterator().next();
        	
    	}
    	
    	
    	double additionalChargeSuburbHeadOfFromBranchAmount = 0;
    	double additionalChargeSuburbHeadOfToBranchAmount = 0;
    	Tariff tariff=null;
    	Suburb suburbFacilitatingAdditionalChargeInFromBranch=null;
    	Suburb suburbFacilitatingAdditionalChargeInToBranch=null;
    	
    	if(additionalChargeInFromBranch!=null) {
    		
    		additionalChargeSuburbHeadOfFromBranchAmount=additionalChargeInFromBranch.getAmount();
    		if(additionalChargeSuburbHeadOfFromBranchAmount<=0) {
    			suburbFacilitatingAdditionalChargeInFromBranch=suburbHeadOfFromBranch;
    		}else {
    			suburbFacilitatingAdditionalChargeInFromBranch=additionalChargeInFromBranch.getSuburbFacilitatingAdditionalChargeId();
    		}
    		
    	}else {
    		suburbFacilitatingAdditionalChargeInFromBranch=suburbHeadOfFromBranch;
    	}
    	
    	if(additionalChargeInToBranch!=null) {
    		additionalChargeSuburbHeadOfToBranchAmount=additionalChargeInToBranch.getAmount();
    		
    		if(additionalChargeSuburbHeadOfToBranchAmount<=0) {
    			suburbFacilitatingAdditionalChargeInToBranch=suburbHeadOfToBranch;
    		}else {
    			suburbFacilitatingAdditionalChargeInToBranch=additionalChargeInToBranch.getSuburbFacilitatingAdditionalChargeId();
    		}
    	}else {
    		suburbFacilitatingAdditionalChargeInToBranch=suburbHeadOfToBranch;
    	}
    	
    	
    	if(!tariffRepository.findByFromSuburbIdAndToSuburbIdAndReviewDateIsLatest(suburbFacilitatingAdditionalChargeInFromBranch,suburbFacilitatingAdditionalChargeInToBranch, PageRequest.of(0, 1)).isEmpty()  	) {
    		tariff=tariffRepository.findByFromSuburbIdAndToSuburbIdAndReviewDateIsLatest(suburbFacilitatingAdditionalChargeInFromBranch,suburbFacilitatingAdditionalChargeInToBranch, PageRequest.of(0, 1)).getContent().iterator().next();
      	  
    	}
    	
    	
    	
    	  	
    	if(tariff==null) {
    		List<String> errors=new ArrayList<String>();
    		errors.add("No Tariff Defined!");
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body(new ApiError(HttpStatus.FORBIDDEN, "There is no tariff Defined to be used in the suburb with name = "+suburbFacilitatingAdditionalChargeInFromBranch.getName()+" or "+suburbFacilitatingAdditionalChargeInToBranch.getName(), errors));
    	
    	}
    	
    
    	
    	
    	double extraPerKgAmount = 0;
    	
    	if(packageWeight>suburbFacilitatingAdditionalChargeInToBranch.getStandingWeightCharge().getStandingMaxWeightCharged()) {
    		double totalweight;
    		if(packageWeight>suburbFacilitatingAdditionalChargeInToBranch.getStandingWeightCharge().getStandingMaxWeightNotCharged()) {
    			totalweight=suburbFacilitatingAdditionalChargeInToBranch.getStandingWeightCharge().getStandingMaxWeightNotCharged();
    		}else {
    			totalweight=packageWeight;
    		}
    		double extraweight=totalweight-suburbFacilitatingAdditionalChargeInToBranch.getStandingWeightCharge().getStandingMaxWeightCharged();
    	
    		extraPerKgAmount=extraweight*suburbFacilitatingAdditionalChargeInToBranch.getStandingWeightCharge().getExtraPerKgAmount();
    	
    	}
    	
    	
    	
    	
    	
    	totalAmount=additionalChargeSuburbHeadOfFromBranchAmount+additionalChargeSuburbHeadOfToBranchAmount+tariff.getAmount()+extraPerKgAmount;
    	charge.setAdditionalCharge(additionalChargeSuburbHeadOfToBranchAmount);
    	charge.setCollectionCharge(additionalChargeSuburbHeadOfFromBranchAmount);
    	charge.setExtraKgCharge(extraPerKgAmount);
    	charge.setTariffCharge(tariff.getAmount());
    	charge.setTotalCost(totalAmount);
    	
    	
    	return charge;
    	
    	
    	
    	
    }
    
    
    

}
