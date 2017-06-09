package &{basepackage}.action;

import &{classname};
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

&{if:isflowopt}
import com.centit.workflow.sample.optmodel.BaseWFEntityAction;
&{end-if}	
&{if:!isflowopt}
import com.centit.core.action.BaseEntityDwzAction;
&{end-if}
&{if:hasOnetomany}	
import java.util.List;
&{end-if}		

import &{basepackage}.service.&{simpleclassname}Manager;
&{for-each:onetomany}
import &{basepackage}.po.&{subclassname};&{end-for-each}	

public class &{simpleclassname}Action  extends Base&{if:isflowopt}WF&{end-if}EntityDwzAction<&{simpleclassname}>  {
	private static final Log log = LogFactory.getLog(&{simpleclassname}Action.class);
	
	//private static final ISysOptLog sysOptLog = SysOptLogFactoryImpl.getSysOptLog("optid");
	
	private static final long serialVersionUID = 1L;
	private &{simpleclassname}Manager &{entityname}Mag;
	public void set&{simpleclassname}Manager(&{simpleclassname}Manager basemgr)
	{
		&{entityname}Mag = basemgr;
		this.setBaseEntityManager(&{entityname}Mag);
	}

	&{for-each:onetomany}private List<&{subclassname}> &{subentitylistname};
	public List<&{subclassname}> getNew&{U_subentitylistname}() {
		return this.&{subentitylistname};
	}
	public void setNew&{U_subentitylistname}(List<&{subclassname}> &{subentitylistname}) {
		this.&{subentitylistname} = &{subentitylistname};
	}
	&{end-for-each}
	
	&{if:hasOnetomany}	
	public String save(){
		&{for-each:onetomany}object.replace&{U_subentitylistname}( &{subentitylistname});
		&{end-for-each}
		return super.save();
	}
	
	&{end-if}	
	public String delete() {
	    super.delete();      
	    
	    return "delete";
	}
}
