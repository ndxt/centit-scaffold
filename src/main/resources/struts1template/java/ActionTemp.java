package &{basepackage}.web;

import &{classname};
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
&{if:isflowopt}
import com.centit.workflow.sample.optmodel.BaseWFEntityAction;
&{end-if}	
&{if:!isflowopt}
import com.centit.core.web.BaseEntityAction;
&{end-if}
&{if:hasOnetomany}	
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
&{end-if}		
import &{basepackage}.service.&{simpleclassname}Manager;
&{for-each:onetomany}
import &{basepackage}.po.&{subclassname};&{end-for-each}	

public class &{simpleclassname}Action  extends &{if:isflowopt}BaseWFEntityAction&{end-if}&{if:!isflowopt}BaseEntityAction&{end-if}<&{simpleclassname}>  {
	private static final Log log = LogFactory.getLog(&{simpleclassname}Action.class);
	private &{simpleclassname}Manager &{entityname}Mag;
	public void set&{simpleclassname}Manager(&{simpleclassname}Manager basemgr)
	{
		&{entityname}Mag = basemgr;
		this.setBaseEntityManager(&{entityname}Mag);
	}

&{if:hasOnetomany}	
	@Override
	@SuppressWarnings("unchecked")
	protected void initEntity(ActionForm form, HttpServletRequest request,
			&{simpleclassname} object) {
		copyForm2Object(form, object);

&{for-each:onetomany}	//这个名字 和 Form 中的  logic:iterate id="&{subentityname}" id 相同
		Object &{subentityname}Bean = ((DynaBean) form).get("&{subentityname}");
	
		if( &{subentityname}Bean instanceof List){
			//object.getFAddressBookDetails().clear();
			List ls = (List) &{subentityname}Bean;
			List<&{subclassname}> newObjs = new ArrayList<&{subclassname}>();
			for(Object p :ls){
	    		if( p instanceof DynaBean){
	    			&{subclassname} newdt = object.new&{subclassname}();
	    			copyBean2Object(p,newdt);
	    			newObjs.add(newdt);
	    		}
			}
			//delete
			boolean found = false;
			Set<&{subclassname}> oldObjs = new HashSet<&{subclassname}>();
			oldObjs.addAll(object.get&{U_subentitylistname}());
			for(Iterator<&{subclassname}> it=oldObjs.iterator();
			 it.hasNext();){
				&{subclassname} odt = it.next();
				found = false;
				for(&{subclassname} newdt :newObjs){
					if(odt.get&{U_subidname}().equals( newdt.get&{U_subidname}())){
						found = true;
						break;
					}
				}
				if(! found)
					object.remove&{U_subentityname}(odt);
			}
			oldObjs.clear();
			//insert 
			for(&{subclassname} newdt :newObjs){
				found = false;
				for(Iterator<&{subclassname}> it=object.get&{U_subentitylistname}().iterator();
				 it.hasNext();){
					&{subclassname} odt = it.next();
					if(odt.get&{U_subidname}().equals( newdt.get&{U_subidname}())){
						odt.copy(newdt);
						found = true;
						break;
					}
				}
				if(! found)
					object.add&{U_subentityname}(newdt);
			} 	
		}
&{end-for-each}	
	}
&{end-if}		

}
