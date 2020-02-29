package ke.co.fargocourier.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import ke.co.fargocourier.model.User;
import ke.co.fargocourier.repositories.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ke.co.fargocourier.model.Constants.HEADER_STRING;
import static ke.co.fargocourier.model.Constants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private TokenProvider jwtTokenUtil;
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                
            } catch (IllegalArgumentException e) {
            	
            	req.setAttribute("noUsername", "an error occurred during getting user name from token");
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
            	
            	req.setAttribute("expired", e.getMessage());
            	logger.warn("the token is expired and not valid anymore", e);
            } catch(SignatureException e){
            	req.setAttribute("authenticationFailed", "Authentication Failed. Username or Password not valid.");
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
        	req.setAttribute("noBearerToken", "couldn't find bearer string, will ignore the header");
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            User user=userRepository.findByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
            	
            	System.out.println("validating token");
            	
            	
            	if(!userDetails.isAccountNonExpired()) {
            		//System.out.println("userDetails.isAccountNonExpired()  ==  "+userDetails.isAccountNonExpired());
            		req.setAttribute("expiredAccount", "The user account is expired");
            		
            	}else if(!userDetails.isAccountNonLocked()) {
            		
            		req.setAttribute("accountLocked", "The user account is locked");
            		
            	}else if(!userDetails.isCredentialsNonExpired()) {
            		
            		req.setAttribute("accountCredentialsIsExpired", "The user account Credentials is expired");
            		
            	}else if(!userDetails.isEnabled()) {
            		
            		req.setAttribute("accountDisabled", "The user account is disabled");
            		
            	}else if(!user.isPhoneVerified()) {
            		
            		req.setAttribute("isPhoneVerified", "The user phone number is not verified");
            		
            	}else {
            	
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
              
            	}
            }
        }

        chain.doFilter(req, res);
       
        
    }
    
    
}