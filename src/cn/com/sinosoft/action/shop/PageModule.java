package cn.com.sinosoft.action.shop;


import cn.com.sinosoft.entity.ModuleInfo;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/*
 * 投保录入页，模块处理类
 * 投保人模块，被保人模块，受益人模块，保障信息等模块
 */
public class PageModule {
	private static final Logger logger = LoggerFactory.getLogger(PageModule.class);
	
	public List<ModuleInfo> pageModuleList = new ArrayList<ModuleInfo>();
	
	
	/**
	 * 返回模块集合信息
	 * @param cProductId 产品ID
	 * @param cCompayCode 保险公司ID
	 * @param cProductType 产品小类
	 * @param cTemplateCode
	 */
	public List<ModuleInfo> getPageModuleURL(String cProductId){
		
		//投保人信息模块
		//this.getAppModule(cProductId, cCompayCode, cProductType, cTemplateCode);
		//被保人信息模块
		//this.getInsModule(cProductId, cCompayCode, cProductType, cTemplateCode);
		//受益人信息模块
		//this.getBnfModule(cProductId, cCompayCode, cProductType, cTemplateCode);
		//保障内容信息模块
		//this.getSafeGuard(cProductId, cCompayCode, cProductType, cTemplateCode);
		//保险期限信息模块
		//this.getInsurancePeriod(cProductId, cCompayCode, cProductType, cTemplateCode);
		//对模块的顺序进行排序
		/*ModuleInfo mi1 = new ModuleInfo();
		mi1.setModuleOrder(2);
		mi1.setModuleURL("a");
		ModuleInfo mi2 = new ModuleInfo();
		mi2.setModuleOrder(1);
		mi2.setModuleURL("b");
		ModuleInfo mi3 = new ModuleInfo();
		mi3.setModuleOrder(3);
		mi3.setModuleURL("c");
		pageModuleList.add(mi1);
		pageModuleList.add(mi2);
		pageModuleList.add(mi3);*/
		//this.moduleSort();
		 
		ModuleInfo mi = null;
		//根据产品，保险公司，产品类别以及保险公司+产品类别确定投保页面
		StringBuffer paidSQL = new StringBuffer(" SELECT b.ModuleURL,a.OrderFlag FROM producttotemplate a,moduleinfo b,producttempinfo c WHERE a.TemplateCode = b.id AND a.FactorId=c.templateid AND c.productid=? ORDER BY a.OrderFlag ASC " );
		DataTable dt = new DataTable();
		try{
			QueryBuilder qb = new QueryBuilder(paidSQL.toString()); 
			qb.add(cProductId);
			//qb.add(cCompayCode);
			//qb.add(cProductType);
			//qb.add(cComRiskType);
			dt = qb.executeDataTable();
		}catch(Exception e){
			logger.error("没有查到对应模块信息" + e.getMessage(), e);
			return null;
		}
		for(int i=0;i<dt.getRowCount();i++){
			mi = new ModuleInfo();
			mi.setModuleURL(dt.getString(i, 0));
			mi.setModuleOrder(Integer.parseInt(dt.getString(i, 1)));
			pageModuleList.add(mi);
		} 
		return pageModuleList;
		
	}
	/**
	 * 预览用
	 * @param cProductId 产品ID
	 * @param cCompayCode 保险公司ID
	 * @param cProductType 产品小类
	 * @param cTemplateCode
	 */
	public List<ModuleInfo> showPage(String cFactorID){
		
		
		ModuleInfo mi = null;
		//根据产品，保险公司，产品类别以及保险公司+产品类别确定投保页面
		StringBuffer paidSQL = new StringBuffer(" SELECT DISTINCT b.ModuleURL,a.OrderFlag FROM producttotemplate a,moduleinfo b,producttempinfo c WHERE a.TemplateCode = b.id AND a.FactorId=c.templateid AND a.FactorId=? ORDER BY a.OrderFlag ASC " );
		QueryBuilder qb = null;
		DataTable dt = new DataTable();
		try{
		    qb = new QueryBuilder(paidSQL.toString()); 
			qb.add(cFactorID);
			//qb.add(cCompayCode);
			//qb.add(cProductType);
			//qb.add(cComRiskType);
			dt = qb.executeDataTable();
		}catch(Exception e){
			logger.error("没有查到对应模块信息" + e.getMessage(), e);
			return null;
		}
		if(dt.getRowCount()<=0){
			paidSQL = new StringBuffer(" SELECT DISTINCT b.ModuleURL,a.OrderFlag FROM producttotemplate a,moduleinfo b WHERE a.TemplateCode = b.id AND a.FactorId=? ORDER BY a.OrderFlag ASC " );
			qb = new QueryBuilder(paidSQL.toString()); 
			qb.add(cFactorID);
			dt = qb.executeDataTable();
		}
		for(int i=0;i<dt.getRowCount();i++){
			mi = new ModuleInfo();
			mi.setModuleURL(dt.getString(i, 0));
			mi.setModuleOrder(Integer.parseInt(dt.getString(i, 1)));
			pageModuleList.add(mi);
		} 
		return pageModuleList;
		
	}
public DataTable getExcelModule(String cProductId){
		
		//根据产品，保险公司，产品类别以及保险公司+产品类别确定投保页面
		StringBuffer paidSQL = new StringBuffer(" SELECT a.templateURL,a.KeyValue FROM productExcelTemp a,producttempinfo b WHERE a.Id = b.ExcelTemplate AND b.ProductId = ? " );
		DataTable dt = new DataTable();
		try{
			QueryBuilder qb = new QueryBuilder(paidSQL.toString()); 
			qb.add(cProductId);
			dt = qb.executeDataTable();
			return dt;
		}catch(Exception e){
			logger.error("没有查到对应模块信息" + e.getMessage(), e);
			return null;
		}
		
	}
/**
 * 预览使用方法
 * @param cProductId
 * @return
 */
public String showExcelModule(String cProductId){
	
	String Url = "";
	//根据产品，保险公司，产品类别以及保险公司+产品类别确定投保页面
	StringBuffer paidSQL = new StringBuffer(" SELECT DISTINCT a.templateURL FROM productExcelTemp a, producttempinfo b,producttotemplate c WHERE a.Id = b.ExcelTemplate AND b.TemplateId = c.FactorId AND c.FactorId = ? " );
	DataTable dt = new DataTable();
	try{
		QueryBuilder qb = new QueryBuilder(paidSQL.toString()); 
		qb.add(cProductId);
		dt = qb.executeDataTable();
	}catch(Exception e){
		logger.error("没有查到对应模块信息" + e.getMessage(), e);
		return Url;
	}
	for(int i=0;i<dt.getRowCount();i++){
		 
		Url = dt.getString(i, 0);
		 
	} 
	return Url;
	
}
	/*
	 * 返回投保人信息模板URL
	 * 模块类型A01
	 */
	public boolean getAppModule(String cProductId,String cCompayCode,String cProductType,String cTemplateCode){
		
		setModulepageList(cProductId,cCompayCode,cProductType,"A01");
		
		
		return true;
	}
	
