package com.centit.support.scaffold;

import org.apache.commons.lang.StringUtils;

import com.centit.support.compiler.VariableTranslate;
import com.centit.support.database.metadata.HibernateMapInfo;
import com.centit.support.database.metadata.SimpleTableField;


public class ScaffoldTranslate implements VariableTranslate{

	private String sBasePackage;
	private String sClassSimpleName;
	private String sAppName;
	private HibernateMapInfo hbmMetadata;
	private int keyPPos;
	private int pPos;
	private int subKeyPPos;
	private int subPPos;
	private int one2manyPos;
	private Config handleCfg;
	
	public ScaffoldTranslate()
	{
		sClassSimpleName = null;
		sBasePackage = null;
		keyPPos = 0;
		pPos = 0;

		//hbmMetadata = new HibernateMapInfo();
	}
	
	public void setHandleCfg(Config cfg)
	{
		handleCfg = cfg;
	}	
	public Config getHandleCfg()
	{
		return handleCfg ;
	}		
	public void setHbmMetadata(HibernateMapInfo hbmMD) {
		hbmMetadata = hbmMD;
		String sClassName = hbmMetadata.getClassName();
		int p = sClassName.lastIndexOf('.');
		if( p < 0 ) return;
		
		sClassSimpleName = sClassName.substring(p+1);
		sBasePackage = sClassName.substring(0,p);
		//去掉 .po
		p = sBasePackage.lastIndexOf('.');
		if( p > 0 )
			sBasePackage = sBasePackage.substring(0,p);
		keyPPos = 0;
		pPos = 0;
	}	
	
	public String getClassName() 
	{
		return hbmMetadata.getClassName();
	}

	public String getClassSimpleName() 
	{
		return sClassSimpleName;
	}
	
	public String getBasePackage() 
	{
		return sBasePackage;
	}
	public String getEntityListName() {
		return getEntityName() + "s";
	}

	public boolean isCombineID() 
	{
		return hbmMetadata.isHasID();
	}

	
	public String getEntityName() {
		if( sClassSimpleName.length() > 1 &&
				sClassSimpleName.charAt(0)>='A' && sClassSimpleName.charAt(0)<='Z' &&
				sClassSimpleName.charAt(1)>='A' && sClassSimpleName.charAt(1)<='Z'
		)
			return sClassSimpleName;
		
		return StringUtils.uncapitalize(sClassSimpleName);
	}
	
	public String getIdJspDesc() 
	{
		String sIdDesc="";
		for(SimpleTableField sprop : hbmMetadata.getKeyProperties()){
			sIdDesc += '&'+sprop.getPropertyName()+"=${"+getEntityName()+'.'+sprop.getPropertyName()+'}';
		}
		if(sIdDesc == null || sIdDesc.length()<1)
			return null;
		return sIdDesc.substring(1);
	}	
	public static String upperCaseFirstChar(String sValue){
		if(sValue==null || sValue.length() <= 0)
			return sValue;
		return sValue.substring(0,1).toUpperCase() + sValue.substring(1);
	}
	public static String lowerCaseFirstChar(String sValue){
		if(sValue==null || sValue.length() <= 0)
			return sValue;
		return sValue.substring(0,1).toLowerCase() + sValue.substring(1);
	}	
	
	public String getVarValue(String varName) {
		String vName = varName;
		String sOpt = "";
		int nPos = varName.indexOf('_');
		if(nPos>0){
			sOpt = varName.substring(0,nPos);
			vName = varName.substring(nPos+1);
		}
		
		String sValue = mapVarValue(vName);
		if("U".equals(sOpt)){
			sValue = upperCaseFirstChar(sValue);
		}else if("L".equals(sOpt)){
			sValue = lowerCaseFirstChar(sValue);
		}
		
		return sValue;
	}
	
