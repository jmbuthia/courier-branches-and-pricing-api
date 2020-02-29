package ke.co.fargocourier.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;



@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    
	private static final long serialVersionUID = 1L;

	@Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
		
		
		
		System.out.println("in commence method----------------------------------- "
				+authException.getClass()+
				"--------------------------------- ");
		
		System.out.println("simple name----------------------------------- "
		+authException.getClass().getSimpleName()+
		"--------------------------------- ");

		
		String exp=(String)request.getAttribute("expired");
		String noUsername=(String)request.getAttribute("noUsername");
		String authenticationFailed=(String)request.getAttribute("authenticationFailed");
		String expiredAccount=(String)request.getAttribute("expiredAccount");
		String accountLocked=(String)request.getAttribute("accountLocked");
		String accountCredentialsIsExpired=(String)request.getAttribute("accountCredentialsIsExpired");
		String accountDisabled=(String)request.getAttribute("accountDisabled");
		String noBearerToken=(String)request.getAttribute("noBearerToken");
		String isPhoneVerified=(String)request.getAttribute("isPhoneVerified");
		
		
		
		
		if(exp!=null && exp.startsWith("JWT expired")) {
			 System.out.println("in expied if ============= ");	
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, exp);
			
		}else if(authException.getClass().getSimpleName().equals("BadCredentialsException")) {
			 System.out.println("in BadCredentialsException if ============= ");	
			
			response.sendError(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED, "Password or username incorrect");
		
		}else if(noBearerToken!=null && noBearerToken.startsWith("couldn't find bearer string")) {
	
			System.out.println("in noBearerToken if ============= ");	
			response.sendError(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED, noBearerToken);
			
		}else if(noUsername!=null && noUsername.startsWith("an error occurred during getting user name from token")) {
			
			System.out.println("in noUsername if ============= ");	
			response.sendError(HttpServletResponse.SC_NOT_FOUND, noUsername);
			
		}else if(authenticationFailed!=null && authenticationFailed.startsWith("Authentication Failed. Username or Password not valid.")) {
			
			System.out.println("in authenticationFailed if ============= ");	
			response.sendError(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED,authenticationFailed);
			
		}else if(expiredAccount!=null && expiredAccount.startsWith("The user account is expired")) {
			
			System.out.println("in expiredAccount if ============= ");
			response.sendError(HttpServletResponse.SC_FORBIDDEN,expiredAccount);
			
		}else if(accountLocked!=null && accountLocked.startsWith("The user account is locked")) {
			
			System.out.println("in accountLocked if ============= ");
			response.sendError(HttpServletResponse.SC_FORBIDDEN,accountLocked);
			
		}else if(accountCredentialsIsExpired!=null && accountCredentialsIsExpired.startsWith("The user account Credentials is expired")) {
			
			System.out.println("in accountCredentialsIsExpired if ============= ");
			response.sendError(HttpServletResponse.SC_FORBIDDEN,accountCredentialsIsExpired);
			
		}else if(accountDisabled!=null && accountDisabled.startsWith("The user account is disabled")) {
			
			System.out.println("in accountDisabled if ============= ");
			response.sendError(HttpServletResponse.SC_FORBIDDEN,accountDisabled);
			
		}else if(isPhoneVerified!=null && isPhoneVerified.startsWith("The user phone number is not verified")) {
			
			System.out.println("in isPhoneVerified if ============= ");
			response.sendError(HttpServletResponse.SC_FORBIDDEN,isPhoneVerified);
			
		}else {
			
			System.out.println("in final else ============= ");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        
		}
    }
}
