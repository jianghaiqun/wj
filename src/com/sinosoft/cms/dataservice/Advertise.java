package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCAdPositionSchema;
import com.sinosoft.schema.ZCAdvertisementSchema;
import com.sinosoft.schema.ZCAdvertisementSet;
import com.sinosoft.schema.ZCImageSchema;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Advertise extends Page {
	public static Mapx ADTypes = new Mapx();
	static {
		ADTypes.put("image", "图片");
		ADTypes.put("flash", "动画");
		ADTypes.put("text", "文本");
		ADTypes.put("code", "代码");
		ADTypes.put("", "无广告");
	}

	public static Mapx init(Mapx params) {
		String PosID = params.getString("PosID");
		params.put("PositionID", PosID);
		params.put("PositionName", new QueryBuilder("select PositionName from zcadposition where ID=?", Long.parseLong(PosID)).executeString());
		params.put("PosName", params.getString("PositionName"));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String PosID = (String) dga.getParam("PosID");
		String SearchContent = (String) dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder(
				"select id,AdName,ADType,(select b.PositionName from zcadposition b where b.id=zcadvertisement.positionid) PositionName,case isopen when 'Y' then '√' else '' end isopen,HitCount,OrderFlag,AddTime from zcadvertisement ");
		qb.append(" where PositionID=?", Long.parseLong(PosID));
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" and AdName like ?", "%" + SearchContent.trim() + "%");
		}
		qb.append(" order by OrderFlag desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("ADType", ADTypes);
		dga.bindData(dt);
	}

	public static Mapx DialogInit(Mapx params) {
		String id = (String) params.get("ID");
		String type = (String) params.get("Type");
		String PosID = params.getString("PosID");
		String PositionID = params.getString("PositionID");
		if (!StringUtil.isEmpty(PosID)) {
			if (!"New".equals(type)) {
				id = new QueryBuilder("select ID from zcadvertisement where PositionID=? and IsOpen='Y'", Long.parseLong(PosID)).executeOneValue() + "";
				if (StringUtil.isEmpty(id) || id.equals("null")) {
					id = "";
				}
			}
			PositionID = PosID;
		}
		String PositionType = new QueryBuilder("select PositionType from zcadposition where ID=?", Long.parseLong(PositionID)).executeString();
		// 修改
		if (StringUtil.isNotEmpty(id)) {
			ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
			ad.setID(id);
			ad.fill();
			PositionID = ad.getPositionID() + "";
			String StartTime = ad.getStartTime() + "";
			String EndTime = ad.getEndTime() + "";
			if (StringUtil.isNotEmpty(StartTime)) {
				params.put("StartDate", StartTime.substring(0, 10));
				params.put("STime", StartTime.substring(11, 19));
			}
			if (StringUtil.isNotEmpty(EndTime)) {
				params.put("EndDate", EndTime.substring(0, 10));
				params.put("ETime", EndTime.substring(11, 19));
			}
			params.putAll(ad.toMapx());
			if (PositionType.equals("code")) {
				params.put("AdContent", "");
				params.put("codeContent", ad.getAdContent());
			}
		}
		params.put("PositionID", PositionID);
		params.put("PositionType", PositionType);
		params.put("PositionName", new QueryBuilder("select PositionName from zcadposition where ID=?", Long.parseLong(PositionID)).executeString());
		params.put("PositionTypeName", AdvertiseLayout.PosTypes.get(PositionType));
		params.put("imgADLinkUrl", "http://");
		params.put("textLinkUrl", "http://");
		params.put("showAlt", "Y");
		if (PositionType.equalsIgnoreCase("imagechange") || PositionType.equalsIgnoreCase("imagelist")) {
			params.put("AdTypeOptions", "<span value='image' selected>图片</span>");
		} else if (PositionType.equalsIgnoreCase("fixure") || PositionType.equalsIgnoreCase("float") || PositionType.equalsIgnoreCase("couplet")) {
			params.put("AdTypeOptions", "<span value='image' selected>图片</span><span value='flash'>动画</span>");
		} else if (PositionType.equalsIgnoreCase("code")) {
			params.put("AdTypeOptions", "<span value='code' selected>代码</span>");
		} else if (PositionType.equalsIgnoreCase("banner") || PositionType.equalsIgnoreCase("wap_banner")) {
			params.put("AdTypeOptions", "<span value='image' selected>图片</span><span value='flash'>动画</span>");
		} else if (PositionType.equalsIgnoreCase("text")) {
			params.put("AdTypeOptions", "<span value='text' selected>文本</span>");
		}
		params.put("UploadFilePath", (Config.getValue("StaticResourcePath")) + "/");
		return params;
	}

	public void getImgSrc() {
		String ImgID = $V("ImgID");
		ZCImageSchema image = new ZCImageSchema();
		image.setID(ImgID);
		if (image.fill()) {
			this.Response.put("ImgSrc", (image.getPath() + image.getSrcFileName()).replaceAll("//", "/"));
		} else {
			return;
		}
	}

	public void start() {
		String ID = $V("ID");
		if (!StringUtil.isEmpty(ID)) {
			ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
			ad.setID(ID);
			ad.fill();
			ZCAdPositionSchema adp = new ZCAdPositionSchema();
			adp.setID(ad.getPositionID());
			adp.fill();
			if (!adp.getPositionType().equals("text") && !adp.getPositionType().equals("imagelist") && !adp.getPositionType().equals("imagechange")) {
				new QueryBuilder("update zcadvertisement set IsOpen = 'N' where positionid=?", ad.getPositionID()).executeNoQuery();
			}
			if (ad.getIsOpen().equals("Y")) {
				ad.setIsOpen("N");
			} else {
				ad.setIsOpen("Y");
			}
			ad.update();
			if (CreateJSCode(ad, adp)) {
				Response.setLogInfo(1, "操作成功！");
			} else {
				Response.setLogInfo(0, "操作失败！");
			}
		}
	}

	/**
	 * 对文章进行排序,文章默认排序值是时间戳
	 */
	public void sortAdvertise() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String positionID = $V("PositionID");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		Transaction tran = new Transaction();
		OrderUtil.updateOrder("ZCAdvertisement", "OrderFlag", type, target, orders, " PositionID=" + positionID, tran);
		if (tran.commit()) {
			CreateJSCode(positionID);
			Response.setMessage("操作成功");
		} else {
			Response.setError("操作失败");
		}
	}

	public void add() {
		String id = $V("ID");
		// 修改
		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		ZCAdPositionSchema adp = new ZCAdPositionSchema();
		adp.setID($V("PositionID"));
		adp.fill();
		if (StringUtil.isNotEmpty(id)) {
			ad.setID(id);
			ad.fill();
			ad.setModifyUser(User.getUserName());
			ad.setModifyTime(new Date());
		} else {
			// 增加
			ad.setID(NoUtil.getMaxID("AdvertiseID"));
			ad.setAddUser(User.getUserName());
			ad.setAddTime(new Date());
			if (new QueryBuilder("select Count(*) from ZCAdvertisement where PositionID=?", adp.getID()).executeInt() == 0 || $V("addType").equals("layout") || adp.getPositionType().equals("text")
					|| adp.getPositionType().equals("imagechange") || adp.getPositionType().equals("imagelist")) {
				ad.setIsOpen("Y");
			} else {
				ad.setIsOpen("N");
			}
		}
		String StartTime = "";
		String EndTime = "";
		if (StringUtil.isNotEmpty($V("StartDate"))) {
			StartTime += $V("StartDate") + " ";
			if (StringUtil.isNotEmpty($V("STime"))) {
				StartTime += $V("STime");
			} else {
				StartTime += "00:00:00";
			}
		} else {
			StartTime = DateUtil.getCurrentDateTime();
		}
		if (StringUtil.isNotEmpty($V("EndDate"))) {
			EndTime += $V("EndDate") + " ";
			if (StringUtil.isNotEmpty($V("ETime"))) {
				EndTime += $V("ETime");
			} else {
				EndTime += "23:59:59";
			}
		} else {
			EndTime = "2999-12-31 23:59:59";
		}
		ad.setStartTime(DateUtil.parseDateTime(StartTime, "yyyy-MM-dd hh:mm:ss"));
		ad.setEndTime(DateUtil.parseDateTime(EndTime, "yyyy-MM-dd hh:mm:ss"));
		ad.setPositionID(adp.getID());
		ad.setPositionCode(adp.getCode());
		ad.setSiteID(Application.getCurrentSiteID());
		ad.setAdName(StringUtil.htmlEncode($V("AdName")));
		ad.setAdType($V("AdType"));
		ad.setIsHitCount("N");
		ad.setHitCount(0);
		ad.setOrderFlag(OrderUtil.getDefaultOrder());
		/**
		 * 根据选择的广告类型，分别处理
		 */
		// 图片
		if ("image".equals(ad.getAdType())) {
			if (adp.getPositionType().equals("imagechange") || adp.getPositionType().equals("imagelist")) {
				ad = imagesAD(ad, adp);
			} else {
				ad = imageAD(ad, adp);
			}
		}
		// 动画
		if ("flash".equals(ad.getAdType())) {
			ad = flashAD(ad, adp);
		}
		// 文本
		if ("text".equals(ad.getAdType())) {
			ad = textAD(ad);
		}
		// 代码
		if ("code".equals(ad.getAdType())) {
			ad = codeAD(ad);
		}
		// 一个版位只允许同时开通一个广告
		boolean flag = true;
		try {
			if (StringUtil.isNotEmpty(StartTime)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date start;
				start = df.parse(StartTime);
				Date now = new Date();
				if (start.getTime() > now.getTime()) {
					flag = false;
				}
			}
			if (StringUtil.isNotEmpty(EndTime)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date end = df.parse(EndTime);
				Date now = new Date();
				if (end.getTime() < now.getTime()) {
					flag = false;
				}
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		if (StringUtil.isNotEmpty(id)) {
			if (ad.update()) {
				if (flag && !adp.getPositionType().equals("code")) {
					CreateJSCode(ad, adp);
				}
				Response.setLogInfo(1, "修改广告成功!");
			} else {
				Response.setLogInfo(0, "修改广告发生错误!");
			}
		} else { // 新增
			if (ad.insert()) {
				if (flag && !adp.getPositionType().equals("code")) {
					CreateJSCode(ad, adp);
				}
				Response.setLogInfo(1, "新增广告成功!");
			} else {
				Response.setLogInfo(0, "新增广告发生错误!");
			}
		}
	}

	/**
	 * 增加或修改图片列表、图片轮换广告
	 * 
	 * @param ad
	 */
	public ZCAdvertisementSchema imagesAD(ZCAdvertisementSchema ad, ZCAdPositionSchema adp) {
		String[] Urls = StringUtil.splitEx($V("imgADLinkUrl"), "^");
		String[] Alts = StringUtil.splitEx($V("imgADAlt"), "^");
		String[] Paths = StringUtil.splitEx($V("ImgPath"), "^");
		String showAlt = $V("showAlt");
		if (StringUtil.isEmpty(showAlt) || !showAlt.equals("Y")) {
			showAlt = "N";
		}
		String ContentStr = "{'imgID':'" + ad.getID() + "','imgADLinkUrl':'" + Urls[0] + "','imgADAlt':'" + StringUtil.htmlEncode(Alts[0]) + "','ImgPath':'" + Paths[0] + "','imgADLinkTarget':'"
				+ $V("imgADLinkTarget") + "','showAlt':'" + showAlt + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	/**
	 * 增加或修改图片广告
	 * 
	 * @param ad
	 */
	public ZCAdvertisementSchema imageAD(ZCAdvertisementSchema ad, ZCAdPositionSchema adp) {
		String[] Urls = StringUtil.splitEx($V("imgADLinkUrl"), "^");
		String[] Alts = StringUtil.splitEx($V("imgADAlt"), "^");
		String[] Paths = StringUtil.splitEx($V("ImgPath"), "^");
		String showAlt = $V("showAlt");
		if (StringUtil.isEmpty(showAlt) || !showAlt.equals("Y")) {
			showAlt = "N";
		}
		String ContentStr = "{'Images':[";
		for (int i = 0; i < Paths.length; i++) {
			ContentStr += "{'imgADLinkUrl':'" + Urls[i] + "','imgADAlt':'" + StringUtil.htmlEncode(Alts[i]) + "','ImgPath':'" + Paths[i] + "'}";
			if (i != Paths.length - 1) {
				ContentStr += ",";
			}
		}
		ContentStr += "],'imgADLinkTarget':'" + $V("imgADLinkTarget") + "','Count':'" + Paths.length + "','showAlt':'" + showAlt + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	/**
	 * 增加或修改动画广告
	 * 
	 * @param ad
	 */
	public ZCAdvertisementSchema flashAD(ZCAdvertisementSchema ad, ZCAdPositionSchema adp) {
		String[] Paths = StringUtil.splitEx($V("SwfFilePath"), "^");
		String ContentStr = "{'Flashes':[";
		for (int i = 0; i < Paths.length; i++) {
			ContentStr += "{'SwfFilePath':'" + Paths[i] + "'}";
			if (i != Paths.length - 1) {
				ContentStr += ",";
			}
		}
		ContentStr += "],'Count':'" + Paths.length + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	/**
	 * 增加或修改文字广告
	 * 
	 * @param ad
	 */
	public ZCAdvertisementSchema textAD(ZCAdvertisementSchema ad) {
		String textContent = StringUtil.htmlEncode($V("textContent"));
		String textLinkUrl = $V("textLinkUrl");
		String TextColor = $V("TextColor");
		if (TextColor.equals("^")) {
			TextColor = "";
		}
		String ContentStr = "{'textID':'" + ad.getID() + "','textContent':'" + textContent + "','textLinkUrl':'" + textLinkUrl + "','TextColor':'" + TextColor + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	/**
	 * 增加或修改代码广告
	 * 
	 * @param ad
	 */
	public ZCAdvertisementSchema codeAD(ZCAdvertisementSchema ad) {
		String ContentStr = $V("codeContent");
		ad.setAdContent(ContentStr);
		return ad;
	}

	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		ZCAdvertisementSet set = ad.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void copy() {
		String id = $V("ID");
		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		ad.setID(Long.parseLong(id));
		ad.fill();
		String AdName = ad.getAdName();
		AdName = "复制  " + AdName;
		ad.setID(NoUtil.getMaxID("AdvertiseID"));
		ad.setAddUser(User.getUserName());
		ad.setAddTime(new Date());
		ad.setAdName(AdName);
		ad.setIsOpen("Y");
		if (ad.insert()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public static String getJson(String PositionID) {
		StringBuffer sb = new StringBuffer();
		sb.append("");
		if (StringUtil.isNotEmpty(PositionID)) {
			ZCAdPositionSchema adp = new ZCAdPositionSchema();
			adp.setID(PositionID);
			adp.fill();
			if (adp.getPositionType().equals("text") || adp.getPositionType().equals("imagelist") || adp.getPositionType().equals("imagechange")) {
				String ADContent = "";
				if (adp.getPositionType().equals("text")) {
					ADContent += "{'ADText':[";
				} else {
					ADContent += "{'ADImage':[";
				}
				QueryBuilder qb = new QueryBuilder("where  IsOpen='Y' and PositionID=" + adp.getID() + " and StartTime<=? and EndTime>=? order by OrderFlag desc", new Date(), new Date());
				ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
				for (int i = 0; i < aset.size(); i++) {
					ADContent += aset.get(i).getAdContent();
					if (i != aset.size() - 1) {
						ADContent += ",";
					}
				}
				ADContent += "]}";
				sb.append(ADContent);
			} else {
				ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
				String adID = new QueryBuilder("select ID from ZCAdvertisement where  IsOpen='Y' and PositionID=" + adp.getID() + " and StartTime<=? and EndTime>=? order by OrderFlag desc",
						new Date(), new Date()).executeString();
				if (StringUtil.isNotEmpty(adID)) {
					ad.setID(adID);
					ad.fill();
					sb.append(ad.getAdContent());
				}
			}
		}
		return sb.toString();
	}

	public static boolean CreateJSCode(String PositionID) {
		ZCAdPositionSchema adp = new ZCAdPositionSchema();
		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		Date now = new Date();
		adp.setID(PositionID);
		adp.fill();
		QueryBuilder qb = new QueryBuilder("select ID from ZCAdvertisement where PositionID=? and StartTime<=? and EndTime>=? order by OrderFlag asc");
		qb.add(adp.getID());
		qb.add(now);
		qb.add(now);
		String AdID = qb.executeString();
		if (StringUtil.isEmpty(AdID) || AdID.equalsIgnoreCase("null")) {
			File f = new File(Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(adp.getSiteID()) + "/" + adp.getJsName());
			if (f.exists()) {
				f.delete();
				FileUtil.writeText(f.getPath(), "document.write('广告内容已过期，请管理员更换');");
			}
		} else {
			ad.setID(AdID);
			ad.fill();
			Advertise.CreateJSCode(ad, null);
		}
		return true;
	}

	public static boolean CreateJSCode(ZCAdvertisementSchema adv, ZCAdPositionSchema adp) {
		ArrayList deployList = new ArrayList();
		File file = null;
		StringBuffer sb = new StringBuffer();
		ZCAdPositionSchema adposition = new ZCAdPositionSchema();
		String OutString = "";
		String TempPath = Config.getContextRealPath() + "DataService/ADTemplate/";
		if (adp == null) {
			adposition.setID(adv.getPositionID());
			adposition.fill();
		} else {
			adposition = (ZCAdPositionSchema) adp.clone();
		}

		if (adposition.getPositionType().equals("wap_banner")) { // 0、WAP-矩形横幅
			file = new File(TempPath + "ZCMSAD_Wap_Banner.js");
		} else if (adposition.getPositionType().equals("banner")) { // 1、矩形横幅
			file = new File(TempPath + "ZCMSAD_Banner.js");
		} else if (adposition.getPositionType().equals("fixure")) {// 2、固定位置
			file = new File(TempPath + "ZCMSAD_Fixure.js");
		} else if (adposition.getPositionType().equals("float")) {// 3、漂浮移动
			file = new File(TempPath + "ZCMSAD_Float.js");
			sb.append(" \n");
			sb.append("function cmsAD_" + adposition.getID() + "_pause_resume(){if(cmsAD_" + adposition.getID() + ".Pause){clearInterval(cmsAD_" + adposition.getID() + ".Interval);cmsAD_"
					+ adposition.getID() + ".Pause = false;}");
			sb.append("else {cmsAD_" + adposition.getID() + ".Interval = setInterval(cmsAD_" + adposition.getID() + ".Start(cmsAD_" + adposition.getID() + "),cmsAD_" + adposition.getID()
					+ ".Delay);cmsAD_" + adposition.getID() + ".Pause = true;}}");
		} else if (adposition.getPositionType().equals("couplet")) {// 4、对联广告
			file = new File(TempPath + "ZCMSAD_Couplet.js");
		} else if (adposition.getPositionType().equals("imagechange")) {
			file = new File(TempPath + "ZCMSAD_ImageChange.js");
		} else if (adposition.getPositionType().equals("imagelist")) {
			file = new File(TempPath + "ZCMSAD_ImageList.js");
		} else if (adposition.getPositionType().equals("text")) { // 5、文字广告
			file = new File(TempPath + "ZCMSAD_Text.js");
		} else if (adposition.getPositionType().equals("code")) {
			return true;
		}
		OutString = FileUtil.readText(file);

		sb.append(" \n");
		String Services = Config.getValue("ServicesContext");
		sb.append("var cmsAD_" + adposition.getID() + " = new ZCMSAD('cmsAD_" + adposition.getID() + "'); \n");
		sb.append("cmsAD_" + adposition.getID() + ".PosID = " + adposition.getID() + "; \n");
		sb.append("cmsAD_" + adposition.getID() + ".ADID = " + adv.getID() + "; \n");
		sb.append("cmsAD_" + adposition.getID() + ".ADType = \"" + adv.getAdType() + "\"; \n");
		sb.append("cmsAD_" + adposition.getID() + ".ADName = \"" + adv.getAdName() + "\"; \n");
		if (adposition.getPositionType().equals("text") || adposition.getPositionType().equals("imagelist") || adposition.getPositionType().equals("imagechange")) {
			String ADContent = "";
			if (adposition.getPositionType().equals("text")) {
				ADContent += "{'ADText':[";
			} else {
				ADContent += "{'ADImage':[";
			}
			QueryBuilder qb = new QueryBuilder("where  IsOpen='Y' and PositionID = " + adposition.getID() + " and StartTime<=? and EndTime>=? order by OrderFlag desc", new Date(), new Date());
			ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
			if(aset.size()>0){
				for (int i = 0; i < aset.size(); i++) {
					ADContent += aset.get(i).getAdContent();
					if (i != aset.size() - 1) {
						ADContent += ",";
					}
				}
			}else{
				QueryBuilder qb1 = new QueryBuilder("where  IsOpen='Y' and PositionID = " + adposition.getID() + " and EndTime<=NOW() order by EndTime asc limit 1");
				ZCAdvertisementSet aset1 = new ZCAdvertisementSchema().query(qb1);
				if(aset1.size()>0){
					ADContent += aset1.get(0).getAdContent();
				}
			}
			
			ADContent += "]}";
			sb.append("cmsAD_" + adposition.getID() + ".ADContent = \"" + ADContent + "\"; \n");
		} else {
			String ADContent = "";
			QueryBuilder qb = new QueryBuilder("where  IsOpen='Y' and PositionID = " + adposition.getID() + " and StartTime<=? and EndTime>=? order by OrderFlag desc", new Date(), new Date());
			ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
			if(aset.size()>0){
				ADContent += aset.get(0).getAdContent();
			}else{
				QueryBuilder qb1 = new QueryBuilder("where  IsOpen='Y' and PositionID = " + adposition.getID() + " and EndTime<=NOW() order by EndTime desc limit 1");
				ZCAdvertisementSet aset1 = new ZCAdvertisementSchema().query(qb1);
				if(aset1.size()>0){
					ADContent += aset1.get(0).getAdContent();
				}
			}
			sb.append("cmsAD_" + adposition.getID() + ".ADContent = \"" + ADContent + "\"; \n");
		}
		sb.append("cmsAD_" + adposition.getID() + ".URL = \"" + Services + "/ADClick.jsp\"; \n");
		sb.append("cmsAD_" + adposition.getID() + ".SiteID = " + adposition.getSiteID() + "; \n");
		if (adposition.getPositionType().equals("fixure") || adposition.getPositionType().equals("float") || adposition.getPositionType().equals("couplet")) {
			sb.append("cmsAD_" + adposition.getID() + ".PaddingLeft = " + adposition.getPaddingLeft() + "; \n");
			sb.append("cmsAD_" + adposition.getID() + ".PaddingTop = " + adposition.getPaddingTop() + "; \n");
		}
		if (adposition.getPositionType().equals("fixure") || adposition.getPositionType().equals("couplet")) {
			sb.append("cmsAD_" + adposition.getID() + ".Scroll = '" + adposition.getScroll() + "'; \n");
			if (adposition.getPositionType().equals("fixure")) {
				sb.append("cmsAD_" + adposition.getID() + ".Align = '" + adposition.getAlign() + "'; \n");
			}
		}
		if (!adposition.getPositionType().equals("wap_banner")) { 
			sb.append("cmsAD_" + adposition.getID() + ".Width = " + adposition.getPositionWidth() + "; \n");
			sb.append("cmsAD_" + adposition.getID() + ".Height = " + adposition.getPositionHeight() + "; \n");
		}
		String imagePath = Config.getValue("StaticResourcePath");
		if (!imagePath.endsWith("/")) {
			imagePath += "/";
		}
		/*String siteUrl = SiteUtil.getURL(adposition.getSiteID());
		if (siteUrl.equals("http://")) {
			imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(adposition.getSiteID());
			
		} else {
			imagePath = siteUrl;
			if (!imagePath.endsWith("/")) {
				imagePath += "/";
			}
		}*/

		sb.append("cmsAD_" + adposition.getID() + ".UploadFilePath = \"" + imagePath + "\"; \n");
		if (adposition.getPositionType().equals("text") || adposition.getPositionType().equals("imagelist")) {
			sb.append("cmsAD_" + adposition.getID() + ".ShowAD();\n");
		} else {
			if (adv.getIsOpen().equals("Y")) {
				sb.append("cmsAD_" + adposition.getID() + ".ShowAD();\n");
				if (adposition.getPositionType().equals("float")) {
					sb.append("document.getElementById('ZCMSAD_" + adposition.getID() + "').visibility = 'visible';\n");
					sb.append("cmsAD_" + adposition.getID() + ".Interval = setInterval(cmsAD_" + adposition.getID() + ".Start(cmsAD_" + adposition.getID() + "),cmsAD_" + adposition.getID()
							+ ".Delay);");
				} else if (adposition.getPositionType().equals("couplet")) {
					sb.append("cmsAD_" + adposition.getID() + ".Start(cmsAD_" + adposition.getID() + ".Scroll);\n");
				} else if (adposition.getPositionType().equals("imagechange")) {
					sb.append("ldh.on(window,'load',function (){\n");
					sb.append("shower.init({box:'ZCMSAD_" + adposition.getID() + "_Box',tip:'ZCMSAD_" + adposition.getID() + "_Tip'});})\n");
					sb.append("displayAlt(cmsAD_" + adposition.getID() + ".ADContent,'ZCMSAD_" + adposition.getID() + "_Tip');");
				}
			}
		}
		OutString += sb.toString();
		String positionPath = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(adposition.getSiteID()) + "/"
				+ adposition.getJsName().substring(0, adposition.getJsName().lastIndexOf("/")) + "/";
		File f = new File(positionPath);
		if (!f.exists()) {
			FileUtil.mkdir(positionPath);
		}
		
		String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(adposition.getSiteID()) + "/" + adposition.getJsName();
		
		InputStream in = new ByteArrayInputStream(StringUtil.noNull(OutString).getBytes());
		
		try {
			OSSUploadFile.upload(in, path);
			in.close();
		} catch (Exception e) {
			logger.error("广告管理远程上传JS文件异常！！！" + e.getMessage(), e);
		}
		
		// 将js分发到远程服务器上
		/*deployList.add(Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(adposition.getSiteID()) + "/" + adposition.getJsName());
		Deploy d = new Deploy();
		d.addJobs(adposition.getSiteID(), deployList);*/
		
		return true;
	}
}
