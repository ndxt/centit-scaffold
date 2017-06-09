<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%>
<%@ include file="/page/common/css.jsp"%> 
<html>
	<head>
		<title><c:out value="&{entityname}.list.title" /></title>
		<link href="<c:out value='${STYLE_PATH}'/>/css/am.css" type="text/css"
			rel="stylesheet">
		<link href="<c:out value='${STYLE_PATH}'/>/css/extremecomponents.css"
			type="text/css" rel="stylesheet">
		<link href="<c:out value='${STYLE_PATH}'/>/css/messages.css"
			type="text/css" rel="stylesheet">
	</head>

	<body>
		<%@ include file="/page/common/messages.jsp"%>
		<fieldset
			style="border: hidden 1px #000000; ">
			<legend>
				 <s:text name="label.list.filter" />
			</legend>
			<html:form action="/&{appname}/&{entityname}.do" style="margin-top:0;margin-bottom:5">
				<table cellpadding="0" cellspacing="0" align="center">
&{for-each:keyproperty}
					<tr height="22">
						<td><c:out value="&{entityname}.&{keyproperty}" />:</td>
						<td><html:text property="s_&{keyproperty}" /> </td>
					</tr>	
&{end-for-each}
&{for-each:property}
					<tr height="22">
						<td><c:out value="&{entityname}.&{property}" />:</td>
						<td><html:text property="s_&{property}" /> </td>
					</tr>	
&{end-for-each}
					<tr>
						<td>
							<html:submit property="method_list" styleClass="btn" > <bean:message key="opt.btn.query" /></html:submit>
						</td>
						<td>
							<html:submit property="method_edit" styleClass="btn" > <bean:message key="opt.btn.new" /> </html:submit>
						</td>
					</tr>
				</table>
			</html:form>
		</fieldset>

			<ec:table action="&{entityname}.do" items="&{entitylistname}" var="&{entityname}"
			imagePath="${STYLE_PATH}/images/table/*.gif" retrieveRowsCallback="limit">
			<ec:exportXls fileName="&{entitylistname}.xls" ></ec:exportXls>
			<ec:exportPdf fileName="&{entitylistname}.pdf" headerColor="blue" headerBackgroundColor="white" ></ec:exportPdf>
			<ec:row>
&{for-each:keyproperty}
				<c:set var="t&{keyproperty}"><bean:message bundle='&{appname}Res' key='&{entityname}.&{keyproperty}' /></c:set>	
				<ec:column property="&{keyproperty}" title="${t&{keyproperty}}" style="text-align:center" />
&{end-for-each}
&{for-each:property}
				<c:set var="t&{property}"><bean:message bundle='&{appname}Res' key='&{entityname}.&{property}' /></c:set>	
				<ec:column property="&{property}" title="${t&{property}}" style="text-align:center" />
&{end-for-each}		
				<c:set var="optlabel"><bean:message key="opt.btn.collection"/></c:set>	
				<ec:column property="opt" title="${optlabel}" sortable="false"
					style="text-align:center">
					<c:set var="deletecofirm"><bean:message key="label.delete.confirm"/></c:set>
					<a href='&{entityname}.do?&{idjspdesc}&method=view'><bean:message key="opt.btn.view" /></a>
					<a href='&{entityname}.do?&{idjspdesc}&method=edit'><bean:message key="opt.btn.edit" /></a>
					<a href='&{entityname}.do?&{idjspdesc}&method=delete' 
							onclick='return confirm("${deletecofirm}&{entityname}?");'><bean:message key="opt.btn.delete" /></a>
				</ec:column>

			</ec:row>
		</ec:table>

	</body>
</html>
