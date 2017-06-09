<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%> 
<%@ include file="/page/common/css.jsp"%> 
<html>
<head>
<title><s:text name="&{entityname}.view.title" /></title>
</head>

<body>
<p class="ctitle"><s:text name="&{entityname}.view.title" /></p>

<%@ include file="/page/common/messages.jsp"%>

<a href='&{appname}/&{entityname}!list.do?ec_p=${param.ec_p}&ec_crd=${param.ec_crd}' property="none">
	<s:text name="opt.btn.back" />
</a>
<p>	
	
<table width="200" border="0" cellpadding="1" cellspacing="1">		
&{for-each:keyproperty}  
				<tr>
					<td class="TDTITLE">
						<s:text name="&{entityname}.&{keyproperty}" />
					</td>
					<td align="left">
						<s:property value="%{&{keyproperty}}" />
					</td>
				</tr>
&{end-for-each}
&{for-each:property}
				<tr>
					<td class="TDTITLE">
						<s:text name="&{entityname}.&{property}" />
					</td>
					<td align="left">
						<s:property value="%{&{property}}" />
					</td>
				</tr>	
&{end-for-each}
</table>

&{for-each:onetomany}
<p/>
<div class="eXtremeTable" >
	<table id="ec_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >

		<thead>
			<tr>
&{for-each:subkeyproperty}&{if:!keypropertyisref}    
				<td class="tableHeader">
					<s:text name="&{subentityname}.&{subkeyproperty}" />
				</td>&{end-if}
&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}  
				<td class="tableHeader">
					<s:text name="&{subentityname}.&{subproperty}" />
				</td>&{end-if}
&{end-for-each}		
				<td class="tableHeader"><s:text name="opt.btn.collection" /></td>
			</tr>  
		</thead>
		
		<tbody class="tableBody" >
		<c:set value="odd" var="rownum" />
		
		<c:forEach var="&{subentityname}" items="${object.&{subentitylistname}}">    
			<tr class="${rownum}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${rownum}'" >
&{for-each:subkeyproperty}&{if:!keypropertyisref}    
				<td><c:out value="${&{subentityname}.&{subkeyproperty}}"/></td>  
&{end-if}&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}  
				<td><c:out value="${&{subentityname}.&{subproperty}}"/></td>  
&{end-if}&{end-for-each}		
				<td>
					<c:set var="deletecofirm"><s:text name="label.delete.confirm"/></c:set>
					<a href='&{appname}/&{subentityname}!edit.do?&{subidjspdesc}'><s:text name="opt.btn.edit" /></a>
					<a href='&{appname}/&{subentityname}!delete.do?&{subidjspdesc}' 
							onclick='return confirm("${deletecofirm}&{subentityname}?");'><s:text name="opt.btn.delete" /></a>
				</td>
			</tr>  
            <c:set value="${rownum eq 'odd'? 'even': 'odd'}" var="rownum" />
		</c:forEach> 
		</tbody>        
	</table>
</div>
&{end-for-each}

</body>
</html>
