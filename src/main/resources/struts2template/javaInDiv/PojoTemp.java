package &{basepackage}.po;

import java.util.Date;
&{if:hasOnetomany}
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
&{end-if}
/**
 * create by scaffold
 * @author codefan@hotmail.com
 */ 

public class &{simpleclassname} implements java.io.Serializable {
	private static final long serialVersionUID =  1L;
&{if:hasID}	private &{idtype} &{idname};&{end-if}
&{if:!hasID}&{for-each:keyproperty}
	private &{keypropertytype} &{keyproperty};&{end-for-each}&{end-if}
&{for-each:property}
	private &{propertytype}  &{property};&{end-for-each}
&{for-each:onetomany}	private Set<&{subclassname}> &{subentitylistname} = null;// new ArrayList<&{subclassname}>();
&{end-for-each}
	// Constructors
	/** default constructor */
	public &{simpleclassname}() {
	}
	/** minimal constructor */
	public &{simpleclassname}(&{if:hasID}&{idtype} id &{end-if}
		&{if:!hasID}&{for-each:keyproperty}&{if:!isfirstkeyproperty},&{end-if}&{keypropertytype} &{keyproperty}&{end-for-each}&{end-if}		
		&{for-each:property}&{if:propertynotnull},&{propertytype}  &{property}&{end-if}&{end-for-each}) {
	&{if:hasID}	this.&{idname} = id; &{end-if}
	&{if:!hasID}&{for-each:keyproperty}
		this.&{keyproperty} = &{keyproperty};&{end-for-each}&{end-if}		
	&{for-each:property}&{if:propertynotnull}
		this.&{property}= &{property}; &{end-if}&{end-for-each}		
	}

/** full constructor */
	public &{simpleclassname}(&{if:hasID}&{idtype} id&{end-if}
	&{if:!hasID}&{for-each:keyproperty}&{if:!isfirstkeyproperty},&{end-if} &{keypropertytype} &{keyproperty}&{end-for-each}&{end-if}		
	&{for-each:property},&{propertytype}  &{property}&{end-for-each}) {
	&{if:hasID}	this.&{idname} = id; &{end-if}
	&{if:!hasID}&{for-each:keyproperty}
		this.&{keyproperty} = &{keyproperty};&{end-for-each}&{end-if}		
	&{for-each:property}
		this.&{property}= &{property};&{end-for-each}		
	}
&{if:hasID}
	public &{idtype} get&{U_idname}() {
		return this.&{idname};
	}
	
	public void set&{U_idname}(&{idtype} id) {
		this.&{idname} = id;
	}
&{for-each:keyproperty}  
	public &{keypropertytype} get&{U_keyproperty}() {
		if(this.&{idname}==null)
			this.&{idname} = new &{idtype}();
		return this.&{idname}.get&{U_keyproperty}();
	}
	
	public void set&{U_keyproperty}(&{keypropertytype} &{keyproperty}) {
		if(this.&{idname}==null)
			this.&{idname} = new &{idtype}();
		this.&{idname}.set&{U_keyproperty}(&{keyproperty});
	}
&{end-for-each}	
&{end-if}	
&{if:!hasID}
&{for-each:keyproperty}  
	public &{keypropertytype} get&{U_keyproperty}() {
		return this.&{keyproperty};
	}

	public void set&{U_keyproperty}(&{keypropertytype} &{keyproperty}) {
		this.&{keyproperty} = &{keyproperty};
	}&{end-for-each}&{end-if}
	// Property accessors
&{for-each:property}  
	public &{propertytype} get&{U_property}() {
		return this.&{property};
	}
	
	public void set&{U_property}(&{propertytype} &{property}) {
		this.&{property} = &{property};
	}
&{end-for-each}
&{for-each:onetomany}
	public Set<&{subclassname}> get&{U_subentitylistname}(){
		if(this.&{subentitylistname}==null)
			this.&{subentitylistname} = new HashSet<&{subclassname}>();
		return this.&{subentitylistname};
	}

	public void set&{U_subentitylistname}(Set<&{subclassname}> &{subentitylistname}) {
		this.&{subentitylistname} = &{subentitylistname};
	}	

	public void add&{subclassname}(&{subclassname} &{L_subentityname} ){
		if (this.&{subentitylistname}==null)
			this.&{subentitylistname} = new HashSet<&{subclassname}>();
		this.&{subentitylistname}.add(&{L_subentityname});
	}
	
	public void remove&{subclassname}(&{subclassname} &{L_subentityname} ){
		if (this.&{subentitylistname}==null)
			return;
		this.&{subentitylistname}.remove(&{L_subentityname});
	}
	
	public &{subclassname} new&{subclassname}(){
		&{subclassname} res = new &{subclassname}();
&{for-each:keyproperty}  
		res.set&{U_refproperty}(this.get&{U_keyproperty}());
&{end-for-each}
		return res;
	}
	/**
	 * 替换子类对象数组，这个函数主要是考虑hibernate中的对象的状态，以避免对象状态不一致的问题
	 * 
	 */
	public void replace&{U_subentitylistname}(List<&{subclassname}> &{subentitylistname}) {
		List<&{subclassname}> newObjs = new ArrayList<&{subclassname}>();
		for(&{subclassname} p :&{subentitylistname}){
			if(p==null)
				continue;
			&{subclassname} newdt = new&{subclassname}();
			newdt.copyNotNullProperty(p);
			newObjs.add(newdt);
		}
		//delete
		boolean found = false;
		Set<&{subclassname}> oldObjs = new HashSet<&{subclassname}>();
		oldObjs.addAll(get&{U_subentitylistname}());
		
		for(Iterator<&{subclassname}> it=oldObjs.iterator(); it.hasNext();){
			&{subclassname} odt = it.next();
			found = false;
			for(&{subclassname} newdt :newObjs){
				if(odt.get&{U_subidname}().equals( newdt.get&{U_subidname}())){
					found = true;
					break;
				}
			}
			if(! found)
				remove&{subclassname}(odt);
		}
		oldObjs.clear();
		//insert or update
		for(&{subclassname} newdt :newObjs){
			found = false;
			for(Iterator<&{subclassname}> it=get&{U_subentitylistname}().iterator();
			 it.hasNext();){
				&{subclassname} odt = it.next();
				if(odt.get&{U_subidname}().equals( newdt.get&{U_subidname}())){
					odt.copy(newdt);
					found = true;
					break;
				}
			}
			if(! found)
				add&{subclassname}(newdt);
		} 	
	}	
&{end-for-each}

	public void copy(&{simpleclassname} other){
&{for-each:keyproperty}  
		this.set&{U_keyproperty}(other.get&{U_keyproperty}());&{end-for-each}
&{for-each:property}  
		this.&{property}= other.get&{U_property}();&{end-for-each}
&{for-each:onetomany}	
		this.&{subentitylistname} = other.get&{U_subentitylistname}();&{end-for-each}
	}
	
	public void copyNotNullProperty(&{simpleclassname} other){
&{for-each:keyproperty}  
	if( other.get&{U_keyproperty}() != null)
		this.set&{U_keyproperty}(other.get&{U_keyproperty}());&{end-for-each}
&{for-each:property}  
		if( other.get&{U_property}() != null)
			this.&{property}= other.get&{U_property}();&{end-for-each}
&{for-each:onetomany}	
		this.&{subentitylistname} = other.get&{U_subentitylistname}();&{end-for-each}
	}
	
	public void clearProperties(){
&{for-each:property}  
		this.&{property}= null;&{end-for-each}
&{for-each:onetomany}	
		this.&{subentitylistname} = new HashSet<&{subclassname}>();&{end-for-each}
	}
}
