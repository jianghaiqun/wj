package com.sinosoft.platform;

import java.util.Arrays;
import java.util.List;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.SessionListener;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class UserSelect extends Page{
	
	private static final String Show_Branch = "branch";
	
	private static final String Show_Role = "role";
	
	private static final String Show_OnLine = "online";
	
	private static final String Show_Duty = "duty";
	
	private static Mapx Show_Map = new Mapx();
	
	static{
		Show_Map.put(Show_Branch, "机构");
		Show_Map.put(Show_Role, "角色");
//		Show_Map.put(Show_OnLine, "在线用户");
//		Show_Map.put(Show_Duty, "职务");
	}
	
	public static Mapx init(Mapx params) {
		params.put("ShowType", HtmlUtil.mapxToOptions(Show_Map));
		params.put("SelectedUser", params.getString("SelectedUser"));
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga){
		String showType = dga.getParam("ShowType");
		String selectedUser = dga.getParam("SelectedUser");
		
		String[] userNames = null;
		if (StringUtil.isNotEmpty(selectedUser) && StringUtil.isNotEmpty(selectedUser.substring(0, selectedUser.indexOf("|")))) {
			String userName = selectedUser.substring(0, selectedUser.indexOf("|"));
			if (userName.lastIndexOf(",") == userName.length()) {
				userName = userName.substring(0, userName.length()-1);
			}
			
			userNames = StringUtil.splitEx(userName, ",");
		}
		
		String type = dga.getParam("Type");
		if (StringUtil.isEmpty(type)) {
			type = "checkbox";
		}
		
		if (StringUtil.isEmpty(showType)) {
			showType = Show_Branch;
		}
		
		List list = SessionListener.getUserNames(User.ONLINE);
		String usernames = StringUtil.join(list.toArray(), "','");
		
		DataTable dt = new DataTable();
		if (Show_Role.equalsIgnoreCase(showType)) {
			dt = new QueryBuilder("select RoleCode as ID,RoleCode,RoleName as name from zdrole order by id asc").executeDataTable();
		} else if (Show_Duty.equalsIgnoreCase(showType)) {
			
		} else if (Show_OnLine.equalsIgnoreCase(showType)) {
			dt = new QueryBuilder("select branchinnercode,branchinnercode as ID,name,treelevel,type from zdbranch where type='0' order by orderflag").executeDataTable();
		} else {
			dt = new QueryBuilder("select branchinnercode,branchinnercode as ID,name,treelevel,type from zdbranch where type='0' order by orderflag").executeDataTable();
		}
		
		String userName = "";
		dt.insertColumns(new String[]{"LevelStr", "UserName"});
		int level = 0;
		DataTable userDT = null;
		
		for (int i=0; dt!=null && i<dt.getRowCount(); i++) {
			if (Show_Role.equalsIgnoreCase(showType)) {
				dt.set(i, "LevelStr", "");
				
				userDT = new QueryBuilder("select realname,username from zduser where exists (select * from zduserrole where rolecode = ? and username = "
						+ "zduser.username) order by addTime", dt.getString(i, "RoleCode")).executeDataTable();
				filterUser(dga.getParams(),userDT);
				if ("checkbox".equalsIgnoreCase(type)) {
					userName = dealDataTable("UserList_" + dt.getString(i, "branchinnercode"), userDT, userNames, type);
				} else {
					userName = dealDataTable("UserList", userDT, userNames, type);
				}
				
				dt.set(i, "UserName", userName);
			} else if (Show_OnLine.equalsIgnoreCase(showType)) {
				String levelStr = "";
				level = Integer.parseInt(dt.getString(i, "TreeLevel"));
				for (int j=0; j<level; j++) {
					levelStr += "&nbsp;&nbsp;";
				}
				dt.set(i, "LevelStr", levelStr);
				
				userDT = new QueryBuilder("select realname,username from zduser where branchinnercode = ? and " 
						+ "username in ('" + usernames + "') order by addTime"
						, dt.getString(i, "branchinnercode")).executeDataTable();
				filterUser(dga.getParams(),userDT);
				
				if ("checkbox".equalsIgnoreCase(type)) {
					userName = dealDataTable("UserList_" + dt.getString(i, "branchinnercode"), userDT, userNames, type);
				} else {
					userName = dealDataTable("UserList", userDT, userNames, type);
				}
				
				dt.set(i, "UserName", userName);
			} else if (Show_Duty.equalsIgnoreCase(showType)) {
				
			} else {
				String levelStr = "";
				level = Integer.parseInt(dt.getString(i, "TreeLevel"));
				for (int j=0; j<level; j++) {
					levelStr += "&nbsp;&nbsp;";
				}
				dt.set(i, "LevelStr", levelStr);
				
				userDT = new QueryBuilder("select realname,username from zduser where branchinnercode = ?  order by addTime"
							, dt.getString(i, "branchinnercode")).executeDataTable();
				
				if ("checkbox".equalsIgnoreCase(type)) {
					userName = dealDataTable("UserList_" + dt.getString(i, "branchinnercode"), userDT, userNames, type);
				} else {
					userName = dealDataTable("UserList", userDT, userNames, type);
				}
				
				dt.set(i, "UserName", userName);
			}
		}
		
		dga.bindData(dt);
	}
	
	private static String dealDataTable(String name, DataTable dt, String[] checkedArray, String Type) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < dt.getRowCount(); k++) {
			
//			User user = SessionListener.getUser(dt.getString(k, "UserName"));
			String value = dt.getString(k, "UserName") + "," + dt.getString(k, "RealName");
			sb.append("<input type='" + Type + "' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			boolean flag = false;
			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (dt.getString(k, "UserName").equals(checkedArray[j])) {
						sb.append("' checked >");
						flag = true;
						break;
					}
				}
			}

			if (!flag) {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			
//			if (user != null) {
//				if (User.ONLINE.equals(user.Status)) {
//					sb.append("<img src='Images/online.gif' width='13' height='13' align='absmiddle'>");
//					sb.append(dt.getString(k, "RealName"));
//				} else if (User.AWAY.equals(user.Status)) {
//					sb.append("<img src='Images/online_absent.gif' width='13' height='13' align='absmiddle'>");
//					sb.append(dt.getString(k, "RealName"));
//				} else if (User.BUSY.equals(user.Status)) {
//					sb.append("<img src='Images/online_busy.gif' width='13' height='13' align='absmiddle'>");
//					sb.append(dt.getString(k, "RealName"));
//				} else if (User.HIDDEN.equals(user.Status)) {
//					sb.append("<img src='Images/online_hide.gif' width='13' height='13' align='absmiddle'>");
//					sb.append(dt.getString(k, "RealName"));
//				} else if (User.OFFLINE.equals(user.Status)) {
//					sb.append("<img src='Images/online_hide.gif' width='13' height='13' align='absmiddle'>");
//					sb.append(dt.getString(k, "RealName"));
//				}
//			} else {
				sb.append(dt.getString(k, "RealName"));
//			}
			
			sb.append("</label>&nbsp;");
		}
		return sb.toString();
	}
	
	private static void filterUser(Mapx params,DataTable dt){
		if(params.containsKey("FilterUsers")){
			String filterUsers = params.getString("FilterUsers");
			final List list = Arrays.asList(StringUtil.splitEx(filterUsers, ","));
			dt.filter(new Filter(){
				public boolean filter(Object obj) {
					DataRow dr = (DataRow)obj;
					return list.contains(dr.getString(1));
				}
				
			});
		}
	}
}