	public String mapVarValue(String varName) {
		//获取类名
		if( varName.equalsIgnoreCase("classname") )
			return  getClassName();
		//获取业务名
		else if( varName.equalsIgnoreCase("appname") )
			return  sAppName;
		//业务对应的表名
		else if( varName.equalsIgnoreCase("tablename") )
			return  hbmMetadata.getTableName();
		//业务对应的表 中文名
		else if( varName.equalsIgnoreCase("tabledesc") )
			return  hbmMetadata.getTableLabelName();
		//业务对应的表  说明与注释
		else if( varName.equalsIgnoreCase("tableComment") )
			return   hbmMetadata.getTableComment();
		//获取类名
		else if( varName.equalsIgnoreCase("simpleclassname") )
			return  sClassSimpleName;
		//获取实体名
		else if( varName.equalsIgnoreCase("entityname") )
			return  getEntityName();
		//获取实体集合名
		else if( varName.equalsIgnoreCase("entitylistname") )
			return  getEntityListName();
		//获取主键字段
		else if( varName.equalsIgnoreCase("idjspdesc") )
			return getIdJspDesc();
		//获取基础包名
		else if( varName.equalsIgnoreCase("basepackage") )
			return sBasePackage;
		
		//获取当前属性名
		else if( varName.equalsIgnoreCase("property") )
			return getProperty(pPos);
		//获取当前属性 对应的字段名
		else if( varName.equalsIgnoreCase("propertyColumn") )
			return getPropertyColumn(pPos);
		//获取当前属性最大长度
		else if( varName.equalsIgnoreCase("propertylength") )
			return getPropertyLength(pPos);
		//获取当前属性 类型
		else if( varName.equalsIgnoreCase("propertytype") )
			return getPropertyType(pPos);
		
		//获取当前属性 有效位数
		else if( varName.equalsIgnoreCase("propertyprecision") )
			return getPropertyPrecision(pPos);
		//获取当前属性 精度
		else if( varName.equalsIgnoreCase("propertyscale") )
			return getPropertyScale(pPos);
		//获取当前属性 中文名
		else if( varName.equalsIgnoreCase("propertydesc") )
			return getPropertyDesc(pPos);
		//获取当前属性 注释
		else if( varName.equalsIgnoreCase("propertycomment") )
			return getPropertyComment(pPos);	
		
		//获取当前主键属性名
		else if( varName.equalsIgnoreCase("keyproperty") )
			return getKeyProperty(keyPPos);		
		//获取当前主键属性 对应的字段名
		else if( varName.equalsIgnoreCase("keypropertyColumn") )
			return getKeyPropertyColumn(keyPPos);
		//获取当前主键属性 类型
		else if( varName.equalsIgnoreCase("keypropertytype") )
			return getKeyPropertyType(keyPPos);
		//获取当前主键属性最大长度
		else if( varName.equalsIgnoreCase("keypropertylength") )
			return getKeyPropertyLength(keyPPos);
		
		//获取当前主键属性有效位数
		else if( varName.equalsIgnoreCase("keypropertyprecision") )
			return getKeyPropertyPrecision(keyPPos);
		//获取当前属性 精度
		else if( varName.equalsIgnoreCase("keypropertyscale") )
			return getKeyPropertyScale(keyPPos);
		//获取当前属性 中文名
		else if( varName.equalsIgnoreCase("keypropertydesc") )
			return getKeyPropertyDesc(keyPPos);
		//获取当前属性 注释
		else if( varName.equalsIgnoreCase("keypropertycomment") )
			return getKeyPropertyComment(keyPPos);
		
		else if( varName.equalsIgnoreCase("idtype") )
			return hbmMetadata.getIdType();
		else if( varName.equalsIgnoreCase("idname") )
			return hbmMetadata.getIdName();
		
//////////one to many/////////////////////////////////////////////////////////////////		
		else if( varName.equalsIgnoreCase("subproperty") )
			return getSubProperty(one2manyPos,subPPos);
		else if( varName.equalsIgnoreCase("subkeyproperty") )
			return getSubKeyProperty(one2manyPos,subKeyPPos);
		//获取子类实体名
		else if( varName.equalsIgnoreCase("subclassname") )
			return  getSubClassName(one2manyPos);
		//获取子类实体名
		else if( varName.equalsIgnoreCase("subentityname") )
			return  getSubEntityName(one2manyPos);
		//获取子类符合主键名
		else if( varName.equalsIgnoreCase("subidname") )
			return  getSubIdName(one2manyPos);
		
		
		//获取子类实体集合名
		else if( varName.equalsIgnoreCase("subentitylistname") )
			return  getSubEntityListName(one2manyPos);
		//获取子类主键字段
		else if( varName.equalsIgnoreCase("subidjspdesc") )
			return getSubIdJspDesc(one2manyPos);
		//获取reference 键名
		else if( varName.equalsIgnoreCase("refproperty") )
			return getSubRefProperty(one2manyPos,keyPPos);
//////////many to one/////////////////////////////////////////////////////////////////		
		return varName;
	}

