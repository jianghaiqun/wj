/**批量评论导入功能
 * <p>Date        :2012-07-01</p> 
 * <p>Module      :CMS </p> 
 * <p>Description :计划保费导入功能</p> 
 * <p>Remark      : </p> 
 * @version 1.0 
 * <p>------------------------------------------------------------</p> 
 * <p>  修改历史</p> 
 * <p>  序号   日期                修改人     修改原因</p> 
 */
package com.sinosoft.framework.utility;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.schema.jdproductcSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductInfoTwoUpLoad extends Page {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private Transaction trans;

	/** 处理条数 */
	private Integer count;

	/**
	 * 保存上传文件
	 * 
	 * @param upLoadFileName
	 *            上传文件
	 * @return
	 */
	public String upLoadSave(String upLoadFileName) {
		String result = "Succ";
		// 解析Excel内容成功，保存数据
		if (resolvingExcel(upLoadFileName)) {
			if (!trans.commit()) {
				this.addError("执行插入语句失败！");
				result = "Fail";
			}
		} else {
			result = "Fail";
		}

		return result;
	}

	/**
	 * 解析Excel内容
	 * 
	 * @param upLoadFileName
	 *            上传文件
	 * @return 解析是否成功
	 */
	public boolean resolvingExcel(String upLoadFileName) {
		try {
			trans = new Transaction();

			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			// 取得条数
			this.setCount(sheet.getLastRowNum() - 6);
			// 读取Excel
			for (int i = 7; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					
					if (row.getCell(1) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(1))))) {
						
						//产品编码
						String productid = String.valueOf(getCellString(row.getCell(1))).trim();
						if(StringUtil.isEmpty(productid)){
							continue;
						}
						trans.add(new QueryBuilder(" DELETE FROM jdproductc WHERE eriskid=? ",productid));//先删除
						//计划编码
						String plancode = String.valueOf(getCellString(row.getCell(2)));
						
						String[] plancodearr = null;
						if(StringUtil.isNotEmpty(plancode)){
							plancodearr = plancode.split(";");
						}
						//保险期间
						String period = String.valueOf(getCellString(row.getCell(3)));
						String[] periodarr = null;
						if(StringUtil.isNotEmpty(period)){
							periodarr = period.split(";");
						}
						//对应主站保险期间
						String tperiod = String.valueOf(getCellString(row.getCell(4)));
						String[] tperiodarr = null;
						if(StringUtil.isNotEmpty(tperiod)){
							tperiodarr = tperiod.split(";");
						}
						//投保年龄
						String textage = String.valueOf(getCellString(row.getCell(5)));
						String[] textagearr = null;
						if(StringUtil.isNotEmpty(textage)){
							textagearr = textage.split(";");
						}
						//对应主站投保年龄
						String ttextage = String.valueOf(getCellString(row.getCell(6)));
						String[] ttextagearr = null;
						if(StringUtil.isNotEmpty(ttextage)){
							ttextagearr = ttextage.split(";");
						}
						
						/*//投保性别限制
						String sex = String.valueOf(getCellString(row.getCell(7)));*/
						
						//投保性别限制
						String sex = String.valueOf(getCellString(row.getCell(7)));
						String[] sexarr = null;
						if(StringUtil.isNotEmpty(sex)){
							sexarr = sex.split(";");
						}
						// 缴费年期
						String PaymentMethods = String.valueOf(getCellString(row.getCell(12)));
						String[] payMetharr = null;
						if(StringUtil.isNotEmpty(PaymentMethods)){
							payMetharr = PaymentMethods.split(";");
						}
						
						List<String[]> alist = new ArrayList<String[]>();
						
						if(plancodearr!=null && plancodearr.length>=1){
							alist.add(plancodearr);
						}
						if(periodarr!=null && periodarr.length>=1){
							alist.add(periodarr);						
						}
						if(textagearr!=null && textagearr.length>=1){
							alist.add(textagearr);
						}
						if(sexarr!=null && sexarr.length>=1){
							alist.add(sexarr);
						}
						if(payMetharr!=null && payMetharr.length>=1){
							alist.add(payMetharr);
						}
						List<String> result = new ArrayList<String>();
				        
				        run(alist, result, 0, "");
				        
				        int resultlen = result.size();
				        //拼装 计划、期限、年龄、缴费年期信息
				        for(int k=0;k<resultlen;k++){
				        	jdproductcSchema tjdproductcSchema = new jdproductcSchema();
				        	//tjdproductcSchema.setGender(sex);
				        	tjdproductcSchema.setERiskID(productid);
				        	DataTable dt = new QueryBuilder(" SELECT ProductName,Remark6 FROM sdproduct WHERE ProductID=?  ",productid).executeDataTable();
				        	if(dt!=null && dt.getRowCount()>=1){
					        	tjdproductcSchema.setProductName(String.valueOf(dt.get(0, 0)));
					        	tjdproductcSchema.setInsureCode(String.valueOf(dt.get(0, 1)));
				        	}
				        	String cusinduex = StringUtil.leftPad(k+1+"", '0', 3);
				        	tjdproductcSchema.setCustomCode(productid+""+cusinduex);
				        	tjdproductcSchema.setMakeDate(PubFun.getCurrentDate());
				        	String productflag = String.valueOf(getCellString(row.getCell(8)));
				        	String planflag = String.valueOf(getCellString(row.getCell(9)));
				        	String dutyflag = String.valueOf(getCellString(row.getCell(10)));
				        	String riskflag = String.valueOf(getCellString(row.getCell(11)));
				        	String discountPrice = String.valueOf(getCellString(row.getCell(13))); 
				        	if(StringUtil.isEmpty(productflag) || StringUtil.isEmpty(planflag) || StringUtil.isEmpty(dutyflag)||
				        			StringUtil.isEmpty(riskflag)){
				        		this.addError("有未正确填写的值，请检查excel信息！");
				        		return false;
				        	}
				        	tjdproductcSchema.setProductXmlHybrid(productflag);
				        	tjdproductcSchema.setPlanXmlHybrid(planflag);
				        	tjdproductcSchema.setDutyCodeXmlHybrid(dutyflag);
				        	tjdproductcSchema.setInsuranceXmlHybrid(riskflag);
				        	if(StringUtil.isNotEmpty(discountPrice)){
				        		tjdproductcSchema.setDiscountPrice(discountPrice);
				        	}
				        	/*if(plancodearr!=null && plancodearr.length>=1){
				        		tjdproductcSchema.setPlanXmlHybrid("Y");
				        	}
				        	tjdproductcSchema.setInsuranceXmlHybrid("N");
				        	tjdproductcSchema.setDutyCodeXmlHybrid("N");
				        	if(productid.startsWith("1065")){
				        		//昆仑特殊处理
				        		tjdproductcSchema.setProductXmlHybrid("Y");
				        		tjdproductcSchema.setPlanXmlHybrid("N");
					        	tjdproductcSchema.setInsuranceXmlHybrid("N");
					        	tjdproductcSchema.setDutyCodeXmlHybrid("Y");
				        	}
				        	if(productid.startsWith("2071")){
				        		//史带特殊处理，史带产品都走计划码流程 
				        		tjdproductcSchema.setProductXmlHybrid("Y");
				        		tjdproductcSchema.setPlanXmlHybrid("Y");
					        	tjdproductcSchema.setInsuranceXmlHybrid("N");
					        	tjdproductcSchema.setDutyCodeXmlHybrid("N");
				        	}*/
				        	tjdproductcSchema.setProductType("yj");
				        	
				        	String[] sub_arr = result.get(k).split("_");
				        	//如果 计划、保险期间、投保年龄、缴费年期都有值，则sub_arr的长度为5
				        	//1：长度==1
				        	tjdproductcSchema.setPlanCode("");//jdt程序要求，当没有计划时设置为空串
				        	if(sub_arr.length==1){
				        		String value_1 = sub_arr[0];
					        	if(plancodearr!=null && plancodearr.length>=1){
					        		tjdproductcSchema.setPlanCode(value_1);
					        	}else if(periodarr!=null && periodarr.length>=1){
					        		tjdproductcSchema.setCoverageYear(value_1);
					        		if(tperiodarr!=null && tperiodarr.length>=1){
					        			tjdproductcSchema.setPeriod(getindex(periodarr,value_1,tperiodarr));
					        		}
					        	}else if(textagearr!=null && textagearr.length>=1){
					        		tjdproductcSchema.setAgeIssue(value_1);
					        		if(ttextagearr!=null && ttextagearr.length>=1){
					        			tjdproductcSchema.setTextAge(getindex(textagearr,value_1,ttextagearr));
					        		}
					        	}else if(sexarr != null && sexarr.length >= 1){
					        		tjdproductcSchema.setGender(value_1);
					        	}else if (payMetharr != null && payMetharr.length >= 1) {
					        		tjdproductcSchema.setPaymentMethods(value_1);
					        	}
					        	
				        	}else if(sub_arr.length==2){
				        		//长度==2
				        		String value_1 = sub_arr[0];
				        		String value_2 = sub_arr[1];
					        	if(plancodearr!=null && plancodearr.length>=1){
					        		tjdproductcSchema.setPlanCode(value_1);
					        		if(periodarr!=null && periodarr.length>=1){
						        		tjdproductcSchema.setCoverageYear(value_2);
						        		if(tperiodarr!=null && tperiodarr.length>=1){
						        			tjdproductcSchema.setPeriod(getindex(periodarr,value_2,tperiodarr));
						        		}
						        	}
					        		if(textagearr!=null && textagearr.length>=1){
						        		tjdproductcSchema.setAgeIssue(value_2);
						        		if(ttextagearr!=null && ttextagearr.length>=1){
						        			tjdproductcSchema.setTextAge(getindex(textagearr,value_2,ttextagearr));
						        		}
						        	}
					        		if(sexarr != null && sexarr.length >= 1){
						        		tjdproductcSchema.setGender(value_2);
						        	}
					        		if (payMetharr != null && payMetharr.length >= 1) {
						        		tjdproductcSchema.setPaymentMethods(value_2);
						        	}
					        	}else if(periodarr!=null && periodarr.length>=1){
					        		tjdproductcSchema.setCoverageYear(value_1);
					        		if(tperiodarr!=null && tperiodarr.length>=1){
					        			tjdproductcSchema.setPeriod(getindex(periodarr,value_1,tperiodarr));
					        		}
					        		if(textagearr!=null && textagearr.length>=1){
						        		tjdproductcSchema.setAgeIssue(value_2);
						        		if(ttextagearr!=null && ttextagearr.length>=1){
						        			tjdproductcSchema.setTextAge(getindex(textagearr,value_2,ttextagearr));
						        		}
						        	}
					        		if(sexarr != null && sexarr.length >= 1){
						        		tjdproductcSchema.setGender(value_2);
						        	}
					        		if (payMetharr != null && payMetharr.length >= 1) {
						        		tjdproductcSchema.setPaymentMethods(value_2);
						        	}
					        	}else if(textagearr!=null && textagearr.length>=1){
					        		tjdproductcSchema.setAgeIssue(value_1);
					        		if(ttextagearr!=null && ttextagearr.length>=1){
					        			tjdproductcSchema.setTextAge(getindex(textagearr,value_1,ttextagearr));
					        		}
					        		if(sexarr != null && sexarr.length >= 1){
						        		tjdproductcSchema.setGender(value_2);
						        	}
					        		if (payMetharr != null && payMetharr.length >= 1) {
						        		tjdproductcSchema.setPaymentMethods(value_2);
						        	}
					        	} else if(sexarr != null && sexarr.length >= 1){
					        		tjdproductcSchema.setGender(value_1);
					        		tjdproductcSchema.setPaymentMethods(value_2);
					        	}
					        	
				        	}else if(sub_arr.length==3){
				        		//长度==3
				        		String value_1 = sub_arr[0];
				        		String value_2 = sub_arr[1];
				        		String value_3 = sub_arr[2];
					        	if(plancodearr!=null && plancodearr.length>=1){
					        		tjdproductcSchema.setPlanCode(value_1);
					        		if(periodarr!=null && periodarr.length>=1){
						        		tjdproductcSchema.setCoverageYear(value_2);
						        		if(tperiodarr!=null && tperiodarr.length>=1){
						        			tjdproductcSchema.setPeriod(getindex(periodarr,value_2,tperiodarr));
						        		}
						        		if(textagearr!=null && textagearr.length>=1){
							        		tjdproductcSchema.setAgeIssue(value_3);
							        		if(ttextagearr!=null && ttextagearr.length>=1){
							        			tjdproductcSchema.setTextAge(getindex(textagearr,value_3,ttextagearr));
							        		}
							        	}
						        		if(sexarr != null && sexarr.length >= 1){
							        		tjdproductcSchema.setGender(value_3);
							        	}
						        		if (payMetharr != null && payMetharr.length >= 1) {
							        		tjdproductcSchema.setPaymentMethods(value_3);
							        	}
						        	}else if(textagearr!=null && textagearr.length>=1){
						        		tjdproductcSchema.setAgeIssue(value_2);
						        		if(ttextagearr!=null && ttextagearr.length>=1){
						        			tjdproductcSchema.setTextAge(getindex(textagearr,value_2,ttextagearr));
						        		}
						        		if(sexarr != null && sexarr.length >= 1){
							        		tjdproductcSchema.setGender(value_3);
							        	}
						        		if (payMetharr != null && payMetharr.length >= 1) {
							        		tjdproductcSchema.setPaymentMethods(value_3);
							        	}
						        	} else if(sexarr != null && sexarr.length >= 1){
						        		tjdproductcSchema.setGender(value_2);
						        		tjdproductcSchema.setPaymentMethods(value_3);
						        	}
					        	}else if(periodarr!=null && periodarr.length>=1){
					        		tjdproductcSchema.setCoverageYear(value_1);
					        		if(tperiodarr!=null && tperiodarr.length>=1){
					        			tjdproductcSchema.setPeriod(getindex(periodarr,value_1,tperiodarr));
					        		}
					        		if(textagearr!=null && textagearr.length>=1){
					        			tjdproductcSchema.setAgeIssue(value_2);
						        		if(ttextagearr!=null && ttextagearr.length>=1){
						        			tjdproductcSchema.setTextAge(getindex(textagearr,value_2,ttextagearr));
						        		}
						        		if(sexarr != null && sexarr.length >= 1){
							        		tjdproductcSchema.setGender(value_3);
							        	}
						        		if (payMetharr != null && payMetharr.length >= 1) {
							        		tjdproductcSchema.setPaymentMethods(value_3);
							        	}
					        		} else if (sexarr != null && sexarr.length >= 1) {
					        			tjdproductcSchema.setGender(value_2);
					        			tjdproductcSchema.setPaymentMethods(value_3);
					        		}
					        	} else if (textagearr!=null && textagearr.length>=1) {
					        		tjdproductcSchema.setAgeIssue(value_1);
					        		if(ttextagearr!=null && ttextagearr.length>=1){
					        			tjdproductcSchema.setTextAge(getindex(textagearr,value_1,ttextagearr));
					        			tjdproductcSchema.setGender(value_2);
					        			tjdproductcSchema.setPaymentMethods(value_3);
					        		}
					        	}
					        	
				        	}else if(sub_arr.length==4){
				        		//长度==4
				        		String value_1 = sub_arr[0];
				        		String value_2 = sub_arr[1];
				        		String value_3 = sub_arr[2];
				        		String value_4 = sub_arr[3];
					        	if(plancodearr!=null && plancodearr.length>=1){
					        		tjdproductcSchema.setPlanCode(value_1);
					        		if(periodarr!=null && periodarr.length>=1){
						        		tjdproductcSchema.setCoverageYear(value_2);
						        		if(tperiodarr!=null && tperiodarr.length>=1){
						        			tjdproductcSchema.setPeriod(getindex(periodarr,value_2,tperiodarr));
						        		}
						        		if(textagearr!=null && textagearr.length>=1){
							        		tjdproductcSchema.setAgeIssue(value_3);
							        		if(ttextagearr!=null && ttextagearr.length>=1){
							        			tjdproductcSchema.setTextAge(getindex(textagearr,value_3,ttextagearr));
							        		}
							        		if(sexarr != null && sexarr.length >= 1){
								        		tjdproductcSchema.setGender(value_4);
								        	}
							        		if (payMetharr != null && payMetharr.length >= 1) {
								        		tjdproductcSchema.setPaymentMethods(value_4);
								        	}
							        	} else {
							        		tjdproductcSchema.setGender(value_3);
							        		tjdproductcSchema.setPaymentMethods(value_4);
							        	}
						        	} else if (textagearr!=null && textagearr.length>=1) {
						        		tjdproductcSchema.setAgeIssue(value_2);
						        		if(ttextagearr!=null && ttextagearr.length>=1){
						        			tjdproductcSchema.setTextAge(getindex(textagearr,value_2,ttextagearr));
						        		}
						        		tjdproductcSchema.setGender(value_3);
						        		tjdproductcSchema.setPaymentMethods(value_4);
						        	}
					        	} else {
					        		if(periodarr!=null && periodarr.length>=1){
						        		tjdproductcSchema.setCoverageYear(value_1);
						        		if(tperiodarr!=null && tperiodarr.length>=1){
						        			tjdproductcSchema.setPeriod(getindex(periodarr,value_1,tperiodarr));
						        		}
						        	}
						        	if(textagearr!=null && textagearr.length>=1){
						        		tjdproductcSchema.setAgeIssue(value_2);
						        		if(ttextagearr!=null && ttextagearr.length>=1){
						        			tjdproductcSchema.setTextAge(getindex(textagearr,value_2,ttextagearr));
						        		}
						        	}
						        	if(sexarr != null && sexarr.length >= 1){
						        		tjdproductcSchema.setGender(value_3);
						        	}
						        	if (payMetharr != null && payMetharr.length >= 1) {
						        		tjdproductcSchema.setPaymentMethods(value_4);
						        	}
					        	}
					        	
				        	}else if(sub_arr.length==5){
				        		String value_1 = sub_arr[0];
				        		String value_2 = sub_arr[1];
				        		String value_3 = sub_arr[2];
				        		String value_4 = sub_arr[3];
				        		String value_5 = sub_arr[4];
				        		if(plancodearr!=null && plancodearr.length>=1){
					        		tjdproductcSchema.setPlanCode(value_1);
					        	}
					        	if(periodarr!=null && periodarr.length>=1){
					        		tjdproductcSchema.setCoverageYear(value_2);
					        		if(tperiodarr!=null && tperiodarr.length>=1){
					        			tjdproductcSchema.setPeriod(getindex(periodarr,value_2,tperiodarr));
					        		}
					        	}
					        	if(textagearr!=null && textagearr.length>=1){
					        		tjdproductcSchema.setAgeIssue(value_3);
					        		if(ttextagearr!=null && ttextagearr.length>=1){
					        			tjdproductcSchema.setTextAge(getindex(textagearr,value_3,ttextagearr));
					        		}
					        	}
					        	if(sexarr != null && sexarr.length >= 1){
					        		tjdproductcSchema.setGender(value_4);
					        	}
					        	if (payMetharr != null && payMetharr.length >= 1) {
					        		tjdproductcSchema.setPaymentMethods(value_5);
					        	}
				        	}
				        	trans.add(tjdproductcSchema,Transaction.INSERT);
				        }
					}  
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 笛卡尔积组合返回数组
	 * @param dimvalue
	 * @param result
	 * @param layer
	 * @param curstring
	 */
	public static void run(List<String[]> dimvalue, List<String> result, int layer, String curstring)
	{
        //大于一个集合时：
        if (layer < dimvalue.size() - 1)
        {
            //大于一个集合时，第一个集合为空
            if (dimvalue.get(layer).length == 0)
                run(dimvalue, result, layer + 1, curstring);
            else
            {
                for (int i = 0; i < dimvalue.get(layer).length; i++)
                {
                    StringBuilder s1 = new StringBuilder();
                    s1.append(curstring);
                    s1.append(dimvalue.get(layer)[i]+"_");//组合之间用“_”分隔
                    run(dimvalue, result, layer + 1, s1.toString());
                }
            }
        }
        //只有一个集合时：
        else if (layer == dimvalue.size() - 1)
        {
            //只有一个集合，且集合中没有元素
            if (dimvalue.get(layer).length == 0)
                result.add(curstring);
            //只有一个集合，且集合中有元素时：其笛卡尔积就是这个集合元素本身
            else
            {
                for (int i = 0; i < dimvalue.get(layer).length; i++)
                {
                    result.add(curstring + dimvalue.get(layer)[i]);
                }
            }
        }
    }
	
	/**
	 * 获取所在数组内的下标
	 * @param arr
	 * @param value
	 * @return
	 */
	public static String getindex(String arr[],String value,String[] result){
		
		String result_value="";
        for (int i = 0; i < arr.length; i++) {
            String string = arr[i];
            if(string.equals(value)){
            	
            	if(i<=result.length-1){
            		result_value = result[i];
            	}
            }
        }
		return result_value;
	}
	public static void main(String[] args) {
		/*String[] arr1 = {};
		String[] arr2 = {"c","d"};
		String[] arr3 = {"e","f"};
		
		List<String[]> alist = new ArrayList<String[]>();
		
		alist.add(arr1);
		alist.add(arr2);
		alist.add(arr3);
		
		List<String> result = new ArrayList<String>();
        
        run(alist, result, 0, "--");
        
        System.out.println(result);
        int i = 1;
        for (String s : result)
        {
            System.out.println(i++ + ":" +s);
        }*/
        int max=20;
        int min=10;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
	}
	/**
	 * 添加错误信息
	 * 
	 * @param errorMessage
	 *            错误信息
	 */
	protected void addError(String errorMessage) {
		CError tError = new CError();
		tError.moduleName = this.getClass().getName();
		tError.errorMessage = errorMessage;
		this.mErrors.addOneError(tError);
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	private Object getCellString(Cell cell) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Object result = null;
		  if(cell != null){
		    //单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
		    int cellType = cell.getCellType();
		    switch (cellType) {
		    case Cell.CELL_TYPE_STRING:
		      result = cell.getRichStringCellValue().getString().trim();
		      break;
		    case Cell.CELL_TYPE_NUMERIC:
		    	if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
			    	  result = sdf.format(cell.getDateCellValue());
		    	}else{
		    		 result = cell.getNumericCellValue();
		    	}
		      break;
		    case Cell.CELL_TYPE_FORMULA:
		      
		      cell.getCellFormula();
		      try{
		    	  if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
			    	  result = sdf.format(cell.getDateCellValue());
			      }else{
			    	  result = cell.getStringCellValue();
			      }
		      }catch(Exception e){
		    	  result = cell.getStringCellValue();
		      }
		      break;
		    case Cell.CELL_TYPE_BOOLEAN:
		      result = cell.getBooleanCellValue();
		      break;
		    case Cell.CELL_TYPE_BLANK:
		      result = null;
		      break;
		    case Cell.CELL_TYPE_ERROR:
		      result = null;
		      break;
		    default:
		      break;
		    }
		  }
		  return result;
		}
}
