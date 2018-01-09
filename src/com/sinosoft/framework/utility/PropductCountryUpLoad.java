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

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PropductCountryUpLoad extends Page {
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
			this.setCount(sheet.getLastRowNum() - 9);
			// 读取Excel
			HSSFRow row1 = sheet.getRow(7);
			//产品编码
			String productid = String.valueOf(getCellString(row1.getCell(1))).trim();
			
			//保险公司编码
			String comCode = String.valueOf(getCellString(row1.getCell(2))).trim();
			if(StringUtil.isEmpty(comCode)){
				addError("保险公司编码不能为空！");
				return false;
			}
			if(StringUtil.isNotEmpty(productid)){
				trans.add(new QueryBuilder(" DELETE FROM dictionary WHERE codetype='CountryCode' AND productId=? ",productid));//先删除
			}else if(StringUtil.isNotEmpty(comCode)){
				trans.add(new QueryBuilder(" DELETE FROM dictionary WHERE codetype='CountryCode' AND insuranceCode=? AND (productId IS NULL OR productId='') ",comCode));//先删除
			}
			for (int i = 10; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					
					if (row.getCell(1) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(1))))) {
						
						QueryBuilder qb  = new QueryBuilder(" INSERT INTO dictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
						String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
						qb.add(id_index);
						qb.add(new Date());
						qb.add(new Date());

						//旅游目的地显示值
						String countryenname = String.valueOf(getCellString(row.getCell(3)));
						qb.add(countryenname);
						//旅游目的地显示值
						String countryname = String.valueOf(getCellString(row.getCell(2)));
						if(StringUtil.isEmpty(countryname)){
							continue;
						}
						qb.add(countryname);
						qb.add("CountryCode");
						qb.add("国家代码");
						//旅游目的地编码
						String countrycode = String.valueOf(getCellString(row.getCell(1)));
						
						if(StringUtil.isEmpty(countrycode)){
							continue;
						}
						qb.add(countrycode.trim());
						qb.add(comCode.trim());
						//申根标记
						String shengenflag = String.valueOf(getCellString(row.getCell(4)));
						qb.add(shengenflag);
						if(StringUtil.isNotEmpty(productid)){
							qb.add(productid.trim());
						}else{
							qb.add("");
						}
						//常用地区标记
						String normalflag = String.valueOf(getCellString(row.getCell(5)));
						qb.add(normalflag);
						
						//默认目的地
						String defaultflag = String.valueOf(getCellString(row.getCell(6)));
						qb.add(defaultflag);
						
				        trans.add(qb);
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
		      result = cell.getRichStringCellValue().getString();
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
