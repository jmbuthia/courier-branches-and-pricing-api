package ke.co.fargocourier.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="branch_head_suburbs")
@Setter
@Getter
@NoArgsConstructor
public class BranchHeadSuburb {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name="branch_id",unique=true,nullable=false,foreignKey=@ForeignKey(name="FK_BRANCH_HEAD_SUBURB"))
	private Branch branch;
	
	@OneToOne
	@JoinColumn(name="suburb_id",nullable=false,unique=true,foreignKey=@ForeignKey(name="FK_SUBURB_HEAD_OF_BRANCH"))
	private Suburb suburb;
	
	@Column(name="start_date",nullable=false)
	private Date startDate;
	@Column(name="description")
	private String description;

}
