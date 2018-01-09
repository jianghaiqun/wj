/**
 * 
 */
package cn.com.sinosoft.action.shop;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.allinpay.ets.client.StringUtil;
import com.sinosoft.cms.resource.uploader.UploadCustomClaimsData;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.ImageJDKUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.UnderwritingOfflineHealthinfoSchema;
import com.sinosoft.schema.UnderwritingOfflineImagesSchema;
import com.sinosoft.schema.UnderwritingOfflineImagesSet;
import com.sinosoft.schema.UnderwritingOfflineInfoSchema;

import cn.com.sinosoft.bean.UnderwritingOfflineImages;
import cn.com.sinosoft.bean.UnderwritingOfflineInfo;
import cn.com.sinosoft.bean.UnderwritingOfflineHealthinfo;
import cn.com.sinosoft.service.OrderConfigNewService;

import java.util.regex.Pattern; 
import java.util.regex.Matcher;
/**
 * @author wangcaiyun
 *
 */
@ParentPackage("shop")
public class UnderwritingOfflineAction extends BaseShopAction {

	/**
	 * 
	 */
	@Resource
	private OrderConfigNewService orderConfigNewService;

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request = ServletActionContext.getRequest();

	private String productDetailed;//产品详细页地址
	
	private String productUse;//还购买了...

	private String productId;//产品编码

	private String productName;//产品名
	
	private String imagePath;//图片路径

	private UnderwritingOfflineInfo underwritingOfflineInfo;

	private List<UnderwritingOfflineImages> underwritingOfflineImagesList = new ArrayList<UnderwritingOfflineImages>();

	private UnderwritingOfflineHealthinfo UnderwritingOfflineHealthinfo ;
	
	public String init() {
		String productNameSql = "SELECT productname FROM sdsearchrelaproduct WHERE ProductID ='" + productId + "'";
		productName = new QueryBuilder(productNameSql).executeString() + "【" + productId.substring(0, 4) + "】";
		if(productId.equals("224801001")){
			return "question";
		}
		return "init";
	}
	


	public String healthyQuestion() {
		return "question";
	}

