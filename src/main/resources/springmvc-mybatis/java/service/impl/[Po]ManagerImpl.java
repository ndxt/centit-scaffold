package &{basepackage}.service&{if:managerInterface}.impl&{end-if};

import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.centit.framework.core.dao.PageDesc;
import com.centit.framework.mybatis.dao.SysDaoOptUtils;
import com.centit.framework.mybatis.service.BaseEntityManagerImpl;
import &{classname};
import org.apache.ibatis.session.SqlSession;
import com.centit.support.common.KeyValuePair;
import &{basepackage}.dao.&{simpleclassname}Dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
&{if:managerInterface}import &{basepackage}.service.&{simpleclassname}Manager;&{end-if}
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * &{simpleclassname}  Service.
 * create by scaffold &{today()} 
 * @author codefan@sina.com
 * &{tabledesc + tableComment}   
*/
@Service
public class &{simpleclassname}Manager&{if:managerInterface}Impl&{end-if} 
		extends BaseEntityManagerImpl<&{simpleclassname},&{idtype},&{simpleclassname}Dao>
	&{if:managerInterface}implements &{simpleclassname}Manager&{end-if}{

	public static final Log log = LogFactory.getLog(&{simpleclassname}Manager.class);

	
	private &{simpleclassname}Dao &{entityname}Dao ;
	
	@Resource(name = "&{entityname}Dao")
    @NotNull
	public void set&{simpleclassname}Dao(&{simpleclassname}Dao baseDao)
	{
		this.&{entityname}Dao = baseDao;
		setBaseDao(this.&{entityname}Dao);
	}
	
/*
 	@PostConstruct
    public void init() {
        
    }
 	
 */
	@Override
    @Transactional(propagation=Propagation.REQUIRED) 
	public JSONArray list&{simpleclassname}sAsJson(
            String sqlSen, String[] fields,
            Map<String, Object> filterMap, 
            Map<String, KeyValuePair<String,String>> dictionaryMap, PageDesc pageDesc){
			
		return SysDaoOptUtils.listObjectsAsJson(baseDao,sqlSen, filterMap, fields,
				dictionaryMap , pageDesc);
	}
	
}

