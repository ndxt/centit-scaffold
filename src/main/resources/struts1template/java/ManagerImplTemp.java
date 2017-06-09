package &{basepackage}.service&{if:managerInterface}.impl&{end-if};

import com.centit.core.service.BaseEntityManagerImpl;
import &{classname};
import &{basepackage}.dao.&{simpleclassname}Dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
&{if:managerInterface}import &{basepackage}.service.&{simpleclassname}Manager;&{end-if}

public class &{simpleclassname}Manager&{if:managerInterface}Impl&{end-if} extends BaseEntityManagerImpl<&{simpleclassname}>
	&{if:managerInterface}implements &{simpleclassname}Manager&{end-if}{
	private static final long serialVersionUID = 1L;
	public static final Log log = LogFactory.getLog(&{simpleclassname}Manager.class);

	private &{simpleclassname}Dao &{entityname}Dao ;
	public void set&{simpleclassname}Dao(&{simpleclassname}Dao baseDao)
	{
		this.&{entityname}Dao = baseDao;
		setBaseDao(this.&{entityname}Dao);
	}
	
}

