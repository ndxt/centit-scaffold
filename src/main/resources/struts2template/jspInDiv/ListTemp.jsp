<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%>

<form id="pagerForm" method="post" action="&{entityname}.do">
	<input type="hidden" name="pageNum" value="1" /> <input type="hidden" name="numPerPage" value="${pageDesc.pageSize}" /> <input type="hidden" name="orderField"
		value="${s_orderField}" />
</form>



<div class="pageHeader">
	<s:form id="pagerForm" onsubmit="return navTabSearch(this);" action="/&{appname}/&{entityname}.do" method="post">
		<div class="searchBar">
			<ul class="searchContent">
				&{for-each:keyproperty}
					<li><label><c:out value="&{entityname}.&{keyproperty}" />:</label> <s:textfield name="s_&{keyproperty}" value="%{#parameters['s_&{keyproperty}']}" /></li>
				&{end-for-each}
				&{for-each:property}
					<li><label><c:out value="&{entityname}.&{property}" />:</label> <s:textfield name="s_&{property}" value="%{#parameters['s_&{property}']}" /></li>
				&{end-for-each}
			</ul>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<s:submit method="list"><bean:message key="opt.btn.query" /></s:submit>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<!-- 参数 navTabId 根据实际情况填写 -->
								<button type="button" onclick="javascript:navTabAjaxDone({'statusCode' : 200, 'callbackType' : 'closeCurrent', 'navTabId' : ''});">返回</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</s:form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${contextPath }/&{appname}/&{entityname}!edit.do" rel="" target='dialog'><span>添加</span></a></li>
			<li><a class="edit" href="${contextPath }/&{appname}/&{entityname}!edit.do?&{for-each:keyproperty}&{keyproperty}&{end-for-each}={pk}" warn="请选择一条记录" rel="" target='dialog'><span>编辑</span></a></li>
			<li><a class="delete" href="${contextPath }/&{appname}/&{entityname}!delete.do?&{for-each:keyproperty}&{keyproperty}&{end-for-each}={pk}" warn="请选择一条记录" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
		</ul>
	</div>

	<div layoutH="116">
		<table class="list" width="98%" targetType="navTab" asc="asc" desc="desc">
			<thead align="center">

				<tr>
					&{for-each:keyproperty}
						<c:set var="t&{keyproperty}"><bean:message bundle='&{appname}Res' key='&{entityname}.&{keyproperty}' /></c:set>	
						<th>${t&{keyproperty}}</th>
					&{end-for-each}
					&{for-each:property}
						<c:set var="t&{property}"><bean:message bundle='&{appname}Res' key='&{entityname}.&{property}' /></c:set>	
						<th>${t&{property}}</th>
					&{end-for-each}
				</tr>
			</thead>
			<tbody align="center">
				<c:forEach items="${objList }" var="&{entityname}">
						<tr target="pk" rel="${&{entityname}.&{keyproperty}}">
							&{for-each:keyproperty}
								<td>${&{entityname}.&{keyproperty}}</td>
							&{end-for-each}
							&{for-each:property}
								<td>${&{entityname}.&{property}}</td>
							&{end-for-each}
						</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="../common/panelBar.jsp"></jsp:include>

<%-- 
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
 --%>