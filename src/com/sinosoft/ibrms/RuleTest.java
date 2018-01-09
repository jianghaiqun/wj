package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.ibrms.bom.AbstractBOM;
import org.apache.commons.lang.StringUtils;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RuleTest extends Page {

	public Errorx mErrors = new Errorx();
	
	private String LRTemplate_ID = "";
	
	private String LRTemplateName = "";
	
	private String Business ="";
	
	private long Interval;
	

	public boolean submitData() {
		LRTemplateName = $V("LRTemplateName");
		LRTemplate_ID = $V("LRTemplate_ID");
		Business = "";
		
		String BOMS = "";
		String sql = "select BOMs,Business from " + LRTemplateName.trim()
				+ " where id='" + LRTemplate_ID.trim() + "'";
		try {
			DataTable dt = null;

			dt = new QueryBuilder(sql).executeDataTable();

			if (dt.getRowCount() == 1) {
				BOMS = dt.getString(0, 0);
				Business =  dt.getString(0, 1);
			} else {
				Response.setMessage("未查询到该规则，请刷新后重新操作");
				return false;
			}
			String pageName = "com.sinosoft.ibrms.bom.";
			String[] arrBOM = BOMS.split(",");
			Object[] objs = new Object[arrBOM.length];
			for (int i = 0; i < arrBOM.length; i++) {
				
				String className = arrBOM[i];
				Class c = Class.forName(pageName+className);
				Field[] fs = c.getDeclaredFields();
				AbstractBOM bom = (AbstractBOM) c.newInstance();
				for(int j=0;j<fs.length;j++){
					Field field= fs[j];
					field.setAccessible(true);
					String tdName = c.getName().substring(c.getName().lastIndexOf(".")+1)+"."+field.getName();
					if(!StringUtils.isEmpty($V(tdName))){
						if("int".equalsIgnoreCase(field.getType().toString())){
		                    field.setInt(bom, Integer.parseInt($V(tdName)));
		                }else if("long".equalsIgnoreCase(field.getType().toString())){
		                    field.setLong(bom, Long.parseLong($V(tdName)));
		                }else if("boolean".equalsIgnoreCase(field.getType().toString())){
		                    field.setBoolean(bom, $V(tdName).equals("true")?true:false);
		                }else if("double".equalsIgnoreCase(field.getType().toString())){
		                    field.setDouble(bom, Double.parseDouble($V(tdName)));
		                }else if("class java.util.Date".equalsIgnoreCase(field.getType().toString())){
		                    field.set(bom, DateUtil.parse($V(tdName)));
		                }else{
		                    field.set(bom, field.getType().cast($V(tdName)));
		                }
					}else{
						if("int".equalsIgnoreCase(field.getType().toString())){
		                    field.setInt(bom, 0);
		                }else if("long".equalsIgnoreCase(field.getType().toString())){
		                    field.setLong(bom, 0);
		                }else if("boolean".equalsIgnoreCase(field.getType().toString())){
		                    field.setBoolean(bom, false);
		                }else if("double".equalsIgnoreCase(field.getType().toString())){
		                    field.setDouble(bom,0);
		                }else{
		                    field.set(bom,null);
		                }
					}
				}
				objs[i]=bom;
			}
			
			if(!testRule(objs)){
				return false;
			}
			
			String arrResult = $V("arrResult");
			String[] arrRes = arrResult.split(";");
			for(int i =1;i<arrRes.length;i++){
				String[] str = arrRes[i].split("-");
				String className = str[0];
				String colName = str[1];
				for(int j=0;j<objs.length;j++){
					if(className.equals(objs[j].getClass().getName().substring(objs[j].getClass().getName().lastIndexOf(".")+1))){
						Method invokeMethod = objs[j].getClass().getMethod("get"+colName.substring(0,1).toUpperCase()+colName.substring(1), null );
						if("java.lang.Boolean".equals(invokeMethod.invoke(objs[j],null).getClass().getName())){
				        	Response.put(arrRes[i], invokeMethod.invoke(objs[j],null).toString());
				        }else{
				        	Response.put(arrRes[i], invokeMethod.invoke(objs[j],null));
				        }
					}
				}
			}
			Response.put("Interval", Interval);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setMessage("测试失败,失败原因是:"+e.getMessage());
			return false;
		}
		Response.setMessage("测试成功");
		return true;
	}
	
	public boolean testRule(Object[] objs){
		String pathSql = "select codevalue from zdcode where codetype = 'DRLPath' and parentcode='"+Business+"'";
		String drlPath = "";
		DataTable dt = null;
		dt = new QueryBuilder(pathSql).executeDataTable();
		if (dt.getRowCount() == 1) {
			drlPath = dt.getString(0, 0)+LRTemplate_ID+".drl";
		} 
		try{
			PackageBuilder builder = new PackageBuilder();
			FileInputStream drlStream = new FileInputStream(drlPath);
			builder.addPackageFromDrl(new InputStreamReader(drlStream));
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			Package pkg = builder.getPackage();  
			ruleBase.addPackage(pkg);
			StatelessSession session = ruleBase.newStatelessSession();
			long start = System.currentTimeMillis();
			session.execute(objs);
			long end = System.currentTimeMillis();
			Interval = end-start;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			Response.setMessage("测试失败,失败原因是:"+e.getMessage());
			return false;
		}
		return true;
	}


}
