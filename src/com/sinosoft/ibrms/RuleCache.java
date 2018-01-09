package com.sinosoft.ibrms;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RuleCache {
	private static final Logger logger = LoggerFactory.getLogger(RuleCache.class);
	private static Map<String,Package> RulePackage = new HashMap<String,Package>();
	
	public static StatelessSession getSessions(String pathsql){
		try{
			if(RulePackage.get(pathsql)==null){
				return storeSession(pathsql);
			}else{
				RuleBase ruleBase = RuleBaseFactory.newRuleBase();
				ruleBase.addPackage(RulePackage.get(pathsql));
				return ruleBase.newStatelessSession();
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static StatelessSession storeSession(String pathsql){
		try{
        	PackageBuilder builder = new PackageBuilder();
        	StatelessSession session = null;
        	DataTable dt = null;
        	dt=new QueryBuilder(pathsql).executeDataTable();
        	for(int i=0;i<dt.getRowCount();i++){
				logger.info("读取规则文件：{}", dt.getString(i, 0));
        		FileInputStream drlStream = new FileInputStream(
        				dt.getString(i, 0));
    			builder.addPackageFromDrl(new InputStreamReader(
    					drlStream));
        	}
        	Package pkg = builder.getPackage();
        	if(pkg!=null){
        		RulePackage.put(pathsql, pkg);
        	}
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			if(pkg!=null){
				ruleBase.addPackage(pkg);
				session = ruleBase.newStatelessSession();
			}
			
			return session;
        }catch(Exception e){
        	logger.error(e.getMessage(), e);
        	return null;
        }
	}
	
	public static void clearSession(){
		RulePackage = new HashMap<String,Package>();
	}
	
}