	public String success() {
		productDetailed =  new QueryBuilder("SELECT URL FROM sdsearchrelaproduct WHERE productId='"+productId+"'").executeString();
		String SalesVolume="";
		String sql ="SELECT  a.URL,  a.ProductName, a.InitPrem,a.SalesVolume,a.suppliercode2   FROM sdsearchrelaproduct a,  "
				+ " RecommendToDetail r  WHERE a.Prop1 = r.RelaArticleID AND r.ProductID ='"+productId+"'  order by r.Prop2 + 0 asc limit 3 ";
		DataTable dt =new QueryBuilder(sql ).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			productUse = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				SalesVolume=dt.getString(i, "SalesVolume");
				if(StringUtil.isEmpty(SalesVolume)){
					SalesVolume="1";
				}
				productUse = productUse +"<dl class=\"tj-listdl\"> " +"<a href=\"  "+dt.getString(i, "URL")+"  \">"
						+ " <dt><span class=\"tj-logo icon_C"+dt.getString(i, "suppliercode2")+"\"></span></dt>  "
						+ "<dd class=\"tj-listdd\"><a href=\"  "+dt.getString(i, "URL")+"  \">"+dt.getString(i, "ProductName")+"</a></dd> <dd class=\"tj-listdd2\">"
						+ "<span class=\"tj-listmoney f_mi\"> ¥"+dt.getString(i, "InitPrem")+"</span><span class=\"tj-num\">"+SalesVolume+"人购买</span></dd>"
								+ "</a></dl> ";
			}
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	public void saveApply() throws IOException {
		String channelSn = orderConfigNewService.getChannelSn( request, "");
		Mapx<String, String> underwritingOfflineProducts =  CacheManager.getMapx("Code", "underwritingOfflineProducts");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html"); 
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(!underwritingOfflineProducts.containsKey(productId)){
			out.println("<script>parent.afterUpLoad2('该产品不支持线下核保！')</script>");
			return;
		}
		getUnderwritingOfflineImagesListUse(imagePath);
		productName = new QueryBuilder("SELECT productname FROM sdsearchrelaproduct WHERE ProductID ='" + productId + "'").executeString() ;
		try {
			if (underwritingOfflineInfo == null) {
				out.println("<script>parent.afterUpLoad2('请按照页面的填写项填写后提交')</script>");
				return;
			}
			if (StringUtil.isEmpty(underwritingOfflineInfo.getName())) {
				out.println("<script>parent.afterUpLoad2('请填写姓名！')</script>");
				return;
			}
			if (StringUtil.isEmpty(underwritingOfflineInfo.getEmail())) {
				out.println("<script>parent.afterUpLoad2('请填写邮箱！')</script>");
				return;
			}
			if (StringUtil.isEmpty(underwritingOfflineInfo.getMobile())) {
				out.println("<script>parent.afterUpLoad2('请填写手机号！')</script>");
				return;
			}
			if (StringUtil.isEmpty(underwritingOfflineInfo.getSituationDesc())) {
				out.println("<script>parent.afterUpLoad2('请填写情况说明！')</script>");
				return;
			}
			if (underwritingOfflineImagesList == null || underwritingOfflineImagesList.size() == 0) {
				out.println("<script>parent.afterUpLoad2('请选择要上传的图片！')</script>");
				return;
			}
			
			Date createTime = new Date();
			UnderwritingOfflineInfoSchema infoSchema = new UnderwritingOfflineInfoSchema();
			BeanUtils.copyProperties(infoSchema, underwritingOfflineInfo);
			QueryBuilder queryMailBuilder = new QueryBuilder(
					"SELECT codeName FROM zdcode WHERE codetype='UnderwritingOfflineInsureEmail' AND codevalue=?");
			queryMailBuilder.add(infoSchema.getProductId().substring(0, 4));
			String insureEmail = queryMailBuilder.executeString();
			if (insureEmail == null) {
				insureEmail = "";
			}
			infoSchema.setInsureEmail(insureEmail);
			infoSchema.setProductId(productId);
			infoSchema.setProductName(productName);
			infoSchema.setDealStatus("0");
			infoSchema.setCreateTime(createTime);
			infoSchema.setProp1(channelSn);
			infoSchema.insertReturnPk();

			UnderwritingOfflineImagesSet imageSet = new UnderwritingOfflineImagesSet();
			File f;
			String fileSize = "";
			for (UnderwritingOfflineImages image : underwritingOfflineImagesList) {
				f= new File(image.getImageUrl());
				if (f.exists() && f.isFile()){
			    	fileSize = FileUtils.byteCountToDisplaySize(f.length()).replace(" ", "");
			    	UnderwritingOfflineImagesSchema imageSchema = new UnderwritingOfflineImagesSchema();
					BeanUtils.copyProperties(imageSchema, image);
					imageSchema.setInfoId(infoSchema.getId());
					imageSchema.setImageSize(fileSize);
					if(image.getImageUrl().toLowerCase().endsWith(".bmp")){
						imageSchema.setThumUrl1(ImageJDKUtil.compressPic(image.getImageUrl(), "1", 0.1));
						if(UploadCustomClaimsData.isCondenseFile(fileSize)){
							imageSchema.setThumUrl2(ImageJDKUtil.compressPic(image.getImageUrl(), "2", 0.6));
						}else{
							imageSchema.setThumUrl2(image.getImageUrl());
						}
					}else{
						imageSchema.setThumUrl1(UploadCustomClaimsData.scaleRateImageFile(image.getImageUrl(), "1", 0.1));
						if(UploadCustomClaimsData.isCondenseFile(fileSize)){
							imageSchema.setThumUrl2(UploadCustomClaimsData.scaleRateImageFile(image.getImageUrl(), "2", 0.6));
						}else{
							imageSchema.setThumUrl2(image.getImageUrl());
						}
					}
					imageSchema.setCreateTime(createTime);
					imageSet.add(imageSchema);
			    }else{
			    	infoSchema.delete();
			    	out.println("<script>parent.afterUpLoad2('未找到图片信息，请重新上传！')</script>");
			    	return;
			    } 
			}
			if (!imageSet.insert()) {
				infoSchema.delete();
				out.println("<script>parent.afterUpLoad2('提交数据有误，请检查或者联系客服~')</script>");
				return;
			}
		} catch (Exception e) {
			out.println("<script>parent.afterUpLoad2('提交数据有误，请检查或者联系客服~')</script>");
			return;
		}
		out.println("<script>parent.afterUpLoad2('success')</script>");
		
	}
	
	
	
	public Map<String, Object> setResult(String status, String message) {
		Map<String, Object> tData = new HashMap<String, Object>();
		tData.put("status", status);
		tData.put("message", message);
		return tData;
	}
	
