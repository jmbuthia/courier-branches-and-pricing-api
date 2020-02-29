package ke.co.fargocourier.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="roles")
@Setter
@Getter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @Column
    private String description;
    
    //@ManyToMany(mappedBy = "roles")
    //private Set<User> users = new HashSet<>();

   
}
