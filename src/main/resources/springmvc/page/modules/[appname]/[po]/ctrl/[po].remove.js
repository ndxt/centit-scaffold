define(function(require) {
	var Config = require('config');
	var Core = require('core/core');
	var Page = require('core/page');
	
	// 删除数据字典
	var &{simpleclassname}Remove = Page.extend(function() {
		
		// @override
		this.submit = function(table, data) {
			Core.ajax(Config.ContextPath+'service/&{appname}/&{lowcase(simpleclassname)}/'+data.&{idname}, {
            	type: 'json',
                method: 'post',
                data: {
                    _method: 'delete'
                }
			}).then(function() {
				table.datagrid('reload');
            });
		}
	});
	
	return &{simpleclassname}Remove;
});