	public int getPropertySize()
	{
		return hbmMetadata.getProperties().size();
	}
	
	public int getKeyPropertySize()
	{
		return hbmMetadata.getKeyProperties().size();
	}
		
	public String getProperty(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getProperty(indx).getPropertyName();
	}
	
	public String getPropertyColumn(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getProperty(indx).getColumnName();
	}
	
	public String getPropertyType(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getProperty(indx).getJavaType();
	}
	
	public String getPropertyLength(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return String.valueOf(hbmMetadata.getProperty(indx).getMaxLength());
	}
	
	public boolean isPropertyNotNull(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return false;
		return hbmMetadata.getProperty(indx).isMandatory();
	}
	
	public String getPropertyPrecision(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return String.valueOf(hbmMetadata.getProperty(indx).getPrecision());
	}	
	
	public String getPropertyScale(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return String.valueOf(hbmMetadata.getProperty(indx).getScale());
	}	
	
	public String getPropertyDesc(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getProperty(indx).getFieldLabelName();
	}

	public String getPropertyComment(int indx)
	{
		int n = hbmMetadata.getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getProperty(indx).getColumnComment();
	}
	
	public String getKeyProperty(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getKeyProperty(indx).getPropertyName();
	}	
	
	public String getKeyPropertyColumn(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getKeyProperty(indx).getColumnName();
	}	
	
	public String getKeyPropertyType(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getKeyProperty(indx).getJavaType();
	}	
	
	public String getKeyPropertyLength(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return String.valueOf(hbmMetadata.getKeyProperty(indx).getMaxLength());
	}	
	
	public String getKeyPropertyPrecision(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return String.valueOf(hbmMetadata.getKeyProperty(indx).getPrecision());
	}	
	
	public String getKeyPropertyScale(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return String.valueOf(hbmMetadata.getKeyProperty(indx).getScale());
	}	
	
	public String getKeyPropertyDesc(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getKeyProperty(indx).getFieldLabelName();
	}	
	
	public String getKeyPropertyComment(int indx)
	{
		int n = hbmMetadata.getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		return hbmMetadata.getKeyProperty(indx).getColumnComment();
	}
	
