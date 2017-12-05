package &{basepackage}.controller;

import &{classname};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import &{basepackage}.service.&{simpleclassname}Manager;
&{for-each:onetomany}
import &{basepackage}.po.&{subclassname};&{end-for-each}	

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.centit.framework.common.JsonResultUtils;
import com.centit.framework.common.ResponseMapData;
import com.centit.framework.core.controller.BaseController;
import com.centit.support.database.utils.PageDesc;
/**
 * &{simpleclassname}  Controller.
 * create by scaffold &{today()} 
 * @author codefan@sina.com
 * &{tabledesc + tableComment}   
*/


@Controller
@RequestMapping("/&{appname+'/'+lowcase(simpleclassname)}")
public class &{simpleclassname}Controller  extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(&{simpleclassname}Controller.class);

	@Resource
	private &{simpleclassname}Manager &{entityname}Mag;	
	/*public void set&{simpleclassname}Mag(&{simpleclassname}Manager basemgr)
	{
		&{entityname}Mag = basemgr;
		//this.setBaseEntityManager(&{entityname}Mag);
	}*/

    /**
     * 查询所有   &{tabledesc}  列表
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return {data:[]}
     */
    @RequestMapping(method = RequestMethod.GET)
    public void list(PageDesc pageDesc, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> searchColumn = convertSearchColumn(request);        
        
        JSONArray listObjects = &{entityname}Mag.list&{simpleclassname}sAsJson(
			super.getLoginUser(request),searchColumn, pageDesc);

        if (null == pageDesc) {
            JsonResultUtils.writeSingleDataJson(listObjects, response);
            return;
        }

		ResponseMapData resData = new ResponseMapData();
        resData.addResponseData(OBJLIST, listObjects);
        resData.addResponseData(PAGE_DESC, pageDesc);

        JsonResultUtils.writeResponseDataAsJson(resData, response);
    }
    
    /**
     * 查询单个  &{tabledesc} 
	&{for-each:keyproperty}
	 * @param &{keyproperty}  &{keypropertyColumn}&{end-for-each}
     * @param response    {@link HttpServletResponse}
     * @return {data:{}}
     */
    @RequestMapping(value = "&{for-each:keyproperty}/{&{keyproperty}}&{end-for-each}", method = {RequestMethod.GET})
    public void get&{simpleclassname}(&{for-each:keyproperty}@PathVariable &{keypropertytype} &{keyproperty},&{end-for-each} HttpServletResponse response) {
    	&{if:hasID}
    	&{simpleclassname} &{entityname} =     			
    			&{entityname}Mag.getObjectById(new &{idtype}( &{for-each:keyproperty}&{if:!isfirstkeyproperty},&{end-if} &{keyproperty}&{end-for-each}) );
    	&{end-if}&{if:!hasID}
    	&{simpleclassname} &{entityname} =     			
    			&{entityname}Mag.getObjectById( &{for-each:keyproperty}&{keyproperty}&{end-for-each});
        &{end-if}
        JsonResultUtils.writeSingleDataJson(&{entityname}, response);
    }
    
    /**
     * 新增 &{tabledesc}
     *
     * @param &{entityname}  {@link &{simpleclassname}}
     * @return
     */
    @RequestMapping(method = {RequestMethod.POST})
    public void create&{simpleclassname}(@Valid &{simpleclassname} &{entityname}, HttpServletResponse response) {
    	&{entityname}Mag.saveNewObject(&{entityname});
        JsonResultUtils.writeSingleDataJson(&{entityname}.get&{U_idname}(),response);
    }

    /**
     * 删除单个  &{tabledesc} 
	&{for-each:keyproperty}
	 * @param &{keyproperty}  &{keypropertyColumn}&{end-for-each}
     */
    @RequestMapping(value = "&{for-each:keyproperty}/{&{keyproperty}}&{end-for-each}", method = {RequestMethod.DELETE})
    public void delete&{simpleclassname}(&{for-each:keyproperty}@PathVariable &{keypropertytype} &{keyproperty},&{end-for-each} HttpServletResponse response) {
    	&{if:hasID}
    	&{entityname}Mag.deleteObjectById(new &{idtype}( &{for-each:keyproperty}&{if:!isfirstkeyproperty},&{end-if} &{keyproperty}&{end-for-each}) );
    	&{end-if}&{if:!hasID}
    	&{entityname}Mag.deleteObjectById( &{for-each:keyproperty}&{keyproperty}&{end-for-each});
        &{end-if}
        JsonResultUtils.writeBlankJson(response);
    } 
    
    /**
     * 新增或保存 &{tabledesc} 
    &{for-each:keyproperty}
	 * @param &{keyproperty}  &{keypropertyColumn}&{end-for-each}
	 * @param &{entityname}  {@link &{simpleclassname}}
     * @param response    {@link HttpServletResponse}
     */
    @RequestMapping(value = "&{for-each:keyproperty}/{&{keyproperty}}&{end-for-each}", method = {RequestMethod.PUT})
    public void update&{simpleclassname}(&{for-each:keyproperty}@PathVariable &{keypropertytype} &{keyproperty},&{end-for-each} 
    	@Valid &{simpleclassname} &{entityname}, HttpServletResponse response) {
    	
    	&{if:hasID}
    	&{simpleclassname} db&{simpleclassname} =     			
    			&{entityname}Mag.getObjectById(new &{idtype}( &{for-each:keyproperty}&{if:!isfirstkeyproperty},&{end-if} &{keyproperty}&{end-for-each}) );
    	&{end-if}&{if:!hasID}
    	&{simpleclassname} db&{simpleclassname}  =     			
    			&{entityname}Mag.getObjectById( &{for-each:keyproperty}&{keyproperty}&{end-for-each});
        &{end-if}
        

        if (null != &{entityname}) {
        	db&{simpleclassname} .copy(&{entityname});
        	&{entityname}Mag.mergeObject(db&{simpleclassname});
        } else {
            JsonResultUtils.writeErrorMessageJson("当前对象不存在", response);
            return;
        }

        JsonResultUtils.writeBlankJson(response);
    }
}
