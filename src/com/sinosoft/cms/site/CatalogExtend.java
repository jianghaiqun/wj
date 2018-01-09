package com.sinosoft.cms.site;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZDColumnRelaSchema;
import com.sinosoft.schema.ZDColumnRelaSet;
import com.sinosoft.schema.ZDColumnSchema;
import com.sinosoft.schema.ZDColumnSet;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CatalogExtend extends Page {
	public static String Input = "1";

	public static String ImageInput = "7";

	public static String HtmlInput = "8";

	public static Mapx InputTypeMap = new Mapx();

	static {
		InputTypeMap.put(Input, "文本框");
		InputTypeMap.put(ImageInput, "媒体库图片框");
		InputTypeMap.put(HtmlInput, "HTML");
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ColumnID");
		if (StringUtil.isEmpty(ID)) {
			params.put("ImagePath", "upload/Image/nopicture.jpg");
			params.put("ImageFile", (Config.getValue("StaticResourcePath") + "/" + "upload/Image/nopicture.jpg").replaceAll("//", "/"));
			params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap));
		} else {
			String CatalogID = params.getString("CatalogID");
			QueryBuilder qb = new QueryBuilder(
					"select b.ColumnID as id,a.Code,a.InputType,a.Name,b.ID as ValueID,b.ColumnID,b.TextValue from ZDColumn a, ZDColumnValue b where a.ID = b.ColumnID and b.RelaType='"
							+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND
							+ "' and b.RelaID =? and b.ColumnID= ? order by a.OrderFlag asc", CatalogID, Long.parseLong(ID));
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			params = dr.toMapx();
			String inputType = dr.getString("InputType");
			if (Input.equals(inputType)) {
				params.put("Text", dr.getString("TextValue"));
			} else if (ImageInput.equals(inputType)) {
				params.put("ImagePath", dr.getString("TextValue"));
				params.put("ImageFile", (Config.getValue("StaticResourcePath")+ "/" + dr.getString("TextValue")));
			} else if (HtmlInput.equals(inputType)) {
				params.put("Content", dr.getString("TextValue"));
			}
			params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap, inputType));
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		QueryBuilder qb = new QueryBuilder(
				"select a.Code,a.InputType,a.Name,b.ID,b.ColumnID,b.TextValue from ZDColumn a, ZDColumnValue b where a.ID = b.ColumnID and b.RelaType='"
						+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND + "' and b.RelaID =? order by a.OrderFlag asc", CatalogID);
		DataTable dt = qb.executeDataTable();
		String path = Config.getValue("StaticResourcePath");
		String Str = "";
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (ImageInput.equals(dt.getString(i, "InputType"))) {
				dt.set(i, "TextValue", "<img src='" + path +"/"+ dt.getString(i, "TextValue")
						+ "' width='100' height='75'>");
			}
			if (HtmlInput.equals(dt.getString(i, "InputType"))) {
				Str = dt.getString(i, "TextValue");
				if (Str.length() > 50) {
					Str = Str.substring(0, 50) + "...";
				}
				dt.set(i, "TextValue", StringUtil.htmlEncode(Str));
			}
		}
		dt.decodeColumn("InputType", InputTypeMap);
		dga.bindData(dt);
	}

	public void add() {
		String catalogID = $V("CatalogID");
		String columnCode = $V("Code");
		String textValue = "";
		long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND + "' and RelaID = ? and ColumnCode =? ", catalogID, columnCode)
				.executeLong();
		if (count > 0) {
			Response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		Transaction trans = new Transaction();
		ZDColumnSchema column = new ZDColumnSchema();
		column.setID(NoUtil.getMaxID("ColumnID"));
		column.setCode($V("Code"));
		column.setName($V("Name"));
		column.setSiteID(Application.getCurrentSiteID());
		column.setInputType($V("InputType"));
		column.setVerifyType(ColumnUtil.STRING);
		column.setIsMandatory("N");
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(new Date());
		column.setAddUser(User.getUserName());

		ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
		rela.setID(NoUtil.getMaxID("ColumnRelaID"));
		rela.setColumnID(column.getID());
		rela.setColumnCode(columnCode);
		rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		rela.setRelaID(catalogID);
		rela.setAddTime(column.getAddTime());
		rela.setAddUser(column.getAddUser());

		if (Input.equals(column.getInputType())) {
			textValue = $V("TextValue");
		} else if (ImageInput.equals(column.getInputType())) {
			textValue = $V("ImagePath");
			if (StringUtil.isNotEmpty(textValue) && textValue.indexOf("upload") > 0) {
				textValue = textValue.substring(textValue.indexOf("upload"));
			}
		} else if (HtmlInput.equals(column.getInputType())) {
			textValue = $V("Content");
		}
		ZDColumnValueSchema value = new ZDColumnValueSchema();
		value.setID(NoUtil.getMaxID("ColumnValueID"));
		value.setColumnID(column.getID());
		value.setColumnCode(columnCode);
		value.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		value.setRelaID(catalogID);
		value.setTextValue(textValue);

		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		trans.add(column, Transaction.INSERT);
		trans.add(rela, Transaction.INSERT);
		trans.add(value, Transaction.INSERT);
		if (trans.commit()) {
			Response.setLogInfo(1, "新建成功");
		} else {
			Response.setLogInfo(0, "新建失败");
		}
	}
	
	public void uploadExcel(){
		String fileaddress = $V("fileaddress");
		fileaddress = fileaddress.replace("//", "/");
		DataTable dt = null;
		try {
			dt = DataTableUtil.xlsToDataTable(fileaddress);
		} catch (Exception e) {
			logger.error("读取数据导入文件时发生错误:" + e.getMessage(), e);
			this.Response.setError("导入失败！请选择正确模板");
			return;
		}
		
        String catalogID = $V("CatalogID");
        String textValue = "";
        Map<String, String> map = new HashMap<String, String>();
        try{
        for(int x=0;x<dt.getRowCount();x++ ){
        	if(dt.getString(x, 0)!=null && dt.getString(x, 0)!=""){
        		String columnCode = dt.getString(x, "代码");
    			long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
    					+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND + "' and RelaID = ? and ColumnCode =? ", catalogID, columnCode)
    					.executeLong();
    			if (count > 0) {
    				Response.setLogInfo(0, "已经存在"+columnCode+"字段");
    				return;
    			}
    			if(map.get(dt.getString(x, "代码"))!=null){
            		Response.setLogInfo(0, "模板中有重复字段,重复字段为"+dt.getString(x, "代码")+",请仔细检查，并重新上传！");
        			return;
            	}
            	map.put(dt.getString(x, "代码"), x+"");
        	}
           
        }
        }catch(Exception e){
        	logger.error(e.getMessage(), e);
        	Response.setLogInfo(0,"导入失败！");
        	return;
        }
        Transaction trans = new Transaction();
		ZDColumnSchema column= new ZDColumnSchema();
		try{
		String InputType="";
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, 0) != null && dt.getString(i, 0) != "") {
				if("文本框".equals(dt.getString(i, "类型").trim())){
					InputType="1";
				}else if("媒体库图片框".equals(dt.getString(i, "类型").trim())){
					InputType="7";
				}else if("HTML".equals(dt.getString(i, "类型").trim())){
					InputType="8";
				}
				String columnCode = dt.getString(i, "代码");
				column = new ZDColumnSchema();
				try {
					column.setID(NoUtil.getMaxID("ColumnID"));
					column.setCode(dt.getString(i, "代码"));
					column.setName(dt.getString(i, "名称"));
					column.setSiteID(Application.getCurrentSiteID());
					column.setInputType(InputType);
					column.setVerifyType(ColumnUtil.STRING);
					column.setIsMandatory("N");
					column.setOrderFlag(OrderUtil.getDefaultOrder());
					column.setAddTime(new Date());
					column.setAddUser(User.getUserName());

					ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
					rela.setID(NoUtil.getMaxID("ColumnRelaID"));
					rela.setColumnID(column.getID());
					rela.setColumnCode(columnCode);
					rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
					rela.setRelaID(catalogID);
					rela.setAddTime(column.getAddTime());
					rela.setAddUser(column.getAddUser());

					if (Input.equals(column.getInputType())) {
						textValue = dt.getString(i, "属性内容");
					} else if (ImageInput.equals(column.getInputType())) {
						textValue = dt.getString(i, "属性内容");
						if (StringUtil.isNotEmpty(textValue) && textValue.indexOf("upload") > 0) {
							textValue = textValue.substring(textValue.indexOf("upload"));
						}
					} else if (HtmlInput.equals(column.getInputType())) {
						textValue = dt.getString(i, "属性内容");
					}
					ZDColumnValueSchema value = new ZDColumnValueSchema();
					value.setID(NoUtil.getMaxID("ColumnValueID"));
					value.setColumnID(column.getID());
					value.setColumnCode(columnCode);
					value.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
					value.setRelaID(catalogID);
					value.setTextValue(textValue);
					trans.add(column, Transaction.INSERT);
					trans.add(rela, Transaction.INSERT);
					trans.add(value, Transaction.INSERT);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					Response.setLogInfo(0, "导入失败！模板内容有误，请仔细检查！");
					return;
				}
			}
		}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "导入失败！");
			return;
		}
		try {
			// 需要更新栏目修改时间
			ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
			catalog.setModifyTime(new Date());
			catalog.setModifyUser(User.getUserName());
			trans.add(catalog, Transaction.UPDATE);
			if (trans.commit()) {
				Response.setLogInfo(1, "导入成功！");
			} else {
				Response.setLogInfo(0, "导入失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "导入失败！");
			return;
		}
		
	}

	public void save() {
		String catalogID = $V("CatalogID");
		String columnID = $V("ColumnID");
		String valueID = $V("ValueID");
		String columnCode = $V("Code");
		String textValue = "";
		QueryBuilder qb = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND + "' and RelaID = ? and ColumnCode =? and ColumnID<>?",
				catalogID, columnCode);
		qb.add(Long.parseLong(columnID));
		long count = qb.executeLong();
		if (count > 0) {
			Response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		Transaction trans = new Transaction();
		ZDColumnSchema column = new ZDColumnSchema();
		column.setID(columnID);
		column.fill();
		column.setCode($V("Code"));
		column.setName($V("Name"));
		column.setInputType($V("InputType"));
		column.setVerifyType(ColumnUtil.STRING);
		column.setIsMandatory("N");
		column.setModifyTime(new Date());
		column.setModifyUser(User.getUserName());

		ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
		rela.setColumnID(column.getID());
		rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		rela.setRelaID(catalogID);
		rela = rela.query().get(0);

		rela.setColumnID(column.getID());
		rela.setColumnCode(columnCode);
		rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		rela.setRelaID(catalogID);
		rela.setModifyTime(column.getModifyTime());
		rela.setModifyUser(column.getModifyUser());

		if (Input.equals(column.getInputType())) {
			textValue = $V("TextValue");
		} else if (ImageInput.equals(column.getInputType())) {
			textValue = $V("ImagePath");
			if (StringUtil.isNotEmpty(textValue) && textValue.indexOf("upload") > 0) {
				textValue = textValue.substring(textValue.indexOf("upload"));
			}
		} else if (HtmlInput.equals(column.getInputType())) {
			textValue = $V("Content");
		}
		ZDColumnValueSchema value = new ZDColumnValueSchema();
		value.setID(valueID);
		value.setColumnID(column.getID());
		value.setColumnCode(columnCode);
		value.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		value.setRelaID(catalogID);
		value.setTextValue(textValue);
		
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		trans.add(column, Transaction.UPDATE);
		trans.add(rela, Transaction.UPDATE);
		trans.add(value, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改失败");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		long catalogID = Long.parseLong($V("CatalogID"));
		Transaction trans = new Transaction();
		ZDColumnSet columnSet = new ZDColumnSchema().query(new QueryBuilder("where ID in (" + ids + ") "));
		ZDColumnRelaSet relaSet = new ZDColumnRelaSchema().query(new QueryBuilder("where columnID in (" + ids
				+ ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_EXTEND + "' and RelaID='" + catalogID + "'"));
		ZDColumnValueSet valueSet = new ZDColumnValueSchema().query(new QueryBuilder("where columnID in (" + ids
				+ ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_EXTEND + "' and RelaID='" + catalogID + "'"));

		trans.add(columnSet, Transaction.DELETE_AND_BACKUP);
		trans.add(relaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(valueSet, Transaction.DELETE_AND_BACKUP);
		
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder("select path,filename,srcfilename from zcimage where id=?", Long.parseLong(ID))
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			String imageSrc =Config.getValue("StaticResourcePath") + "/" + dt.get(0, "path").toString() + "s_"
			+ dt.get(0, "filename").toString();
			this.Response.put("picSrc",imageSrc);
			this.Response.put("ImagePath", dt.get(0, "path").toString() + dt.get(0, "srcfilename").toString());
		}
	}
}