	public String getLabelValue(String labelName) 
	{
		if(labelName.equalsIgnoreCase("daoInterface"))
			return handleCfg.isCreateDaoImpl() ?"1":"0";
		if(labelName.equalsIgnoreCase("managerInterface"))
			return handleCfg.isCreateManagerImpl()?"1":"0";
		if(labelName.equalsIgnoreCase("hasID"))
			return this.hbmMetadata.isHasID()?"1":"0";
		
		if(labelName.equalsIgnoreCase("isflowopt"))
			return handleCfg.isWorkFlowOpt()?"1":"0";

		if(labelName.equalsIgnoreCase("hasnullproperties")){
			for(SimpleTableField tf : hbmMetadata.getProperties())
				if(!tf.isMandatory())
					return "1";
			return "0";
		}
		
		if(labelName.equalsIgnoreCase("hasOnetomany")){
			if( this.hbmMetadata.getOne2manys() == null)
				return "0";
			if( this.hbmMetadata.getOne2manys().size() <= 0)
				return "0";
			
			return "1";
		}
		if(labelName.equalsIgnoreCase("propertynotnull")){
			return isPropertyNotNull(pPos)?"1":"0";
		}
		if(labelName.equalsIgnoreCase("isfirstproperty")){
			return (pPos==0)?"1":"0";
		}
		if(labelName.equalsIgnoreCase("isfirstkeyproperty")){
			return (keyPPos==0)?"1":"0";
		}
		if( labelName.equalsIgnoreCase("propertyisref") ){
			return  hbmMetadata.isReferenceColumn(one2manyPos,
					getSubProperty(one2manyPos,subPPos))?"1":"0";
		}
		if( labelName.equalsIgnoreCase("keypropertyisref") ){
			return  hbmMetadata.isReferenceColumn(one2manyPos,
					getSubKeyProperty(one2manyPos,subKeyPPos))?"1":"0";
		}
		String s = getVarValue(labelName);
		return '\''+s+'\'';
	}
	
	public int getKeyPPos() {
		return keyPPos;
	}

	public void setKeyPPos(int keyPPos) {
		this.keyPPos = keyPPos;
	}
	
	public boolean resetKeyPPos() {
		if(hbmMetadata.getKeyProperties().size() < 1 )
			return false;
		keyPPos = 0;
		return true;
	}
	
	public int nextKeyPPos() {
		if( keyPPos < hbmMetadata.getKeyProperties().size()-1){
			keyPPos ++;
			return keyPPos;
		}
		return -1;
	}
	
	public int getPPos() {
		return pPos;
	}

	public void setPPos(int pos) {
		pPos = pos;
	}
	
	public boolean resetPPos() {
		if(hbmMetadata.getProperties().size() < 1 )
			return false;
		pPos = 0;
		return true;
	}
	
	public int nextPPos() {
		if( pPos < hbmMetadata.getProperties().size()-1){
			pPos ++;
			return pPos;
		}
		return -1;
	}

	public boolean hasCompositeId() {
		return hbmMetadata.isHasID();
	}	
	public void setAppName(String apn) {
		sAppName = apn;
	}

	public int getSubKeyPPos() {
		return subKeyPPos;
	}

	public void setSubKeyPPos(int subKeyPPos) {
		this.subKeyPPos = subKeyPPos;
	}

	public int getSubPPos() {
		return subPPos;
	}

	public void setSubPPos(int subPPos) {
		this.subPPos = subPPos;
	}

	public int getOne2manyPos() {
		return one2manyPos;
	}

	public void setOne2manyPos(int one2manyPos) {
		this.one2manyPos = one2manyPos;
	}
	
	public boolean resetOne2ManyPos() {
		if(hbmMetadata.getOne2manys() == null || hbmMetadata.getOne2manys().size() < 1 )
			return false;
		one2manyPos = 0;
		return true;
	}
	
	public int nextOne2ManyPos() {
		if(hbmMetadata.getOne2manys() == null )
			return -1;

		if( one2manyPos < hbmMetadata.getOne2manys().size()-1){
			one2manyPos ++;
			return one2manyPos;
		}
		return -1;
	}
	
	public boolean resetSubPPos() {
		if(hbmMetadata.getOne2manys() == null )
			return false;

		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	one2manyPos <0 || one2manyPos >= n)
			return false;

		if(hbmMetadata.getOne2manys().get(one2manyPos).getProperties().size() < 1 )
			return false;
		
