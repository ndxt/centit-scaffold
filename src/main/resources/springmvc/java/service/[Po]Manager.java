package &{basepackage}.service;

import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.centit.support.database.utils.PageDesc;
import com.centit.framework.jdbc.service.BaseEntityManager;
import com.centit.framework.security.model.CentitUserDetails;
import &{classname};

/**
 * &{simpleclassname}  Service.
 * create by scaffold &{today()} 
 * @author codefan@sina.com
 * &{tabledesc + tableComment}   
*/

public interface &{simpleclassname}Manager extends BaseEntityManager<&{simpleclassname},&{idtype}> 
{
	
	public JSONArray list&{simpleclassname}sAsJson(CentitUserDetails ud,
            Map<String, Object> filterMap, PageDesc pageDesc);
}
