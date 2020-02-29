package ke.co.fargocourier.initializations;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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

@Component
public class InitialData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    BranchRepository branchRepository;
    
    @Autowired
    RouteRepository routeRepository; 
    
    @Autowired
    StandingWeightChargeRepository standingWeightChargeRepository;
    
    @Autowired
    CollectionCentreRepository collectionCentreRepository;
    
	/*
	 * @Autowired TariffZoneRepository tariffZoneRepository;
	 */
    @Autowired
    TariffRepository tariffRepository;
    
    @Autowired
    SuburbRepository suburbRepository;
    
    @Autowired
    BranchHeadSuburbRepository branchHeadSuburbRepository;
    
    @Autowired
    AdditionalChargeRepository additionalChargeRepository;
    
    @Autowired
	private BCryptPasswordEncoder bcryptEncoder;
    

    @Override
    public void run(String...args) throws Exception {
    	
    	//Adding route into the database
    	
    	
    	Route nairobi =new Route();
    	nairobi.setDescription("Nairobi route covers Nairobi county, Kiambu county"
    			+ ", Kajiado county and other outscarts.");
    	nairobi.setName("Nairobi");
    	
    	Route flamingo =new Route();
    	flamingo.setDescription("Flamingo route covers Nakuru county, Nyandarua county"
    			+ " and other outscarts.");
    	flamingo.setName("Flamingo");
    	
    	Route elgon =new Route();
    	elgon.setDescription("Elgon route covers Turnaka county, Uasin Gishu county, Bungoma "
    			+ "county, Nandi Hills county and other outscarts.");
    	elgon.setName("Elgon");
    	
    	Route mtKenya =new Route();
    	mtKenya.setDescription("MT Kenya route covers Meru county, Nyeri county, Muranga "
    			+ "county, Laikipia county and other outscarts.");
    	mtKenya.setName("MT Kenya");
    	
    	Route lake =new Route();
    	lake.setDescription("Lake route covers Kisumu county, Busia county "
    			+ "and other outscarts.");
    	lake.setName("Lake");
    	
    	Route beach =new Route();
    	beach.setDescription("Beach route covers Mombasa county, Lamu county, Malindi "
    			+ "county, Voi county and other outscarts.");
    	beach.setName("Beach");
    	
    	Route mashariki =new Route();
    	mashariki.setDescription("Mashariki route covers Kitui county, Garissa county"
    			+ ", Machakos county and other outscarts.");
    	mashariki.setName("Mashariki");
    	
    	
    	Set<Route> routes=new HashSet<>();
    	routes.add(elgon);
    	routes.add(mtKenya);
    	routes.add(flamingo);
    	routes.add(lake);
    	routes.add(beach);
    	routes.add(mashariki);
    	routes.add(nairobi);
    	
    	if(routeRepository.count()>=7) {
    		return;
    	}else {
    	
    	routeRepository.saveAll(routes);
    	}
    	
    	
    	
    	//Adding Standing weight charge into the database
    	
    	StandingWeightCharge standingWeightCharge=new StandingWeightCharge();
    	String dateOfTarrif="01/07/2017";
    	Date dateOfTarrifApproval=new SimpleDateFormat("dd/MM/yyyy").parse(dateOfTarrif);  
    	standingWeightCharge.setDateUpdated(dateOfTarrifApproval);
    	standingWeightCharge.setDescription("For all suburbs without *");
    	standingWeightCharge.setName("Normal Rate");
    	standingWeightCharge.setExtraPerKgAmount(60.00);
    	standingWeightCharge.setStandingMaxWeightCharged(5.00);
    	standingWeightCharge.setStandingMaxWeightNotCharged(1000.00);
    	
    	if(standingWeightChargeRepository.count()>=1) {
    		return;
    	}else {
    	
    		standingWeightChargeRepository.save(standingWeightCharge);
    	}
    	
    	
    	
    	
    	
    	//Adding branch into the database
    	Branch nairobiBranch =new Branch();
    	nairobiBranch.setDescription("Head Office");
    	nairobiBranch.setGpsLocation("1.3224252,36.8633382");
    	nairobiBranch.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQqoROnru-N5NZcCWzUwtvNWCHrMJR0MLMIIE20VCMiGBUd7Nav");
    	nairobiBranch.setName("Fargo courier ltd - Head office");
    	nairobiBranch.setPhone("0703077000 020211333/4");
    	nairobiBranch.setPhysicalLocation("Central Busines Park, Road C off Enterprise Road, "
    			+ "Behind Sameer Industrial Park");
    	nairobiBranch.setRoute(nairobi);
    	
    	if(branchRepository.findByName(nairobiBranch.getName())==null) {
    		nairobiBranch.setRoute(routeRepository.findByName(nairobiBranch.getRoute().getName()));
        
    		//saving branch
        	branchRepository.save(nairobiBranch);
    	}
    	
    	
    	//saving collection centre
    	
    	CollectionCentre collectionCentre= new CollectionCentre();
    	collectionCentre.setBranch(nairobiBranch);
    	collectionCentre.setDescription("Humphly munene is the co ordinator");
    	collectionCentre.setGpsLocation("1.3224252,36.8633382");
    	collectionCentre.setImageUrl("https://encrypted-tbn0.gstatic.com/images?"
    			+ "q=tbn%3AANd9GcSOtUOCCZcSPxyEJtyFKhgSXx1by05kz8-DJvNkj6XLTHzgom0W");
    	collectionCentre.setName("Upperhill Collection Centre");
    	collectionCentre.setPhone("0771284712");
    	collectionCentre.setPhysicalLocation("Hospital Road, Outside Blue Shield Tower");
    	
    	if(collectionCentreRepository.findByName(collectionCentre.getName())==null) {
    		
    		collectionCentreRepository.save(collectionCentre);
    	}
    	
    	

    	//saving suburb A
    	
    	Suburb suburbA=new Suburb();
    	suburbA.setBranch(nairobiBranch);
    	suburbA.setDescription("Nairobi Town");
    	suburbA.setName("Nairobi");
    	suburbA.setDateCreated(dateOfTarrifApproval);
    	suburbA.setStandingWeightCharge(standingWeightCharge);
    	
    	if(suburbRepository.findByName(suburbA.getName())==null) {
    		suburbRepository.save(suburbA);
    	}
    	
    	

    	//saving suburb B
    	
    	Suburb suburbB=new Suburb();
    	suburbB.setBranch(nairobiBranch);
    	suburbB.setDescription("Ngong Town");
    	suburbB.setName("Ngong");
    	suburbB.setDateCreated(dateOfTarrifApproval);
    	suburbB.setStandingWeightCharge(standingWeightCharge);
    	
    	if(suburbRepository.findByName(suburbB.getName())==null) {
    		suburbRepository.save(suburbB);
    	}
    	
    	
    	//saving branch head suburb
    	
    	BranchHeadSuburb branchHeadSuburb=new BranchHeadSuburb();
    	branchHeadSuburb.setBranch(nairobiBranch);
    	branchHeadSuburb.setDescription("Head office");
    	branchHeadSuburb.setStartDate(dateOfTarrifApproval);
    	branchHeadSuburb.setSuburb(suburbA);
    	
    	branchHeadSuburbRepository.save(branchHeadSuburb);
    	
    	
    	//saving additional charge
    	
    	AdditionalCharge additionalCharge=new AdditionalCharge();
    	additionalCharge.setAmount(40);
    	additionalCharge.setDescription("Public means used");
    	additionalCharge.setReviewDate(dateOfTarrifApproval);
    	additionalCharge.setSuburbFacilitatingAdditionalChargeId(suburbA);
    	additionalCharge.setSuburbWithAdditionalChargeId(suburbB);
    	
    	
    	additionalChargeRepository.save(additionalCharge);
    	
    	
    	
    	
    	//saving tariff
    	
    	
    	Tariff tariff= new Tariff();
    	tariff.setAmount(260);
    	tariff.setDescription("nairobi nairobi");
    	tariff.setFromSuburbId(suburbA);
    	tariff.setReviewDate(dateOfTarrifApproval);
    	tariff.setToSuburbId(suburbA);
    	
    	
    	
    	tariffRepository.save(tariff);
    	
    	
    	
    	//saving user roles
    	   	
    	
    	Role admin= new Role();
    	admin.setDescription("Admin has all rights. That is ability to view all reports, add, "
    			+ "edit and deletes.");
    	admin.setName("ADMIN");
    	
    	Role manager= new Role();
    	manager.setDescription("Manager has fewer rights. That is ability to view some reports, "
    			+ "add and edit "
    			+ "suburbs cost and additional costs.");
    	manager.setName("MANAGER");
    	
    	Role escort= new Role();
    	escort.setDescription("Escort has the few rights. That is ability to view all branches,"
    			+ " its phone "
    			+ "number and cost additional charges and extra kg amount.");
    	escort.setName("ESCORT");
    	
    	Role user= new Role();
    	user.setDescription("User has the least rights. That is ability to only view branches, "
    			+ "its phone number and cost.");
    	user.setName("USER");
    	
    	Set<Role> roles=new HashSet<>();
    	roles.add(admin);
    	roles.add(manager);
    	roles.add(escort);
    	roles.add(user);
    	
    	if(roleRepository.count()==4) {
    		return;
    	}else {
    	
    	roleRepository.saveAll(roles);
    	}
    	
    	
    	//saving user
    	
    	String dob="01/09/1990";
    	 Date date=new SimpleDateFormat("dd/MM/yyyy").parse(dob);  
    	
    	User initialUser=new User();
    	initialUser.setLastname("admin");
    	initialUser.setPassword(bcryptEncoder.encode("admin"));  
    	
    	initialUser.setFirstname("admin");
    	initialUser.setUsername("admin");
    	initialUser.setProfile("https://www.google.co.ke/"
    			+ "imgres?imgurl=https%3A%2F%2Fcdn.shopify.com"
    			+ "%2Fs%2Ffiles%2F1%2F0716%2F6107%2Fproducts"
    			+ "%2F51839_980_large.png%3Fv%3D1554209789&imgrefurl="
    			+ "https%3A%2F%2Fvikingmarine.ie%2Fproducts"
    			+ "%2Fhh-colour-play-midlayer-jacket&docid="
    			+ "pYCt4asdD2ytEM&tbnid=P3lSAkQL5m_OHM%3A&vet="
    			+ "10ahUKEwi98cipypTmAhVIWxUIHfV0BRIQMwheKB4wHg."
    			+ ".i&w=388&h=480&hl=en&bih=914&biw=1920&q=hh&ved="
    			+ "0ahUKEwi98cipypTmAhVIWxUIHfV0BRIQMwheKB4wHg&iact=mrc&uact=8");
    	initialUser.setDateOfBirth(date);
    	initialUser.setPhone("+254717925741");
    	initialUser.setPhoneVerified(true);
    	initialUser.setAccountNonExpired(true);
    	initialUser.setAccountNonLocked(true);
    	initialUser.setCredentialsNonExpired(true);
    	initialUser.setEnabled(true);
    	initialUser.setRoles(roles);
    	initialUser.setBranch(nairobiBranch);
    	
    	if(userRepository.findByUsername("admin")==null) {
    		
    		Set<Role> adminroles=new HashSet<>();
    		for (Role role : initialUser.getRoles()) {
    			adminroles.add(this.roleRepository.findByName(role.getName()));
    		}
    		initialUser.setRoles(adminroles);
    		
    		userRepository.saveAndFlush(initialUser);
    	}
    	
    	

    	
    	
		/*
		 * 
		 * 
		 * //saving TariffZone
		 * 
		 * TariffZone tariffZone=new TariffZone(); tariffZone.setAmount(260.00);
		 * tariffZone.setDescription("Nairobi to Nairobi");
		 * tariffZone.setReviewDate(dateOfTarrifApproval);
		 * 
		 * TariffZoneId tariffZoneId = new TariffZoneId();
		 * tariffZoneId.setFromBranchId(nairobiBranch);
		 * tariffZoneId.setToBranchId(nairobiBranch);
		 * 
		 * tariffZone.setTariffZoneId(tariffZoneId);
		 * 
		 * if(tariffZoneRepository.findByDescription(tariffZone.getDescription())==null)
		 * { tariffZoneRepository.save(tariffZone); }
		 * 
		 * 
		 */
    	
    	
    	
        
    }
}