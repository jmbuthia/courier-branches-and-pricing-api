package ke.co.fargocourier.controller;

import ke.co.fargocourier.config.TokenProvider;
import ke.co.fargocourier.model.AuthToken;
import ke.co.fargocourier.model.dto.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//import static com.example.model.Constants.TOKEN_PREFIX;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(){
    			
		return "<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<body>\n" + 
				"\n" + 
				"<h1>Courier branches and pricing api</h1>\n" + 
				"<p>Courier branches and price calculator api is a spring boot, jpa rest controller system that allows you to set all company branches, suburbs, its sub-zones and calculate cost to transport any kind of parcel from one suburb to another."
				+ " For more information please contact Email:jmbuthia12@gmail.com or Phone:+254717925741</p>\n" + 
				"\n" + 
				"</body>\n" + 
				"</html>";
	
	}
    
    	

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

}
