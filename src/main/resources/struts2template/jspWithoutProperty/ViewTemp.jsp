<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%>
<%@ include file="/page/common/css.jsp"%> 
<html>
<head>
<title><c:out value="&{entityname}.view.title" /></title>
<link href="<c:out value='${STYLE_PATH}'/>/css/am.css" type="text/css"
	rel="stylesheet">
&{if:hasOnetomany}
<link href="<c:out value='${STYLE_PATH}'/>/css/extremecomponents.css"
	type="text/css" rel="stylesheet">
&{end-if}
<link href="<c:out value='${STYLE_PATH}'/>/css/messages.css" type="text/css"
	rel="stylesheet">

</head>

<body>
<p class="ctitle"><c:out value="&{entityname}.view.title" /></p>

<%@ include file="/page/common/messages.jsp"%>

<html:button styleClass="btn" onclick="window.history.back()" property="none">
	<bean:message key="opt.btn.back" />
</html:button>
<p>	
	
<table width="200" border="0" cellpadding="1" cellspacing="1">		
&{for-each:keyproperty}  
				<tr>
					<td class="TDTITLE">
						<c:out value="&{entityname}.&{keyproperty}" />
					</td>
					<td align="left">
						<c:out value="${&{entityname}.&{keyproperty}}" />
					</td>
				</tr>
&{end-for-each}
&{for-each:property}
				<tr>
					<td class="TDTITLE">
						<c:out value="&{entityname}.&{property}" />
					</td>
					<td align="left">
						<c:out value="${&{entityname}.&{property}}" />
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
					<c:out value="&{subentityname}.&{subkeyproperty}" />
				</td>&{end-if}
&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}  
				<td class="tableHeader">
					<c:out value="&{subentityname}.&{subproperty}" />
				</td>&{end-if}
&{end-for-each}		
				<td class="tableHeader"><bean:message key="opt.btn.collection" /></td>
			</tr>  
		</thead>
		
		<tbody class="tableBody" >
		<c:set value="odd" var="rownum" />
		
		<c:forEach var="&{subentityname}" items="${&{entityname}.&{subentitylistname}}">    
			<tr class="${rownum}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${rownum}'" >
&{for-each:subkeyproperty}&{if:!keypropertyisref}    
				<td><c:out value="${&{subentityname}.&{subkeyproperty}}"/></td>  
&{end-if}&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}  
				<td><c:out value="${&{subentityname}.&{subproperty}}"/></td>  
&{end-if}&{end-for-each}		
				<td>
					<c:set var="deletecofirm"><bean:message key="label.delete.confirm"/></c:set>
					<a href='&{subentityname}.do?&{subidjspdesc}&method=edit'><bean:message key="opt.btn.edit" /></a>
					<a href='&{subentityname}.do?&{subidjspdesc}&method=delete' 
							onclick='return confirm("${deletecofirm}&{subentityname}?");'><bean:message key="opt.btn.delete" /></a>
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
