package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageTemplate {
	private static final Logger logger = LoggerFactory.getLogger(PageTemplate.class);
	
	//根据产品ID，保险公司，产品小类（旅游，意外，健康，人寿）确定投保页面
	public DataTable getTemplateURL(String cProductId,String cCompayCode,String cProductType){
		
		DataTable dt = new DataTable(); 
		String paidSQL = " select a.templateurl,a.templatecode from producttotemplate a where 1=1 and (a.elementcode=? or a.elementcode=? or a.elementcode=? ) and a.templateurl is not null  order by a.elementweight desc limit 1 ";
		try{
			QueryBuilder qb = new QueryBuilder(paidSQL);
			qb.add(cProductId);
			qb.add(cCompayCode);
			qb.add(cProductType);
			dt = qb.executeDataTable();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		} 
		return dt;
	}
 
}
