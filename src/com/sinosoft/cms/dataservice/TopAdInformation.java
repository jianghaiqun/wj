package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.TopAdInfoSchema;
import com.sinosoft.schema.TopAdInfoSet;
import com.sinosoft.schema.TopAdRelaSchema;
import com.sinosoft.schema.ZCAdPositionSchema;
import com.sinosoft.schema.ZCAdvertisementSchema;
import com.sinosoft.schema.ZCAdvertisementSet;
import com.sinosoft.schema.ZCImageSchema;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TopAdInformation extends Page {
	public static Mapx ADTypes = new Mapx();
	static {
		ADTypes.put("image", "图片");
		/*ADTypes.put("flash", "动画");
		ADTypes.put("text", "文本");
		ADTypes.put("code", "代码");
		ADTypes.put("", "无广告");*/
	}

	public static Mapx init(Mapx params) {
		String RelaID = params.getString("RelaID");
		params.put("TopRelaID", RelaID);
		params.put("TopRelaName", new QueryBuilder("select AdSpaceName from TopAdRela where ID=?", Long.parseLong(RelaID)).executeString());
		params.put("RelaName", params.getString("TopRelaName"));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String RelaID = (String) dga.getParam("RelaID");
		String SearchContent = (String) dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder(
				"SELECT ID,AdName,AdType, case Active when 'Y' then '√' else '' end Active,CreateDate  FROM topadinfo");
		qb.append(" where ParentID=?", Long.parseLong(RelaID));
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" and AdName like ?", "%" + SearchContent.trim() + "%");
		}
		qb.append(" order by CreateDate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("ADType", ADTypes);
		dga.bindData(dt);
	}

	public static Mapx DialogInit(Mapx params) {
		String id = (String) params.get("ID");
		String RelaID = params.getString("RelaID");
		String ParentID = params.getString("ParentID");
		if (!StringUtil.isEmpty(RelaID)) {

			ParentID = RelaID;
		}
		String AdSpaceType = new QueryBuilder("select AdSpaceType from topadrela where ID=?", Long.parseLong(ParentID)).executeString();
		// 修改
		if (StringUtil.isNotEmpty(id)) {
			TopAdInfoSchema ad = new TopAdInfoSchema();
			ad.setID(id);
			ad.fill();
			ParentID = ad.getParentID();
			String StartTime = String.valueOf(ad.getStartTime());
			String EndTime = String.valueOf(ad.getEndTime());
			if (StringUtil.isNotEmpty(StartTime)) {
				params.put("StartDate", StartTime.substring(0, 10));
				params.put("STime", StartTime.substring(11, 19));
			}
			if (StringUtil.isNotEmpty(EndTime)) {
				params.put("EndDate", EndTime.substring(0, 10));
				params.put("ETime", EndTime.substring(11, 19));
			}
			params.putAll(ad.toMapx());
		}
		params.put("ParentID", ParentID);
		params.put("AdSpaceType", AdSpaceType);
		params.put("AdSpaceName", new QueryBuilder("select AdSpaceName from topadrela where ID=?", Long.parseLong(ParentID)).executeString());
		params.put("AdSpaceTypeName", AdvertiseManage.getAdSpaceTypeNs(AdSpaceType));
		params.put("AdTypeOptions", "<span value='image' selected>图片</span>");
		params.put("UploadFilePath", Config.getValue("StaticResourcePath")+"/");
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
		String EndTime = "";
		int status = 0;
		if (!StringUtil.isEmpty(ID)) {
			TopAdInfoSchema ad = new TopAdInfoSchema();
			ad.setID(ID);
			ad.fill();
			if(ad.getEndTime()!=null){
				EndTime = String.valueOf(ad.getEndTime()).substring(0,19);
			}
			if (ad.getActive().equals("Y")) {
				ad.setActive("N");
			} else {
				// 一个版位只允许同时开通一个广告
				try {
					int countTime = new QueryBuilder("SELECT count(*) FROM topadinfo WHERE '"+EndTime+"' < NOW() and id =?",ad.getID()).executeInt();
					if(countTime>0){
						status = 1;
					}else{
						
						String sql_prop1="";
						DataTable dt_prop1=new QueryBuilder("SELECT prop1, AdSpaceType FROM  topadrela WHERE id=(SELECT parentid FROM topadinfo WHERE id=?)",ID).executeDataTable();
						if(StringUtil.isNotEmpty(dt_prop1.getString(0,0))){
							sql_prop1=" AND r.prop1 ='popup' ";
						}else{
							sql_prop1=" AND (r.prop1 IS NULL OR r.prop1 = '') ";
						}

						String timeSql = " AND (('" + DateUtil.toDateTimeString(ad.getStartTime()) + "' >= i.starttime and '" + DateUtil.toDateTimeString(ad.getStartTime()) + "' <= i.endtime) or ";
						timeSql += " ('" + DateUtil.toDateTimeString(ad.getEndTime()) + "' >= i.starttime and '" + DateUtil.toDateTimeString(ad.getEndTime()) + "' <= i.endtime) or ";
						timeSql += " ('" + DateUtil.toDateTimeString(ad.getStartTime()) + "' >= i.starttime and '" + DateUtil.toDateTimeString(ad.getEndTime()) + "' <= i.endtime) or ";
						timeSql += " ('" + DateUtil.toDateTimeString(ad.getStartTime()) + "' <= i.starttime and '" + DateUtil.toDateTimeString(ad.getEndTime()) + "' >= i.endtime)) ";
						
						String topAllSql = "SELECT * FROM topadinfo i ,topadrela r "
								+ "WHERE i.parentid = r.id AND i.active='Y' AND LOCATE('topAll', r.adspacetype) "
								+ sql_prop1 + timeSql;
						int topAllCount = new QueryBuilder(topAllSql)
								.executeInt();
						if (topAllCount > 0) {
							status = 2;
						} else {
							String adSpaceType = dt_prop1.getString(0, 1);

							String[] adSpaceTypeArr = adSpaceType.split(",");
							for (String temp : adSpaceTypeArr) {
								String sql = "";

								if ("topAll".equals(temp)) {
									sql = "SELECT * FROM topadinfo i ,topadrela r "
											+ " WHERE i.parentid = r.id AND i.active='Y' "
											+ sql_prop1 + timeSql;
								} else {
									sql = "SELECT * FROM topadinfo i ,topadrela r "
											+ " WHERE i.parentid = r.id AND i.active='Y' AND LOCATE('"
											+ temp
											+ "', r.adspacetype) "
											+ sql_prop1 + timeSql;
								}

								int count = new QueryBuilder(sql).executeInt();
								if (count > 0) {
									status = 2;
									break;
								}
							}
						}
						
						
						/*String topAll = "SELECT i.starttime,i.endtime,r.adspacetype FROM topadinfo i ,topadrela r WHERE i.parentid = r.id AND i.active='Y' AND r.adspacetype = 'topAll' ORDER BY i.starttime "+sql_prop1;
						DataTable dtTopAll = new QueryBuilder(topAll).executeDataTable();
						
						String index = "SELECT i.starttime,i.endtime,r.adspacetype FROM topadinfo i ,topadrela r WHERE i.parentid = r.id AND i.active='Y' AND (r.adspacetype = 'index' or r.adspacetype = 'indexAndList') "+sql_prop1+" ORDER BY i.starttime";
						DataTable dtIndex = new QueryBuilder(index).executeDataTable();
						
						String zixun = "SELECT i.starttime,i.endtime,r.adspacetype FROM topadinfo i ,topadrela r WHERE i.parentid = r.id AND i.active='Y' AND (r.adspacetype = 'zixunList' or r.adspacetype = 'indexAndList') "+sql_prop1+" ORDER BY i.starttime";
						DataTable dtZixun = new QueryBuilder(zixun).executeDataTable();
						
						String carList = "SELECT i.starttime,i.endtime,r.adspacetype FROM topadinfo i ,topadrela r WHERE i.parentid = r.id AND i.active='Y' AND (r.adspacetype = 'carList') "+sql_prop1+"  ORDER BY i.starttime";
						DataTable dtCarList = new QueryBuilder(carList).executeDataTable();
						
						String adType =(String) new QueryBuilder("SELECT AdSpaceType FROM topadrela WHERE id = '"+ad.getParentID()+"'").executeOneValue();
						//网站全站投放top广告位
						if(dtTopAll!=null && dtTopAll.getRowCount()>0 && !"carList".equals(adType)){
							int rowCount = dtTopAll.getRowCount();
							for(int j = 0;j<rowCount;j++){
								Date date1 = DateUtil.parseDateTime(dtTopAll.getString(j, "starttime"), "yyyy-MM-dd hh:mm:ss");
								Date date2 = DateUtil.parseDateTime(dtTopAll.getString(j, "endtime"), "yyyy-MM-dd hh:mm:ss");
								if(ad.getStartTime().getTime()<date2.getTime() && ad.getStartTime().getTime()>date1.getTime()){
									status = 2;
									break;
								}else if(ad.getEndTime().getTime()<date2.getTime() && ad.getEndTime().getTime()>date1.getTime()){
									status = 2;
									break;
								}else if(rowCount>1 && j < rowCount){
									Date date3 = DateUtil.parseDateTime(dtTopAll.getString(j+1, "starttime"), "yyyy-MM-dd hh:mm:ss");
									if(ad.getStartTime().getTime()>date2.getTime() && ad.getStartTime().getTime()<date3.getTime() && ad.getEndTime().getTime()>date3.getTime()){
										status = 2;
										break;
									}
								}
							}
						//仅首页投放 与 首页+产品列表页+资讯页
						}else if("index".equals(adType) || "indexAndList".equals(adType)){
							if(dtIndex!=null && dtIndex.getRowCount()>0){
								int rowCount = dtIndex.getRowCount();
								for(int k = 0;k<rowCount;k++){
									Date date1 = DateUtil.parseDateTime(dtIndex.getString(k, "starttime"), "yyyy-MM-dd hh:mm:ss");
									Date date2 = DateUtil.parseDateTime(dtIndex.getString(k, "endtime"), "yyyy-MM-dd hh:mm:ss");
									if(ad.getStartTime().getTime()<date2.getTime() && ad.getStartTime().getTime()>date1.getTime()){
										status = 2;
										break;
									}else if(ad.getEndTime().getTime()<date2.getTime() && ad.getEndTime().getTime()>date1.getTime()){
										status = 2;
										break;
									}else if(rowCount>1 && k < rowCount){
										Date date3 = DateUtil.parseDateTime(dtIndex.getString(k+1, "starttime"), "yyyy-MM-dd hh:mm:ss");
										if(ad.getStartTime().getTime()>date2.getTime() && ad.getStartTime().getTime()<date3.getTime() && ad.getEndTime().getTime()>date3.getTime()){
											status = 2;
											break;
										}
									}
								}
							}
						//首页+产品列表页+资讯页 与 仅资讯页投放
						}else if("zixunList".equals(adType) || "indexAndList".equals(adType)){
							if(dtZixun!=null && dtZixun.getRowCount()>0){
								int rowCount = dtZixun.getRowCount();
								for(int m = 0;m<rowCount;m++){
									Date date1 = DateUtil.parseDateTime(dtZixun.getString(m, "starttime"), "yyyy-MM-dd hh:mm:ss");
									Date date2 = DateUtil.parseDateTime(dtZixun.getString(m, "endtime"), "yyyy-MM-dd hh:mm:ss");
									if(ad.getStartTime().getTime()<date2.getTime() && ad.getStartTime().getTime()>date1.getTime()){
										status = 2;
										break;
									}else if(ad.getEndTime().getTime()<date2.getTime() && ad.getEndTime().getTime()>date1.getTime()){
										status = 2;
										break;
									}else if(rowCount>1 && m < rowCount){
										Date date3 = DateUtil.parseDateTime(dtZixun.getString(m+1, "starttime"), "yyyy-MM-dd hh:mm:ss");
										if(ad.getStartTime().getTime()>date2.getTime() && ad.getStartTime().getTime()<date3.getTime() && ad.getEndTime().getTime()>date3.getTime()){
											
											status = 2;
											break;
										}
									}
								}
							}
						}else if("carList".equals(adType)){
							if(dtCarList!=null && dtCarList.getRowCount()>0){
								int rowCount = dtCarList.getRowCount();
								for(int l = 0;l<rowCount;l++){
									Date date1 = DateUtil.parseDateTime(dtCarList.getString(l, "starttime"), "yyyy-MM-dd hh:mm:ss");
									Date date2 = DateUtil.parseDateTime(dtCarList.getString(l, "endtime"), "yyyy-MM-dd hh:mm:ss");
									if(ad.getStartTime().getTime()<date2.getTime() && ad.getStartTime().getTime()>date1.getTime()){
										status = 2;
										break;
									}else if(ad.getEndTime().getTime()<date2.getTime() && ad.getEndTime().getTime()>date1.getTime()){
										status = 2;
										break;
									}else if(rowCount>1 && l < rowCount){
										Date date3 = DateUtil.parseDateTime(dtCarList.getString(l+1, "starttime"), "yyyy-MM-dd hh:mm:ss");
										if(ad.getStartTime().getTime()>date2.getTime() && ad.getStartTime().getTime()<date3.getTime() && ad.getEndTime().getTime()>date3.getTime()){
											status = 2;
											break;
										}
									}
								}
							} 
						}else if("topAll".equals(adType)){
							if((dtTopAll!=null && dtTopAll.getRowCount()>0) || (dtIndex!=null && dtIndex.getRowCount()>0) 
									||(dtZixun!=null && dtZixun.getRowCount()>0)){
								status = 2;
							}
						}*/
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				if (status == 0) {
					ad.setActive("Y");
				} 
			}
			ad.update();
			if(status == 0){
				Response.setLogInfo(1, "操作成功！");
			}else if(status == 1){
				Response.setLogInfo(0, "此广告位已过期！");
			}else if(status == 2){
				Response.setLogInfo(0, "此时段已有同类广告位启用！");
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
			Response.setMessage("操作成功");
		} else {
			Response.setError("操作失败");
		}
	}

	public void add() {
		String id = $V("ID");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 修改
		TopAdInfoSchema adi = new TopAdInfoSchema();
		TopAdRelaSchema adr = new TopAdRelaSchema();
		adr.setID($V("RelaID"));
		adr.fill();
		if (StringUtil.isNotEmpty(id)) {
			adi.setID(id);
			adi.fill();
			if("Y".equals(adi.getActive())){
				Response.setLogInfo(0, "启用中的广告不可修改!");
				return;
			}
			adi.setAuthor(User.getUserName());
			adi.setModifyDate(sdf.format(new Date()));
		} else {
			// 增加
			adi.setID(NoUtil.getMaxID("TopAdInfoID")+"");
			adi.setAuthor(User.getUserName());
			adi.setCreateDate(sdf.format(new Date()));
		    adi.setActive("N");
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
		adi.setStartTime(DateUtil.parseDateTime(StartTime, "yyyy-MM-dd hh:mm:ss"));
		adi.setEndTime(DateUtil.parseDateTime(EndTime, "yyyy-MM-dd hh:mm:ss"));
		adi.setParentID(adr.getID());
		adi.setAdName($V("AdName"));
		adi.setAdType($V("AdType"));
		adi.setAdContent($V("ImgPath"));
		adi.setPicOpenType($V("imgADLinkTarget"));
		adi.setPicDesc($V("imgADAlt"));
		adi.setPicLinkURL($V("imgADLinkUrl"));
		if (StringUtil.isNotEmpty(id)) {
			if (adi.update()) {
				Response.setLogInfo(1, "修改广告成功!");
			} else {
				Response.setLogInfo(0, "修改广告发生错误!");
			}
		} else { // 新增
			if (adi.insert()) {
				Response.setLogInfo(1, "新增广告成功!");
			} else {
				Response.setLogInfo(0, "新增广告发生错误!");
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		TopAdInfoSchema ad = new TopAdInfoSchema();
		TopAdInfoSet set = ad.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
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

}
