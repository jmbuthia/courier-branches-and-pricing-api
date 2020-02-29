package ke.co.fargocourier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/*import javax.persistence.Table;
import javax.persistence.UniqueConstraint;*/

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="branches")
//You can use at class level with following syntax
//@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name","phone"})})
@Setter
@Getter
@NoArgsConstructor
public class Branch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique=true,nullable=false)
	private String name;
	@Column(unique=true,nullable=false)
	private String phone;
	@Column(name="physical_location",nullable=false)
	private String physicalLocation;
	@Column(name="gps_location")
	private String gpsLocation;
	@Column(name="image_url")
	private String imageUrl;
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="route_id",nullable=false,foreignKey=@ForeignKey(name="FK_BRANCH_ROUTE"))
	private Route route;
	

}
