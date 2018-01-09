package com.sinosoft.member;

import java.util.Date;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDMemberAddrSchema;

public class MemberAddress extends Ajax {
	
	public static void dg1DataList(DataListAction dla) {
		QueryBuilder qb = new QueryBuilder("select * from zdmemberaddr where UserName = ?  Order by AddTime Desc",User.getUserName());
		dla.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		dt.insertColumn("ProvinceName");
		dt.insertColumn("CityName");
		dt.insertColumn("DistrictName");
		dt.insertColumn("IsDefaultName");
		Mapx DistrictMap = PubFun.getDistrictMap();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if(StringUtil.isNotEmpty(dt.getString(i,"Province"))){
				dt.set(i,"ProvinceName",DistrictMap.getString(dt.getString(i,"Province")));
			}
			if(StringUtil.isNotEmpty(dt.getString(i,"City"))){
				dt.set(i,"CityName",DistrictMap.getString(dt.getString(i,"City")));
			}
			if(StringUtil.isNotEmpty(dt.getString(i,"District"))){
				dt.set(i,"DistrictName",DistrictMap.getString(dt.getString(i,"District")));
			}
			if(dt.getString(i,"IsDefault").equals("Y")){
				dt.set(i,"IsDefaultName","<b>默认</b>");
			}else{
				dt.set(i,"IsDefaultName","<a href='#;' onClick='setDefault(\""+dt.getString(i,"ID")+"\",\""+dt.getString(i,"UserName")+"\")'>设为默认</a>");
			}
		}
		dla.bindData(dt);
	}
	
	public void setDefault() {
		String ID = $V("ID");
		String UserName = $V("UserName");
		if(StringUtil.isEmpty(UserName)||StringUtil.isEmpty(ID)){
			Response.setLogInfo(0, "发生错误");
			return;
		}
		Transaction trans  = new Transaction();
		trans.add(new QueryBuilder("update zdmemberaddr set IsDefault = 'N' where UserName = ?",UserName));
		ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
		addr.setID(ID);
		addr.fill();
		addr.setIsDefault("Y");
		trans.add(addr,Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "设置成功");
		} else {
			Response.setLogInfo(0, "发生错误");
		}
	}
	
	public void getAddress() {
		String ID = $V("AddrID");
		if(StringUtil.isEmpty(ID)){
			Response.setLogInfo(0, "发生错误");
			return;
		}
		ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
		addr.setID(ID);
		addr.fill();
		Response.putAll(addr.toMapx());
	}
	
	public void doSave(){
		String UserName = User.getUserName();
		String ID = $V("ID");
		if(StringUtil.isEmpty(UserName)){
			Response.setLogInfo(0, "发生错误");
			return;
		}
		ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
		boolean isExists = false;
		if(StringUtil.isNotEmpty(ID)){
			memberaddr.setID(ID);
			memberaddr.fill();
			memberaddr.setModifyUser(UserName);
			memberaddr.setModifyTime(new Date());
			isExists = true;
		}else{
			memberaddr.setID(NoUtil.getMaxID("MemberAddr"));
			memberaddr.setAddUser(UserName);
			memberaddr.setAddTime(new Date());
		}
		memberaddr.setValue(Request);
		memberaddr.setUserName(UserName);
		if(isExists){
			if (memberaddr.update()) {
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "修改失败!");
			}
		}else{
			if (memberaddr.insert()) {
				Response.setLogInfo(1, "新增成功");
			} else {
				Response.setLogInfo(0, "新增失败!");
			}
		}
	}
	
	public void del(){
		String ID = $V("ID");
		if(StringUtil.isNotEmpty(ID)){
			ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
			addr.setID(ID);
			addr.fill();
			if(addr.deleteAndBackup()){
				Response.setLogInfo(1,"删除地址成功");
			}else{
				Response.setLogInfo(0,"删除地址失败");
			}
		}
	}
	
}
