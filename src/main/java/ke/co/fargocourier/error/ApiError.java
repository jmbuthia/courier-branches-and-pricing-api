package ke.co.fargocourier.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ApiError {
	private String timestamp;
    private HttpStatus status;
    private List<String> errors;
    private String message;
    
   
 
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
		/*
		 * DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); Date
		 * date = new Date(); this.timestamp=dateFormat.format(date);
		 */
		/*
		 * DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); Calendar
		 * cal = Calendar.getInstance(); this.timestamp=dateFormat.format(cal);
		 */
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    	LocalDateTime now = LocalDateTime.now();
    	this.timestamp=dtf.format(now);
 
        
    }
 
    public ApiError(HttpStatus status, String message, String  error) {
        super();
        this.status = status;
        this.message = message;
        errors = (List<String>) Arrays.asList(error);
       
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    	LocalDateTime now = LocalDateTime.now();
    	this.timestamp=dtf.format(now);
    }
    
    
    
}