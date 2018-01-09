package com.wangjin.pointsMall;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.GiftClassifySchema;
import com.sinosoft.schema.GiftClassifySet;
import com.sinosoft.schema.GoodsStockSchema;
import com.sinosoft.schema.ZCImageSchema;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
* @ClassName: GiftManage 
* @Description: TODO(礼品管理) 
* @author XXX 
* @date 2015年5月13日 下午12:39:52 
*
* <p> 修改历史</p>
* <p> 序号 日期 修改人 修改原因</p>
 */
public class GiftManage extends Page { 
	/**
	 * 
	* @Title: init 
	* @Description: TODO(礼品管理查询页面 初始化查询条件数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> init(Mapx<String, String> params) {
		params.put("Type",HtmlUtil.codeToOptions("PointsMall.Type", true));//种类
		params.put("YesOrNo",HtmlUtil.codeToOptions("YesOrNo", true));//wap站是否显示
		return params;
	}
	/**
	 * 
	* @Title: initTemplateName 
	* @Description: TODO(获取模板名称数据集合) 
	* @return void    返回类型 
	* @author
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  void initTemplateName() {
		Mapx map_Template=CacheManager.getMapx("Code", "PointsTemplate");//模板列表
		JSONObject json=JSONObject.fromObject(map_Template);
		if(json.size()>0){
			//Response.setStatus(1);
			//Response.setMessage("");
			Response.put("TemplateName",json.toString());//模板列表json
			return;
		}else{
			Response.put("TemplateName","");
		}
	}
	/**
	 * 
	* @Title: dg1DataBind 
	* @Description: TODO(礼品查询) 
	* @return void    返回类型 
	* @author
	 */
	@SuppressWarnings("rawtypes")
	public static void dg1DataBind(DataGridAction dga) {
		String giftTitle = (String) dga.getParams().get("giftTitle");//礼品描述
		String  CreateUser= (String) dga.getParams().get("CreateUser");//创建人
		String  type_param= (String) dga.getParams().get("type");//类别
		String  ModifyUser= (String) dga.getParams().get("ModifyUser");//修改人
		String  createStartDate= (String) dga.getParams().get("createStartDate");//创建开始时间
		String  createEndDate= (String) dga.getParams().get("createEndDate");//创建结束时间
		String isWap = dga.getParams().getString("isWap");
		//查询礼品数据
		QueryBuilder qb = new QueryBuilder("select gift.*,'' as TYPENAME,'' as recommendname, '' as isWapName  from GiftClassify gift where  gift.status !='9'  ");
		//礼品标题
		if (StringUtil.isNotEmpty(giftTitle)) {
			qb.append(" and gift.giftTitle like " + "'%" + giftTitle + "%'");
		}
		//创建人
		if (StringUtil.isNotEmpty(CreateUser)) {
			qb.append(" and gift.CreateUser like " + "'%" + CreateUser + "%'");
		}
		//修改人
		if (StringUtil.isNotEmpty(ModifyUser)) {
			qb.append(" and gift.ModifyUser like " + "'%" + ModifyUser + "%'");
		}
		//种类
		if (StringUtil.isNotEmpty(type_param)) {
			qb.append(" and gift.type = '" + type_param + "'");
		}
		//开始时间
		if (StringUtil.isNotEmpty(createStartDate)) {
			qb.append(" and DATE_FORMAT(gift.CreateDate,'%Y-%m-%d') >= '"+createStartDate.trim()+"'");
		}
		//结束时间
		if (StringUtil.isNotEmpty(createEndDate)) {
			qb.append(" and DATE_FORMAT(gift.CreateDate,'%Y-%m-%d') <= '"+createEndDate.trim()+"'");
		}
		if (StringUtil.isNotEmpty(isWap)) {
			qb.append(" and ( ");
			if ("N".equals(isWap)) {
				qb.append("gift.isWap is null or gift.isWap = '' or ");
			}
			qb.append(" gift.isWap = '" + isWap + "') ");
			
		}
		qb.append(" order by gift.OrderFlag desc, gift.CreateDate  desc");
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		Mapx map_type=CacheManager.getMapx("Code", "PointsMall.Type");
		Mapx map_model=CacheManager.getMapx("Code", "PointsMall.Model");
		Mapx map_status=CacheManager.getMapx("Code", "Gift.status");
		for (int i = 0; i < dt.getRowCount(); i++) {
			//礼品种类
			String type=dt.getString(i, "TYPE");
			if (StringUtil.isNotEmpty(type)) {
				dt.set(i, "TYPENAME", map_type.get(type));
			} else {
				dt.set(i, "TYPENAME", "");
			}
			//归属模块
			String ModelType=dt.getString(i, "ModelType");
			if (StringUtil.isNotEmpty(ModelType)) {
				String modelname="";
				String modeltype[]=ModelType.split(",");
				for (int j = 0; j < modeltype.length; j++) {
					modelname=modelname+map_model.get(modeltype[j])+",";
				}
				if(StringUtil.isNotEmpty(modelname)){
					dt.set(i, "ModelType",modelname.substring(0,modelname.length()-1));
				}else{
					dt.set(i, "ModelType", "");
				}
			} else {
				dt.set(i, "ModelType", "");
			}

			//状态
			String status=dt.getString(i, "status");
			if (StringUtil.isNotEmpty(status)) {
				dt.set(i, "status", map_status.get(status));
			} else {
				dt.set(i, "status", "");
			}
			//是否推荐
			String recommend=dt.getString(i, "recommend");
			if (StringUtil.isNotEmpty(recommend)&&"Y".equals(recommend)) {
				dt.set(i, "recommendname", "是");
			} else {
				dt.set(i, "recommendname", "否");
			}
			//wap是否显示
			if (StringUtil.isNotEmpty(dt.getString(i, "isWap"))&&"Y".equals(dt.getString(i, "isWap"))) {
				dt.set(i, "isWapName", "是");
			} else {
				dt.set(i, "isWapName", "否");
			}
		}
		
		dga.bindData(dt);
	}
	/**
	 * 
	 * @Title: dg1DataBindRecords 
	 * @Description: TODO(初始化库存记录) 
	 * @return void    返回类型 
	 * @author
	 */
	public static void dg1DataBindRecords(DataGridAction dga) {
		String ID = (String) dga.getParams().get("ID");
		QueryBuilder qb_stock = new QueryBuilder("select stock.*,mem.email,po.mobileNo,'' as Operator from GoodsStock stock left join member mem  on stock.memberid=mem.id left  join  PointExchangeInfo po on stock.id=po.goodsStockID where stock.GiftID=? and stock.status!='9'  ",ID);
		//邮箱查询
		String email = (String) dga.getParams().get("email");
		if(StringUtil.isNotEmpty(email)){
				qb_stock.append(" and mem.email like " + "'%" +email  + "%'");
		}
		//电话查询
		String mobileNO = (String) dga.getParams().get("mobileNO");
		if(StringUtil.isNotEmpty(mobileNO)){
			qb_stock.append(" and po.mobileNo like " + "'%" +mobileNO  + "%'");
		}
		//开始时间
		String  StartDate= (String) dga.getParams().get("StartDate");//过期开始时间
		if (StringUtil.isNotEmpty(StartDate)) {
			qb_stock.append(" and UNIX_TIMESTAMP(DATE_FORMAT(stock.outDate,'%Y-%m-%d')) >=  UNIX_TIMESTAMP('"+StartDate.trim()+"')");
		}
		//结束时间
		String  EndDate= (String) dga.getParams().get("EndDate");//过期结束时间
		if (StringUtil.isNotEmpty(EndDate)) {
			qb_stock.append(" and UNIX_TIMESTAMP(DATE_FORMAT(stock.outDate,'%Y-%m-%d')) <=  UNIX_TIMESTAMP('"+EndDate.trim()+"')");
		}
		qb_stock.append(" order by  stock.CreateDate  desc");
		qb_stock.append(dga.getSortString());
		dga.setTotal(qb_stock);
		DataTable dt = qb_stock.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		for (int i = 0; i < dt.getRowCount(); i++) {
			String status=dt.getString(i, "status");
			if("0".equals(status)){
				dt.set(i, "status","待兑换");
			}else if("1".equals(status)){
				dt.set(i, "status","已兑换");
			}else if("2".equals(status)){
				dt.set(i, "status","已暂停");
			}
			String type=dt.getString(i, "type");
			if("01".equals(type)){
				dt.set(i, "Operator","中国移动");
			}else if("02".equals(type)){
				dt.set(i, "Operator","中国联通");
			}else if("03".equals(type)){
				dt.set(i, "Operator","中国电信");
			}
		}
		dga.bindData(dt);
	}
	
