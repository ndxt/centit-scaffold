<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%> 
<%@ include file="/page/common/css.jsp"%> 
<html>
<head>
<title><s:text name="&{entityname}.edit.title" /></title>
</head>

<body>
<p class="ctitle"><s:text name="&{entityname}.edit.title" /></p>

<%@ include file="/page/common/messages.jsp"%>

<s:form action="&{entityname}"  method="post" namespace="/&{appname}" id="&{entityname}Form" >
	<s:submit name="save"  method="save" cssClass="btn" key="opt.btn.save" />
	<s:submit type="button" name="back" cssClass="btn" key="opt.btn.back"/>
		
<table width="200" border="0" cellpadding="1" cellspacing="1">		
 &{for-each:keyproperty}
				<tr>
					<td class="TDTITLE">
						<s:text name="&{entityname}.&{keyproperty}" />
					</td>
					<td align="left">
&{if:keypropertylength>=80}  
							<s:textarea name="&{keyproperty}" cols="40" rows="2" />
&{end-if}	
&{if:keypropertylength<80}  
							<s:textfield name="&{keyproperty}" size="40" />
&{end-if}	
					</td>
				</tr>
&{end-for-each}
&{for-each:property}
				<tr>
					<td class="TDTITLE">
						<s:text name="&{entityname}.&{property}" />
					</td>
					<td align="left">
&{if:propertylength>=80}  
						<s:textarea name="&{property}" cols="40" rows="2"/>
&{end-if}	
&{if:propertylength<80}  
						<s:textfield name="&{property}"  size="40"/>
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
					<s:text name="&{subentityname}.&{subkeyproperty}" />
				</td>&{end-if}	
&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}
				<td class="tableHeader">
					<s:text name="&{subentityname}.&{subproperty}" />
				</td>&{end-if}	
&{end-for-each}		
				<td class="tableHeader">
					<a href='javascript:void(0)' onclick='add&{U_subentityname}Item(this);'><s:text name="opt.btn.new" /></a>
				</td>
			</tr>  
		</thead>
		
		<tbody class="tableBody" >
		<c:set value="odd" var="rownum" />
		 <s:iterator value="&{subentitylistname}" status="status" >    
			<tr class="${rownum}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${rownum}'" >
&{for-each:subkeyproperty}&{if:!keypropertyisref}    
				<td><s:textfield name="&{subkeyproperty}" /> </td>   
&{end-if}&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}  
				<td><s:textfield name="&{subproperty}" /> </td>   
&{end-if}&{end-for-each}		
				<td>
					<a href='javascript:void(0)' onclick='del&{U_subentityname}Item(this);'><s:text name='opt.btn.delete' /></a>
				</td>
			</tr>  
            <c:set value="${rownum eq 'odd'? 'even': 'odd'}" var="rownum" />
		</s:iterator> 
		</tbody>        
	</table>
</div>
&{end-for-each}
</s:form>
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
			
	        $("input[name='method:save']").bind("click", function()
	        {
&{for-each:onetomany}	            $("table#t_&{subentityname} tr").each(function(i)
	            {
	                $(this).attr("id", "tr_&{subentityname}" + i);
	                $("#tr_&{subentityname}" + i + "  input[type='text']").each(function(j)
	                {
	                    $(this).attr("name", "new&{U_subentitylistname}["+(i-1)+"]." + &{subentityname}ColName[j]);
	                });
	            });
&{end-for-each}	            
	        });
	    });    
	    
&{for-each:onetomany}        function add&{U_subentityname}Item()
        {
             var htmlItem = '<tr>';
&{for-each:subkeyproperty}&{if:!keypropertyisref}  
			htmlItem += '<td><input type="text" name="new&{U_subentitylistname}['+t_&{subentityname}RowCount+'].&{subkeyproperty}" /></td>'; 
&{end-if}&{end-for-each}
&{for-each:subproperty}&{if:!propertyisref}
			htmlItem += '<td><input type="text" name="new&{U_subentitylistname}['+t_&{subentityname}RowCount+'].&{subproperty}" /></td>'; 
&{end-for-each}            
            t_&{subentityname}RowCount++;
            htmlItem += "<td> <a href='javascript:void(0)' onclick='del&{U_subentityname}Item(this);'><s:text name='opt.btn.delete' /></a></td></tr>";
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
