package ke.co.fargocourier.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name="users")
@Setter
@Getter
@NoArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "first_name", nullable = false)
    private String firstname;
    
    @Column(name = "last_name", nullable = false)
    private String lastname;
    
    @Column(name = "phone_number",nullable=false)
    private String phone;
    
    @Column(name = "profile_picture",length = 3000)
    private String profile;
    
    @Column(nullable=false)
    @JsonIgnore
    private String password;
    
    
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @Column(name="is_phone_verified")
    private boolean isPhoneVerified;
    
    @Column(name="is_account_non_expired")
    private boolean isAccountNonExpired;

    @Column(name="is_account_non_locked")
    private boolean isAccountNonLocked;
    
    @Column(name="is_credential_non_expired")
    private boolean isCredentialsNonExpired;
    
    @Column(name="is_enabled")
    private boolean isEnabled;

	@OneToOne
	@JoinColumn(name="branch_id",foreignKey=@ForeignKey(name="FK_USER_BRANCH"))
	private Branch branch;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles  = new HashSet<>();



	/*
	 * @OneToMany(cascade = {CascadeType.MERGE},fetch=FetchType.EAGER) private
	 * Set<Role> roles = new HashSet<>();
	 */
  	
}
