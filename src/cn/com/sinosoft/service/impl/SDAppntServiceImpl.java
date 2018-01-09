package cn.com.sinosoft.service.impl;


import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.dao.SDAppntDao;
import cn.com.sinosoft.entity.SDAppnt;
import cn.com.sinosoft.service.SDAppntService;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.memberSchema;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Service实现类 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA46293E39B40E5C54C6BC841B973A701
 * ============================================================================
 */

@Service
public class SDAppntServiceImpl extends BaseServiceImpl<SDAppnt, String> implements SDAppntService {

	@Resource
	private SDAppntDao sdAppntDao;
	public void setBaseDao(SDAppntDao tSDAppntDao) {
		super.setBaseDao(tSDAppntDao);
	}
	@Override
	public void userRegisted(SDAppnt cSDAppnt,HttpServletRequest request)
	{
		//判断手机号是否已注册，如果已注册，则跳过自动注册
		int tCount = new QueryBuilder(" SELECT COUNT(1) FROM member WHERE mobileno=?",cSDAppnt.getAppntMobile()).executeInt();
		if(tCount>=1){
			return;
		}
		String email = cSDAppnt.getAppntEmail();
		Transaction trans = new Transaction();
		memberSchema members = new memberSchema();
		double a = (2 / 20.0) * 100;
		DecimalFormat myformat = new DecimalFormat("#####0");
		String fullDegree = myformat.format(a) + "%";
		long menberID = NoUtil.getMaxID("MemberID");
		members.setid(menberID + "");
		members.setcreateDate(new Date());
		members.setmodifyDate(new Date());
		members.setfullDegree(fullDegree);
		String password = randomPW();
		members.setpassword(DigestUtils.md5Hex(password));
		members.setVIPFrom("0");
		members.setsafeQuestion(null);
		members.setsafeAnswer(null);
		//CMS报表设定RegisterType的值为2时认为是自动注册。
		members.setregisterType("2");
		members.setmemberRank_id(getDefaultMemberRank());
		if (StringUtil.isEmpty(email)) {
			members.setisEmailBinding("N");
		} else {
			members.setisEmailBinding("Y");
		}
		members.setisMobileNOBinding("Y");
		members.setpoint(0);
		members.setdeposit(new BigDecimal("0"));
		members.setisAccountEnabled("Y");
		members.setisAccountLocked("N");
		members.setloginFailureCount(0);
		members.setpasswordRecoverKey(null);
		members.setlockedDate(null);
		members.setregisterIp(request.getRemoteAddr());
		members.setloginIp(request.getRemoteAddr());
		members.setusedPoint(0);
		members.setcurrentValidatePoint(0);
		members.setexpiricalValue(0);
		members.setemail(email);
		members.setmobileNO(cSDAppnt.getAppntMobile());
		members.setusername(cSDAppnt.getAppntMobile());
		members.setverifyEmailSendDate(new Date());
		members.setFromWap("wj");
		members.setgrade("K0");
		members.setvipFlag("N");
		
		trans.add(members, Transaction.INSERT);

		if (trans.commit()) {
			try {
				if(StringUtil.isNotEmpty(cSDAppnt.getAppntEmail())){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("username", cSDAppnt.getAppntMobile());
					map.put("PassWord", password);
					String realName = "尊敬的"+cSDAppnt.getAppntName()+"先生/女士";
					map.put("SOSMemberRealName", realName);
					map.put("descInfo", "感谢您参与开心保网联合中英人寿、国际SOS援助中心推出的“SOS国内医疗援助”赠送活动。");
					map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
					ActionUtil.sendMail("wj00201", cSDAppnt.getAppntEmail(), map);
				}
			} catch (Exception e) {
				logger.error("赠险：用户名：" + email + "发送自动注册用户信息邮件/短信没有成功！" + e.getMessage(), e);
			}
		} else {
			logger.error("赠险：用户名：{}自动注册用户信息没有注册成功！", email);
		}

	
	}
	private String randomPW() {
		Random r = new Random();
		int i = 0;
		while (i < 100000) {
			i = r.nextInt(1000000);
		}
		return String.valueOf(i);
	}
	private String getDefaultMemberRank() {
		String defaultMemberRank_id = "";
		String sql1 = "select * from MemberRank as memberRank where memberRank.isDefault = ?";
		String sql2 = "select * from MemberRank as memberRank order by memberRank.createDate asc";
		QueryBuilder qb = new QueryBuilder(sql1, "0");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			defaultMemberRank_id = dt.getString(0, "id");
		} else {
			qb = new QueryBuilder(sql2);
			dt = qb.executeDataTable();
			defaultMemberRank_id = dt.getString(0, "id");
		}

		return defaultMemberRank_id;
	}
	}