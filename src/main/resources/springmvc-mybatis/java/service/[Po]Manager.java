package &{basepackage}.service;

import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.centit.framework.core.dao.PageDesc;
import com.centit.framework.core.service.BaseEntityManager;
import &{classname};

/**
 * &{simpleclassname}  Service.
 * create by scaffold &{today()} 
 * @author codefan@sina.com
 * &{tabledesc + tableComment}   
*/

public interface &{simpleclassname}Manager extends BaseEntityManager<&{simpleclassname},&{idtype}> 
{
	
	public JSONArray list&{simpleclassname}sAsJson(
            String[] fields,
            Map<String, Object> filterMap, PageDesc pageDesc);
}
