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
import java.util.HashMap;
import java.util.Map;
import com.centit.core.dao.CodeBook;
import com.centit.core.dao.BaseDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import &{classname};
&{if:daoInterface}import &{basepackage}.dao.&{simpleclassname}Dao;&{end-if}
public class &{simpleclassname}Dao&{if:daoInterface}Impl&{end-if} extends BaseDaoImpl<&{simpleclassname}>
	&{if:daoInterface}implements &{simpleclassname}Dao&{end-if}{
		private static final long serialVersionUID = 1L;
		public static final Log log = LogFactory.getLog(&{simpleclassname}Dao.class);
		
	public Map<String, String> getFilterField() {
		if( filterField == null){
			filterField = new HashMap<String, String>();
&{for-each:keyproperty}
			filterField.put("&{keyproperty}" , CodeBook.EQUAL_HQL_ID);
&{end-for-each}
&{for-each:property}
			filterField.put("&{property}" , CodeBook.LIKE_HQL_ID);
&{end-for-each}
		}
		return filterField;
	} 
}