		subPPos = 0;
		return true;
	}
	
	public int nextSubPPos() {
		if(hbmMetadata.getOne2manys() == null )
			return -1;
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	one2manyPos <0 || one2manyPos >= n)
			return -1;

		if( subPPos < hbmMetadata.getOne2manys().get(one2manyPos).getProperties().size()-1){
			subPPos ++;
			return subPPos;
		}
		return -1;
	}		

	public boolean resetSubKeyPPos() {
		if(hbmMetadata.getOne2manys() == null )
			return false;
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	one2manyPos <0 || one2manyPos >= n)
			return false;

		if(hbmMetadata.getOne2manys().get(one2manyPos).getKeyProperties().size() < 1 )
			return false;
		
		subKeyPPos = 0;
		return true;
	}
	
	public int nextSubKeyPPos() {
		if(hbmMetadata.getOne2manys() == null )
			return -1;
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	one2manyPos <0 || one2manyPos >= n)
			return -1;

		if( subKeyPPos < hbmMetadata.getOne2manys().get(one2manyPos).getKeyProperties().size()-1){
			subKeyPPos ++;
			return subKeyPPos;
		}
		return -1;
	}
	
	public String getSubProperty(int subPos,int indx)
	{
		if(hbmMetadata.getOne2manys() == null )
			return "";
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	subPos <0 || subPos >= n)
			return "";

		n = hbmMetadata.getOne2manys().get(subPos).getProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";
		
		return hbmMetadata.getOne2manys().get(subPos).getProperties().get(indx).getPropertyName();
	}
	
	public String getSubKeyProperty(int subPos,int indx)
	{
		if(hbmMetadata.getOne2manys() == null )
			return "";
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	subPos <0 || subPos >= n)
			return "";

		n = hbmMetadata.getOne2manys().get(subPos).getKeyProperties().size();
		if(n < 1 || indx<0 || indx>=n)
			return "";

		return hbmMetadata.getOne2manys().get(subPos).getKeyProperties().get(indx).getPropertyName();
	}
	
	public String getSubRefProperty(int subPos,int indx)
	{
		int n = hbmMetadata.getReferences().size();
		if(n < 1 ||	subPos <0 || subPos >= n)
			return "";

		int n2 = hbmMetadata.getReferences().get(subPos).getFkColumns().size();
		if(n2 < 1 || indx<0 || indx>=n2)
			return "";
		
		return hbmMetadata.getReferences().get(subPos).getFkColumns().get(indx).getPropertyName();
	}	
	
	public String getSubEntityListName(int subPos) {
		return getSubEntityName(subPos) + "s";
	}
	
	public String getSubClassName(int subPos) {
		if(hbmMetadata.getOne2manys() == null )
			return "";
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	subPos <0 || subPos >= n)
			return "";
		
		String sSubClassName = hbmMetadata.getOne2manys().get(subPos).getClassSimpleName();
		return sSubClassName;
	}
	public String getSubEntityName(int subPos) {
		if(hbmMetadata.getOne2manys() == null )
			return "";
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	subPos <0 || subPos >= n)
			return "";
		
		String sSubClassName = hbmMetadata.getOne2manys().get(subPos).getClassSimpleName();
		
		if( sSubClassName.length() > 1 &&
				sSubClassName.charAt(0)>='A' && sSubClassName.charAt(0)<='Z' &&
				sSubClassName.charAt(1)>='A' && sSubClassName.charAt(1)<='Z'
		)
			return sSubClassName;
		return StringUtils.uncapitalize(sSubClassName);
	}

	public String getSubIdName(int subPos) {
		if(hbmMetadata.getOne2manys() == null )
			return "";
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	subPos <0 || subPos >= n)
			return "";
		return hbmMetadata.getOne2manys().get(subPos).getIdName();
	}
	
	public String getSubIdJspDesc(int subPos) 
	{
		if(hbmMetadata.getOne2manys() == null )
			return "";
		
		int n = hbmMetadata.getOne2manys().size();
		if(n < 1 ||	subPos <0 || subPos >= n)
			return "";
		
		String sIdDesc=getIdJspDesc();
		HibernateMapInfo subMetadata = hbmMetadata.getOne2manys().get(subPos);
		
		for(SimpleTableField sprop : subMetadata.getKeyProperties()){
			sIdDesc += '&'+sprop.getPropertyName()+"=${"+getSubEntityName(subPos)+'.'+sprop.getPropertyName()+'}';
		}
		return sIdDesc;//.substring(1);
	}		
}
