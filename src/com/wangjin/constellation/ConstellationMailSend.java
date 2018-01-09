package com.wangjin.constellation;

import cn.com.sinosoft.common.email.MessageConstant;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ConstellationMailSend extends ConfigEanbleTaskManager { 
	public static final String CODE = "com.wangjin.constellation.ConstellationMailSend";

	public boolean isRunning(long id) {
		return false;
	}

	/**
	 * 
	 * @Title: checkConstellation
	 * @Description: TODO(生日当天发送星座邮件)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	public boolean checkConstellation() {
		String sql = "SELECT applicantmail,applicantname, applicantsexname,DATE_FORMAT(applicantBirthday,'%m-%d') as applicantBirthday FROM sdrelationappnt WHERE DATE_FORMAT(applicantBirthday,'%m-%d')=DATE_FORMAT(NOW(),'%m-%d')";
		QueryBuilder querybuilder = new QueryBuilder(sql);
		DataTable dt = querybuilder.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String mail = String.valueOf(dt.get(i, 0));
				if ("".equals(mail) || mail == null) {
					logger.warn("投保人为{}的邮箱为空", String.valueOf(dt.get(i, 1)));
				} else {
					//获取星座
					String[] birthday=String.valueOf(dt.get(i, 3)).split("-");
					Map<String,String> map=getAstro(Integer.parseInt(birthday[0]),Integer.parseInt(birthday[1]));
					if("".equals(map.get("content"))||map.get("content")==null){
						return false;
					}
					// 发送邮件
					if (this.postEmail(mail,map.get("title"), String.valueOf(dt.get(i, 1)),String.valueOf(dt.get(i, 2)),map.get("content"),map.get("image"))) {
						logger.info("邮件发送成功");
					}
				}
			}
			return true;
		} else {
			logger.info("没有需要发送星座祝福的邮件");
			return false;
		}
	}

	/**
	 * 
	 * @Title: postEmail
	 * @Description: TODO(星座祝福发送邮件)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	public boolean postEmail(String mail, String title, String name, String sex, String content,String image) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			title = java.net.URLDecoder.decode(title, "utf-8");
			data.put("title", title);
			data.put(MessageConstant.PARAM_SUBJECT_NAME, title);
			// 用户姓名
			data.put("name", java.net.URLDecoder.decode(name, "utf-8"));
			// 用户性别
			if("男".equals(sex)){
				data.put("sex", java.net.URLDecoder.decode("先生", "utf-8"));
			}else{
				data.put("sex", java.net.URLDecoder.decode("女士", "utf-8"));
			}
			// 星座描述内容
			data.put("content", java.net.URLDecoder.decode(content, "utf-8"));
			// 星座图片
			data.put("imagepath", Config.getValue("FrontServerContextPath")+"/wj/wwwroot/kxb/images/shengri/"+image);
			data.put("serverpath", Config.getValue("FrontServerContextPath")+"/wj/wwwroot/kxb/images/shengri/");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		// 发邮件
		if (ActionUtil.sendMail("wj00092", mail, data)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: getAstro
	 * @Description: TODO(获取星座)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private Map<String, String> getAstro(int month, int day) {
		String[] starArr = { "魔羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座" };
		int[] DayArr = { 19, 18, 20, 20, 20, 21, 22, 22, 22, 22, 21, 21 }; // 两个星座分割日
		String[] contentArr = { "", "大师说'崇尚自由，充满好奇心，具有优秀的推理力和创造力是水瓶座人的特点。客观、冷静，善于思考是水瓶座人的优点' 今天是你的生日，在这个属于你的精彩日子里，开心保送上最诚挚的祝福，祝你生日快乐！愿你生活如意，工作顺利！",
				"双鱼座的人天真纯朴、有自我牺牲精神，对朋友充满温暖和鼓励，是良师也是挚友。我们合作后，正是双鱼座的您的厚爱肯定了开心保的服务，肯定了开心保的诚信，也是您的赞扬肯定开心保的创造。在你生日这一天，开心保送上最真挚的祝福，愿你事事顺利，阖家幸福。",
				"对新鲜事物很投入，并且勇于冒险，追求速度，热情冲动、爱冒险、慷慨、天不怕地不怕，这些优点都属于白羊座的人。对于有这么多有的人，开心保说'每一年，都有这样一个特殊的日子，完全属于你。因为这一天，是你的生日。请收下开心保真心的祝福，愿你未来的日子更加快乐健康平安如意。生日快乐！'",
				"金牛座人拥有普通人不具有的艺术细胞，具有高度的艺术品味及鉴赏能力，而且还是天生的……吃货，哦，不，是美食家。在你生日这一天，开心保把一份开心、一份欣喜送给特别日子里特别的你：祝生日快乐，健康幸福!",
				"头脑灵敏，推理能力强人，多才多艺且生气蓬勃，深受异性垂青的是哪些人呢？是双子座的人！我们知道，只有懂得生活的双子座人,才能领略人生的美丽，只有懂得爱的双子座人,才能领略到心中的温情。今天我们祝愿您有一个特别的生日",
				"热爱事业，重情爱家，珍惜爱情，真诚待友。在生活中有包容心，不会为了一点芝麻小事而耿耿于怀，具有容人的雅量。这么好的人是谁呢？就是巨蟹座的您！今天是您的生日，在这个特别的日子里,我们把最真的祝福送给你。虽然只是短短几句话，但我们希望能给您带来一个心中的美丽世界。生日快乐!", "", "", "", "", "", };
		String[] imagesArr={"","q_07.jpg","11_07.jpg","10_07.jpg","9_07.jpg","8_07.jpg","7_07.jpg","","","","",""};
		int index = month;
		// 所查询日期在分割日之前，索引-1，否则不变
		if (day <= DayArr[month - 1]) {
			index = index - 1;
		} else if (month == 12) {
			index = 0;
		}
		// 返回索引指向的星座string
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", starArr[index]);
		map.put("content", contentArr[index]);
		map.put("image", imagesArr[index]);
		return map;
	}

	/**
	 * 定时任务调用主方法
	 * 
	 * @param id
	 */
	public void execute(long id) {
		if ("0".equals(id + "")) {
			if (checkConstellation()) {
				logger.info("星座邮件发送成功！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "生日星座发送邮件定时任务");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "生日星座发送邮件定时任务";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendItem#getID()
	 */
	@Override
	public String getID() {
		return "com.wangjin.constellation.ConstellationMailSend";
	}
}