	/**
	 * 保险产品库存
	 * @Title: dg1DataBindRecords 
	 * @Description: TODO(初始化库存记录) 
	 * @return void    返回类型 
	 * @author
	 */
	public static void dg1DataBindRecords1(DataGridAction dga) {
		String ID = (String) dga.getParams().get("ID");
		QueryBuilder qb_stock = new QueryBuilder("select stock.*,mem.email,mem.mobileNO from GoodsStock stock left join member mem  on stock.memberid=mem.id where stock.GiftID=? and stock.status!='9' ",ID);
		//邮箱查询
		String email = (String) dga.getParams().get("email");
		if(StringUtil.isNotEmpty(email)){
				qb_stock.append(" and mem.email like " + "'%" +email  + "%'");
		}
		//电话查询
		String mobileNO = (String) dga.getParams().get("mobileNO");
		if(StringUtil.isNotEmpty(mobileNO)){
			qb_stock.append(" and mem.mobileNO like " + "'%" +mobileNO  + "%'");
		}
		qb_stock.append(" order by  stock.CreateDate  desc");
		qb_stock.append(dga.getSortString());
		dga.setTotal(qb_stock);
		DataTable dt = qb_stock.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		for (int i = 0; i < dt.getRowCount(); i++) {
			String status=dt.getString(i, "status");
			if("0".equals(status)){
				dt.set(i, "status","待兑换");
			}else if("1".equals(status)){
				dt.set(i, "status","已兑换");
			}else if("2".equals(status)){
				dt.set(i, "status","已暂停");
			}
		}
		dga.bindData(dt);
	}
	