	/*
	 * 返回被保人信息模板URL
	 * 模块类型B01
	 */
	public boolean getInsModule(String cProductId,String cCompayCode,String cProductType,String cTemplateCode){
		
		setModulepageList(cProductId,cCompayCode,cProductType,"B01");
		
		return true;
	}
	
	/*
	 * 返回受益人信息模块URL
	 * 模块类型C01
	 */
	public boolean getBnfModule(String cProductId,String cCompayCode,String cProductType,String cTemplateCode){
		
		setModulepageList(cProductId,cCompayCode,cProductType,"C01");
		
		return true;
	}
	
	/*
	 * 返回保障内容信息模块URL
	 * 模块类型D01
	 */
	public boolean getSafeGuard(String cProductId,String cCompayCode,String cProductType,String cTemplateCode){
		
		setModulepageList(cProductId,cCompayCode,cProductType,"D01");
		
		return true;
	}
	/*
	 * 返回保险期限信息模块URL
	 * 模块类型E01
	 */
	public boolean getInsurancePeriod(String cProductId,String cCompayCode,String cProductType,String cTemplateCode){
        
		setModulepageList(cProductId,cCompayCode,cProductType,"E01");
		
		return true;
	}
	//得到模块路径与显示顺序信息
	public DataTable getModuleInfo(String cProductId,String cCompayCode,String cProductType,String cTemplateCode,String cModualType){
		
		
		String paidSQL = " select a.moduleurl,a.moduleorder from moduleinfo a where 1=1  and moduletype=? and (a.elementcode=? or a.elementcode=? or a.elementcode=? ) and a.moduleurl is not null  order by a.elementweight desc limit 1 ";
		paidSQL = " SELECT b.ModuleURL,a.factorname FROM producttotemplate a,moduleinfo b WHERE a.TemplateCode = b.id ";
		DataTable dt = new DataTable();
		try{
			QueryBuilder qb = new QueryBuilder(paidSQL);
			qb.add(cModualType);
			qb.add(cProductId);
			qb.add(cCompayCode);
			qb.add(cProductType);
			dt = qb.executeDataTable();
		}catch(Exception e){
			logger.error("没有查到对应模块信息" + e.getMessage(), e);
			return null;
		}
		return dt;
	}
	
	//排序
	public void moduleSort(){
		ComparatorModule cm = new ComparatorModule();
		Collections.sort(pageModuleList,cm);
	}
	//路径集合
	public void setModulepageList(String cProductId,String cCompayCode,String cProductType,String cTemplateCode){
		
		DataTable dt=this.getModuleInfo(cProductId, cCompayCode, cProductType, cTemplateCode, cTemplateCode);
		ModuleInfo mi = new ModuleInfo();
		mi.setModuleURL(dt.getString(0, 0));
		mi.setModuleOrder(Integer.parseInt(dt.getString(0, 1)));
		pageModuleList.add(mi);
	}
}
