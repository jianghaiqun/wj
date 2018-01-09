package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ZdrecordCps;
import cn.com.sinosoft.service.ZdrecordCpsService;
import cn.com.sinosoft.util.CookieUtil;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.WeixiManage;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCpsAction extends BaseShopAction {

	private static final long serialVersionUID = 4440774113615173246L;
	private String productId;
	private String jumpAddress;
	@Resource
	private ZdrecordCpsService zdrecordCpsService;

	public void recordJump() {
		Member m = getLoginMember();
		try {
			String ip = getRequest().getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getRemoteAddr();
			}
			jumpAddress = getCpsAddress(productId);
			ZdrecordCps zdc = zdrecordCpsService.createZDRecordCPS(productId, ip, m , jumpAddress);
			zdrecordCpsService.save(zdc);
			//维析发送数据，统计cps产品的跳转数目
			try {
				Map<String, String> map_weixi = new HashMap<String, String>();
				// flag 为1 发送page和action数据
				map_weixi.put("flag", "1");
				// 分组名称（及weixitrack.js中的正则表达式的分组名称）
				map_weixi.put("groupname", "touzi_licai");
				// html页面title内容
				map_weixi.put("title", "licai_cps");
				// html页面url
				map_weixi.put("url", "licai_cps.shtml");
				// dataID a1 a0000002 表示支付一览
				map_weixi.put("a1", "a0000002");
				// dataID a2 a0000003 表示支付详细
				map_weixi.put("a2", "a0000003");
				// 会员id
				String memberid = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
				map_weixi.put("memberid",memberid);
				// 商品productid
				map_weixi.put("productid", productId);
				// 商品价格 默认为0
				map_weixi.put("totalamout", "0");
				// 商品数量 默认为1
				map_weixi.put("pieces", "1");
				// 维析所需的客户浏览器cookie的值
				String weixiCookie = "";
				Cookie cookie = CookieUtil.getCookieByName(getRequest(), "vlid_1001");
				if (cookie != null) {
					weixiCookie = cookie.getValue();
				}
				//发送维析请求
				WeixiManage.sendWeixiPaySuccess(weixiCookie,map_weixi);
			} catch (Exception e) {
				logger.error("cps产品跳转时维析统计错误！" + e.getMessage(), e);
			}
			getResponse().sendRedirect(jumpAddress);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private String getCpsAddress(String productId){
		String cpsAddress = "";
		try {
			JdbcTemplateData jtd = new JdbcTemplateData();
			String sql = "select TextValue from zdcolumnvalue where columncode='cpsAdress' " +
					"and relaid in (select relaid from zdcolumnvalue where columncode='RiskCode' " +
					"and textvalue=?)";
			String[] sqltemp = {productId};  
			List<Map> cpsAddressList = jtd.obtainData(sql,sqltemp);  
			if(cpsAddressList!=null && cpsAddressList.size()>0){
				for (int i = 0; i < cpsAddressList.size(); i++) {
					if(StringUtil.isNotEmpty(cpsAddressList.get(i).get("TextValue").toString())){
						cpsAddress = cpsAddressList.get(i).get("TextValue").toString();
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		return cpsAddress;
	}
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getJumpAddress() {
		return jumpAddress;
	}

	public void setJumpAddress(String jumpAddress) {
		this.jumpAddress = jumpAddress;
	}

}
