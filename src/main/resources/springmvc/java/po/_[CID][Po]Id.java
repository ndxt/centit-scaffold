package &{basepackage}.po;

import java.util.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.validator.constraints.NotBlank;

/**
 * &{simpleclassname}Id  entity.
 * create by scaffold &{today()} 
 * @author codefan@sina.com
 * &{tabledesc + tableComment}   
*/
//&{tabledesc} 的主键
@Embeddable
public class &{simpleclassname}Id implements java.io.Serializable {
	private static final long serialVersionUID =  1L;
&{for-each:keyproperty}
	/**
	 * &{keypropertydesc} &{keypropertyComment} 
	 */
	@Column(name = "&{keypropertyColumn}")
	@NotBlank(message = "字段不能为空")
	private &{keypropertytype} &{keyproperty};
&{end-for-each}
	// Constructors
	/** default constructor */
	public &{simpleclassname}Id() {
	}
	/** full constructor */
	public &{simpleclassname}Id(&{for-each:keyproperty}&{if:!isfirstkeyproperty}, &{end-if}&{keypropertytype} &{keyproperty}&{end-for-each}) {
&{for-each:keyproperty}
		this.&{keyproperty} = &{keyproperty};&{end-for-each}	
	}

&{for-each:keyproperty}  
	public &{keypropertytype} get&{U_keyproperty}() {
		return this.&{keyproperty};
	}

	public void set&{U_keyproperty}(&{keypropertytype} &{keyproperty}) {
		this.&{keyproperty} = &{keyproperty};
	}
&{end-for-each}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof &{simpleclassname}Id))
			return false;
		
		&{simpleclassname}Id castOther = (&{simpleclassname}Id) other;
		boolean ret = true;
&{for-each:keyproperty}  
		ret = ret && ( this.get&{U_keyproperty}() == castOther.get&{U_keyproperty}() ||
					   (this.get&{U_keyproperty}() != null && castOther.get&{U_keyproperty}() != null
							   && this.get&{U_keyproperty}().equals(castOther.get&{U_keyproperty}())));
&{end-for-each}
		return ret;
	}
	
	public int hashCode() {
		int result = 17;
&{for-each:keyproperty}  
		result = 37 * result +
		 	(this.get&{U_keyproperty}() == null ? 0 :this.get&{U_keyproperty}().hashCode());
&{end-for-each}	
		return result;
	}
}
