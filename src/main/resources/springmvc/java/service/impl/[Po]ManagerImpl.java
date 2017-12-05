package &{basepackage}.service&{if:managerInterface}.impl&{end-if};

import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.centit.support.database.utils.PageDesc;
import com.centit.framework.core.dao.DictionaryMapUtils;
import com.centit.framework.core.dao.DataPowerFilter;
import com.centit.framework.jdbc.service.BaseEntityManagerImpl;
import com.centit.framework.security.model.CentitUserDetails;
import com.centit.framework.system.service.GeneralService;
import &{classname};
import &{basepackage}.dao.&{simpleclassname}Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
&{if:managerInterface}import &{basepackage}.service.&{simpleclassname}Manager;&{end-if}

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

	private static final Logger logger = LoggerFactory.getLogger(&{simpleclassname}Manager.class);

	@Resource
	protected GeneralService generalService;

	private &{simpleclassname}Dao &{entityname}Dao ;
	
	@Resource(name = "&{entityname}Dao")
    @NotNull
	public void set&{simpleclassname}Dao(&{simpleclassname}Dao baseDao)
	{
		this.&{entityname}Dao = baseDao;
		setBaseDao(this.&{entityname}Dao);
	}

	public String getOptId(){
		return "&{simpleclassname}";
	}

	@Override
    @Transactional(propagation=Propagation.REQUIRED) 
	public JSONArray list&{simpleclassname}sAsJson(CentitUserDetails ud,
            Map<String, Object> filterMap, PageDesc pageDesc){
		// 无需数据范围权限过滤
		/*return DictionaryMapUtils.mapJsonArray(
				this.&{entityname}Dao.listObjectsAsJson(filterMap, pageDesc),
			&{simpleclassname}.class);*/
		// 需要数据范围权限过滤
		DataPowerFilter dataPowerFilter = generalService.createUserDataPowerFilter(ud);
		dataPowerFilter.addSourceData(filterMap);
		return DictionaryMapUtils.mapJsonArray(
			this.&{entityname}Dao.listObjectsAsJson(
					dataPowerFilter.getSourceData(),
					generalService.listUserDataFiltersByOptIDAndMethod(
						ud.getUserCode(), getOptId(), "list"),
					pageDesc),
			&{simpleclassname}.class);

	}
	
}

