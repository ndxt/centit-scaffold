<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%> 
<%@ include file="/page/common/css.jsp"%> 
<html>
	<head>
		<title>
			<s:text name="&{entityname}.list.title" />
		</title>
	</head>

	<body>
		<%@ include file="/page/common/messages.jsp"%>
		<fieldset
			style="border: hidden 1px #000000; ">
			<legend>
				 <s:text name="label.list.filter" />
			</legend>
			
			<s:form action="&{entityname}" namespace="/&{appname}" style="margin-top:0;margin-bottom:5">
				<table cellpadding="0" cellspacing="0" align="center">
&{for-each:keyproperty}
					<tr >
						<td><s:text name="&{entityname}.&{keyproperty}" />:</td>
						<td><s:textfield name="s_&{keyproperty}" /> </td>
					</tr>	
&{end-for-each}
&{for-each:property}
					<tr >
						<td><s:text name="&{entityname}.&{property}" />:</td>
						<td><s:textfield name="s_&{property}" /> </td>
					</tr>	
&{end-for-each}
					<tr>
						<td>
							<s:submit method="list"  key="opt.btn.query" cssClass="btn"/>
						</td>
						<td>
							<s:submit method="built"  key="opt.btn.new" cssClass="btn"/>
						</td>
					</tr>
				</table>
			</s:form>
		</fieldset>

		<ec:table action="&{appname}/&{entityname}!list.do" items="objList" var="&{entityname}"
			imagePath="${STYLE_PATH}/images/table/*.gif" retrieveRowsCallback="limit">
			<ec:exportXls fileName="&{entitylistname}.xls" ></ec:exportXls>
			<ec:exportPdf fileName="&{entitylistname}.pdf" headerColor="blue" headerBackgroundColor="white" ></ec:exportPdf>
			<ec:row>
&{for-each:keyproperty}
				<c:set var="t&{keyproperty}"><s:text name='&{entityname}.&{keyproperty}' /></c:set>	
				<ec:column property="&{keyproperty}" title="${t&{keyproperty}}" style="text-align:center" />
&{end-for-each}
&{for-each:property}
				<c:set var="t&{property}"><s:text name='&{entityname}.&{property}' /></c:set>	
				<ec:column property="&{property}" title="${t&{property}}" style="text-align:center" />
&{end-for-each}		
				<c:set var="optlabel"><s:text name="opt.btn.collection"/></c:set>	
				<ec:column property="opt" title="${optlabel}" sortable="false"
					style="text-align:center">
					<c:set var="deletecofirm"><s:text name="label.delete.confirm"/></c:set>
					<a href='&{appname}/&{entityname}!view.do?&{idjspdesc}&ec_p=${ec_p}&ec_crd=${ec_crd}'><s:text name="opt.btn.view" /></a>
					<a href='&{appname}/&{entityname}!edit.do?&{idjspdesc}'><s:text name="opt.btn.edit" /></a>
					<a href='&{appname}/&{entityname}!delete.do?&{idjspdesc}' 
							onclick='return confirm("${deletecofirm}&{entityname}?");'><s:text name="opt.btn.delete" /></a>
				</ec:column>

			</ec:row>
		</ec:table>

	</body>
</html>
