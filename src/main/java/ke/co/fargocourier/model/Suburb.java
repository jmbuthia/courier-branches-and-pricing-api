package ke.co.fargocourier.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="suburbs")
@Setter
@Getter
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name","branch_id"})})
public class Suburb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="date_created",nullable=false)
	private Date dateCreated;
	
	@ManyToOne
	@JoinColumn(name="branch_id",nullable=false,foreignKey=@ForeignKey(name="FK_BRANCH_SUBURB"))
	private Branch branch;
	

	@ManyToOne
	@JoinColumn(name="standing_weight_charge_id",nullable=false,
	foreignKey=@ForeignKey(name="FK_STANDINGWEIGHTCHARGE_SUBURB"))
	private StandingWeightCharge standingWeightCharge;
	
	
	
}
