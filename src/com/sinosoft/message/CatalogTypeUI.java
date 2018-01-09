package com.sinosoft.message;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCCatalogTypeSchema;
import com.sinosoft.schema.SCCatalogTypeSet;

import java.util.Date;

/**
 * @Author周翔
 * @Date 2012-6-11
 * @Mail zhouxiang@sinosoft.com.cn
 */

public class CatalogTypeUI extends Page {
	
	public static Mapx initDialog(Mapx params) {
		String tID = params.getString("ID");
		if(!"".equals(tID)&&tID!=null){
			SCCatalogTypeSchema tSCCatalogTypeSchema=new SCCatalogTypeSchema();
			tSCCatalogTypeSchema.setId(tID);
			if (!tSCCatalogTypeSchema.fill()) {
				logger.info("查询不到该信息");
				return params;
			}
			params = tSCCatalogTypeSchema.toMapx();
		}
		
		return params;
	}
	
	public void save() {
		
		String ID = $V("ColumnID");
		
		//获取问题信息
		Transaction trans = new Transaction();
		SCCatalogTypeSchema tSCCatalogTypeSchema=new SCCatalogTypeSchema();
		if (StringUtil.isEmpty(ID)) {
			tSCCatalogTypeSchema.setId(NoUtil.getMaxID("CatalogTypeID")+"");
			tSCCatalogTypeSchema.setValue(this.Request);
			tSCCatalogTypeSchema.setAddTime(new Date());
			tSCCatalogTypeSchema.setAddUser(User.getUserName());
			trans.add(tSCCatalogTypeSchema, Transaction.INSERT);
			
		}else{
			tSCCatalogTypeSchema.setId(ID);
			tSCCatalogTypeSchema.fill();
			tSCCatalogTypeSchema.setValue(this.Request);
			tSCCatalogTypeSchema.setModifyTime(new Date());
			tSCCatalogTypeSchema.setModifyUser(User.getUserName());
			trans.add(tSCCatalogTypeSchema, Transaction.UPDATE);
		}
		
		
		try {
			if (!trans.commit()) {
				Response.setError("操作失败!");
			} else {
				Response.setMessage("操作成功!");
			}
		} catch (Exception e) {
			Response.setError("系统异常!");
		}

	}
	
	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		Transaction trans = new Transaction();
		SCCatalogTypeSchema tSCCatalogTypeSchema=new SCCatalogTypeSchema();
		SCCatalogTypeSet tSCCatalogTypeSet = tSCCatalogTypeSchema.query(new QueryBuilder("where ID in (" + ids + ")"));
		trans.add(tSCCatalogTypeSet, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除提问成功");
		} else {
			Response.setLogInfo(0, "删除提问失败");
		}
	}
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from  SCCatalogType order by addtime");
		dga.bindData(qb);
	}
	
}