	public void getUnderwritingOfflineImagesListUse(String Images){
		underwritingOfflineImagesList = new ArrayList<UnderwritingOfflineImages>();
		String[] Image = Images.split(",");
		String ServerPath = Config.getValue("newPolicyPath");//测试//Config.getContextRealPath();
		String thistime = DateUtil.toString(new Date(), "yyyy/MM/dd/");
		UnderwritingOfflineImages underwritingOfflineImages = null;
		for(int i=0;i < Image.length;i++){
			underwritingOfflineImages = new UnderwritingOfflineImages();
			underwritingOfflineImages.setImageUrl(ServerPath+"/EPolicy/UnderwritingOffline/"+thistime +Image[i]);
			underwritingOfflineImagesList.add(underwritingOfflineImages);
		}
	}
	
	
	
	
	public String saveHealthyQuestion() throws IOException {
		String channelSn = orderConfigNewService.getChannelSn( request, "");
		Transaction trans = new Transaction();
		Map<String, String> data = new HashMap<String, String>();
		if (!isNumeric(UnderwritingOfflineHealthinfo.getHeight()) ||!isDecimal(UnderwritingOfflineHealthinfo.getWeight()) ){
			data.put("massage", "亲，身高体重必须是数字喔!");
			return ajaxJson(data);
		}else if (!isNumeric(underwritingOfflineInfo.getMobile())){
			data.put("massage", "请输入11位电话号码!");
			return ajaxJson(data);
		}
		if(!productId.equals("224801001")){
			data.put("massage", "该产品不支持线下核保!");
			return ajaxJson(data);
		}
		UnderwritingOfflineHealthinfoSchema underwritingOfflineHealthinfoSchema = new UnderwritingOfflineHealthinfoSchema();
		UnderwritingOfflineInfoSchema underwritingOfflineInfoSchema = new UnderwritingOfflineInfoSchema();
		try {
			BeanUtils.copyProperties(underwritingOfflineHealthinfoSchema, UnderwritingOfflineHealthinfo);
			BeanUtils.copyProperties(underwritingOfflineInfoSchema, underwritingOfflineInfo);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			data.put("massage", "数据操作有误，请联系管理员！");
			return ajaxJson(data);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
			data.put("massage", "数据操作有误，请联系管理员！");
			return ajaxJson(data);
		}
		String productNameSql = "SELECT productname FROM sdsearchrelaproduct WHERE ProductID ='" + productId + "'";
		productName = new QueryBuilder(productNameSql).executeString();
		
		QueryBuilder queryMailBuilder = new QueryBuilder(
				"SELECT codeName FROM zdcode WHERE codetype='UnderwritingOfflineInsureEmail' AND codevalue=?");
		queryMailBuilder.add(productId.substring(0, 4));
		String insureEmail = queryMailBuilder.executeString();
		if (insureEmail == null) {
			insureEmail = "";
		}
		underwritingOfflineInfoSchema.setInsureEmail(insureEmail);
		underwritingOfflineInfoSchema.setProductId(productId);
		underwritingOfflineInfoSchema.setProductName(productName);
		underwritingOfflineInfoSchema.setCreateTime(new Date());
		underwritingOfflineInfoSchema.setSituationDesc("");
		underwritingOfflineInfoSchema.setDealStatus("0");
		underwritingOfflineInfoSchema.setProp1(channelSn);
		int HealthinfoId = Integer.parseInt( new QueryBuilder("SELECT id  FROM  underwriting_offline_info    ORDER BY id DESC  LIMIT 1").executeString()) +1;
		underwritingOfflineHealthinfoSchema.setInfoId(HealthinfoId);
		underwritingOfflineInfoSchema.setId(HealthinfoId);
		underwritingOfflineHealthinfoSchema.setIdType("0");//0=身份证
		trans.add(underwritingOfflineHealthinfoSchema, Transaction.INSERT);
		trans.add(underwritingOfflineInfoSchema, Transaction.INSERT);
		
		if(trans.commit()){
			data.put("massage", "success");
		}else{
			trans = new Transaction();
			HealthinfoId = Integer.parseInt( new QueryBuilder("SELECT id  FROM  underwriting_offline_info    ORDER BY id DESC  LIMIT 1").executeString()) +1;
			underwritingOfflineHealthinfoSchema.setInfoId(HealthinfoId);
			underwritingOfflineInfoSchema.setId(HealthinfoId);
			trans.add(underwritingOfflineHealthinfoSchema, Transaction.INSERT);
			trans.add(underwritingOfflineInfoSchema, Transaction.INSERT);
			if(!trans.commit()){
			data.put("massage", "未知错误，请联系管理员！");
			}
		}
		return ajaxJson(data);
	}
	
	public boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	/**
	 * isDecimal:(浮点数判断). <br/>
	 * @author guozc
	 * @param str
	 * @return
	 */
	public boolean isDecimal(String str){
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
	    Matcher isNum = pattern.matcher(str);
	    if( !isNum.matches() ){
	    	return false; 
	    } 
	    return true; 
	}
	
	
	

 
	public UnderwritingOfflineHealthinfo getUnderwritingOfflineHealthinfo() {
		return UnderwritingOfflineHealthinfo;
	}

	public void setUnderwritingOfflineHealthinfo(UnderwritingOfflineHealthinfo underwritingOfflineHealthinfo) {
		this.UnderwritingOfflineHealthinfo = underwritingOfflineHealthinfo;
	}


	public String getProductDetailed() {
		return productDetailed;
	}

	public void setProductDetailed(String productDetailed) {
		this.productDetailed = productDetailed;
	}

	public String getProductUse() {
		return productUse;
	}

	public void setProductUse(String productUse) {
		this.productUse = productUse;
	}
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public UnderwritingOfflineInfo getUnderwritingOfflineInfo() {
		return underwritingOfflineInfo;
	}

	public void setUnderwritingOfflineInfo(UnderwritingOfflineInfo underwritingOfflineInfo) {
		this.underwritingOfflineInfo = underwritingOfflineInfo;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<UnderwritingOfflineImages> getUnderwritingOfflineImagesList() {
		return underwritingOfflineImagesList;
	}

	public void setUnderwritingOfflineImagesList(List<UnderwritingOfflineImages> underwritingOfflineImagesList) {
		this.underwritingOfflineImagesList = underwritingOfflineImagesList;
	}

 
	
}
