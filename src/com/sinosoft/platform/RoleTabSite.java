package com.sinosoft.platform;

import java.util.Map;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDPrivilegeSchema;

public class RoleTabSite extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String RoleCode = dga.getParam("RoleCode");
		if (StringUtil.isEmpty(RoleCode)) {
			RoleCode = dga.getParam("Role.LastRoleCode");
			if (StringUtil.isEmpty(RoleCode)) {
				dga.bindData(new DataTable());
				return;
			}
		}
		String PrivType = dga.getParam("PrivType");
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + RoleCode + "' as RoleCode");
		Object[] ks = Priv.SITE_MAP.keyArray();
		for (int i = 0; i < Priv.SITE_MAP.size(); i++) {
			sb.append(",'' as " + ks[i]);
		}
		/* 根据登陆人的权限获得所管辖的站点*/
		String siteuseName = User.getUserName();
//		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType" + sb.toString()
//				+ " from ZCSite a order by orderflag ,id";
		DataTable siteDT;
		String roleSql="select distinct roleCode  from  zduserrole where UserName='"+siteuseName+"'";
		DataTable roleDt= new QueryBuilder(roleSql).executeDataTable();
		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType " + sb.toString()
		+ " from ZCSite a order by id";
		if(!"admin".equals(siteuseName)){
			 sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType " + sb.toString()
			+ " from ZCSite a where id in (select  distinct id  FROM  zdprivilege where Owner=? " ;
			 if(roleDt.getRowCount()>0){
				
				 for(int i=0;i<roleDt.getRowCount();i++){
					 String ruserRole=roleDt.get(i).toString();
					 ruserRole=ruserRole.substring(ruserRole.indexOf(":")+1,ruserRole.length());
					 sql=sql+" or Owner='"+ruserRole+"' ";
					}
				
			 }
			sql=sql+" and PrivType='site'  and Value='1' ) order by id";
			
			dga.setTotal(new QueryBuilder(sql,siteuseName));
			 siteDT = new QueryBuilder(sql,siteuseName).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		}else{
			dga.setTotal(new QueryBuilder("select * from ZCSite "));
			 siteDT = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		}
	
		
		Map PrivTypeMap = RolePriv.getPrivTypeMap(RoleCode, PrivType);
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			String ID = dr.getString("ID");
			Map mapRow = (Map) PrivTypeMap.get(ID);
			if (mapRow != null) {
				for (int j = 0; j < dr.getColumnCount(); j++) {
					if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
						dr.set(j, "0".equals(mapRow.get(dr.getDataColumn(j).getColumnName().toLowerCase())) ? "" : "√");
					}
				}
			}
		}
		dga.bindData(siteDT);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		String RoleCode = $V("RoleCode");
		// String PrivType = $V("PrivType");
		Transaction trans = new Transaction();
		ZDPrivilegeSchema p = new ZDPrivilegeSchema();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < dt.getColCount(); j++) {
				if (dt.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					trans.add(p.query(new QueryBuilder("where OwnerType='" + RolePriv.OWNERTYPE_ROLE
							+ "' and Owner =? and PrivType = '" + dt.getString(i, "PrivType") + "' and ID = '"
							+ dt.getString(i, "ID") + "' and Code = '" + dt.getDataColumn(j).getColumnName().toLowerCase() + "' ",
							RoleCode)), Transaction.DELETE_AND_BACKUP);
				}
			}
		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
					priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
					priv.setOwner(dr.getString("RoleCode"));
					priv.setID(dr.getString("ID"));
					priv.setPrivType(dr.getString("PrivType"));
					priv.setCode(dr.getDataColumn(j).getColumnName().toLowerCase());
					priv.setValue("".equals(dr.getString(j)) ? "0" : "1");
					trans.add(priv, Transaction.INSERT);
				}
			}
		}
		if (trans.commit()) {
			RolePriv.updateAllPriv(RoleCode);
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

}
