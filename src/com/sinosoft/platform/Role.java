package com.sinosoft.platform;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
public class Role extends Page {

	public final static String EVERYONE = "everyone";
	
	public static void treeDataBind(TreeAction ta) {
		DataTable dt =null;
		if(!"admin".equals(User.getUserName())){
			String branchinnercode = User.getBranchInnerCode();
			if(branchinnercode.length()>=27){
				branchinnercode=branchinnercode.substring(0,27);
				if(branchinnercode.indexOf("120000095120000001")>=0){
					
				}else{
					branchinnercode=branchinnercode.substring(0,18);
				}
				
			}
			dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName from ZDRole Where BranchInnerCode like '"+branchinnercode+"%' and RoleCode not like '%_admin'  and RoleCode not like '%_everyone' ").executeDataTable();
		}else{
			dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName from ZDRole ").executeDataTable();
		}
	
		ta.setRootText("角色列表");
		ta.setIdentifierColumnName("RoleCode");
		ta.setBranchIcon("Icons/icon025a1.gif");
		ta.setLeafIcon("Icons/icon025a1.gif");
		ta.bindData(dt);
	}

}
