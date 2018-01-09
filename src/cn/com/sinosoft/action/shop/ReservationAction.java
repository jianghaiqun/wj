package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.Reservation;
import cn.com.sinosoft.service.ReservationService;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class ReservationAction extends BaseShopAction {

	private static final long serialVersionUID = 2568103988353044174L;
	private Pager pager;
	private List<Reservation> reservations = new ArrayList<Reservation>();
	@Resource
	private ReservationService reservationService;

	public String list(){
		reservations = reservationService.getAll();
		logger.info("====预约========");
		return LIST;
	}
	
	public String save() throws Exception{
		Reservation reservation = new Reservation();
		Map<String, Object> map = new HashMap<String, Object>();
		GetDBdata db1 = new GetDBdata();
		String receiver = db1.getOneValue("select value from zdconfig where type='serviceEmail1'");
//		String name = java.net.URLDecoder.decode(getRequest().getParameter("name"),"utf-8");
		String tel = java.net.URLDecoder.decode(getRequest().getParameter("tel"),"utf-8");
		String area1 = getRequest().getParameter("area1");
		String area2 = java.net.URLDecoder.decode(getRequest().getParameter("area2"),"utf-8");
		String productName = getRequest().getParameter("productName");
		String isTeam = getRequest().getParameter("isTeam");
		if("Y".equals(isTeam))
		{
			reservation.setIsTeam(isTeam);
		}
		reservation.setCreateDate(new Date());
		reservation.setCustomerAreas1(area1);
		reservation.setCustomerAreas2(area2);
		reservation.setCustomerTel(tel);
//		reservation.setCustomerName(name);
		String areaName1 = reservationService.getAreaNameByareaId(area1);
		String areaName2 = reservationService.getAreaNameByareaId(area2);
		reservation.setAreaShow(areaName1+" "+areaName2);
		reservation.setProductName(productName);
		String id = reservationService.save(reservation);
		QueryBuilder qb = new QueryBuilder("select customerTel as companyname,customerName as peoplenum,customerAreas1 as connecttime," +
				"customerAreas2 as connectname,productName as connectiphone,createDate as submitDate from reservation " +
				"where isTeam='Y' order by createDate desc");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			map.put("companyname", dt.getString(0, 0));
			map.put("peoplenum", dt.getString(0, 1));
			map.put("connecttime", dt.getString(0, 2).toString());
			map.put("connectname", dt.getString(0, 3));
			map.put("connectiphone", dt.getString(0, 4));
			map.put("submitDate", dt.getString(0, 5));

		} else {
			logger.warn("未查询到团险预约相关信息");
		}
		if(id!=null&&!"".equals(id)){
			if(StringUtil.isNotEmpty(receiver)){
				ActionUtil.sendMail("wj00066", receiver, map);
			}
			return ajaxText("ok");
		}else{
			return ajaxText("error");
		}
	}
	public String save1() throws Exception{
		Reservation reservation = new Reservation();
		Map<String, Object> map = new HashMap<String, Object>();
		GetDBdata db1 = new GetDBdata();
		String receiver = db1.getOneValue("select value from zdconfig where type='serviceEmail1'");
		String name = java.net.URLDecoder.decode(getRequest().getParameter("name"),"utf-8");
		String tel = java.net.URLDecoder.decode(getRequest().getParameter("tel"),"utf-8");
		String area1 = getRequest().getParameter("area1");
		String area2 = getRequest().getParameter("area2");
		String productName = java.net.URLDecoder.decode(getRequest().getParameter("productName"),"utf-8");
		String isTeam = getRequest().getParameter("isTeam");
		if("Y".equals(isTeam))
		{
			reservation.setIsTeam(isTeam);
		}
		reservation.setCreateDate(new Date());
		reservation.setCustomerAreas1(area1);
		reservation.setCustomerAreas2(area2);
		reservation.setCustomerTel(tel);
		reservation.setCustomerName(name);
		String areaName1 = reservationService.getAreaNameByareaId(area1);
		String areaName2 = reservationService.getAreaNameByareaId(area2);
		reservation.setAreaShow(areaName1+" "+areaName2);
		reservation.setProductName(productName);
		String id = reservationService.save(reservation);
		QueryBuilder qb = new QueryBuilder("select customerName,customerTel,createDate,areaShow,productName from reservation " +
				"where isTeam!='Y'or isTeam is null order by createDate desc");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			map.put("customerName", dt.getString(0, 0));
			map.put("customerTel", dt.getString(0, 1));
			map.put("createDate", dt.getString(0, 2).toString());
			map.put("areaShow", dt.getString(0, 3));
			map.put("productName", dt.getString(0, 4));
		} else {
			logger.warn("未查询到团险预约相关信息");
		}
		if(id!=null&&!"".equals(id)){
			if(StringUtil.isNotEmpty(receiver)){
				ActionUtil.sendMail("wj00077", receiver, map);
			}
			return ajaxText("ok");
		}else{
			return ajaxText("error");
		}
	}
	
	public String getSuperArea(){
		try {
			List<City> citys = reservationService.getSuperCitys();
			logger.info("=====选择地区========{}", citys.size());
			JSONArray jsonArray = JSONArray.fromObject(citys);
			String jsonString = jsonArray.toString();
			return ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxJson("");
		}
	}
	public String getChildArea(){
		try {
			String areaId = getRequest().getParameter("areaId");
			List<City> citys = reservationService.getChilderCitys(areaId);
			JSONArray jsonArray = JSONArray.fromObject(citys);
			String jsonString = jsonArray.toString();
			return ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxJson("");
		}
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

}
