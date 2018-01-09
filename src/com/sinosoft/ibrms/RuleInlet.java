package com.sinosoft.ibrms;

import com.sinosoft.ibrms.bom.AbstractBOM;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RuleInlet {
	private static final Logger logger = LoggerFactory.getLogger(RuleInlet.class);

	private List<String> listID;

	private Object[] objs;

	private StatelessSession session;

	public RuleInlet(List<String> tListID, List<AbstractBOM> tListBOM) {
		this.listID = tListID;
		this.objs = getObjs(tListBOM);
		loadRule();
	}

	public RuleInlet(String pathsql, List<AbstractBOM> tListBOM) {
		this.session = RuleCache.getSessions(pathsql);
		this.objs = getObjs(tListBOM);
	}
	
	public RuleInlet(String pathsql, AbstractBOM tBOM) {
		this.session = RuleCache.getSessions(pathsql);
		this.objs = new Object[1];
		objs[0] = tBOM;
	}

	public boolean excuteAppoint(){
		if(session==null){
			logger.error("未找到指定规则");
			return false;
		}
		try{
			session.execute(objs);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	//用于测试
	public void loadRule() {
		PackageBuilder builder = new PackageBuilder();
		try {
//			if (listID != null) {
//				StringBuffer whBuffer = new StringBuffer();
//				for (int i = 0; i < listID.size(); i++) {
//					if (!StringUtils.isEmpty(listID.get(i))) {
//						whBuffer.append("'" + listID.get(i) + "',");
//					}
//				}
//
//				String wh = whBuffer.toString().substring(0,
//						whBuffer.toString().length() - 1);
//				String sql = "select drlpath from lrtemplatet where id in ("
//						+ wh
//						+ ") and valid='1'  and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)";
//				DataTable dt = null;
//				dt = new QueryBuilder(sql).executeDataTable();
//
//				if (dt != null) {
//					for (int j = 0; j < dt.getRowCount(); j++) {
//						FileInputStream drlStream = new FileInputStream(
//								dt.getString(0, 0));
//						builder.addPackageFromDrl(new InputStreamReader(
//								drlStream));
//					}
//				}
//			}
			for(int i=0;i<listID.size();i++){
				FileInputStream drlStream = new FileInputStream(
						listID.get(i));
				builder.addPackageFromDrl(new InputStreamReader(
						drlStream));
			}
			Package pkg = builder.getPackage();
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			if(pkg==null){
				session = null;
			}else{
				ruleBase.addPackage(pkg);
				session = ruleBase.newStatelessSession();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	public Object[] getObjs(List<AbstractBOM> listBOM){
		if(listBOM!=null){
			Object[] objs = new Object[listBOM.size()];
			for(int i = 0;i<objs.length;i++){
				objs[i] = listBOM.get(i);
			}
			return objs;
		}
		return null;
	}

}
