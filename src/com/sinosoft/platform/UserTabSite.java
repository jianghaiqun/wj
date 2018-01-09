package com.sinosoft.platform;

import java.util.List;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.ZDPrivilegeSchema;


public class UserTabSite extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		Mapx map = dga.getParams();
		String UserName = (String) map.get("UserName");
		
		if (UserName == null || "".equals(UserName)) {
			dga.bindData(new DataTable());
			return;
		}
		String s = "";
		String PrivType = (String) map.get("PrivType");
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + UserName + "' as UserName");

		Object[] ks = Priv.SITE_MAP.keyArray();
		for (int i = 0; i < Priv.SITE_MAP.size(); i++) {
			sb.append(",'" + s + "' as " + ks[i].toString());
		}
		DataTable siteDT;
		String siteuseName = User.getUserName();
		String roleSql="select distinct roleCode  from  zduserrole where UserName='"+siteuseName+"'";
		DataTable roleDt= new QueryBuilder(roleSql).executeDataTable();
		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType " + sb.toString()
		+ " from ZCSite a order by id";
		if(!"admin".equals(siteuseName)){
			/*
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
			System.out.println(sql);
			
			dga.setTotal(new QueryBuilder(sql,siteuseName));
			 siteDT = new QueryBuilder(sql,siteuseName).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			*/
			dga.setTotal(new QueryBuilder("select * from ZCSite "));
			 siteDT = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		}else{
			dga.setTotal(new QueryBuilder("select * from ZCSite "));
			 siteDT = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		}
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName().toLowerCase().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(UserName, PrivType, dr.getString("ID"), columnName) ? "√" : "");
				}
			}
		}
//		dga.setTotal(new QueryBuilder(sql,siteuseName));
		dga.bindData(siteDT);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");// 结果
		String UserName = $V("UserName");
		String PrivType = $V("PrivType");
		String[] RoleCodes = new String[0];
		List roleCodeList = PubFun.getRoleCodesByUserName(UserName);
		if (roleCodeList != null && roleCodeList.size() != 0) {
			RoleCodes = (String[]) roleCodeList.toArray(new String[roleCodeList.size()]);
		}
		Transaction trans = new Transaction();
		ZDPrivilegeSchema p = new ZDPrivilegeSchema();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < dt.getColCount(); j++) {
				if (dt.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					trans.add(p.query(new QueryBuilder("where OwnerType='" + Priv.OWNERTYPE_USER
							+ "' and Owner=? and PrivType = '" + dt.getString(i, "PrivType") + "' and ID = '"
							+ dt.getString(i, "ID") + "' and Code = '" + dt.getDataColumn(j).getColumnName().toLowerCase() + "' ",
							UserName)), Transaction.DELETE_AND_BACKUP);
				}
			}
		}
		boolean value = false;
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			// 检查用户的权限与所属角色的权限是否相等
			int j = 0;
			boolean same = true;

			while (j < dr.getColumnCount()) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					value = "√".equals(dr.getString(j));
					boolean roleValue = RolePriv.getRolePriv(RoleCodes, PrivType, dr.getString("ID"), dr.getDataColumn(
							j).getColumnName().toLowerCase());
					if (!(value == roleValue)) {
						same = false;
						break;
					}
				}
				j++;
			}
			if (same) {
				continue;
			}
			boolean roleValue = false;
			for (j = 0; j < dr.getColumnCount(); j++) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					value = "√".equals(dr.getString(j));
					roleValue = RolePriv.getRolePriv(RoleCodes, PrivType, dr.getString("ID"), dr.getDataColumn(j)
							.getColumnName().toLowerCase());

					ZDPrivilegeSchema userPriv = new ZDPrivilegeSchema();
					userPriv.setOwnerType(Priv.OWNERTYPE_USER);
					userPriv.setOwner(UserName);
					userPriv.setID(dr.getString("ID"));
					userPriv.setPrivType(dr.getString("PrivType"));
					userPriv.setCode(dr.getDataColumn(j).getColumnName().toLowerCase());
					if (roleValue) {
						if (value) {
							userPriv.setValue("0");
						} else {
							userPriv.setValue("-1");
						}
					} else {
						if (value) {
							userPriv.setValue("1");
						} else {
							userPriv.setValue("0");
						}
					}
					trans.add(userPriv, Transaction.INSERT);
				}
			}
		}
		if (trans.commit()) {
			Priv.updateAllPriv(UserName);
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}
}
