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
package com.sinosoft.cms.dataservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.ResponseImpl;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.orders.CancelCont;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

public class SurrenderDataUpLoad extends Page {
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
	public String upLoadSave(String upLoadFileName, String clientIP) {
		String result = "Succ";
		// 解析Excel内容成功，保存数据
		if (resolvingExcel(upLoadFileName, clientIP)) {
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
	public boolean resolvingExcel(String upLoadFileName, String clientIP) {
		try {
			trans = new Transaction();

			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			// 取得条数
			this.setCount(sheet.getLastRowNum() - 6);
			// 读取Excel
			List<String> importPolicyNums = new ArrayList<String>();
			StringBuffer policyNums = new StringBuffer();
			for (int i = 7; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (null != row && null !=row.getCell(1)) {
					String policyNo = (String) getCellString(row.getCell(1));
					if(StringUtil.isNotEmpty(policyNo)){
						policyNums.append("'"+policyNo+"',");
						importPolicyNums.add(policyNo);
					}
				}
			}
			
			List<Object[]> results = new ArrayList<Object[]>();//result 添加列表展示结果集
			if(policyNums.indexOf(",")>-1){
				String policyNumsStr = policyNums.substring(0, policyNums.length()-1);
				//查询订单信息
				 String orderInfoSQL ="SELECT DISTINCT sd.channelsn, sdr.policyNo, sdi.recognizeeSn, sdi.insuredSn FROM sdinformationinsured sdi"+
				 " INNER JOIN sdinformationrisktype sdr ON sdi.recognizeeSn=sdr.recognizeeSn"+
				 " INNER JOIN sdorders sd ON sd.orderSn=sdr.orderSn WHERE sdr.policyNo in("+policyNumsStr+")";
				 DataTable orderInfoDT = new QueryBuilder(orderInfoSQL).executeDataTable();
				 if(null != orderInfoDT && orderInfoDT.getRowCount()>0){
					 List<String[]> orderInfoList = new ArrayList<String[]>();
					 for(int i=0;i<orderInfoDT.getRowCount();i++){
						 orderInfoList.add(new String[]{orderInfoDT.getString(i, 0), orderInfoDT.getString(i, 1), orderInfoDT.getString(i, 2), orderInfoDT.getString(i, 3)});
					 }
					 //导入数据筛选不存在的
					 for(String importPolicyNum : importPolicyNums){
						 int count = 0;
						 for(String[] orderInfo : orderInfoList){
							 if(importPolicyNum.equals(orderInfo[1])){
								 break;
							 }
							 count++;
						 }
						 if(count == orderInfoList.size()){
							 results.add(new String[]{"失败",importPolicyNum,"未查询到订单信息!"});
						 }
					 }
					//微商渠道校验，查询结果：提现流程中的单子(已生效、处理中)  状态、保单号、订单号、发起人
					 StringBuffer b2bAppRecoginizeeSn = new StringBuffer();
					 for(String[] orderInfo : orderInfoList){
						 if("b2b_app".equals(orderInfo[0])){
							 b2bAppRecoginizeeSn.append("'"+orderInfo[2]+"',");
						 }
					 }
					 if(b2bAppRecoginizeeSn.indexOf(",")>-1){
						 //查询提现中、已提现的被保人数据
						 String b2b_app_Sql = "SELECT  t.PolicyNo,t.BalanceStatus,u.RealName FROM sdbalancedetail t INNER JOIN zduser u ON t.UserName=u.UserName GROUP BY t.PolicyNo,t.BalanceStatus"+
								" HAVING t.policyno  IN("+b2bAppRecoginizeeSn.substring(0, b2bAppRecoginizeeSn.length()-1)+") AND (t.BalanceStatus='00' OR t.BalanceStatus='02')";
						 DataTable dataTable = new QueryBuilder(b2b_app_Sql).executeDataTable("B2B");
						 if(null != dataTable && dataTable.getRowCount()>0){
							//results中增加微商处理失败的数据
							 for(int i=0;i<dataTable.getRowCount();i++){
								 String policyNo = dataTable.getString(i, 0);
								 String balanceStatus = dataTable.getString(i, 1);
								 String userName = dataTable.getString(i, 2);
								 for(String[] orderInfo : orderInfoList){
									 if(policyNo.equals(orderInfo[2])){
										String mesg = null;
										if("00".equals(balanceStatus)){//#00结算中 01待结算  02已结算
											mesg ="保单号("+orderInfo[1]+")在提现流程中,请提醒发起人("+userName+")修改后再进行导入!";
										}else{
											mesg ="保单号("+orderInfo[1]+")已完成提现流程,请无法导入!";
										}
										results.add(new String[]{"失败",orderInfo[1],mesg});
										 break;
									 }
								 }
							 }
							//orderInfoList 中移除微商提现数据
							 Iterator<String[]> it = orderInfoList.iterator();
							 while(it.hasNext()){
								 String[] orderInfo = it.next();
								 for(Object[] result : results){
									 if(orderInfo[1].equals(result[1])){
										 it.remove();
										 break;
									 }
								 }
							 }
						 }
					 }
//					 orderInfoList请求撤单逻辑
					 CancelCont cancelCont = new CancelCont();
					 for(String[] orderInfo : orderInfoList){
						String[] result = new String[3];
						RequestImpl requestImpl = new RequestImpl();
						requestImpl.put("surrenderData", orderInfo[3]);
						cancelCont.setRequest(requestImpl);
						cancelCont.dealCancel();
						
						ResponseImpl response = (ResponseImpl)cancelCont.getResponse();
						int status = response.getStatus();
						String returnMsg = response.getMessage();
						result[0] = status==1?"成功":"失败";
						result[1] = orderInfo[1];
						result[2] = returnMsg;
						System.out.println(status + "     "+returnMsg);
						results.add(result);
					 }
				 }else{
					 for(String importPolicyNum : importPolicyNums){
						 results.add(new String[]{"失败",importPolicyNum,"未查询到订单信息!"});
					 }
				 }
			}
			//将列表数据放入redis中
			System.out.println(clientIP+"surrenderDatas");
			ObjectMapper mapper = new ObjectMapper();
			JedisCommonUtil.setString(3, clientIP+"surrenderDatas", mapper.writeValueAsString(results));
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	/**
	 * 从redis中读取加载列表
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		System.out.println(this.Request.getClientIP()+"surrenderDatas");
		String serializeableStr = JedisCommonUtil.getString(3, this.Request.getClientIP()+"surrenderDatas");
		System.out.println(serializeableStr);
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Object[].class);
		List<Object[]> results = null;
		try {
			results = (List<Object[]>)mapper.readValue(serializeableStr, javaType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		DataTable dt = new DataTable();
		dt.insertColumn("status");
		dt.insertColumn("policyNo");
		dt.insertColumn("remark");
		
		if(null != results){
			for(Object[] result : results){
				dt.insertRow(result);
			}
		}
		dga.bindData(dt);
		JedisCommonUtil.remove(3, this.Request.getClientIP()+"surrenderDatas");
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
