package com.sinosoft.module;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ElementRelaInfoSchema;
import com.sinosoft.schema.ElementRelaInfoSet;
import com.sinosoft.schema.ModuleElementInfoSchema;
import com.sinosoft.schema.ModuleElementInfoSet;
import com.sinosoft.schema.ModuleElementSchema;
import com.sinosoft.schema.ModuleElementSet;
import com.sinosoft.schema.PurchaseRelaInfoSchema;
import com.sinosoft.schema.PurchaseRelaInfoSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module extends Page {

	/**
	 * 页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initModuleList(Mapx params) {

		if (StringUtil.isEmpty(params.get("Id") + "")) {
			params.put("ElementTypeList", HtmlUtil.codeToOptions("ElementType", true));

		} else {
			ModuleElementInfoSchema module = new ModuleElementInfoSchema();
			ModuleElementInfoSet set = module.query(new QueryBuilder("where id = ? ", params.get("Id") + ""));
			if (set != null && set.size() == 1) {
				module = set.get(0);
				params.put("Id", module.getId());
				params.put("ElementName", module.getElementName());
				params.put("ElementType", module.getElementType());
				params.put("ElementContent", module.getElementContent());
				params.put("Remark1", module.getRemark1());
				params.put("Memo", module.getMemo());
			}
			params.put("ElementTypeList", HtmlUtil.codeToOptions("ElementType", module.getElementType()));
		}
		params.put("IsMustInput", HtmlUtil.codeToOptions("IsMustInput", false));
		params.put("InfoType", HtmlUtil.codeToOptions("InputType"));
		params.put("IsModify", HtmlUtil.codeToOptions("IsModify", false));
		params.put("Remark", HtmlUtil.codeToOptions("IsModify", false));
		return params;
	}

	@SuppressWarnings("unchecked")
	public static Mapx initModuleList1(Mapx params) {

		params.put("DataNodeList", HtmlUtil.codeToOptions("DataNode", true));
		params.put("IsMustInput", HtmlUtil.codeToOptions("IsMustInput", false));
		params.put("InfoType", HtmlUtil.codeToOptions("InputType"));
		params.put("IsModify", HtmlUtil.codeToOptions("IsModify", false));

		return params;
	}

	/**
	 * 元素数据查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String sql = "select Id,ElementName,ElementType,z.CodeName ElementTypeName,ElementContent,CreateDate,m.Memo,Id OrderFlag,Remark1 from ModuleElementInfo m left join "
				+ " zdcode z on (z.codetype='ElementType' and z.codevalue=m.ElementType) where 1=1 ";

		String ElementType = (String) dga.getParams().get("ElementType");
		String ElementName = (String) dga.getParams().get("ElementName");
		String ElementContent = (String) dga.getParams().get("ElementContent");
		String IDs = (String) dga.getParams().get("IDs");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(IDs)) {
			qb.append(" and id in (" + IDs + ") ");
		}

		if (StringUtil.isNotEmpty(ElementType)) {
			qb.append(" and ElementType = ? ", ElementType);
		}

		if (StringUtil.isNotEmpty(ElementName)) {
			qb.append(" and ElementName like ? ", "%" + ElementName + "%");
		}

		if (StringUtil.isNotEmpty(ElementContent)) {
			qb.append(" and ElementContent like ? ", "%" + ElementContent + "%");
		}

		qb.append(" order by id");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 元素配置信息查询
	 * 
	 * @param dga
	 */
	public static void dg2DataBind(DataGridAction dga) {

		String Id = (String) dga.getParams().get("Id");
		String sql = "SELECT id AS infoID,InputType,InputCode,InputName,IsMustInput,InfoType,(SELECT codename FROM zdcode WHERE 1=1 AND codetype='InputType' AND codevalue=InfoType ) AS InfoTypeName,IsModify,ValidateInfo,Prop1,Prop2 FROM ElementRelaInfo WHERE ElementID ="
				+ Id;

		QueryBuilder qb = new QueryBuilder(sql);

		qb.append(" order by infoID");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 元素配置信息查询
	 * 
	 * @param dga
	 */
	public static void dg3DataBind(DataGridAction dga) {

		String Id = (String) dga.getParams().get("Id");
		String sql = "SELECT id AS infoID,InputType,InputCode,InputName,IsMustInput,InfoType,(SELECT codename FROM zdcode WHERE 1=1 AND codetype='InputType' AND codevalue=InfoType ) AS InfoTypeName,IsModify,ValidateInfo,Description,OrderFlag,DataNode,Prop1,Prop2 "
				+ "FROM PurchaseRelaInfo WHERE ElementID =" + Id;

		QueryBuilder qb = new QueryBuilder(sql);

		qb.append(" order by infoID");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 新增/修改元素节点
	 */
	public void add() {

		try {
			ModuleElementInfoSchema module = new ModuleElementInfoSchema();
			String Id = $V("Id");
			module.setId(Id);
			boolean flag = false;
			if (StringUtil.isNotEmpty(Id) && module.fill()) {
				module.setModifyDate(new Date());
				module.setElementName($V("ElementName"));
				module.setElementType($V("ElementType"));
				module.setElementContent($V("ElementContent"));
				module.setRemark1($V("Remark1"));
				module.setMemo($V("Memo"));
				flag = module.update();

			} else {
				module.setId(NoUtil.getMaxIDLocal("ModuleElementInfoID") + "");
				module.setCreateDate(new Date());
				module.setElementName($V("ElementName"));
				module.setElementType($V("ElementType"));
				module.setElementContent($V("ElementContent"));
				module.setRemark1($V("Remark1"));
				module.setMemo($V("Memo"));
				flag = module.insert();
			}

			if (flag) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 新增/修改元素配置信息
	 */
	public void add2() {

		try {
			ElementRelaInfoSchema elementreal = new ElementRelaInfoSchema();
			ElementRelaInfoSet tElementRelaInfoSet = new ElementRelaInfoSet();
			String ElementId = $V("Id");
			DataTable dt = Request.getDataTable("Data");
			boolean flag = true;
			Transaction trans = new Transaction();
			int b = dt.getRowCount();

			for (int i = 0; i < b; i++) {
				elementreal = new ElementRelaInfoSchema();
				Date dt1 = new Date();
				DataRow dr = dt.getDataRow(i);
				String id = dr.getString("InfoID");
				if (StringUtil.isEmpty(id)) {
					id = NoUtil.getMaxID("ElementRelaID") + dt1.getTime() + "";
				}
				elementreal.setid(id);
				elementreal.setIsMustInput(dr.getString("IsMustInput"));
				elementreal.setInfoType(dr.getString("InfoType"));
				elementreal.setInputCode(dr.getString("InputCode"));
				elementreal.setInputName(dr.getString("InputName"));
				elementreal.setInputType(dr.getString("InputType"));
				elementreal.setIsModify(dr.getString("IsModify"));
				elementreal.setValidateInfo(dr.getString("ValidateInfo"));
				elementreal.setProp1(dr.getString("Prop1"));
				elementreal.setProp2(dr.getString("Prop2"));
				elementreal.setElementID(ElementId);
				elementreal.setMakeDate(new Date());

				tElementRelaInfoSet.add(elementreal);

			}
			trans.add(new QueryBuilder("delete from ElementRelaInfo where ElementID=?", ElementId));
			if (tElementRelaInfoSet != null) {
				trans.add(tElementRelaInfoSet, Transaction.DELETE_AND_INSERT);
			}
			if (!trans.commit()) {
				flag = false;
			}
			if (flag) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 新增/修改元素配置信息
	 */
	public void add3() {

		try {
			PurchaseRelaInfoSchema elementreal = new PurchaseRelaInfoSchema();
			PurchaseRelaInfoSet tElementRelaInfoSet = new PurchaseRelaInfoSet();
			String ElementId = $V("Id");
			DataTable dt = Request.getDataTable("Data");
			boolean flag = true;
			Transaction trans = new Transaction();
			int b = dt.getRowCount();

			for (int i = 0; i < b; i++) {
				elementreal = new PurchaseRelaInfoSchema();
				Date dt1 = new Date();
				DataRow dr = dt.getDataRow(i);
				String id = dr.getString("InfoID");
				if (StringUtil.isEmpty(id)) {
					id = NoUtil.getMaxID("PurchaseRelaInfo") + dt1.getTime() + "";
				}
				elementreal.setid(id);
				elementreal.setIsMustInput(dr.getString("IsMustInput"));
				elementreal.setInfoType(dr.getString("InfoType"));
				elementreal.setInputCode(dr.getString("InputCode"));
				elementreal.setInputName(dr.getString("InputName"));
				elementreal.setInputType(dr.getString("InputType"));
				elementreal.setIsModify(dr.getString("IsModify"));
				elementreal.setValidateInfo(dr.getString("ValidateInfo"));
				elementreal.setOrderFlag(dr.getString("OrderFlag"));
				elementreal.setDescription(dr.getString("Description"));
				elementreal.setDataNode(dr.getString("DataNode"));
				elementreal.setElementID(ElementId);
				elementreal.setMakeDate(new Date());
				elementreal.setProp1(dr.getString("Prop1"));
				elementreal.setProp2(dr.getString("Prop2"));
				tElementRelaInfoSet.add(elementreal);
			}
			trans.add(new QueryBuilder("delete from PurchaseRelaInfo where ElementID=?", ElementId));
			if (tElementRelaInfoSet != null) {
				trans.add(tElementRelaInfoSet, Transaction.DELETE_AND_INSERT);
			}
			if (!trans.commit()) {
				flag = false;
			}
			if (flag) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 元素节点删除
	 */
	public void del() {

		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}

			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}

			ModuleElementSchema element = new ModuleElementSchema();
			ModuleElementSet element_set = element.query(new QueryBuilder("where ElementCode in (" + ids + ")"));
			if (element_set != null && element_set.size() > 0) {
				Response.setStatus(0);
				Response.setMessage("删除失败，元素在模块中有使用，请先删除对应的模块!");
				return;
			}

			ModuleElementInfoSchema module = new ModuleElementInfoSchema();
			ModuleElementInfoSet set = module.query(new QueryBuilder("where id in (" + ids + ")"));
			StringBuffer moduleLog = new StringBuffer("删除配置元素：");
			for (int i = 0; i < set.size(); i++) {
				module = set.get(i);
				moduleLog.append(module.getElementName() + ",");
			}
			if (set.delete()) {
				Response.setStatus(1);
				UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, moduleLog + "成功", Request.getClientIP());
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, moduleLog + "失败", Request.getClientIP());
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("删除失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 
	 * showWap:(将需要排序的产品显示出来). <br/>
	 *
	 * @author chouweigao
	 */
	public static void dg2DataBindShowWAP(DataGridAction dga) {
		String pid = dga.getParam("ProductID");
		
		DataTable dt_tem = getOrderSortInfo(pid);
		//公用排序查询
		String sql = "SELECT e.ElementType,f.id,f.inputname,f.inputcode,f.orderflag "
							+ " FROM ProductTempInfo a,ProductToTemplate b,ModuleInfo c,ModuleElement d,ModuleElementInfo e,PurchaseRelaInfo f "
							+ "  WHERE a.ProductID = '"+pid+"' "
							+ " AND a.TemplateId = b.FactorID AND b.TemplateCode = c.ID AND c.Id = d.ModuleCode "
							+ " AND e.Id = d.ElementCode AND e.Remark1 = 'Y' AND e.ID = f.ElementID "
							+ " ORDER BY CAST(f.orderflag AS UNSIGNED INT) ASC ";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt =  qb.executeDataTable();
		
		//获得临时表中数据的
		int tem_row = dt_tem.getRowCount() ;
		
		//临时表有这个产品的顺序设置
		if (tem_row > 0) {
			Map<String,Object> map = compareData(dt,dt_tem);
			List<String> add = (List<String>) map.get("add");
			List<String> sub = (List<String>)map.get("sub");
			if (add.size() == 0 && sub.size() == 0) {
				dga.bindData(dt_tem);
			}else{
				boolean issave = update(add, sub, pid);
				if (issave) {
					//添加或删除后  重新查询
					 dt_tem = getOrderSortInfo(pid);
					dga.bindData(dt_tem);
				}else{
					//保存失败 显示空的table
					DataTable dts = new DataTable();
					dga.bindData(dts);
				}
			}
		} else {
			dga.bindData(dt);
		}
	}
	public static DataTable getOrderSortInfo(String pid){
		//查询临时表数据
		String sql_tem = "SELECT oso.productid,oso.orderflag,oso.ordercode as id,pri.inputname,pri.inputcode FROM ordersortinfo AS oso , PurchaseRelaInfo AS pri "
				+ "WHERE oso.ordercode = pri.id AND oso.ProductID = ?  ORDER BY  CAST(oso.orderflag AS UNSIGNED INT) ASC ";
		QueryBuilder qb_tem = new QueryBuilder(sql_tem);
		qb_tem.add(pid);
		DataTable dt_tem = qb_tem.executeDataTable();
		return dt_tem;
	}
	
	public static boolean update(List<String> add , List<String> sub,String pid){
		Transaction ts = new Transaction();
		QueryBuilder qb = null;
		for (int i = 0; i < add.size(); i++) {
			String id = add.get(i);
			//取得原始元素顺序
			String sqlord = "SELECT orderflag FROM PurchaseRelaInfo WHERE id =? LIMIT 1";
			qb = new QueryBuilder(sqlord);
			qb.add(id);
			String orderflag = qb.executeString();
			//保存到临时表
			String sql = "INSERT INTO ordersortinfo (`productid`,`orderflag`, `ordercode`, `createdate`) VALUES (?,?,?, NOW())";
			qb = new QueryBuilder(sql);
			qb.add(pid);
			qb.add(orderflag);
			qb.add(id);
			ts.add(qb);
			UserLog.log(UserLog.SYSTEM, UserLog.LOG, User.getRealName()+"新增元素id："+id , "00.00.00.00");
		}
		
		for (int i = 0; i < sub.size(); i++) {
			String sql = "DELETE FROM ordersortinfo WHERE ordercode = ? AND productid = ?";
			String id = sub.get(i);
			qb = new QueryBuilder(sql);
			qb.add(id);
			qb.add(pid);
			ts.add(qb);
			UserLog.log(UserLog.SYSTEM, UserLog.LOG, User.getRealName()+"删除元素id："+id , "00.00.00.00");
		}
		boolean save = false;
		try {
			save = ts.commit();
		} catch (Exception e) {
			logger.error("更新元素顺序出现异常:"+e.getMessage(), e);
		}
		return save;
	}
	
	/**
	 * 
	 * compare:(判断新增表和临时表). <br/>
	 *
	 * @author chouweigao
	 * @param dts
	 * @param dts1
	 * @param loop true：dts为主循环，
	 * @return
	 */
	public static Map<String,Object> compareData(DataTable dts ,DataTable dts1){
		DataTable dt = dts;
		DataTable dt_tem = dts1;
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> addlist = new ArrayList<String>();
		List<String> sublist = new ArrayList<String>();
		for (int x = 0; x < 2; x++) {
			if (x == 1) {
				dt = dts1;
				dt_tem = dts;
			}
			for (int i = 0; i < dt.getRowCount(); i++) {
				//获得共同表的id
				String id = dt.getString(i, "id");
				boolean isExists = false;
				for (int j = 0; j < dt_tem.getRowCount(); j++) {
					String ordercode = dt_tem.getString(j, "id");
					if (id.equals(ordercode)) {
						isExists = true;
					}
				}
				if (!isExists) {
					if (x == 0) {
						addlist.add(id);
					}else if(x == 1){
						sublist.add(id);
					}
				}
			}
		}
		map.put("add", addlist);
		map.put("sub", sublist);
		return map;
	}
	
	public  void updateWapOrder() {
		String productid = $V("productid");
		String ids = $V("IDs");
		String idv = $V("IDv");
		Transaction ts = new Transaction();
		try {
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("没有找到要更新的行!");
				return;
			}
			String sql_del = "delete from ordersortinfo where productid = ?";
			QueryBuilder qb_del = new QueryBuilder(sql_del);
			qb_del.add(productid);
			ts.add(qb_del);
			String[] idss = ids.split(",");
			String[] idvs = idv.split(",");
			for (int i = 0; i <idss.length; i++) {
				String id = idss[i];
				String val = idvs[i];
				if (StringUtil.isEmpty(val.trim())) {
					val = null;
				}
				String sql = "INSERT INTO ordersortinfo (`productid`,`orderflag`, `ordercode`, `createdate`) VALUES (?,?,?, NOW())";
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(productid);
				qb.add(val);
				qb.add(id);
				ts.add(qb);
			}
			
			if (ts.commit()) {
				Response.setStatus(1);
				UserLog.log(UserLog.SYSTEM, UserLog.LOG, User.getRealName()+"更新产品编码："+productid+"投保要素顺序成功" , Request.getClientIP());
				Response.setMessage("更新顺序成功!");
			}else{
				Response.setStatus(0);
				UserLog.log(UserLog.SYSTEM, UserLog.LOG, User.getRealName()+"更新产品编码："+productid+"投保要素顺序失败" , Request.getClientIP());
				Response.setMessage("更新顺序失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			UserLog.log(UserLog.SYSTEM, UserLog.LOG, User.getRealName()+"更新产品编码："+productid+"投保要素顺序失败"  , Request.getClientIP());
			Response.setMessage("更新顺序失败!");
		}
	}
}
