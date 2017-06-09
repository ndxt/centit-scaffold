<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<form class="centit form" novalidate="true">

       	&{for-each:keyproperty}
       	<div class="field required">
       		<label>&{keypropertydesc}</label>
       		<input type="text" class="easyui-textbox" name="&{keyproperty}"
				required 
			/>
		</div>
       	&{end-for-each}
   		&{for-each:property}
   		<div class="field &{if:propertynotnull}required&{end-if}">
       		<label>&{propertydesc}</label>
       		<input type="text" class="&{if(propertytype="Timestamp" || propertytype="Date","easyui-datebox","easyui-textbox")}" 
       		name="&{property}" &{if:propertynotnull}required&{end-if}/>
		</div>
    	&{end-for-each}
</form>