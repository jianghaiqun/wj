/**
 * 
 */
package com.sinosoft.cms.resource;

import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ClaimsInfoCompanyClassifySchema;
import com.sinosoft.schema.ClaimsInfoCompanyInfoSchema;
import com.sinosoft.schema.ClaimsInfoCompanyInfoSet;
import com.sinosoft.schema.ClaimsInfoSchema;
import com.sinosoft.schema.ClaimsInfoSet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcaiyun
 *
 */
public class ClaimsInfo extends Page {

	public static Mapx init(Mapx params) {
		// 保险公司下拉框
		params.put("supplier", HtmlUtil.codeToOptions("SupplierCode", true));
		params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo", true));
		return params;
	}
	
	public static Mapx initDialog(Mapx params) {
		String comCode = params.getString("comCode");
		if(StringUtil.isNotEmpty(comCode)){
			StringBuffer Detail=new StringBuffer("");
			DataTable dt = new QueryBuilder("SELECT name FROM ClaimsInfoCompanyClassify where comCode=? order by orderFlag asc",comCode).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					Detail.append(dt.getString(i, 0) + ",");
				}
				if(StringUtil.isEmpty(Detail)){
					params.put("Detail","");
				}else{
					params.put("Detail",Detail.deleteCharAt(Detail.length()-1).toString());
				}
			}
		}
		return params;
	}
	
	public static Mapx initClassifyInfo (Mapx params) {
		String id = params.getString("id");
		String comCode = "";
		String isHot = "";
		if (StringUtil.isNotEmpty(id) ) {
			ClaimsInfoSchema schema = new ClaimsInfoSchema();
			schema.setid(id);
			if (schema.fill()) {
				comCode = schema.getcomCode();
				isHot = schema.getisHot();
				params.putAll(schema.toMapx());
			}
		}
		
		DataTable comDt = new QueryBuilder("select comCode, comClassifyName from ClaimsInfoCompanyInfo").executeDataTable();
		Mapx comMap = new Mapx();
		if (comDt != null && comDt.getRowCount() > 0) {
			int count = comDt.getRowCount();
			for (int i = 0; i < count; i++) {
				comMap.put(comDt.getString(i, 0), comDt.getString(i, 1));
			}
		}
		if (StringUtil.isNotEmpty(comCode)) {
			params.put("supplier", HtmlUtil.mapxToOptions(comMap, comCode, true));
			Mapx map = new Mapx();
			DataTable dt = new QueryBuilder("SELECT id,name FROM ClaimsInfoCompanyClassify where comCode=? order by orderFlag asc",comCode).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int count = dt.getRowCount();
				for (int i = 0; i < count; i++) {
					map.put(dt.getString(i, 0), dt.getString(i, 1));
				}
			}
			Object[] checkedArray = null;
			String classifyId = params.getString("classifyId");
			if (StringUtil.isNotEmpty(classifyId)) {
				checkedArray = classifyId.split(",");
			}
			params.put("ClassifyType",HtmlUtil.mapxToCheckboxes("ClassifyType", map, checkedArray));
		} else {
			params.put("supplier", HtmlUtil.mapxToOptions(comMap, true));
		}
		if (StringUtil.isNotEmpty(isHot)) {
			params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo", isHot, true));
		} else {
			params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo", true));
		}
		
		params.put("allowType", Config.getValue("AllowAttachExt"));
		
		//允许上传附件大小设置
		String fileSize = "6291456"; //默认6M;
		params.put("fileMaxSize", fileSize);
		long size = Long.parseLong(fileSize);
		String fileMaxSizeHuman = "";
		if (size < 1048576) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1024, 1) + "K";
		} else if (size < 1073741824) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1048576, 1) + "M";
		}
		params.put("fileMaxSizeHuman", fileMaxSizeHuman);
		
		return params;
	}
	
	public static Mapx initClassify(Mapx params) {
		String comCode = params.getString("comCode");
		Mapx map = new Mapx();
		DataTable dt = new QueryBuilder("SELECT id,name FROM ClaimsInfoCompanyClassify where comCode=? order by orderFlag asc",comCode).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				map.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		}
		params.put("ClassifyType",HtmlUtil.mapxToCheckboxes("ClassifyType", map));
		return params;
	}
	
	public static Mapx initEditDialog(Mapx params) {
		String id = params.getString("ID");
		ClaimsInfoSchema schema = new ClaimsInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			params.putAll(schema.toMapx());
		}
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String comCode = (String) dga.getParams().get("comCode");
		String recomFlag = (String) dga.getParams().get("recomFlag");
		
		QueryBuilder qb = new QueryBuilder("select comCode,comName,comClassifyName,orderFlag,recommendFlag,(select CodeName from zdcode where codetype='YesOrNo' and codevalue=recommendFlag) recommFlagName from ClaimsInfoCompanyInfo where 1=1 ");
		if (StringUtil.isNotEmpty(comCode)) {
			qb.append(" and comCode = '" + comCode + "'");
		}
		if (StringUtil.isNotEmpty(recomFlag)) {
			qb.append(" and recommendFlag = '" + recomFlag + "'");
		}
		qb.append(" order by orderFlag asc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		String comCode = (String) dga.getParams().get("comCode");
		String isHot = (String) dga.getParams().get("isHot");
		QueryBuilder qbt = new QueryBuilder("select id, name from ClaimsInfoCompanyClassify ");
		String path = Config.getValue("StaticResourcePath");
		QueryBuilder qb = new QueryBuilder("select id,name,suffix,fileSize,(select a.comClassifyName from ClaimsInfoCompanyInfo a where a.comCode = ClaimsInfo.comCode) comName,classifyId, (select CodeName from zdcode where codetype='YesOrNo' and codevalue=isHot) isHotName,orderFlag,isHot,'' as classifyName,CONCAT('"+path+"/',path,fileName) as Link from ClaimsInfo where 1=1 ");
		if (StringUtil.isNotEmpty(comCode)) {
			qb.append(" and comCode = '" + comCode + "'");
			qbt.append(" where comCode = '" + comCode + "'");
		}
		if (StringUtil.isNotEmpty(isHot)) {
			qb.append(" and isHot = '" + isHot + "'");
		}
		qb.append(" order by orderFlag asc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		DataTable dtt = qbt.executeDataTable();
		Map<String, String> map = new HashMap<String, String>();
		if (dtt != null && dtt.getRowCount() > 0) {
			int row = dtt.getRowCount();
			for (int i = 0; i < row; i++) {
				map.put(dtt.getString(i, 0), dtt.getString(i, 1));
			}
		}
		if (dt != null && dt.getRowCount() > 0){
			int row = dt.getRowCount();
			String classifyId = "";
			String[] ids;
			String classifyName = "";
			for (int i = 0; i < row; i++) {
				classifyId = dt.getString(i, "classifyId");
				classifyName = "";
				if (StringUtil.isNotEmpty(classifyId)) {
					ids = classifyId.split(",");
					for (int j = 0; j < ids.length; j++) {
						classifyName += (","+map.get(ids[j]));
					}
					dt.set(i, "classifyName", classifyName.substring(1));
				}
				//dt.set(i, "classifyName", classifyName.substring(1));
			}
		}
		dga.bindData(dt);
	}
	
	public void addCompany() {
		String comCode = Request.getString("supplierCode");
		String comClassifyName = Request.getString("comClassifyName");
		String orderFlag = Request.getString("orderFlag");
		String recommendFlag = Request.getString("recommendFlag");
		if (StringUtil.isEmpty(recommendFlag)) {
			recommendFlag = "N";
		}
		if (StringUtil.isEmpty(comCode)) {
			Response.setStatus(0);
			Response.setMessage("请选择保险公司!");
			return;
		}
		
		int count = new QueryBuilder("select count(1) from ClaimsInfoCompanyInfo where comCode = ?", comCode).executeInt();
		if (count > 0) {
			Response.setStatus(0);
			Response.setMessage("该保险公司已经设置过，不能重复设置!");
			return;
		}
		ClaimsInfoCompanyInfoSchema schema = new ClaimsInfoCompanyInfoSchema();
		schema.setcomCode(comCode);
		schema.setcomName(new QueryBuilder("select CodeName from zdcode where CodeType='SupplierCode' and CodeValue=?", comCode).executeString());
		schema.setcomClassifyName(comClassifyName);
		schema.setrecommendFlag(recommendFlag);
		if (StringUtil.isEmpty(orderFlag)) {
			schema.setorderFlag(System.currentTimeMillis());
		} else {
			schema.setorderFlag(orderFlag);
		}
		
		if (schema.insert()) {
			Response.setStatus(1);
			Response.setMessage("保存成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败!");
		}
	}
	
	public void saveCompany() {
		DataTable dt = (DataTable) Request.get("DT");
		ClaimsInfoCompanyInfoSet set = new ClaimsInfoCompanyInfoSet();
		ClaimsInfoCompanyInfoSchema schema;
		for (int i = 0; i < dt.getRowCount(); i++) {
			schema = new ClaimsInfoCompanyInfoSchema();
			schema.setcomCode(dt.getString(i, "comCode"));
			schema.fill();
			schema.setcomClassifyName(dt.getString(i, "comClassifyName"));
			schema.setorderFlag(dt.getString(i, "orderFlag"));
			if (StringUtil.isEmpty(dt.getString(i, "recommendFlag"))) {
				schema.setrecommendFlag("N");
			} else {
				schema.setrecommendFlag(dt.getString(i, "recommendFlag"));
			}
			schema.setModifyDate(new Date());
			schema.setModifyUser(User.getUserName());

			set.add(schema);
		}
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("保存发生错误!");
		}
	}
	
	public void delCompany() {
		String ids = Request.getString("IDs");
		if (StringUtil.isEmpty(ids)) {
			Response.setStatus(0);
			Response.setMessage("请选择要删除的数据!");
		}
		ids = ("'"+ids.replace(",", "','")+"'");
		//事务创建
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from ClaimsInfoCompanyClassify where comCode in ("+ids+")"));
		trans.add(new QueryBuilder("delete from ClaimsInfoCompanyInfo where comCode in ("+ids+")"));
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("删除成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("删除失败！");
			return;
		}
	}
	
	public void setClassifys() {
		String comCode=Request.getString("comCode");
		if (StringUtil.isEmpty(comCode)) {
			Response.setStatus(0);
			Response.setMessage("请选择要设置二级分类的保险公司!");
			return;
		}
		
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from ClaimsInfoCompanyClassify where comCode=?",comCode));
		String num = Request.getString("DetailNum");
		String index = Request.getString("index");
		String[] index_array=index.split(",");
		ClaimsInfoCompanyClassifySchema schema;
		for (int i = 0; i < Integer.parseInt(num); i++) {
			schema = new ClaimsInfoCompanyClassifySchema();
			schema.setid(comCode + "_" + i + "e");
			schema.setcomCode(comCode);
			schema.setname(Request.getString("Detail_" +index_array[i]));
			schema.setorderFlag(i+1);
			schema.setCreateUser(User.getUserName());
			schema.setCreateDate(new Date());
			trans.add(schema, Transaction.INSERT);
		}
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("二级分类设置成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("二级分类设置失败！");
			return;
		}
	}
	
	public void saveClaimsInfo() {
		DataTable dt = (DataTable) Request.get("DT");
		ClaimsInfoSet set = new ClaimsInfoSet();
		ClaimsInfoSchema schema;
		for (int i = 0; i < dt.getRowCount(); i++) {
			schema = new ClaimsInfoSchema();
			schema.setid(dt.getString(i, "id"));
			schema.fill();
			schema.setname(dt.getString(i, "name"));
			schema.setorderFlag(dt.getString(i, "orderFlag"));
			schema.setcomCode(dt.getString(i, "comCode"));
			schema.setclassifyId(dt.getString(i, "classifyId"));
			schema.setisHot(dt.getString(i, "isHot"));
			schema.setModifyDate(new Date());
			schema.setModifyUser(User.getUserName());

			set.add(schema);
		}
		if (set.update()) {
			Response.setStatus(1);
			Response.setMessage("保存成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存发生错误!");
		}
	}
	
	public void editSave() {
		String id = Request.getString("id");
		if (StringUtil.isEmpty(id)) {
			Response.setStatus(0);
			Response.setMessage("请选择要修改的附件!");
			return;
		}
		ClaimsInfoSchema schema = new ClaimsInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			schema.setname(Request.getString("FileName"));
			schema.setcomCode(Request.getString("comCode"));
			schema.setisHot(Request.getString("isHot"));
			schema.setclassifyId(Request.getString("ClassifyType"));
			String orderFlag = Request.getString("orderFlag");
			if (StringUtil.isNotEmpty(orderFlag)) {
				schema.setorderFlag(orderFlag);
			}
			schema.setorderFlag(Request.getString("orderFlag"));
			if (schema.update()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("未查询到附件信息!");
		}
	}
	public void delClaimsInfo() {
		String ids=Request.getString("IDs");
		if (StringUtil.isEmpty(ids)) {
			Response.setStatus(0);
			Response.setMessage("请选择要删除的数据!");
		}
		ids = ("'"+ids.replace(",", "','")+"'");
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from ClaimsInfo where id in ("+ids+")"));
		String path = Config.getContextRealPath()+ Config.getValue("UploadDir") + "/kxb/";
		DataTable dt = new QueryBuilder("select CONCAT('"+path+"',path,fileName) as Link from ClaimsInfo where id in ("+ids+")").executeDataTable();
		
		if (dt != null && dt.getRowCount() > 0) {
			try {
				for (int i = 0; i < dt.getRowCount(); i++) {
					OSSUploadFile.deleteObject(dt.getString(i, 0));
				}
			}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("删除成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("删除失败！");
			return;
		}
		
	}
	
	public void getClassifyType() {
		String comCode = $V("comCode");
		if (StringUtil.isEmpty(comCode)) {
			Response.put("ClassifyType", "");
			return;
		}
		Mapx map = new Mapx();
		DataTable dt = new QueryBuilder("SELECT id,name FROM ClaimsInfoCompanyClassify where comCode=? order by orderFlag asc",comCode).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				map.put(dt.getString(i, 0), dt.getString(i, 1));
			}
			Response.put("ClassifyType", HtmlUtil.mapxToCheckboxes("ClassifyType", map));
		} else {
			Response.put("ClassifyType", "");
		}
		
	}
	
}
