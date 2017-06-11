package &{basepackage}.dao&{if:daoInterface}.impl&{end-if};
&{if:false}
if：false 因为永远不会执行到，转换时会被清除，所以可以作为模板的注释使用
所有的变量都是用 &{ } 关联起来的变量可以是
basepackage 基础数据包 比如 com.centit.app 
classname po类名
simpleclassname po类短名
appname 应用名，比如 app
entityname 实体名，就是 simpleclassname 的首字母改成小写
entitylistname 实体复数名 就是 entityname 后面 加 s
keyproperty 主键属性名，配合 for-each:keyproperty 使用
property 属性名，配合 for-each:property 使用
idjspdesc 主键的描述符用在 URL中标识一个对象
daoInterface 是否有dao接口
managerInterface 是否有manager接口

控制语句也是 &{ } 关联起来，其中有冒号
 if:contdition 语句，冒号后为条件，条件中可以引用上面的 变量 ，也可以是四则运输
 if-not:contdition 相当于 if:!(contdition)
 for-each:items 现在的items只能是 keyproperty:property 
&{end-if}
import java.util.List;
import java.util.Map;
import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.core.dao.PageDesc;
import &{classname};
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * &{simpleclassname}Dao  Repository.
 * create by scaffold &{today()} 
 * @author codefan@sina.com
 * &{tabledesc + tableComment}   
*/

@Repository
public interface &{simpleclassname}Dao {

	public static final Log log = LogFactory.getLog(&{simpleclassname}Dao.class);

	/**
	 * @param obj
	 */
	void saveNew&{simpleclassname}(&{simpleclassname} obj);

	/**
	 * @param map
	 */
	void delete&{simpleclassname}ById(Map<String,String> map);
	/**
	 * @param obj
	 */
	void update&{simpleclassname}(MedplanYearPrep obj);

	/**
	 * @param key
	 * @return
	 */
	&{simpleclassname} get&{simpleclassname}(Map<String,String> key);
	/**
	 * @param filterDescMap
	 * @return
	 */
	int  count&{simpleclassname}(Map<String, Object> filterDescMap);

	/**
	 * @param pageQureyMap
	 * @return
	 */
	List<&{simpleclassname}>  list&{simpleclassname}(Map<String, Object> pageQureyMap, PageDesc pageDesc);


}
