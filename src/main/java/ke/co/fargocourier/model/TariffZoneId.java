/*
 * package ke.co.fargocourier.model;
 * 
 * import java.io.Serializable; import java.util.Objects;
 * 
 * import javax.persistence.Embeddable; import javax.persistence.ForeignKey;
 * import javax.persistence.JoinColumn; import javax.persistence.ManyToOne;
 * 
 * import lombok.AllArgsConstructor; import lombok.Getter; import
 * lombok.NoArgsConstructor; import lombok.Setter;
 * 
 * @Embeddable
 * 
 * @Setter
 * 
 * @Getter
 * 
 * @NoArgsConstructor
 * 
 * @AllArgsConstructor public class TariffZoneId implements Serializable{
 * 
 *//**
	* 
	*//*
		 * private static final long serialVersionUID = 1L;
		 * 
		 * @ManyToOne
		 * 
		 * @JoinColumn(name="from_branch_id",nullable=false,foreignKey=@ForeignKey(name=
		 * "FK_FROM_BRANCH_ID")) private Branch fromBranchId;
		 * 
		 * @ManyToOne
		 * 
		 * @JoinColumn(name="to_branch_id",nullable=false,foreignKey=@ForeignKey(name=
		 * "FK_TO_BRANCH_ID")) private Branch toBranchId;
		 * 
		 * 
		 * 
		 * 
		 * @Column(name="from_branch_id") private Long fromBranchId;
		 * 
		 * @Column(name="to_branch_id") private Long toBranchId;
		 * 
		 * 
		 * 
		 * @Override public boolean equals(Object obj) { if(this==obj) return true;
		 * if(!(obj instanceof TariffZoneId)) return false;
		 * 
		 * TariffZoneId that=(TariffZoneId) obj;
		 * 
		 * return Objects.equals(getFromBranchId(),that.getFromBranchId()) &&
		 * Objects.equals(getToBranchId(),that.getToBranchId()); }
		 * 
		 * @Override public int hashCode() { return
		 * Objects.hash(getFromBranchId(),getToBranchId()); }
		 * 
		 * 
		 * 
		 * }
		 */