	/**
	 * 
	* @Title: initDialog 
	* @Description: TODO(编辑、编辑礼品初始化数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		params.put("Type",HtmlUtil.codeToOptions("PointsMall.Type", true));//种类
		params.put("PointsExchangeType",HtmlUtil.codeToOptions("PointsExchange.type", true));//积分兑换模式（现货/福禄接口）
		params.put("FlowType",HtmlUtil.codeToOptions("FlowType", true));//流量类别（全国/省内）
		params.put("ModelType",HtmlUtil.codeToCheckboxes("ModelType", "PointsMall.Model"));//归属模块
		params.put("UploadFilePath", Config.getValue("StaticResourcePath")+"/");//图片根目录
		params.put("status",HtmlUtil.codeToOptions("Gift.status", false));//状态
		params.put("YesOrNo",HtmlUtil.codeToOptions("YesOrNo", false));//wap站是否显示
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			QueryBuilder qb = new QueryBuilder("SELECT   *   FROM  giftclassify  where id=?");
			qb.add(ID);
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			params.put("GiftTitle", dr.getString("GiftTitle"));
			params.put("GiftName", dr.getString("GiftName"));
			params.put("LastNum", dr.getString("LastNum"));
			params.put("GiftType", dr.getString("TYPE"));
			params.put("LogoUrl", dr.getString("LogoUrl"));
			//模块归属初始化勾选
			params.put("ModelType",HtmlUtil.mapxToCheckboxes("ModelType",CacheManager.getMapx("Code", "PointsMall.Model"),dr.getString("ModelType").split(","),null));
			String productid=dr.getString("ProductID");
			if(StringUtil.isNotEmpty(productid)){
				params.put("ProductID", productid);
				DataTable dt_product=new QueryBuilder("select productname from  sdproduct where productid=? limit 1",productid).executeDataTable();
				if(dt_product.getRowCount()>0){
					params.put("ProductName",dt_product.getString(0,0));
				}else{
					params.put("ProductName","无相关产品信息");
				}
			}else{
				params.put("ProductID", "");
			}
			params.put("Points", dr.getString("Points"));
			params.put("Popularity", dr.getString("Popularity"));
			String StartDate=dr.getString("StartDate");
			//开始时间
			if(StringUtil.isNotEmpty(StartDate)){
				params.put("StartDate",StartDate.substring(0,10));
				params.put("StartTime",StartDate.substring(11,StartDate.length()));
			}else{
				params.put("StartDate","");
				params.put("StartTime","");
			}
			//结束时间
			String EndDate=dr.getString("EndDate");
			if(StringUtil.isNotEmpty(EndDate)){
				params.put("EndDate",EndDate.substring(0,10));
				params.put("EndTime",EndDate.substring(11,StartDate.length()));
			}else{
				params.put("EndDate","");
				params.put("EndTime","");
			}
			//是否推荐
			String Recommend=dr.getString("Recommend");
			if("Y".equals(Recommend)){
				params.put("checkY", "checked");
			}else if("N".equals(Recommend)){
				params.put("checkN", "checked");
			}
			params.put("MetaDescription", dr.getString("MetaDescription"));//温馨提示
			params.put("giftstatus",dr.getString("status"));//礼品状态
			params.put("GiftPrice",dr.getString("GiftPrice"));//礼品价值
			params.put("FlowSize", dr.getString("FlowSize"));//流量大小
			params.put("GiftFlowType", dr.getString("flowtype"));//流量类型
			params.put("GiftFuLuGoodsID", dr.getString("FuLuGoodsID"));//福禄商品编号
			params.put("GiftPointsExchangeType", dr.getString("PointsExchangeType"));//模式
			params.put("isWapValue",dr.getString("isWap"));//wap站是否显示
		}
		return params;
	}
	/**
	 * 
	* @Title: initDialog 
	* @Description: TODO(编辑库存  初始化数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initStockDialog(Mapx<String, String> params) {
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			QueryBuilder qb = new QueryBuilder("SELECT   *   FROM  GoodsStock  where id=?");
			qb.add(ID);
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			params.put("GoodsName", dr.getString("GoodsName"));
			params.put("CardNo", dr.getString("CardNo"));
			params.put("PassWord", dr.getString("PassWord"));
			params.put("OutDate", dr.getString("OutDate"));
		}
		return params;
	}
	/**
	 * 
	* @Title: getImgSrc 
	* @Description: TODO(获取图片路径) 
	* @return void    返回类型 
	* @author
	 */
	@SuppressWarnings("unchecked")
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
	/**
	 * 
	* @Title: dg1DataBindProduct 
	* @Description: TODO(查询积分商城栏目下保险产品)
	* @return void    返回类型 
	* @author
	 */
	public static void dg1DataBindProduct(DataGridAction dga) {
		String productname = (String) dga.getParams().get("productname");
		QueryBuilder qb_product = new QueryBuilder("SELECT title as  productname,prop4 as id  FROM   zcarticle  WHERE catalogid=?  AND STATUS='30' ",Config.getValue("PointProductCatalogID"));
		if (StringUtil.isNotEmpty(productname)) {
			qb_product.append(" and title like " + "'%"+ productname.trim() + "%'");
		}
		qb_product.append(" ORDER BY modifytime desc  ");
		qb_product.append(dga.getSortString());
		dga.setTotal(qb_product);
		DataTable dt = qb_product.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "productname", dt.getString(i, "productname"));
		}
		dga.bindData(dt);
	}
	
	
	public int check_it()
	{
		int isfalse = 0;
		//礼品类型
		String type=Request.getString("Type");
		if(StringUtil.isEmpty(type)){
			Response.setStatus(0);
			Response.setMessage("礼品类型不能为空！");
			return  ++isfalse;
		}
		//推荐
		String Recommend=Request.getString("Recommend");
		if(StringUtil.isEmpty(Recommend)){
			Response.setStatus(0);
			Response.setMessage("请选择是否推荐！");
			return ++isfalse;
		}
		//图片路径
		String ImgPath=Request.getString("ImgPath");
		//归属模块
		String ModelType=Request.getString("ModelType");
		if(StringUtil.isEmpty(ModelType)){
			Response.setStatus(0);
			Response.setMessage("归属模块不能为空！");
			return ++isfalse;
		}
		//积分兑换值
		String Points=Request.getString("Points");
		if("2".equals(type)||"3".equals(type)||"4".equals(type)){
			if(StringUtil.isNotEmpty(Points)){
				if(StringUtil.isInteger(Points)){
					if((!StringUtil.isPositiveInteger(Points))){
						Response.setStatus(0);
						Response.setMessage("积分兑换值应为正整数！");
						return ++isfalse;
					}else if(new BigDecimal(Points).compareTo(new BigDecimal("999999"))==1){
						Response.setStatus(0);
						Response.setMessage("积分兑换值应小于999999！");
						return ++isfalse;
					}
				}
			}else{
				Response.setStatus(0);
				Response.setMessage("积分兑换值为必填项");
				return ++isfalse;
			}
			
		}
		//礼品价值
		String GiftPrice=Request.getString("GiftPrice");
		if((StringUtil.isEmpty(GiftPrice))&&("2".equals(type)||"3".equals(type)||"4".equals(type)||"5".equals(type)||"6".equals(type))){
			Response.setStatus(0);
			Response.setMessage("礼品价值为必填项！");
			return ++isfalse;
		}else if("2".equals(type)||"3".equals(type)||"4".equals(type)||"5".equals(type)||"6".equals(type)){
			if(!StringUtil.isDouble(GiftPrice)){
				Response.setStatus(0);
				Response.setMessage("礼品价值必须为数字！");
				return ++isfalse;
			}
			if("2".equals(type)&&Double.parseDouble(GiftPrice)<10){
				Response.setStatus(0);
				Response.setMessage("通讯礼品最少10元！");
				return ++isfalse;
			}
		}
		//人气
		String Popularity=Request.getString("Popularity");
		if(!StringUtil.isInteger(Popularity)){
			Response.setStatus(0);
			Response.setMessage("人气值应为非负整数！");
			return ++isfalse;
		}
		//库存
		String LastNum=Request.getString("LastNum");
		if(!StringUtil.isInteger(LastNum)&&"1".equals(type)){//保险产品需填写库存
			Response.setStatus(0);
			Response.setMessage("库存应为非负整数！");
			return ++isfalse;
		}
		String PointsExchangeType = Request.getString("PointsExchangeType");//模式(1现货 2福禄接口)
		if(StringUtil.isEmpty(PointsExchangeType)){
			Response.setStatus(0);
			Response.setMessage("兑换模式不能为空！");
			return ++isfalse;
		}else if(!"1".equals(PointsExchangeType)&&!"2".equals(PointsExchangeType)){
			Response.setStatus(0);
			Response.setMessage("兑换模式目前仅支持现货和福禄接口，请确认！");
			return ++isfalse;
		}
		
		if("5".equals(type)||"6".equals(type)){
			if("1".equals(PointsExchangeType)){
				Response.setStatus(0);
				Response.setMessage("现货不支持流量，账号直冲！");
				return ++isfalse;	
			}
		}else if("1".equals(type)&&"2".equals(PointsExchangeType)){
			Response.setStatus(0);
			Response.setMessage("福禄模式不支持保险产品！");
			return ++isfalse;
		}else if("4".equals(type)&&"2".equals(PointsExchangeType)){
			Response.setStatus(0);
			Response.setMessage("福禄模式不支持卡号产品！");
			return ++isfalse;
		}
		
		String FuLuGoodsID = Request.getString("FuLuGoodsID");//福禄商品编号
		if("1".equals(PointsExchangeType)){}else{
		if("3".equals(type)||"6".equals(type)){//卡密产品、账号直冲 才有福禄编号（type=3/6）
		    if(StringUtil.isEmpty(FuLuGoodsID)){
			    Response.setStatus(0);
			    Response.setMessage("福禄商品编号不能为空！");
			    return ++isfalse;
		    }
		}else{
			 if(StringUtil.isEmpty(FuLuGoodsID)){
				 
			 }else{
		    Response.setStatus(0);
		    Response.setMessage("卡密产品、账号直冲 才有福禄编号！");
		    return ++isfalse;
		    }	
		}
		}
		String FlowType = Request.getString("flowtype");//流量类型(全国1  省内0)
		String FlowSize = Request.getString("FlowSize");//流量大小 (单位 MB)
		if("5".equals(type)){//流量直冲才需要流量（type=5）
	    if(StringUtil.isEmpty(FlowType)||StringUtil.isEmpty(FlowSize)){
		    Response.setStatus(0);
		    Response.setMessage("流量信息不能为空！");
		    return ++isfalse;
	    }else if(!"1".equals(FlowType)&&!"0".equals(FlowType)){
		    Response.setStatus(0);
		    Response.setMessage("请确认是全国流量还是省内流量！");
		    return ++isfalse;
	    }else{
	    	for(int i=FlowSize.length();--i>=0;){  
	    	      int chr=FlowSize.charAt(i);  
	    	      if(chr<48 || chr>57){  
	  			    Response.setStatus(0);
				    Response.setMessage("流量应为数字！");
				    return ++isfalse; 
	    	      }
	    	   }  
	    }
	    if(Integer.parseInt(FlowSize)<10||Integer.parseInt(GiftPrice)<3){
			Response.setStatus(0);
		    Response.setMessage("流量最小10M！流量价值最小3元！");
		    return ++isfalse;
		    }
		}
		return isfalse;
	}
	
	/**
	 * 
	* @Title: saveGift 
	* @Description: TODO(保存礼品) 
	* @return void    返回类型 
	* @author
	 */
	public void saveGift() {
		//事务创建
		Transaction trans = new Transaction();
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		String GiftStatusFlag=Request.getString("GiftStatusFlag");
		String type=Request.getString("Type");
		String ImgPath=Request.getString("ImgPath");
		String StartDate=Request.getString("createStartDate");
		String StartTime=Request.getString("createStartTime");
		String EndDate=Request.getString("createEndDate");
		String EndTime=Request.getString("createEndTime");
		String PointsExchangeType = Request.getString("PointsExchangeType");//模式(1现货 2福禄接口)
		String FuLuGoodsID = Request.getString("FuLuGoodsID");//福禄商品编号
		String FlowType = Request.getString("flowtype");//流量类型(全国1  省内0)
		String FlowSize = Request.getString("FlowSize");//流量大小 (单位 MB)
		int check_itis = check_it();
		if(check_itis==1){
			return;
		}
		//编辑
		String LastNum_edit="";
		if("edit".equals(GiftStatusFlag)){
			GiftClassify.setid(Request.getString("ID"));
			if(!GiftClassify.fill()){
				Response.setStatus(0);
				Response.setMessage("编辑没有查询到礼品信息！");
				return;
			}
			LastNum_edit=GiftClassify.getlastNum();
			GiftClassify.setValue(Request);
		}else{
			GiftClassify.setValue(Request);
			GiftClassify.setid(NoUtil.getMaxNo("GiftClassifyID", 11));
		}

		//礼品图片
		GiftClassify.setlogoUrl(ImgPath);
		//开始时间
		GiftClassify.setstartDate(DateUtil.parseDateTime(StartDate.trim()+" "+StartTime.trim()));
		//结束时间
		GiftClassify.setendDate(DateUtil.parseDateTime(EndDate.trim()+" "+EndTime.trim()));
		//保险产品
		if("1".equals(type)){
			//产品ID
			String ProductID=Request.getString("ProductID");
			if(StringUtil.isNotEmpty(ProductID)){
				DataTable dt=new QueryBuilder("SELECT htmlpath,Remark4,ProductName,JFHtmlPath FROM  sdproduct  WHERE productid=?",ProductID).executeDataTable();
				if(dt.getRowCount()>0){
					// sdproduct 增加积分商城产品链接字段 JFHtmlPath 适应旧系统 判断 新字段是否存在
					String linkUrl = dt.getString(0, 3);
					if (StringUtil.isNotEmpty(linkUrl)) {
						GiftClassify.setlinkUrl(linkUrl);
					} else {
						GiftClassify.setlinkUrl(dt.getString(0, 0));
					}
					
					String iniprice=dt.getString(0, 1);
					String PointScalerUnit=Config.getConfigValue("PointScalerUnit");
					if(StringUtil.isEmpty(iniprice)){
						GiftClassify.setpoints("999998");
						GiftClassify.setgiftPrice("0");
					}else{
						double point=Double.parseDouble(iniprice)*Double.parseDouble(PointScalerUnit);
						GiftClassify.setpoints(new BigDecimal(point).setScale(0, BigDecimal.ROUND_UP ).toString());
						GiftClassify.setgiftPrice(iniprice);
					}
					
				}else{
					Response.setStatus(0);
					Response.setMessage("没有查询到产品信息，请重新选择保险产品！");
					return;
				}
				//校验同一时间内同一产品是否有多个礼品
				//DataTable dt_product = new QueryBuilder("SELECT title as  productname,prop4 as id  FROM   zcarticle  WHERE catalogid=?  AND STATUS='30' ",Config.getValue("PointProductCatalogID")).executeDataTable();
				if(!checkGiftSingletonByTime(StartDate.trim()+" "+StartTime.trim(), EndDate.trim()+" "+EndTime.trim(), ProductID,dt.getString(0, 2),GiftClassify.getid())){
					return;
				}
			}else{
				Response.setStatus(0);
				Response.setMessage("产品ID为空，请重新选择保险产品！");
				return;
			}
		}else{
			//非保险产品productid为空
			GiftClassify.setproductID("");
		}
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			//编辑
			if("edit".equals(GiftStatusFlag)){
				//非保险产品
				if(!"1".equals(GiftClassify.gettype()) && !"2".equals(PointsExchangeType)){
					GiftClassify.setlastNum(LastNum_edit);
				}
				GiftClassify.setmodifyUser(User.getUserName());
				GiftClassify.setmodifyDate(date);
				trans.add(GiftClassify,Transaction.UPDATE);
			}else{//新增
				//非保险产品
				if(!"1".equals(GiftClassify.gettype()) && !"2".equals(PointsExchangeType)){
					GiftClassify.setlastNum("0");
				}
				
				GiftClassify.setFlowSize(FlowSize);
				GiftClassify.setflowtype(FlowType);
				GiftClassify.setFuLuGoodsID(FuLuGoodsID);
				GiftClassify.setPointsExchangeType(PointsExchangeType);
				GiftClassify.setcreateUser(User.getUserName());
				GiftClassify.setcreateDate(date);
				GiftClassify.setorderFlag(System.currentTimeMillis());
				trans.add(GiftClassify,Transaction.INSERT);
			}
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("礼品保存成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("保存礼品失败！");
				return;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	* @Title: saveStock 
	* @Description: TODO(保存库存) 
	* @return void    返回类型 
	* @author
	 */
	public void saveStock() {
		//事务创建
		Transaction trans = new Transaction();
		GoodsStockSchema GoodsStock=new GoodsStockSchema();
		GoodsStock.setid(Request.getString("ID"));
		if(!GoodsStock.fill()){
			Response.setStatus(0);
			Response.setMessage("查询库存信息失败，请重新选择！");
			return;
		}
		//库存状态
		if(!"0".equals(GoodsStock.getstatus())){
			Response.setStatus(0);
			Response.setMessage("只有'待兑换'状态的库存才可编辑！");
			return;
		}
		GoodsStock.setValue(Request);
		try {
			//定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			GoodsStock.setmodifyUser(User.getUserName());
			GoodsStock.setmodifyDate(date);
			trans.add(GoodsStock,Transaction.UPDATE);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("库存保存成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("库存保存失败！");
				return;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	* @Title:  PauseGift
	* @Description: TODO(暂停礼品) 
	* @return void    返回类型 
	* @author
	 */
	public void PauseGift() {
		//事务创建
		Transaction trans = new Transaction();
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		GiftClassify.setid(Request.getString("ID"));
		if(!GiftClassify.fill()){
			Response.setStatus(0);
			Response.setMessage("暂停时没有查询到礼品信息！");
			return;
		}
		//暂停
		GiftClassify.setstatus("1");
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			GiftClassify.setmodifyUser(User.getUserName());
			GiftClassify.setmodifyDate(date);
			trans.add(GiftClassify, Transaction.UPDATE);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("礼品暂停成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("保存暂停失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @Title:  PauseStock
	 * @Description: TODO(暂停库存) 
	 * @return void    返回类型 
	 * @author
	 */
	public void PauseStock() {
		//事务创建
		Transaction trans = new Transaction();
		GoodsStockSchema GoodsStock=new GoodsStockSchema();
		GoodsStock.setid(Request.getString("ID"));
		if(!GoodsStock.fill()){
			Response.setStatus(0);
			Response.setMessage("暂停时没有查询到库存信息！");
			return;
		}
		if(!"0".equals(GoodsStock.getstatus())){
			Response.setStatus(0);
			Response.setMessage("只有'待兑换'状态的库存才可以暂停！");
			return;
		}
		//暂停
		GoodsStock.setstatus("2");
		//库存减1
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		GiftClassify.setid(GoodsStock.getgiftID());
		if(!GiftClassify.fill()){
			Response.setStatus(0);
			Response.setMessage("暂停时没有查询到礼品信息！");
			return;
		}
		int LastNum=Integer.parseInt(GiftClassify.getlastNum());
		LastNum=LastNum-1;
		if(LastNum<0){
			LastNum=0;
		}
		GiftClassify.setlastNum(String.valueOf(LastNum));
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			GoodsStock.setmodifyUser(User.getUserName());
			GoodsStock.setmodifyDate(date);
			trans.add(GoodsStock, Transaction.UPDATE);
			trans.add(GiftClassify, Transaction.UPDATE);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("库存暂停成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("库存暂停失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @Title:  RecoveryStock
	 * @Description: TODO(恢复库存) 
	 * @return void    返回类型 
	 * @author
	 */
	public void RecoveryStock() {
		//事务创建
		Transaction trans = new Transaction();
		GoodsStockSchema GoodsStock=new GoodsStockSchema();
		GoodsStock.setid(Request.getString("ID"));
		if(!GoodsStock.fill()){
			Response.setStatus(0);
			Response.setMessage("恢复时没有查询到礼品信息！");
			return;
		}
		if(!"2".equals(GoodsStock.getstatus())){
			Response.setStatus(0);
			Response.setMessage("只有'暂停'状态的库存才可以恢复！");
			return;
		}
		//恢复
		GoodsStock.setstatus("0");
		//库存减1
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		GiftClassify.setid(GoodsStock.getgiftID());
		if(!GiftClassify.fill()){
			Response.setStatus(0);
			Response.setMessage("暂停时没有查询到礼品信息！");
			return;
		}
		int LastNum=Integer.parseInt(GiftClassify.getlastNum());
		LastNum=LastNum+1;
		if(LastNum<0){
			LastNum=0;
		}
		GiftClassify.setlastNum(String.valueOf(LastNum));
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			GoodsStock.setmodifyUser(User.getUserName());
			GoodsStock.setmodifyDate(date);
			trans.add(GoodsStock, Transaction.UPDATE);
			trans.add(GiftClassify, Transaction.UPDATE);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("礼品恢复成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("保存恢复失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * @Title:  RecoveryGift
	 * @Description: TODO(恢复礼品) 
	 * @return void    返回类型 
	 * @author
	 */
	public void RecoveryGift() {
		//事务创建
		Transaction trans = new Transaction();
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		GiftClassify.setid(Request.getString("ID"));
		if(!GiftClassify.fill()){
			Response.setStatus(0);
			Response.setMessage("恢复时没有查询到礼品信息！");
			return;
		}
		//恢复
		GiftClassify.setstatus("0");
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			GiftClassify.setmodifyUser(User.getUserName());
			GiftClassify.setmodifyDate(date);
			trans.add(GiftClassify, Transaction.UPDATE);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("礼品恢复成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("保存恢复失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @Title:  dell
	 * @Description: TODO(删除礼品) 
	 * @return void    返回类型 
	 * @author
	 */
	public void dell() {
		//事务创建
		Transaction trans = new Transaction();
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		GiftClassify.setid(Request.getString("ID"));
		if(!GiftClassify.fill()){
			Response.setStatus(0);
			Response.setMessage("删除时没有查询到礼品信息！");
			return;
		}
		//删除
		GiftClassify.setstatus("9");
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			GiftClassify.setmodifyUser(User.getUserName());
			GiftClassify.setmodifyDate(date);
			trans.add(GiftClassify, Transaction.UPDATE);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("礼品删除成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("礼品删除失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * @Title:  dellStock
	 * @Description: TODO(删除库存) 
	 * @return void    返回类型 
	 * @author
	 */
	public void dellStock() {
		//事务创建
		Transaction trans = new Transaction();
		GoodsStockSchema GoodsStock=new GoodsStockSchema();
		GoodsStock.setid(Request.getString("ID"));
		if(!GoodsStock.fill()){
			Response.setStatus(0);
			Response.setMessage("删除时没有查询到库存信息！");
			return;
		}
		if(!"0".equals(GoodsStock.getstatus())){
			Response.setStatus(0);
			Response.setMessage("只有'待兑换'状态的库存才可以删除！");
			return;
		}
		//删除
		GoodsStock.setstatus("9");
		//库存减1
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		GiftClassify.setid(GoodsStock.getgiftID());
		if(!GiftClassify.fill()){
			Response.setStatus(0);
			Response.setMessage("暂停时没有查询到礼品信息！");
			return;
		}
		int LastNum=Integer.parseInt(GiftClassify.getlastNum());
		LastNum=LastNum-1;
		if(LastNum<0){
			LastNum=0;
		}
		GiftClassify.setlastNum(String.valueOf(LastNum));
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			GoodsStock.setmodifyUser(User.getUserName());
			GoodsStock.setmodifyDate(date);
			trans.add(GoodsStock, Transaction.UPDATE);
			trans.add(GiftClassify, Transaction.UPDATE);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("库存删除成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("库存删除失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	* @Title: checkGiftSingleton 
	* @Description: TODO(校验同一时间段内礼品) 
	* @return boolean    返回类型 
	* @author
	 */
	private boolean checkGiftSingletonByTime(String starttime,String endtime,String productid,String productname,String id){
		String sql="SELECT * FROM giftclassify WHERE  status!=9  and  type='1' "
		+" and  ((  UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s'))   "
		+ "	and  UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s')))"
		+ " or (UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s')) "
		+ " and UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')))"
		+ " or (UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s'))"
		+ " and  UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')))"
		+ " or (UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s'))"
		+ " and  UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')) ))"
		+ " and  id!='"+id+"'";
		DataTable dt_gift=new QueryBuilder(sql).executeDataTable();
		if(dt_gift.getRowCount()>0){
			for (int i = 0; i < dt_gift.getRowCount(); i++) {
					if(productid.equals(StringUtil.noNull(dt_gift.getString(i,"ProductID")))){
						Response.setStatus(0);
						Response.setMessage("同一时间段内,'"+productname+"'已存在于礼品'"+dt_gift.getString(i,"GiftName")+"'中！");
						return false;
					}
			}
		}
		return true;
	}
/**
 * 
* @Title: uploadGoodsStock02 
* @Description: TODO(上传库存信息-电话卡) 
* @return void    返回类型 
* @author
 */
	public void uploadGoodsStock02() {
		String GiftID=$V("GiftID");
		GiftClassifySchema GiftClassify=new GiftClassifySchema();
		GiftClassify.setid(GiftID);
		if(!GiftClassify.fill()){
			this.Response.setError("没有查询到该礼品信息，请联系管理员！");
			return;
		}
		try {
			String fileaddress = $V("fileaddress");
			FileInputStream finput = null;
			try {
				finput = new FileInputStream(fileaddress);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("文件没找到，导入失败！请选择正确模板");
				return;
			}

			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(finput);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("IO异常，导入失败！请选择正确模板");
				return;
			}
			HSSFWorkbook wb = null;
			try {
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("转换对象异常，导入失败！请选择正确模板");
				return;
			}
			HSSFSheet sheet = wb.getSheetAt(0);
			try {
				finput.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("关闭IO异常，导入失败！请选择正确模板");
				return;
			}
			// 最大行数
			int maxRow = sheet.getLastRowNum();
			if (maxRow - 9 <= 0) {
				this.Response.setError("导入失败！导入的文件中不包含有效数据！");
				return;
			}
			// 检查是否有空
			for (int j = 10; j < maxRow + 1; j++) {
				// 获取行数据
				HSSFRow row = sheet.getRow(j);
				//产品名称
				HSSFCell cel_goodsname = row.getCell(1);
				String celval_goodsname = "";
				//卡号
				HSSFCell cel_card = row.getCell(2);
				String celval_card = "";
				//密码
				HSSFCell cel_password = row.getCell(3);
				String celval_password = "";
				//有效期
				HSSFCell cel_period = row.getCell(4);
				String celval_period = "";
				//运营商
				HSSFCell cel_operator = row.getCell(5);
				String celval_operator = "";
				//备注
				HSSFCell cel_memo = row.getCell(6);
				String celval_memo = "";
				//校验是否存在空数据
				if ((cel_card == null) || (cel_password == null)||cel_period==null||cel_goodsname==null||cel_operator==null) {
					this.Response.setError("第" + (j + 1) + "行非'备注'列有空数据，请修改后重新上传！");
					return;
				}
				//校验文本格式
				if(cel_card.getCellType() != 1) {
					this.Response.setError("第" + (j + 1) + "行卡号为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
					return;
				}else if(cel_password.getCellType() != 1) {
					this.Response.setError("第" + (j + 1) + "行密码为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
					return;
				}else if(cel_period.getCellType() != 1) {
					this.Response.setError("第" + (j + 1) + "行有效期为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
					return;
				}else if(cel_operator.getCellType() != 1) {
					this.Response.setError("第" + (j + 1) + "行运营商为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
					return;
				}else if(cel_memo!=null&&cel_memo.getCellType() != 1) {
					this.Response.setError("第" + (j + 1) + "行备注内容为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
					return;
				}else {
					celval_goodsname = cel_goodsname.getStringCellValue().trim();
					celval_card = cel_card.getStringCellValue().trim();
					celval_password = cel_password.getStringCellValue().trim();
					celval_period = cel_period.getStringCellValue().trim();
					celval_operator = cel_operator.getStringCellValue().trim();
					if(cel_memo!=null){
						celval_memo = cel_memo.getStringCellValue().trim();
					}else{
						celval_memo="";
					}
				}
				if (celval_goodsname.equals("")||celval_card.equals("")||celval_password.equals("")||celval_period.equals("")||celval_operator.equals("")) {
					this.Response.setError("第" + (j + 1) + "行非'备注'列有空数据，请修改后重新上传！");
					return;
				}
				// 非法字符串检查
				String regex = "script|iframe";
				Pattern p = Pattern.compile(regex);
				Matcher m1 = p.matcher(celval_goodsname.toLowerCase());
				Matcher m2 = p.matcher(celval_card.toLowerCase());
				Matcher m3 = p.matcher(celval_password.toLowerCase());
				Matcher m4 = p.matcher(celval_period.toLowerCase());
				Matcher m6 = p.matcher(celval_operator.toLowerCase());
				if(StringUtil.isNotEmpty(celval_memo)){
					Matcher m5 = p.matcher(celval_memo.toLowerCase());
					if (m5.find()) {
						this.Response.setError("第" + (j + 1) + "行备注中有非法字符,请重新填写");
						return;
					}
				}
				if (m1.find()||m2.find() || m3.find()|| m4.find()||m6.find()) {
					this.Response.setError("第" + (j + 1) + "行有非法字符,请重新填写");
					return;
				}
				// 判断是否有重复卡号和密码
				for (int i = 2; i <4; i++) {
					for (int k = j + 1; k < maxRow; k++) {
						HSSFRow row_next = sheet.getRow(k);
						if ((row_next.getCell(i) == null)) {
							continue;
						}
						String celval_2_next= row_next.getCell(i).getStringCellValue().trim();
						if (celval_2_next.equals(celval_card)) {
							this.Response.setError("第" + (j + 1) + "行与第" + (k + 1)+ "行的第"+(i+1)+"列数据重复,请重新填写");
							return;
						}
					}
				}
			}
			//添加数据
			Transaction transaction = new Transaction();
			for (int j = 10; j < maxRow + 1; j++) {
				// 获取行数据
				HSSFRow row = sheet.getRow(j);
				//产品名称
				HSSFCell cel_goodsname = row.getCell(1);
				String celval_goodsname = "";
				//卡号
				HSSFCell cel_card = row.getCell(2);
				String celval_card = "";
				//密码
				HSSFCell cel_password = row.getCell(3);
				String celval_password = "";
				//有效期
				HSSFCell cel_period = row.getCell(4);
				String celval_period = "";
				//运营商
				HSSFCell cel_operator = row.getCell(5);
				String celval_operator = "";
				//备注
				HSSFCell cel_memo = row.getCell(6);
				String celval_memo = "";
				//取值
				celval_goodsname = cel_goodsname.getStringCellValue().trim();
				celval_card = cel_card.getStringCellValue().trim();
				celval_password = cel_password.getStringCellValue().trim();
				celval_period = cel_period.getStringCellValue().trim();
				celval_operator = cel_operator.getStringCellValue().trim();
				if(cel_memo!=null){
					celval_memo = cel_memo.getStringCellValue().trim();
				}else{
					celval_memo="";
				}
				GoodsStockSchema GoodsStock=new GoodsStockSchema();
				GoodsStock.setid(NoUtil.getMaxNo("GoodsStockID", 11));
				GoodsStock.setgiftID(GiftID);
				if(celval_goodsname.length()>100){
					this.Response.setError("第" + (j + 1) + "行商品名称长度大于100,请重新填写");
					return;
				}else{
					GoodsStock.setgoodsName(celval_goodsname);
				}
				if(celval_card.length()>255){
					this.Response.setError("第" + (j + 1) + "行卡号长度大于255,请重新填写");
					return;
				}else{
					GoodsStock.setcardNo(celval_card);;
				}
				if(celval_password.length()>100){
					this.Response.setError("第" + (j + 1) + "行密码长度大于100,请重新填写");
					return;
				}else{
					GoodsStock.setpassWord(celval_password);;
				}
				if(celval_memo.length()>100){
					this.Response.setError("第" + (j + 1) + "行备注长度大于100,请重新填写");
					return;
				}else{
					GoodsStock.setmemo(celval_memo);
				}
				if(DateUtil.parse(celval_period)==null){
					this.Response.setError("第" + (j + 1) + "行时间格式错误,请重新填写");
					return;
				}
				//运营商
				if(celval_operator.indexOf("移动")!=-1){
					GoodsStock.settype("01");
				}else if(celval_operator.indexOf("联通")!=-1){
					GoodsStock.settype("02");
				}else if(celval_operator.indexOf("电信")!=-1){
					GoodsStock.settype("03");
				}else{
					this.Response.setError("第" + (j + 1) + "行运营商错误,请重新填写");
					return;
				}
				
				GoodsStock.setoutDate(DateUtil.parse(celval_period));
				//初始化状态为0
				GoodsStock.setstatus("0");
				// 定义时间格式
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date= sdf.parse(PubFun.getCurrent());
				GoodsStock.setcreateDate(date);
				GoodsStock.setcreateUser(User.getUserName());
				transaction.add(GoodsStock,Transaction.INSERT);
			}
			String LastNum=GiftClassify.getlastNum();
			if(StringUtil.isInteger(LastNum)){
				GiftClassify.setlastNum(String.valueOf(Integer.parseInt(LastNum)+(maxRow - 9)));
			}else{
				this.Response.setMessage("礼品库存异常，请联系管理员，礼品名称："+GiftClassify.getgiftName());
			}
			//更新库存
			transaction.add(GiftClassify,Transaction.UPDATE);
			if(transaction.commit()){
				this.Response.setMessage("共上传了" + String.valueOf(maxRow - 9)+ "个商品库存！");
			}else{
				this.Response.setMessage("导入异常！请选择正确模板！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.Response.setError("导入异常！请选择正确模板！");
		}

	}
	/**
	 * 
	* @Title: uploadGoodsStock03 
	* @Description: TODO(上传库存信息-卡密) 
	* @return void    返回类型 
	* @author
	 */
		public void uploadGoodsStock03() {
			String GiftID=$V("GiftID");
			GiftClassifySchema GiftClassify=new GiftClassifySchema();
			GiftClassify.setid(GiftID);
			if(!GiftClassify.fill()){
				this.Response.setError("没有查询到该礼品信息，请联系管理员！");
				return;
			}
			try {
				String fileaddress = $V("fileaddress");
				FileInputStream finput = null;
				try {
					finput = new FileInputStream(fileaddress);
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage(), e);
					this.Response.setError("文件没找到，导入失败！请选择正确模板");
					return;
				}

				POIFSFileSystem fs = null;
				try {
					fs = new POIFSFileSystem(finput);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					this.Response.setError("IO异常，导入失败！请选择正确模板");
					return;
				}
				HSSFWorkbook wb = null;
				try {
					wb = new HSSFWorkbook(fs);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					this.Response.setError("转换对象异常，导入失败！请选择正确模板");
					return;
				}
				HSSFSheet sheet = wb.getSheetAt(0);
				try {
					finput.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					this.Response.setError("关闭IO异常，导入失败！请选择正确模板");
					return;
				}
				// 最大行数
				int maxRow = sheet.getLastRowNum();
				if (maxRow - 9 <= 0) {
					this.Response.setError("导入失败！导入的文件中不包含有效数据！");
					return;
				}
				// 检查是否有空
				for (int j = 10; j < maxRow + 1; j++) {
					// 获取行数据
					HSSFRow row = sheet.getRow(j);
					//产品名称
					HSSFCell cel_goodsname = row.getCell(1);
					String celval_goodsname = "";
					//卡号
					HSSFCell cel_card = row.getCell(2);
					String celval_card = "";
					//密码
					HSSFCell cel_password = row.getCell(3);
					String celval_password = "";
					//有效期
					HSSFCell cel_period = row.getCell(4);
					String celval_period = "";
					//备注
					HSSFCell cel_memo = row.getCell(5);
					String celval_memo = "";
					//校验是否存在空数据
					if ((cel_card == null) || (cel_password == null)||cel_period==null||cel_goodsname==null) {
						this.Response.setError("第" + (j + 1) + "行非'备注'列有空数据，请修改后重新上传！");
						return;
					}
					//校验文本格式
					if(cel_card.getCellType() != 1) {
						this.Response.setError("第" + (j + 1) + "行卡号为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
						return;
					}else if(cel_password.getCellType() != 1) {
						this.Response.setError("第" + (j + 1) + "行密码为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
						return;
					}else if(cel_period.getCellType() != 1) {
						this.Response.setError("第" + (j + 1) + "行有效期为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
						return;
					}else if(cel_memo!=null&&cel_memo.getCellType() != 1) {
						this.Response.setError("第" + (j + 1) + "行备注内容为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
						return;
					}else {
						celval_goodsname = cel_goodsname.getStringCellValue().trim();
						celval_card = cel_card.getStringCellValue().trim();
						celval_password = cel_password.getStringCellValue().trim();
						celval_period = cel_period.getStringCellValue().trim();
						if(cel_memo!=null){
							celval_memo = cel_memo.getStringCellValue().trim();
						}else{
							celval_memo="";
						}
					}
					if (celval_goodsname.equals("")||celval_card.equals("")||celval_password.equals("")||celval_period.equals("")) {
						this.Response.setError("第" + (j + 1) + "行非'备注'列有空数据，请修改后重新上传！");
						return;
					}
					// 非法字符串检查
					String regex = "script|iframe";
					Pattern p = Pattern.compile(regex);
					Matcher m1 = p.matcher(celval_goodsname.toLowerCase());
					Matcher m2 = p.matcher(celval_card.toLowerCase());
					Matcher m3 = p.matcher(celval_password.toLowerCase());
					Matcher m4 = p.matcher(celval_period.toLowerCase());
					if(StringUtil.isNotEmpty(celval_memo)){
						Matcher m5 = p.matcher(celval_memo.toLowerCase());
						if (m5.find()) {
							this.Response.setError("第" + (j + 1) + "行备注中有非法字符,请重新填写");
							return;
						}
					}
					if (m1.find()||m2.find() || m3.find()|| m4.find()) {
						this.Response.setError("第" + (j + 1) + "行有非法字符,请重新填写");
						return;
					}
					// 判断是否有重复卡号和密码
					for (int i = 2; i <4; i++) {
						for (int k = j + 1; k < maxRow; k++) {
							HSSFRow row_next = sheet.getRow(k);
							if ((row_next.getCell(i) == null)) {
								continue;
							}
							String celval_2_next= row_next.getCell(i).getStringCellValue().trim();
							if (celval_2_next.equals(celval_card)) {
								this.Response.setError("第" + (j + 1) + "行与第" + (k + 1)+ "行的第"+(i+1)+"列数据重复,请重新填写");
								return;
							}
						}
					}
				}
				//添加数据
				Transaction transaction = new Transaction();
				for (int j = 10; j < maxRow + 1; j++) {
					// 获取行数据
					HSSFRow row = sheet.getRow(j);
					//产品名称
					HSSFCell cel_goodsname = row.getCell(1);
					String celval_goodsname = "";
					//卡号
					HSSFCell cel_card = row.getCell(2);
					String celval_card = "";
					//密码
					HSSFCell cel_password = row.getCell(3);
					String celval_password = "";
					//有效期
					HSSFCell cel_period = row.getCell(4);
					String celval_period = "";
					//备注
					HSSFCell cel_memo = row.getCell(5);
					String celval_memo = "";
					//取值
					celval_goodsname = cel_goodsname.getStringCellValue().trim();
					celval_card = cel_card.getStringCellValue().trim();
					celval_password = cel_password.getStringCellValue().trim();
					celval_period = cel_period.getStringCellValue().trim();
					if(cel_memo!=null){
						celval_memo = cel_memo.getStringCellValue().trim();
					}else{
						celval_memo="";
					}
					GoodsStockSchema GoodsStock=new GoodsStockSchema();
					GoodsStock.setid(NoUtil.getMaxNo("GoodsStockID", 11));
					GoodsStock.setgiftID(GiftID);
					if(celval_goodsname.length()>100){
						this.Response.setError("第" + (j + 1) + "行商品名称长度大于100,请重新填写");
						return;
					}else{
						GoodsStock.setgoodsName(celval_goodsname);
					}
					if(celval_card.length()>255){
						this.Response.setError("第" + (j + 1) + "行卡号长度大于255,请重新填写");
						return;
					}else{
						GoodsStock.setcardNo(celval_card);;
					}
					if(celval_password.length()>100){
						this.Response.setError("第" + (j + 1) + "行密码长度大于100,请重新填写");
						return;
					}else{
						GoodsStock.setpassWord(celval_password);;
					}
					if(celval_memo.length()>100){
						this.Response.setError("第" + (j + 1) + "行备注长度大于100,请重新填写");
						return;
					}else{
						GoodsStock.setmemo(celval_memo);
					}
					if(DateUtil.parse(celval_period)==null){
						this.Response.setError("第" + (j + 1) + "行时间格式错误,请重新填写");
						return;
					}
					GoodsStock.setoutDate(DateUtil.parse(celval_period));
					//初始化状态为0
					GoodsStock.setstatus("0");
					// 定义时间格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date= sdf.parse(PubFun.getCurrent());
					GoodsStock.setcreateDate(date);
					GoodsStock.setcreateUser(User.getUserName());
					transaction.add(GoodsStock,Transaction.INSERT);
				}
				String LastNum=GiftClassify.getlastNum();
				if(StringUtil.isInteger(LastNum)){
					GiftClassify.setlastNum(String.valueOf(Integer.parseInt(LastNum)+(maxRow - 9)));
				}else{
					this.Response.setMessage("礼品库存异常，请联系管理员，礼品名称："+GiftClassify.getgiftName());
				}
				//更新库存
				transaction.add(GiftClassify,Transaction.UPDATE);
				if(transaction.commit()){
					this.Response.setMessage("共上传了" + String.valueOf(maxRow - 9)+ "个商品库存！");
				}else{
					this.Response.setMessage("导入异常！请选择正确模板！");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入异常！请选择正确模板！");
			}

		}
		/**
		 * 
		* @Title: uploadGoodsStock04 
		* @Description: TODO(上传库存信息-卡号上传) 
		* @return void    返回类型 
		* @author
		 */
			public void uploadGoodsStock04() {
				String GiftID=$V("GiftID");
				GiftClassifySchema GiftClassify=new GiftClassifySchema();
				GiftClassify.setid(GiftID);
				if(!GiftClassify.fill()){
					this.Response.setError("没有查询到该礼品信息，请联系管理员！");
					return;
				}
				try {
					String fileaddress = $V("fileaddress");
					FileInputStream finput = null;
					try {
						finput = new FileInputStream(fileaddress);
					} catch (FileNotFoundException e) {
						logger.error(e.getMessage(), e);
						this.Response.setError("文件没找到，导入失败！请选择正确模板");
						return;
					}

					POIFSFileSystem fs = null;
					try {
						fs = new POIFSFileSystem(finput);
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
						this.Response.setError("IO异常，导入失败！请选择正确模板");
						return;
					}
					HSSFWorkbook wb = null;
					try {
						wb = new HSSFWorkbook(fs);
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
						this.Response.setError("转换对象异常，导入失败！请选择正确模板");
						return;
					}
					HSSFSheet sheet = wb.getSheetAt(0);
					try {
						finput.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
						this.Response.setError("关闭IO异常，导入失败！请选择正确模板");
						return;
					}
					// 最大行数
					int maxRow = sheet.getLastRowNum();
					if (maxRow - 9 <= 0) {
						this.Response.setError("导入失败！导入的文件中不包含有效数据！");
						return;
					}
					// 检查是否有空
					for (int j = 10; j < maxRow + 1; j++) {
						// 获取行数据
						HSSFRow row = sheet.getRow(j);
						//产品名称
						HSSFCell cel_goodsname = row.getCell(1);
						String celval_goodsname = "";
						//卡号
						HSSFCell cel_card = row.getCell(2);
						String celval_card = "";
						//有效期
						HSSFCell cel_period = row.getCell(3);
						String celval_period = "";
						//备注
						HSSFCell cel_memo = row.getCell(4);
						String celval_memo = "";
						//校验是否存在空数据
						if ((cel_card == null) ||cel_period==null||cel_goodsname==null) {
							this.Response.setError("第" + (j + 1) + "行非'备注'列有空数据，请修改后重新上传！");
							return;
						}
						//校验文本格式
						if(cel_card.getCellType() != 1) {
							this.Response.setError("第" + (j + 1) + "行卡号为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
							return;
						}else if(cel_period.getCellType() != 1) {
							this.Response.setError("第" + (j + 1) + "行有效期为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
							return;
						}else if(cel_memo!=null&&cel_memo.getCellType() != 1) {
							this.Response.setError("第" + (j + 1) + "行备注内容为非文本类型，请修改格式或重新下载模板，切记不要修改单元格格式");
							return;
						}else {
							celval_goodsname = cel_goodsname.getStringCellValue().trim();
							celval_card = cel_card.getStringCellValue().trim();
							celval_period = cel_period.getStringCellValue().trim();
							if(cel_memo!=null){
								celval_memo = cel_memo.getStringCellValue().trim();
							}else{
								celval_memo="";
							}
						}
						if (celval_goodsname.equals("")||celval_card.equals("")||celval_period.equals("")) {
							this.Response.setError("第" + (j + 1) + "行非'备注'列有空数据，请修改后重新上传！");
							return;
						}
						// 非法字符串检查
						String regex = "script|iframe";
						Pattern p = Pattern.compile(regex);
						Matcher m1 = p.matcher(celval_goodsname.toLowerCase());
						Matcher m2 = p.matcher(celval_card.toLowerCase());
						Matcher m4 = p.matcher(celval_period.toLowerCase());
						if(StringUtil.isNotEmpty(celval_memo)){
							Matcher m5 = p.matcher(celval_memo.toLowerCase());
							if (m5.find()) {
								this.Response.setError("第" + (j + 1) + "行备注中有非法字符,请重新填写");
								return;
							}
						}
						if (m1.find()||m2.find() || m4.find()) {
							this.Response.setError("第" + (j + 1) + "行有非法字符,请重新填写");
							return;
						}
						// 判断是否有重复卡号和密码
						for (int i = 2; i <4; i++) {
							for (int k = j + 1; k < maxRow; k++) {
								HSSFRow row_next = sheet.getRow(k);
								if ((row_next.getCell(i) == null)) {
									continue;
								}
								String celval_2_next= row_next.getCell(i).getStringCellValue().trim();
								if (celval_2_next.equals(celval_card)) {
									this.Response.setError("第" + (j + 1) + "行与第" + (k + 1)+ "行的第"+(i+1)+"列数据重复,请重新填写");
									return;
								}
							}
						}
					}
					//添加数据
					Transaction transaction = new Transaction();
					for (int j = 10; j < maxRow + 1; j++) {
						// 获取行数据
						HSSFRow row = sheet.getRow(j);
						//产品名称
						HSSFCell cel_goodsname = row.getCell(1);
						String celval_goodsname = "";
						//卡号
						HSSFCell cel_card = row.getCell(2);
						String celval_card = "";
						//有效期
						HSSFCell cel_period = row.getCell(3);
						String celval_period = "";
						//备注
						HSSFCell cel_memo = row.getCell(4);
						String celval_memo = "";
						//取值
						celval_goodsname = cel_goodsname.getStringCellValue().trim();
						celval_card = cel_card.getStringCellValue().trim();
						celval_period = cel_period.getStringCellValue().trim();
						if(cel_memo!=null){
							celval_memo = cel_memo.getStringCellValue().trim();
						}else{
							celval_memo="";
						}
						GoodsStockSchema GoodsStock=new GoodsStockSchema();
						GoodsStock.setid(NoUtil.getMaxNo("GoodsStockID", 11));
						GoodsStock.setgiftID(GiftID);
						if(celval_goodsname.length()>100){
							this.Response.setError("第" + (j + 1) + "行商品名称长度大于100,请重新填写");
							return;
						}else{
							GoodsStock.setgoodsName(celval_goodsname);
						}
						if(celval_card.length()>255){
							this.Response.setError("第" + (j + 1) + "行卡号长度大于255,请重新填写");
							return;
						}else{
							GoodsStock.setcardNo(celval_card);;
						}
						if(celval_memo.length()>100){
							this.Response.setError("第" + (j + 1) + "行备注长度大于100,请重新填写");
							return;
						}else{
							GoodsStock.setmemo(celval_memo);
						}
						if(DateUtil.parse(celval_period)==null){
							this.Response.setError("第" + (j + 1) + "行时间格式错误,请重新填写");
							return;
						}
						
						GoodsStock.setoutDate(DateUtil.parse(celval_period));
						//初始化状态为0
						GoodsStock.setstatus("0");
						// 定义时间格式
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date= sdf.parse(PubFun.getCurrent());
						GoodsStock.setcreateDate(date);
						GoodsStock.setcreateUser(User.getUserName());
						transaction.add(GoodsStock,Transaction.INSERT);
					}
					String LastNum=GiftClassify.getlastNum();
					if(StringUtil.isInteger(LastNum)){
						GiftClassify.setlastNum(String.valueOf(Integer.parseInt(LastNum)+(maxRow - 9)));
					}else{
						this.Response.setMessage("礼品库存异常，请联系管理员，礼品名称："+GiftClassify.getgiftName());
					}
					//更新库存
					transaction.add(GiftClassify,Transaction.UPDATE);
					if(transaction.commit()){
						this.Response.setMessage("共上传了" + String.valueOf(maxRow - 9)+ "个商品库存！");
					}else{
						this.Response.setMessage("导入异常！请选择正确模板！");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					this.Response.setError("导入异常！请选择正确模板！");
				}

			}
	
	public void sortGift() {
		DataTable dt = (DataTable) Request.get("DT");
		GiftClassifySet set = new GiftClassifySet();
		GiftClassifySchema schema;
		for (int i = 0; i < dt.getRowCount(); i++) {
			schema = new GiftClassifySchema();
			schema.setid(dt.getString(i, "id"));
			schema.fill();
			schema.setorderFlag(dt.getString(i, "OrderFlag"));
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());

			set.add(schema);
		}
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void treeDataBind(TreeAction ta) {
		////////////////////////////此处仅是个样例，福禄商品查询 通过http协议实现  FuLuQuery.jsp
		DataTable dt = null;
		Mapx params = ta.getParams();
		long CatalogType = params.getLong("Type");
		//int parentLevel = params.getInt("ParentLevel");
		String parentID = (String) params.get("ParentID");
		StringBuffer sql = new StringBuffer("select ChannelCode,InnerChannelCode ID,ParentInnerChanelCode ParentID,ChannelName Name,ChannelLevel as TreeLevel from ChannelInfo where Prop1='Y' ");
		if(StringUtil.isNotEmpty(parentID)){
			String innercode = new QueryBuilder("select InnerChannelCode from ChannelInfo where ChannelCode='"+parentID+"'").executeString();
			if("Member".equals(parentID)){
				innercode = new QueryBuilder("select InnerChannelCode from ChannelInfo where ChannelCode='b2b'").executeString();
				if(StringUtil.isNotEmpty(innercode)){
					sql.append(" AND InnerChannelCode !='"+innercode+"' AND ParentInnerChanelCode !='"+innercode+"' ");
				}
			}else{
				if(StringUtil.isNotEmpty(innercode)){
					sql.append(" AND (InnerChannelCode='"+innercode+"' or ParentInnerChanelCode='"+innercode+"')");
				}
			}
		}
		sql.append(" order by CreateDate ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		ta.setRootText("demo实验tree");
		dt.setWebMode(false);
		ta.bindData(dt);
		if (CatalogType == 1) {
			List items = ta.getItemList();
			for (int i = 1; i < items.size(); i++) {
				TreeItem item = (TreeItem) items.get(i);
				if ("Y".equals(item.getData().getString("SingleFlag"))) {
					item.setIcon("Icons/treeicon11.gif");
				}
			}
		}
	}
}
