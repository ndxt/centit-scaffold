<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%>

<html>
<head>
<title><c:out value="&{entityname}.edit.title" /></title>
<link href="<c:out value='${style}'/>/css/am.css" type="text/css"
	rel="stylesheet">
<link href="<c:out value='${style}'/>/css/messages.css" type="text/css"
	rel="stylesheet">
&{if:hasOnetomany}
<link href="<c:out value='${style}'/>/css/extremecomponents.css"
	type="text/css" rel="stylesheet">
<script language="JavaScript" src="<c:url value='/scripts/jquery1.3.2.js'/>"
	type="text/JavaScript"></script>	
&{end-if}
<script type="text/javascript"
	src="<c:url value='/page/common/validator.jsp'/>"></script>
<html:javascript formName="&{entityname}Form" staticJavascript="false"
	dynamicJavascript="true" cdata="false" />
</head>

<body>
<p class="ctitle"><c:out value="&{entityname}.edit.title" /></p>

<%@ include file="/page/common/messages.jsp"%>

<html:form action="/&{appname}/&{entityname}"  styleId="&{entityname}Form" onsubmit="return validate&{entityname}Form(this);">
	<html:submit property="method_save" styleClass="btn" ><bean:message key="opt.btn.save" /></html:submit>
	<html:button styleClass="btn" onclick="window.history.back()" property="none"> <bean:message key="opt.btn.back" /> </html:button>
		
<table width="200" border="0" cellpadding="1" cellspacing="1">		
 &{for-each:keyproperty}
				<tr>
					<td class="TDTITLE">
						<c:out value="&{entityname}.&{keyproperty}" />
					</td>
					<td align="left">
&{if:keypropertylength>=80}  
							<html:textarea property="&{keyproperty}" rows="2" readonly="${empty &{entityname}Form.map.&{keyproperty}?'false':'true'}" cols="40" />
&{end-if}	
&{if:keypropertylength<80}  
							<html:text property="&{keyproperty}" readonly="${empty &{entityname}Form.map.&{keyproperty}?'false':'true'}" size="40" />
&{end-if}	
					</td>
				</tr>
&{end-for-each}
&{for-each:property}
				<tr>
					<td class="TDTITLE">
						<c:out value="&{entityname}.&{property}" />
					</td>
					<td align="left">
&{if:propertylength>=80}  
						<html:textarea property="&{property}" rows="2" cols="40" />
&{end-if}	
&{if:propertylength<80}  
						<html:text property="&{property}"  size="40" />
&{end-if}	
					</td>
				</tr>
&{end-for-each}
</table>

&{for-each:onetomany}
<p/>
<div class="eXtremeTable" >
	<table id="t_&{subentityname}"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >

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
				<td class="tableHeader">
					<a href='#' onclick='add&{U_subentityname}Item(this);'><bean:message key="opt.btn.new" /></a>
				</td>
			</tr>  
		</thead>
		
		<tbody class="tableBody" >
		<c:set value="odd" var="rownum" />
		 <logic:iterate id="&{subentityname}" name="&{entityname}Form" property="&{subentitylistname}" >    
			<tr class="${rownum}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${rownum}'" >
&{for-each:subkeyproperty}&{if:!keypropertyisref}    
				<td><html:text name="&{subentityname}" property="&{subkeyproperty}" indexed="true" /> </td>   
&{end-if}&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}  
				<td><html:text name="&{subentityname}" property="&{subproperty}" indexed="true" /> </td>   
&{end-if}&{end-for-each}		
				<td>
					<a href='#' onclick='del&{U_subentityname}Item(this);'><bean:message key='opt.btn.delete' /></a>
				</td>
			</tr>  
            <c:set value="${rownum eq 'odd'? 'even': 'odd'}" var="rownum" />
		</logic:iterate> 
		</tbody>        
	</table>
</div>
&{end-for-each}
</html:form>
&{if:hasOnetomany}
	<script type="text/javascript">
&{for-each:onetomany}	    
		var t_&{subentityname}RowCount; // 行数
&{end-for-each}
	    $(function()
	    {
&{for-each:onetomany}	
			t_&{subentityname}RowCount = $("table#t_&{subentityname} tr").length - 1; // 除去标题行   
	    	var &{subentityname}ColName = 
	    	          [&{for-each:subkeyproperty}&{if:!keypropertyisref}"&{subkeyproperty}",&{end-if}&{end-for-each}
                      &{for-each:subproperty}&{if:!propertyisref}"&{subproperty}",&{end-if}&{end-for-each}"guard"];
&{end-for-each}	    	
			
	        $("input[name='method_save']").bind("click", function()
	        {
&{for-each:onetomany}	            $("table#t_&{subentityname} tr").each(function(i)
	            {
	                $(this).attr("id", "tr_&{subentityname}" + i);
	                $("#tr_&{subentityname}" + i + "  input[type='text']").each(function(j)
	                {
	                    $(this).attr("name", "&{subentityname}["+(i-1)+"]." + &{subentityname}ColName[j]);
	                });
	            });
&{end-for-each}	            
	        });
	    });    
	    
&{for-each:onetomany}        function add&{U_subentityname}Item()
        {
             var htmlItem = '<tr>';
&{for-each:subkeyproperty}&{if:!keypropertyisref}  
			htmlItem += '<td><input type="text" name="&{subentityname}['+t_&{subentityname}RowCount+'].&{subkeyproperty}" /></td>'; 
&{end-if}&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}
			htmlItem += '<td><input type="text" name="&{subentityname}['+t_&{subentityname}RowCount+'].&{subproperty}" /></td>'; 
&{end-for-each}            
            t_&{subentityname}RowCount++;
            htmlItem += "<td> <a href='#' onclick='del&{U_subentityname}Item(this);'><bean:message key='opt.btn.delete' /></a></td></tr>";
            $("table#t_&{subentityname}").append(htmlItem);

   		    $('table#t_&{subentityname}.tableRegion tr:odd').attr('class','odd')
   		    .hover(function(){
       		    	$(this).addClass("highlight");
       		    },function(){
       		    	$(this).removeClass("highlight");
       		});
   		    $('table#t_&{subentityname}.tableRegion tr:even').attr('class','even')
   		    .hover(function(){
	   		    	$(this).addClass("highlight");
	   		    },function(){
	   		    	$(this).removeClass("highlight");
   		    });
     	}
        
        function del&{U_subentityname}Item(varBtn)
        {
            $(varBtn).parent().parent().remove();
            t_&{subentityname}RowCount--;
   		    $('table#t_&{subentityname}.tableRegion tr:odd').attr('class','odd');
   		    $('table#t_&{subentityname}.tableRegion tr:even').attr('class','even');
        }
&{end-for-each}
    </script>	
&{end-if}

</body>
</html>
