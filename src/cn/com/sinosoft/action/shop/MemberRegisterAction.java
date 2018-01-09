package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.Config;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * 前台Action类 - 注册
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT456CA553F200C7A4CFEC6788B8F51192
 * ============================================================================
 */



@ParentPackage("shop")
public class MemberRegisterAction extends BaseShopAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2951145117300612942L;
	
	/* 保存证件类型列表 */
	private List<Dict> listid = new ArrayList<Dict>();
	
	/* 保存兴趣爱好列表 */
	private List<Dict> listHobby = new ArrayList<Dict>();
	
	private String hostName="http://localhost:8080/index.shtml";
		


	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * 评论注册前的地址
	 */
	private String windowAddr = null;
	
	public String getWindowAddr() {
		return windowAddr;
	}

	public void setWindowAddr(String windowAddr) {
		this.windowAddr = windowAddr;
	}
	

	/* 兴趣爱好get方法 */
	public List<Dict> getListHobby() {
		return listHobby;
	}

	/* 兴趣爱好set方法 */
	public void setListHobby(List<Dict> listHobby) {
		this.listHobby = listHobby;
	}
	
	/* 证件类型列表get方法 */
	public List<Dict> getListid() {
		return listid;
	}

	/* 证件类型列表set方法 */
	public void setListid(List<Dict> listid) {
		this.listid = listid;
	}
	
	// 会员注册
	public String index() {
		String idtypeSql = "select codevalue,codename from zdcode where codetype=? and  parentcode=? order by codevalue asc";
		String hobbySql = "select codevalue,codename from zdcode where codetype=? and parentcode=? order by codevalue asc";
		String[] idtypeSqltemp = {"member.IDType","IDType"};  
		String[] hobbySqltemp = {"member.Hobby","hobby"};  
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			List<Map> idtypelist = jtd.obtainData(idtypeSql,idtypeSqltemp);
			List<Map> hobbylist = jtd.obtainData(hobbySql,hobbySqltemp);
			Iterator<Map> it = idtypelist.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict idType = new Dict();
				idType.setDictName((String) map.get("CodeName"));
				idType.setDictSerial((String) map.get("CodeValue"));
				listid.add(idType);
			}
			
			it = hobbylist.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict hobby = new Dict();
				hobby.setDictName((String) map.get("CodeName"));
				hobby.setDictSerial((String) map.get("CodeValue"));
				listHobby.add(hobby);
			}
			
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		
		return "index";
	}
	
	/**
	 * 新注册页面Action
	 * **/
	public String home() {
		//点击注册的来源页面
		
		//System.out.println(getRequest().getParameter("windowAddr"));
		if(windowAddr==null||"".equals(windowAddr)){
			windowAddr=getRequest().getHeader("Referer");
			//若windowAddr 来源页面 是用POST方式提交，那么URL中会有？,或者action结束，这样直接跳回去必然报错，还是返回首页~
			if(windowAddr==null||"".equals(windowAddr)||windowAddr.endsWith("?")||windowAddr.endsWith("action")){
				windowAddr = Config.getValue("FrontServerContextPath");
			}
		}	
		return "home";
	}

}
