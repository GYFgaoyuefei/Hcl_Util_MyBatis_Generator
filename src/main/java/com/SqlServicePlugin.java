package com;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SqlServicePlugin extends PluginAdapter{

	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}
	

	/**
    * 为每个Example类添加top属性以及set、get方法
    */
   /**
* Description: 
* Company:  
* @author Hclang
* @date 2017年7月11日 下午4:23:07
* @param topLevelClass
* @param introspectedTable
* @return
*/
@Override
   public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

       PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

       Field top = new Field();
       top.setName("top");//为每页显示条数
       top.setVisibility(JavaVisibility.PRIVATE);
       top.setType(integerWrapper);
       topLevelClass.addField(top);

       Method setTop = new Method();
       setTop.setVisibility(JavaVisibility.PUBLIC);
       setTop.setName("setTop");
       setTop.addParameter(new Parameter(integerWrapper, "top"));
       setTop.addBodyLine("this.top = top;");
       topLevelClass.addMethod(setTop);

       Method getTop = new Method();
       getTop.setVisibility(JavaVisibility.PUBLIC);
       getTop.setReturnType(integerWrapper);
       getTop.setName("getTop");
       getTop.addBodyLine("return top;");
       topLevelClass.addMethod(getTop);
       
       Field page = new Field();
       page.setName("page");//当前页数
       page.setVisibility(JavaVisibility.PRIVATE);
       page.setType(integerWrapper);
       topLevelClass.addField(page);

       Method setPage = new Method();
       setPage.setVisibility(JavaVisibility.PUBLIC);
       setPage.setName("setPage");
       setPage.addParameter(new Parameter(integerWrapper, "page"));
       setPage.addBodyLine("this.page = page;");
       topLevelClass.addMethod(setPage);

       Method getPage = new Method();
       getPage.setVisibility(JavaVisibility.PUBLIC);
       getPage.setReturnType(integerWrapper);
       getPage.setName("getPage");
       getPage.addBodyLine("return page;");
       topLevelClass.addMethod(getPage);
       

       return true;
   }

   /**
    * 为Mapper.xml的selectByExample添加top
    */
   /**
* Description: 
* Company:  
* @author Hclang
* @date 2017年7月11日 下午4:23:13
* @param element
* @param introspectedTable
* @return
*/
@Override
   public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
           IntrospectedTable introspectedTable) {
	   
       XmlElement ifTopNotNullElement = new XmlElement("if");
       ifTopNotNullElement.addAttribute(new Attribute("test", "top != null"));
       ifTopNotNullElement.addElement(new TextElement("select * from \r\n" + 
       		"(select *, ROW_NUMBER() OVER(order by ${orderByClause}) AS RowNumber from( "));
       element.addElement(5, ifTopNotNullElement);
       
       XmlElement ifTopNotNullElement_end = new XmlElement("if");
       ifTopNotNullElement_end.addAttribute(new Attribute("test", "top != null"));
       ifTopNotNullElement_end.addElement(new TextElement(")as a)as b where RowNumber BETWEEN (${page}-1)*${top}+1 and ${page}*${top}"));
		element.addElement(11, ifTopNotNullElement_end);
       
       return true;
   }
}
