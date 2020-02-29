package ke.co.fargocourier.model;
import static ke.co.fargocourier.model.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthToken {

	@Column(name="token")
    private String token;
	@Column(name="expireOn")
    private Date expireOn;
    
    public AuthToken(String token){
        this.token = token; 
        this.expireOn=new Date(System.currentTimeMillis()+ ACCESS_TOKEN_VALIDITY_SECONDS*1000);
    }


}
