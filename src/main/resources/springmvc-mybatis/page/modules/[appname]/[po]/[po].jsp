<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%> 

<table url="${ctx}/service/&{appname}/&{lowcase(simpleclassname)}"
	idField="id" treeField="text"
	rownumbers="false"
	toolbar=".temp-toolbar" layoutH="0">
    <thead>
        <tr>
        	&{for-each:keyproperty}<th data-options="field:'&{keyproperty}'">&{keypropertydesc}</th> &{end-for-each}
       		&{for-each:property}<th data-options="field:'&{property}'">&{propertydesc}</th>
       		&{end-for-each}
        </tr>
    </thead>
</table>

<div class="temp-toolbar">
	<a iconCls="icon-add" href="modules/&{appname}/&{lowcase(simpleclassname)}/&{lowcase(simpleclassname)}-info.jsp" trigger="none"
		target="dialog" rel="&{lowcase(simpleclassname)}_add" title="新增&{tabledesc}" width="640" height="480" btnValue="添加">新增</a>
	<hr>

	<a iconCls="icon-edit" href="modules/&{appname}/&{lowcase(simpleclassname)}/&{lowcase(simpleclassname)}-info.jsp" trigger="single"
		target="dialog" rel="&{lowcase(simpleclassname)}_edit" title="编辑 {{text}}" width="640" height="480" btnValue="更新">编辑</a>
	<hr>

	<a iconCls="icon-edit" href="modules/&{appname}/&{lowcase(simpleclassname)}/&{lowcase(simpleclassname)}-view.jsp" trigger="single"
		target="dialog" rel="&{lowcase(simpleclassname)}_view" title="查看 {{text}}" width="640" height="480" btnValue="查看">查看</a>
	<hr>
		
	<a iconCls="icon-base icon-base-ban" trigger="single"
		target="confirm" rel="&{lowcase(simpleclassname)}_remove" title="是否确定删除 {{text}}？">删除</a>
</div>

<script>
	$.parser.onComplete = function(panel) {
		$.parser.onComplete = $.noop;
		
		requirejs([
		           'modules/&{appname}/&{lowcase(simpleclassname)}/ctrl/&{lowcase(simpleclassname)}'
		          ], function(&{simpleclassname}) {
			window.&{simpleclassname} = new &{simpleclassname}('&{lowcase(simpleclassname)}', panel);
			window.&{simpleclassname}.load(panel);
		});
	};

</script>