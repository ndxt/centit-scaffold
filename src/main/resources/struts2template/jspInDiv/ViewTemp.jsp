<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="" method="post" id="pagerForm">
		<div class="searchBar">
			<ul class="searchContent">
				&{for-each:keyproperty}
				<li><label><c:out value="&{entityname}.&{keyproperty}" />:</label> <c:out value="${&{entityname}.&{keyproperty}}" /></li> &{end-for-each} &{for-each:property}
				<li><label><c:out value="&{entityname}.&{property}" />:</label> <c:out value="${&{entityname}.&{property}}" /></li> &{end-for-each}
			</ul>

			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">检索</button>
							</div>
						</div></li>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<!-- 参数 navTabId 根据实际情况填写 -->
								<button type="button" onclick="javascript:navTabAjaxDone({'statusCode' : 200, 'callbackType' : 'closeCurrent', 'navTabId' : ''});">返回</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
	</div>

	&{for-each:onetomany}
	<div layoutH="116">
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc">
			<thead align="center">

				<tr>
					&{for-each:subkeyproperty} 
						&{if:!keypropertyisref}
							<th><c:out value="&{subentityname}.&{subkeyproperty}" /></th> 
						&{end-if} 
					&{end-for-each} 
					&{for-each:subproperty} 
						&{if:!propertyisref}
							<th><c:out value="&{subentityname}.&{subproperty}" /></th> 
						&{end-if} 
					&{end-for-each}
				</tr>
			</thead>
			<tbody align="center">
				<c:forEach var="&{subentityname}" items="${&{entityname}.&{subentitylistname}}">
					<tr target="sid_user" rel="${user.usercode}">
						&{for-each:subkeyproperty} 
							&{if:!keypropertyisref}
								<td><c:out value="${&{subentityname}.&{subkeyproperty}}" /></td> 
							&{end-if} 
						&{end-for-each} 
						&{for-each:subproperty} 
							&{if:!propertyisref}
								<td><c:out value="${&{subentityname}.&{subproperty}}" /></td> 
							&{end-if} 
						&{end-for-each}
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	&{end-for-each}
</div>



<%-- 
<html>
<head>
<title><c:out value="&{entityname}.view.title" /></title>
<link href="<c:out value='${STYLE_PATH}'/>/css/am.css" type="text/css" rel="stylesheet">
&{if:hasOnetomany}
<link href="<c:out value='${STYLE_PATH}'/>/css/extremecomponents.css" type="text/css" rel="stylesheet">
&{end-if}
<link href="<c:out value='${STYLE_PATH}'/>/css/messages.css" type="text/css" rel="stylesheet">

</head>

<body>
	<p class="ctitle">
		<c:out value="&{entityname}.view.title" />
	</p>

	<%@ include file="/page/common/messages.jsp"%>

	<html:button styleClass="btn" onclick="window.history.back()" property="none">
		<bean:message key="opt.btn.back" />
	</html:button>
	<p>
	<table width="200" border="0" cellpadding="1" cellspacing="1">
		&{for-each:keyproperty}
		<tr>
			<td class="TDTITLE"><c:out value="&{entityname}.&{keyproperty}" /></td>
			<td align="left"><c:out value="${&{entityname}.&{keyproperty}}" /></td>
		</tr>
		&{end-for-each} &{for-each:property}
		<tr>
			<td class="TDTITLE"><c:out value="&{entityname}.&{property}" /></td>
			<td align="left"><c:out value="${&{entityname}.&{property}}" /></td>
		</tr>
		&{end-for-each}
	</table>

	&{for-each:onetomany}
	<p />
	<div class="eXtremeTable">
		<table id="ec_table" border="0" cellspacing="0" cellpadding="0" class="tableRegion" width="100%">

			<thead>
				<tr>
					&{for-each:subkeyproperty}&{if:!keypropertyisref}
					<td class="tableHeader"><c:out value="&{subentityname}.&{subkeyproperty}" /></td>&{end-if} &{end-for-each} &{for-each:subproperty}&{if:!propertyisref}
					<td class="tableHeader"><c:out value="&{subentityname}.&{subproperty}" /></td>&{end-if} &{end-for-each}
					<td class="tableHeader"><bean:message key="opt.btn.collection" /></td>
				</tr>
			</thead>

			<tbody class="tableBody">
				<c:set value="odd" var="rownum" />

				<c:forEach var="&{subentityname}" items="${&{entityname}.&{subentitylistname}}">
					<tr class="${rownum}" onmouseover="this.className='highlight'" onmouseout="this.className='${rownum}'">
						&{for-each:subkeyproperty}&{if:!keypropertyisref}
						<td><c:out value="${&{subentityname}.&{subkeyproperty}}" /></td> &{end-if}&{end-for-each} &{for-each:subproperty}&{if:!propertyisref}
						<td><c:out value="${&{subentityname}.&{subproperty}}" /></td> &{end-if}&{end-for-each}
						<td><c:set var="deletecofirm">
								<bean:message key="label.delete.confirm" />
							</c:set> <a href='&{subentityname}.do?&{subidjspdesc}&method=edit'><bean:message key="opt.btn.edit" /></a> <a href='&{subentityname}.do?&{subidjspdesc}&method=delete'
							onclick='return confirm("${deletecofirm}&{subentityname}?");'><bean:message key="opt.btn.delete" /></a></td>
					</tr>
					<c:set value="${rownum eq 'odd'? 'even': 'odd'}" var="rownum" />
				</c:forEach>
			</tbody>
		</table>
	</div>
	&{end-for-each}

</body>
</html> --%>
