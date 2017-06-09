define(function(require) {
	var Config = require('config');
	var Core = require('core/core');
	var &{simpleclassname}Add = require('./&{lowcase(simpleclassname)}.add');

	var &{simpleclassname}Edit = &{simpleclassname}Add.extend(function() {
		var _self = this;
		
		// @override
		this.load = function(panel, data) {
			var form = panel.find('form');
			
			Core.ajax(Config.ContextPath+'service/&{appname}/&{lowcase(simpleclassname)}/'+data.&{idname}, {
				type: 'json',
				method: 'get' 
			}).then(function(data) {
				_self.data = data;
				
				form.form('load', data)
					.form('disableValidation')
					.form('readonly', '&{idname}')
					.form('focus');
			});
		};
		
		// @override
		this.submit = function(panel, data, closeCallback) {
			var form = panel.find('form');
			
			// 开启校验
			form.form('enableValidation');
			var isValid = form.form('validate');
			
			if (isValid) {
				form.form('ajax', {
					url: Config.ContextPath + 'service/&{appname}/&{lowcase(simpleclassname)}/' + data.&{idname},
					method: 'put',
					data: data 
				}).then(closeCallback);
			}

			return false;
		};
		
		// @override
		this.onClose = function(table) {
			table.datagrid('reload');
		};
	});

	return &{simpleclassname}Edit;
});