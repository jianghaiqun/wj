package com.sinosoft.platform;

import com.sinosoft.framework.Page;

public class MenuGroup extends Page {
	// public static void dg1DataBind(DataGridAction dga) {
	// String sql = "select * from ZDMenuGroup";
	// DataTable dt = DBUtil.executeDataTable(sql);
	// dga.bindData(dt);
	// }
	//	
	// public static Mapx initDialog(Mapx params){
	// return params;
	// }
	//	
	// public void add(){
	// String ids = $V("IDs");
	// if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
	// Response.setStatus(0);
	// Response.setMessage("传入ID时发生错误!");
	// return;
	// }
	// String IDs = $V("IDs");
	// if(IDs.indexOf("\"")>0||IDs.indexOf("\'")>0){
	// Response.setStatus(0);
	// Response.setMessage("传入ID时发生错误");
	// }
	// ZDMenuGroupSchema menuGroup = new ZDMenuGroupSchema();
	// menuGroup.setName($V("Name"));
	// menuGroup.setMenuGroupID(NoUtil.getMaxID("MenuGroupID"));
	// menuGroup.setMemo($V("Memo"));
	// menuGroup.setAddTime(new Date());
	// menuGroup.setAddUser(User.getUserName());
	//		
	// String[] arr = IDs.split(",");
	//		
	//		
	// ZDMenuGroupDetailSet menuGroupDetailSet = new ZDMenuGroupDetailSet();
	// for (int i = 0; i < arr.length; i++) {
	// ZDMenuGroupDetailSchema menuGroupDetail = new ZDMenuGroupDetailSchema();
	// menuGroupDetail.setMenuGroupID(menuGroup.getMenuGroupID());
	// menuGroupDetail.setMenuID(Long.parseLong(arr[i]));
	// menuGroupDetail.setRelaType("1");
	// menuGroupDetail.setMemo("");
	// menuGroupDetail.setAddTime(new Date());
	// menuGroupDetail.setAddUser(User.getUserName());
	// menuGroupDetailSet.add(menuGroupDetail);
	// }
	// Transaction trans = new Transaction();
	// trans.add(menuGroup, 1);
	// trans.add(menuGroupDetailSet, 1);
	// if(trans.commit()){
	// Response.setStatus(1);
	// Response.setMessage("新建菜单组成功");
	// }else{
	// Response.setStatus(0);
	// Response.setMessage("新建菜单组失败");
	// }
	// }
	//	
	// public void save(){
	// String ids = $V("IDs");
	// if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
	// Response.setStatus(0);
	// Response.setMessage("传入ID时发生错误!");
	// return;
	// }
	// String IDs = $V("IDs");
	// if(IDs.indexOf("\"")>0||IDs.indexOf("\'")>0){
	// Response.setStatus(0);
	// Response.setMessage("传入ID时发生错误");
	// }
	// ZDMenuGroupSchema menuGroup = new ZDMenuGroupSchema();
	// menuGroup.setValue(Request);
	// menuGroup.setAddTime(new Date());
	// menuGroup.setAddUser(User.getUserName());
	//		
	// String[] arr = IDs.split(",");
	//		
	// ZDMenuGroupDetailSet menuGroupDetailSet = new ZDMenuGroupDetailSet();
	// for (int i = 0; i < arr.length; i++) {
	// ZDMenuGroupDetailSchema menuGroupDetail = new ZDMenuGroupDetailSchema();
	// menuGroupDetail.setMenuGroupID(menuGroup.getMenuGroupID());
	// menuGroupDetail.setMenuID(Long.parseLong(arr[i]));
	// menuGroupDetail.setRelaType("1");
	// menuGroupDetail.setMemo("");
	// menuGroupDetail.setAddTime(new Date());
	// menuGroupDetail.setAddUser(User.getUserName());
	// menuGroupDetailSet.add(menuGroupDetail);
	// }
	// Transaction trans = new Transaction();
	// trans.setBackupOperator(User.getUserName());
	// trans.add(menuGroup, 2);
	// trans.add(new ZDMenuGroupDetailSchema().query(" where MenuGroupID = "+$V("MenuGroupID")+""),5);
	// trans.add(menuGroupDetailSet, 1);
	// if(trans.commit()){
	// Response.setStatus(1);
	// Response.setMessage("新建菜单组成功");
	// }else{
	// Response.setStatus(0);
	// Response.setMessage("新建菜单组失败");
	// }
	// }
	//	
	// public void del() {
	// String ids = $V("IDs");
	// if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
	// Response.setLogInfo(0,"传入ID时发生错误!");
	// return;
	// }
	// Transaction trans = new Transaction();
	// trans.setBackupOperator(User.getUserName());
	// trans.add(new ZDMenuGroupSchema().query(" where MenuGroupID in ("+ids+")"),Transaction.DELETE_AND_BACKUP);
	// trans.add(new ZDMenuGroupDetailSchema().query(" where MenuGroupID in ("+ids+")"),Transaction.DELETE_AND_BACKUP);
	// if (trans.commit()) {
	// Response.setStatus(1);
	// Response.setMessage("删除菜单组成功");
	// } else {
	// Response.setStatus(0);
	// Response.setMessage("删除菜单组失败");
	// }
	// }
}
