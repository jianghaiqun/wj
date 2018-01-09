/**
 * 
 */
package cn.com.sinosoft.action.shop;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.sms.messageinterface.process.Md5security;

/**
 * @author wangcaiyun
 *
 */
@ParentPackage("shop") 
public class ActivityGiveProductAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8786544660307337710L;

	public String giveInsured() {
		Map<String, String> map = new HashMap<String, String>();
		
		String realName = getRequest().getParameter("realName");
		String IDNO = getRequest().getParameter("IDNO");
		String IDType = getRequest().getParameter("IDType");
		String sex = getRequest().getParameter("sex");
		String mobileNO = getRequest().getParameter("mobileNO");
		map.put("status", "error");
		if (StringUtil.isEmpty(realName)) {
			map.put("msg", "请输入投保人姓名");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}
		if (StringUtil.isEmpty(IDType)) {
			map.put("msg", "请选择证件类型");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}
		if (StringUtil.isEmpty(IDNO)) {
			map.put("msg", "请输入证件号码");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}
		if (StringUtil.isEmpty(sex)) {
			map.put("msg", "请选择性别");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}
		if (StringUtil.isEmpty(mobileNO)) {
			map.put("msg", "请输入手机号码");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}
		
		QueryBuilder qb = new QueryBuilder("insert into sdactivitymember (ID, mobileNO, realName, IDType, IDNO, sex, createDate) values(?, ?, ?, ?, ?, ?, ?)");
		Md5security md5 = new Md5security();
		md5.md5s((realName+"-"+mobileNO+"-"+DateUtil.getCurrentDate()), "UTF-8");
		qb.add(md5.str);
		qb.add(mobileNO);
		qb.add(realName);
		qb.add(IDType);
		qb.add(IDNO);
		qb.add(sex);
		qb.add(DateUtil.getCurrentDateTime());
		int count = qb.executeNoQuery();
		if (count == 0) {
			map.put("msg", "领取失败！请您重新提交或联系客服！");
		} else {
			map.put("status", "success");
			map.put("msg", "领取成功！");
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return ajax(jsonObject.toString(), "text/html");
	}
}
