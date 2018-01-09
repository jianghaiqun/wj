package com.sinosoft.ibrms;

public class RuleSqlAssemble {
	/*
	 * 得到一个存储规则文件路径的sql，用于RuleInlet的构造方法中。
	 */
	//范例：
	//业务模块01的规则集合
	public static final String module01 ="select drlpath from lrtemplate where business='01' and valid='1' and  state='7' and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)";
	//业务模块02的规则集合
	public static final String module02 ="select drlpath from lrtemplate where business='02' and valid='1' and  state='7' and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)";
	//业务模块03的规则集合
	public static final String module03 ="select drlpath from lrtemplate where business='03' and valid='1' and  state='7' and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)";
	//id为00000000000000000015的sql集合
	public static final String T00000000000000000015 = "select drlpath from lrtemplate where id='00000000000000000015' and valid='1' and  state='7' and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)";
	
	
}
