define(function(require) {
    var Config = require('config');
    var Core = require('core/core');
    var Page = require('core/page');

    
    var &{simpleclassname}Add = require('./&{lowcase(simpleclassname)}.add');
    var &{simpleclassname}Edit = require('./&{lowcase(simpleclassname)}.edit');
    var &{simpleclassname}View = require('./&{lowcase(simpleclassname)}.view');
    var &{simpleclassname}Remove = require('./&{lowcase(simpleclassname)}.remove');

    // 业务信息
    var &{simpleclassname} = Page.extend(function() {
    	
    	this.injecte([
          new &{simpleclassname}Add('&{lowcase(simpleclassname)}_add'),
          new &{simpleclassname}Edit('&{lowcase(simpleclassname)}_edit'),
          new &{simpleclassname}View('&{lowcase(simpleclassname)}_view'),
          new &{simpleclassname}Remove('&{lowcase(simpleclassname)}_remove')
    	]);
    	
    	// @override
    	this.load = function(panel) {
    		var table = this.table = panel.find('table');
    		
    		table.cdatagrid({
    			controller: this
    		});
    	};
    	
    });

    return &{simpleclassname};
});