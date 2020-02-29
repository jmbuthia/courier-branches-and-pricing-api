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

@Entity(name="tariffs")
@Setter
@Getter
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"from_suburb_id",
		"to_suburb_id","review_date"})})
public class Tariff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="from_suburb_id",nullable=false,foreignKey=@ForeignKey(name="FK_FROM_SUBURB_ID"))
	private Suburb fromSuburbId;
	
	@ManyToOne
	@JoinColumn(name="to_suburb_id",nullable=false,foreignKey=@ForeignKey(name="FK_TO_SUBURB_ID"))
	private Suburb toSuburbId;
	@Column(nullable=false)
	private double amount;
	@Column(name="review_date",nullable=false)
	private Date reviewDate;
	@Column(name="description")
	private String description;
}
