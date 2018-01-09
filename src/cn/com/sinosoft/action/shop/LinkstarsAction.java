package cn.com.sinosoft.action.shop;

import com.alibaba.fastjson.JSON;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.tenpay.util.MD5Util;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: LinkstarsAction <br/>
 * Function: Linkstars订单查询. <br/>
 * date: 2017年3月14日 上午9:02:40 <br/>
 *
 * @author wwy
 * @version
 */
@ParentPackage("shop")
public class LinkstarsAction extends BaseShopAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 合作商ID.
	 */
	private String source;
	/**
	 * 时间类型1=下单时间。 用于查询新订单。2=订单状态改变时间。 用于查询状态发生改变的订单.
	 */
	private String time_type;
	/**
	 * 订单开始时间 格式：2016-08-08T08:08:08+0800 yyyy-MM-dd'T'HH:mm:ssZ.
	 */
	private String start_time;
	/**
	 * 订单结束时间.
	 */
	private String end_time;
	/**
	 * 1表示成功 2表示异常.
	 */
	private String code = "1";
	/**
	 * 信息 success.
	 */
	private String msg = "success";

	/**
	 * Linkstars key值
	 */
	private String key;

	/**
	 * page:页码
	 */
	private String page;

	/**
	 * page_size:每页的条数
	 */
	private String page_size;

	/**
	 * order_status:订单状态 留空=所有状态的订单 0=状态待定的订单（如新订单、刚下单而尚未支付的订单）1=有效订单-1=无效订单.
	 */
	private String order_status;

	/**
	 * orderquery:Linkstars订单查询. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	public String orderquery() {

		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> orders = new ArrayList<Map<String, Object>>();
		Map<String, Object> result_Map = new HashMap<String, Object>();

		if (StringUtil.isEmpty(source)) {
			msg = "error:source is null";
			code = "0";
		} else if (StringUtil.isEmpty(start_time)) {
			msg = "error:start_time is null";
			code = "0";
		} else if (StringUtil.isEmpty(end_time)) {
			msg = "error:end_time is null";
			code = "0";
		} else if (StringUtil.isEmpty(time_type)) {
			msg = "error:time_type is null";
			code = "0";
		} else if (StringUtil.isEmpty(key)) {
			msg = "error:key is null";
			code = "0";
		} 
		if (StringUtil.isEmpty(page)) {
			page = "1";
		} 
		if (StringUtil.isEmpty(page_size)) {
			page_size = "20";
		}

		if (StringUtil.isNotEmpty(key)) {
			String sign = MD5Util.MD5Encode(source + "@" + start_time + "@" + end_time, "");
			if (!sign.equals(key)) {
				msg = "error:key is wrong";
				code = "0";
			}
			
		}
		if (StringUtil.isNotEmpty(start_time)) {
			//start_time = DateUtil.toString(DateUtil.parse(start_time, "yyyy-MM-dd'T'HH:mm:ssZ"), "yyyy-MM-dd HH:mm:ss");
			start_time = DateUtil.toString(new Date(Long.valueOf(start_time) * 1000), "yyyy-MM-dd HH:mm:ss");
		}
		if (StringUtil.isNotEmpty(end_time)) {
			//end_time = DateUtil.toString(DateUtil.parse(end_time, "yyyy-MM-dd'T'HH:mm:ssZ"), "yyyy-MM-dd HH:mm:ss");
			end_time = DateUtil.toString(new Date(Long.valueOf(end_time) * 1000), "yyyy-MM-dd HH:mm:ss");
		}
		if ("1".equals(code)) {

			try {
				if (StringUtil.isNotEmpty(CpsAction.isPartners(source, "1"))) {
					String sql = "select o.ordersn as order_number, o.createdate as order_time, 'CNY' as currency, '1' as count,"
							+ "sum(r.payprice) as order_price, c.wi as feedback, if(o.orderstatus = '5', '0', if(r.appstatus = '1', '1', '-1')) as order_status, "
							+ "r.riskcode as goods_id, p.productname as goods_name, r.payprice as goods_price, '1' as goods_count, p.producttype as goods_commission_type, "
							+ " if(c.cid = 'linkstars', '1', '2') as device "
							+ "from sdorders o, cps c, sdinformationrisktype r left join sdproduct p on p.productid = r.riskcode "
							+ " where o.ordersn = r.ordersn and o.ordersn = c.`on` and c.cid in ('linkstars', 'linkstars_wap') "
							+ " and o.createdate >= '" + start_time + "'"
							+ " and o.createdate <= '" + end_time + "'"
							+ " and r.appstatus = '1'";

					if (StringUtil.isNotEmpty(order_status)) {
						if ("0".equals(order_status)) {
							sql += " and o.orderstatus = '5'";
						} else if ("1".equals(order_status)) {
							sql += " and r.appstatus = '1'";
						} else if ("-1".equals(order_status)) {
							sql += " and r.appstatus in ('2','3','4') ";
						} else {

						}
					}
					sql += " GROUP BY order_number limit " + ((Integer.valueOf(page) - 1) * Integer.valueOf(page_size))
							+ "," + page_size;

					QueryBuilder query_Orderinfo = new QueryBuilder(sql);
					//query_Orderinfo.add(source);

					DataTable result_Orderinfo = query_Orderinfo.executeDataTable();

					for (int i = 0; i < result_Orderinfo.getRowCount(); i++) {
						Map<String, Object> map_order = new LinkedHashMap<String, Object>();
						DataRow row = result_Orderinfo.get(i);
						map_order.put("order_number", row.getString("order_number"));
						map_order.put("order_time", DateUtil.toString(DateUtil.parse(row.getString("order_time"), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd'T'HH:mm:ssZ"));
						map_order.put("currency", row.getString("currency"));
						map_order.put("count", row.getString("count"));
						map_order.put("order_price", row.getString("order_price"));
						map_order.put("feedback", row.getString("feedback"));
						map_order.put("order_status", row.getString("order_status"));
						// 1:pc 2:wap
						map_order.put("device", row.getString("device"));

						List<Map<String, String>> goods = new ArrayList<Map<String, String>>();
						Map<String, String> map_goods = new LinkedHashMap<String, String>();
						map_goods.put("goods_id", row.getString("goods_id"));
						map_goods.put("goods_name", row.getString("goods_name"));
						map_goods.put("goods_price", row.getString("goods_price"));
						map_goods.put("goods_count", row.getString("goods_count"));
						map_goods.put("goods_commission_type", row.getString("goods_commission_type"));
						goods.add(map_goods);
						map_order.put("goods", goods);
						orders.add(map_order);
					}

				} else {
					msg = "error:no partner";
					code = "0";
				}
			} catch (Exception e) {
				msg = e.getMessage();
				code = "0";
			}
		}

		result_Map.put("code", code);
		result_Map.put("msg", msg);
		if ("1".equals(code)) {
			data.put("total", orders.size());
			data.put("orders", orders);
			result_Map.put("data", data);
		}
		
		JSONObject object = JSONObject.fromObject(result_Map);
		logger.info("CPS查询接口返回信息:{}", JSON.toJSONString(result_Map));
		return ajax(JSON.toJSONString(result_Map), "text/html");
		//return ajax(object.toString(), "text/html");
	}

	public String getSource() {

		return source;
	}

	public void setSource(String source) {

		this.source = source;
	}

	public String getTime_type() {

		return time_type;
	}

	public void setTime_type(String time_type) {

		this.time_type = time_type;
	}

	public String getStart_time() {

		return start_time;
	}

	public void setStart_time(String start_time) {

		this.start_time = start_time;
	}

	public String getEnd_time() {

		return end_time;
	}

	public void setEnd_time(String end_time) {

		this.end_time = end_time;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public String getMsg() {

		return msg;
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getKey() {

		return key;
	}

	public void setKey(String key) {

		this.key = key;
	}

	public String getPage() {

		return page;
	}

	public void setPage(String page) {

		this.page = page;
	}

	public String getPage_size() {

		return page_size;
	}

	public void setPage_size(String page_size) {

		this.page_size = page_size;
	}

	public String getOrder_status() {

		return order_status;
	}

	public void setOrder_status(String order_status) {

		this.order_status = order_status;
	}
}
