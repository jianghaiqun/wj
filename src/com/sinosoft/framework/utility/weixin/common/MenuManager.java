package com.sinosoft.framework.utility.weixin.common;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.weixin.bean.AccessToken;
import com.sinosoft.framework.utility.weixin.bean.Button;
import com.sinosoft.framework.utility.weixin.bean.ClickButton;
import com.sinosoft.framework.utility.weixin.bean.ComplexButton;
import com.sinosoft.framework.utility.weixin.bean.Menu;
import com.sinosoft.framework.utility.weixin.bean.ViewButton;
import com.sinosoft.schema.MenuDefineSchema;
import com.sinosoft.schema.MenuDefineSet;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;
import com.sinosoft.weixin.WeiXinCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

/**
 * 菜单管理器类
 * 
 * @author
 * @date 2014-06-11 
 */
public class MenuManager extends Page {
	private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);
	private static Properties props = new Properties();
	static{
		try {
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties");
		    InputStreamReader rd = new InputStreamReader(is, "UTF-8");
		    props.load(rd);
		    rd.close();
		    is.close();
		   // props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("waperrorinfos.properties"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		   logger.error(e.getMessage(), e);
		}
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {
		StringBuffer sb1 = new StringBuffer("");
		sb1.append(SelectTag.getOptionHtml("开心保网站", "kxb", true));
		sb1.append(SelectTag.getOptionHtml("开心保车险", "kxbchexian", true));
		params.put("menutype", sb1.toString());
		return params;
	}
	/**
	 * 微信生产菜单
	 */
	public void createMenu(){
		String menuType = $V("MenuType");
		// 第三方用户唯一凭证
		String appId = "";
		// 第三方用户唯一凭证密钥 
		String appSecret = "";
		String token = "";
		if(menuType.equals("kxb")){
			// 第三方用户唯一凭证
			appId = String.valueOf(props.get("appId"));
			// 第三方用户唯一凭证密钥 
			appSecret =  String.valueOf(props.get("appSecret"));
			// 调用接口获取access_token
			token = WeiXinCommon.ajaxtoken();
		}else if(menuType.equals("kxbchexian")){
			// 第三方用户唯一凭证
			appId = String.valueOf(props.get("chexian_appId"));
			// 第三方用户唯一凭证密钥 
			appSecret =  String.valueOf(props.get("chexian_appSecret"));
			// 调用接口获取access_token
			AccessToken at = WeixinMenuUtil.getAccessToken(appId, appSecret);
			if (null != at) {
				token = at.getToken();
			}
		}
		
		if (StringUtil.isNotEmpty(token)) {
			// 调用接口创建菜单
			int result = WeixinMenuUtil.createMenu(getMenu(menuType), token);

			// 判断菜单创建结果
			if (0 == result){
				logger.info("菜单创建成功！");
				Response.setStatus(1);
				Response.setMessage("菜单创建成功!");
			}else{
				logger.error("菜单创建失败，错误码：{}", result);
				Response.setStatus(0);
				Response.setMessage("菜单创建失败!");
			}
		}
		
	}
	/**
	 * 微信删除菜单
	 */
	public void deleteMenu(){
		String menuType = $V("MenuType");
		// 第三方用户唯一凭证
		String appId = "";
		// 第三方用户唯一凭证密钥 
		String appSecret = "";
		String token = "";
		if(menuType.equals("kxb")){
			// 第三方用户唯一凭证
			appId = String.valueOf(props.get("appId"));
			// 第三方用户唯一凭证密钥 
			appSecret =  String.valueOf(props.get("appSecret"));
			// 调用接口获取access_token
			token = WeiXinCommon.ajaxtoken();
		}else if(menuType.equals("kxbchexian")){
			// 第三方用户唯一凭证
			appId = String.valueOf(props.get("chexian_appId"));
			// 第三方用户唯一凭证密钥 
			appSecret =  String.valueOf(props.get("chexian_appSecret"));
			// 调用接口获取access_token
			AccessToken at = WeixinMenuUtil.getAccessToken(appId, appSecret);
			if (null != at) {
				token = at.getToken();
			}
		}
		if (StringUtil.isNotEmpty(token)) {
			// 调用接口创建菜单
			int result = WeixinMenuUtil.deleteMenu(token);

			// 判断菜单创建结果
			if (0 == result){
				logger.info("菜单删除成功！");
				Response.setStatus(1);
				Response.setMessage("菜单删除成功!");
			}else{
				logger.error("菜单删除失败，错误码：{}", result);
				Response.setStatus(0);
				Response.setMessage("菜单删除失败!");
			}
		}
	}
	
	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu(String menuType) {

		// 取出一级菜单

		QueryBuilder menuQB = new QueryBuilder(" SELECT * FROM MenuDefine WHERE MenuLevel='0' and MenuType=? order by id");
		menuQB.add(menuType);
		DataTable menuDT = menuQB.executeDataTable();
		Button[] button = new Button[menuDT.getRowCount()];
		for (int i = 0; i < menuDT.getRowCount(); i++) {
			
			Button mainbtn = null;
			QueryBuilder childMenuQB = new QueryBuilder("SELECT * FROM MenuDefine WHERE ParentMenuID=? order by id");
			childMenuQB.add(menuDT.getString(i, "MenuID"));
			DataTable childMenuDT = childMenuQB.executeDataTable();
			if(childMenuDT.getRowCount()<=0){
				String mainType = menuDT.getString(i, "MenuResponseType");
				if (mainType.equals("click")) {
					ClickButton btn1 = new ClickButton();
					btn1.setType(mainType);
					btn1.setName(menuDT.getString(i, "ExternalMenuName"));
					btn1.setKey(menuDT.getString(i, "MenuAttribute"));
					mainbtn = btn1;
				}
				if (mainType.equals("view")) {
					ViewButton btn1 = new ViewButton();
					btn1.setType(mainType);
					btn1.setName(menuDT.getString(i, "ExternalMenuName"));
					btn1.setUrl(menuDT.getString(i, "MenuURL"));
					mainbtn = btn1;
				}
			}else{
				ComplexButton mainBtn1 = new ComplexButton();
				mainBtn1.setName(menuDT.getString(i, "ExternalMenuName"));
				// 取出二级菜单
				Button[] bton = new Button[childMenuDT.getRowCount()];
				for (int j = 0; j < childMenuDT.getRowCount(); j++) {
					String type = childMenuDT.getString(j, "MenuResponseType");
					Button btn = null;
					if (type.equals("click")) {
						ClickButton btn1 = new ClickButton();
						btn1.setType(type);
						btn1.setName(childMenuDT.getString(j, "ExternalMenuName"));
						btn1.setKey(childMenuDT.getString(j, "MenuAttribute"));
						btn = btn1;
					}
					if (type.equals("view")) {
						ViewButton btn1 = new ViewButton();
						btn1.setType(type);
						btn1.setName(childMenuDT.getString(j, "ExternalMenuName"));
						btn1.setUrl(childMenuDT.getString(j, "MenuURL"));
						btn = btn1;
					}
					bton[j] = btn;

				}
				mainBtn1.setSub_button(bton);
				mainbtn = mainBtn1;
			}
			button[i] = mainbtn;
		}
		// 拼装菜单对象
		Menu menu = new Menu();
		menu.setButton(button);

		return menu;
	}
	
	/**
	 * dg1DataBind:微信公众号菜单查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from MenuDefine WHERE MENUtYPE = 'kxb'");
		DataTable dt = qb.executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "Id", "ParentMenuId");
		dga.bindData(dt);
	}
	
	/**
	 * initDialog:微信公众号添加修改初始化. <br/>
	 *
	 * @author wwy
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		String ID = (String) params.get("ID");
		QueryBuilder qb = new QueryBuilder(" SELECT * FROM MenuDefine WHERE id=? ");
		qb.add(ID);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount() >= 1){
			params.put("ID", dt.getString(0, "ID"));
			params.put("MenuLevel", dt.getString(0, "MenuLevel"));
			params.put("ParentMenuID", dt.getString(0, "ParentMenuID"));
			params.put("ExternalMenuName", dt.getString(0, "ExternalMenuName"));
			params.put("MenuAttribute", dt.getString(0, "MenuAttribute"));
			params.put("MenuResponseType", dt.getString(0, "MenuResponseType"));
			params.put("MenuURL", dt.getString(0, "MenuURL"));
		} 
		QueryBuilder qb1 = new QueryBuilder("select * from MenuDefine WHERE MENUtYPE = 'kxb' AND MenuLevel = '0'");
		DataTable dt1 = qb1.executeDataTable();
		Mapx map = dt1.toMapx("ID", "ExternalMenuName");
		params.put("ParentMenu", HtmlUtil.mapxToOptions(map, true));
		
		return params;
	}
	
	/**
	 * save:微信菜单保存. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	public boolean save() {
		String ID= $V("ID");
		MenuDefineSchema info = new MenuDefineSchema();
		String ParentMenuID= $V("ParentMenuID");
		String ExternalMenuName = $V("ExternalMenuName");
		String MenuAttribute = $V("MenuAttribute");
		String MenuResponseType = $V("MenuResponseType");
		String MenuURL = $V("MenuURL");
		
		Transaction trans = new Transaction();
		info.setID(ID);
		if (StringUtil.isNotEmpty(ID)) {
			info.fill();
		}
		info.setParentMenuID(ParentMenuID);
		info.setExternalMenuName(ExternalMenuName);
		info.setMenuAttribute(MenuAttribute);
		info.setMenuResponseType(MenuResponseType);
		info.setMenuURL(MenuURL);
		
		if (StringUtil.isEmpty(ID)) {
			QueryBuilder qb = new QueryBuilder("SELECT MAX(CONVERT(ID, SIGNED)) FROM MenuDefine");
			Long maxId = qb.executeLong();
			ID = String.valueOf(maxId + 1);
			
			info.setID(ID);
			info.setMenuID(ID);
			info.setMenuStatus("1");
			info.setMenuType("kxb");
			info.setMenuLevel("1");
			info.setCreateDate(new Date());
			trans.add(info, Transaction.INSERT);
		}else {
			info.setModifyDate(new Date());
			trans.add(info, Transaction.UPDATE);
		}
		
		// 图文回复，需要代码表中存在 菜单KEY
		if ("click".equals(MenuResponseType)) {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setCodeType("WeiXin.Menu");
			code.setParentCode("WeiXin.Menu");
			code.setCodeValue(MenuAttribute);
			ZDCodeSet codeSet = code.query();
			if (codeSet.size() == 0) {
				code.setCodeName(ExternalMenuName);
				code.setAddUser(User.getUserName());
				code.setAddTime(new Date());
				code.setCodeOrder(System.currentTimeMillis());
				trans.add(code, Transaction.INSERT);
			}
		}
		
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("发生错误：操作失败！"); 
			return false;
		}
		return true;
		
	}
	
	/**
	 * del:删除菜单. <br/>
	 *
	 * @author wwy
	 */
	public void del() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		MenuDefineSchema schema = new MenuDefineSchema();
		MenuDefineSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		for (int i = 0; i < set.size(); i++) {
			schema = set.get(i);
			if ("0".equals(schema.getMenuLevel())) {
				Response.setLogInfo(1, "一级菜单数据不能删除！");
				return;
			}
		}
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else{
			Response.setLogInfo(0, "删除失败");
		}
	}
	
	/*private static Menu getMenu1() {

		// 取出一级菜单
		ComplexButton mainBtn1 = new ComplexButton();
		ComplexButton mainBtn2 = new ComplexButton();
		ComplexButton mainBtn3 = new ComplexButton();

		QueryBuilder menuQB = new QueryBuilder(" SELECT * FROM MenuDefine WHERE MenuLevel='0' ");
		DataTable menuDT = menuQB.executeDataTable();
		//Button[] button = new Button[menuDT.getRowCount()];
		for (int i = 0; i < menuDT.getRowCount(); i++) {
			if (0==i) {
				mainBtn1.setName(menuDT.getString(i, "ExternalMenuName"));
			}
			if (1==i) {
				mainBtn2.setName(menuDT.getString(i, "ExternalMenuName"));
			}
			if (2==i) {
				mainBtn3.setName(menuDT.getString(i, "ExternalMenuName"));
			}
			// 取出二级菜单
			QueryBuilder childMenuQB = new QueryBuilder("SELECT * FROM MenuDefine WHERE ParentMenuID=?");
			childMenuQB.add(menuDT.getString(i, "MenuID"));
			DataTable childMenuDT = childMenuQB.executeDataTable();
			Button[] bton = new Button[childMenuDT.getRowCount()];
			for (int j = 0; j < childMenuDT.getRowCount(); j++) {
				String type = childMenuDT.getString(j, "MenuResponseType");
				Button btn = null;
				if (type.equals("click")) {
					ClickButton btn1 = new ClickButton();
					btn1.setType(type);
					btn1.setName(childMenuDT.getString(j, "ExternalMenuName"));
					btn1.setKey(childMenuDT.getString(j, "MenuAttribute"));
					btn = btn1;
				}
				if (type.equals("view")) {
					ViewButton btn1 = new ViewButton();
					btn1.setType(type);
					btn1.setName(childMenuDT.getString(j, "ExternalMenuName"));
					btn1.setUrl(childMenuDT.getString(j, "MenuURL"));
					btn = btn1;
				}
				bton[j] = btn;

			}
			//如果没有
			if(childMenuDT.getRowCount()<=0){
				
			}
			// 将二级菜单与一级菜单关联
			if (i+1 == 1&&bton.length>=1) {
				mainBtn1.setSub_button(bton);
			}
			if (i+1 == 2&&bton.length>=1) {
				mainBtn2.setSub_button(bton);
			}
			if (i+1 == 3&&bton.length>=1) {
				mainBtn3.setSub_button(bton);
			}

		}
		// 拼装菜单对象
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}*/
	public static void main(String[] args) {
		/*// 第三方用户唯一凭证
		String appId = "wx30efeba01a41aad8";
		// 第三方用户唯一凭证密钥
		String appSecret = "14ba47d4675a036de2396728b5293f2a";
		// 调用接口获取access_token
		AccessToken at = WeixinMenuUtil.getAccessToken(appId, appSecret);
        System.out.println(at.getToken());
		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinMenuUtil.createMenu(getMenu("kxb"), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}*/
	}
}