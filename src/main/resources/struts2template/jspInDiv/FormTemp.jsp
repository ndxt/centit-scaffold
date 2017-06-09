<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%>



<div class="pageContent">
	<s:form action="/&{appname}/&{entityname}!save.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
		
			&{for-each:keyproperty}
			<p>
				<label><c:out value="&{entityname}.&{keyproperty}" />：</label>
				&{if:keypropertylength >= 80} 
				<textarea name="&{keyproperty}" rows="2" <c:if test="${!empty &{entityname}Form.map.&{keyproperty} }">disabled="disabled"</c:if> cols="40" />${object.&{keyproperty} }</textarea>
				&{end-if} 
				&{if:keypropertylength < 80} 
				<input name="&{keyproperty}" type="text" class="required" <c:if test="${!empty object.&{keyproperty} }">readonly="readonly"</c:if> size="40" value="${object.&{keyproperty} }" />
				&{end-if}
			</p>

			&{for-each:property}
			<p>
				<label><c:out value="&{entityname}.&{property}" />：</label>
				&{if:keypropertylength >= 80} 
				<textarea name="&{property}" rows="2" <c:if test="${!empty &{entityname}Form.map.&{property} }">disabled="disabled"</c:if> cols="40" />${object.&{property} }</textarea>
				&{end-if} 
				&{if:keypropertylength < 80} 
				<input name="&{property}" type="text" <c:if test="${!empty &{entityname}Form.map.&{property} }">readonly="readonly"</c:if> size="40" value="${object.&{property} }"/>
				&{end-if}
			</p>
			&{end-for-each}
			
			
			&{if:hasOnetomany}
			<div class="divider"></div>
			<div>
				<table class="list nowrap itemDetail" addButton="新建从表1条目" width="100%">
					<thead>
						<tr>
						
						&{for-each:onetomany}
							&{for-each:subkeyproperty}
								&{if:!keypropertyisref} 
									<th type="text" name="&{subentityname}.&{subkeyproperty}" fieldClass="required"> <c:out value="&{subentityname}.&{subkeyproperty}" /> </th>
								&{end-if}
							&{end-for-each}
							&{for-each:subproperty}
								&{if:!propertyisref} 
									<th type="text" name="&{subentityname}.&{subproperty}" fieldClass="required"> <c:out value="&{subentityname}.&{subproperty}" /> </th>
								&{end-if}
							&{end-for-each}
						&{end-for-each}
							<th type="del" width="60">操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
			&{end-if}
		</div>


		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>

	</s:form>
</div>

