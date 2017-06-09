define(function(require) {
	var Config = require('config');
	var Core = require('core/core');
	var Page = require('core/page');
	
	var &{simpleclassname}View = Page.extend(function() {
		
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
					.form('focus');
			});
		};
	});
	
	return &{simpleclassname}View;
});