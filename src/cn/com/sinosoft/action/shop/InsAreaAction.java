 package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class InsAreaAction extends BaseShopAction {

	public List<Map<String, String>> area = new ArrayList<Map<String,String>>();;
	
	
	public List<Map<String, String>> getArea() {
	
		return area;
	}

	
	public void setArea(List<Map<String, String>> area) {
	
		this.area = area;
	}
	
	/**
	 * 
	 * @Title: queryOrder
	 * @Description: TODO(缺口保障存档)
	 * @return String 返回类型
	 * @author
	 */
	public String InsAreaTest() {
		String provinceSql = "select id,name from area where productId = '224801001' and id = '2248224801001aeonlife_area_0121'";
//		String citySql = "select * from area where productid = '224801001' and parent_id = '2248224801001aeonlife_area_0121'";
		DataTable provinceDt = new QueryBuilder(provinceSql).executeDataTable();
		for(int i=0;i<provinceDt.getRowCount();i++){
			Map<String, String > map=new HashMap<String,String>();
			String id=provinceDt.getString(i, "id");
			String name=provinceDt.getString(i, "name");
			map.put("id",id);
			map.put("name",name);
			area.add(map);	
		}
		return "test";
	}
	
	public String getChildArea() {
		try {
			String areaId = getParameter("areaId");
			String citySql = "select id,name from area where productid = '224801001' and parent_id = '" + areaId + "'";
			DataTable citys = new QueryBuilder(citySql).executeDataTable();
			String citysString = DataTableToJson(citys);
			return ajaxJson(citysString); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxJson("");
		}
	}
	
	public String DataTableToJson(DataTable table)  
    {  
		StringBuilder JsonString = new StringBuilder();  
        if (table.getRowCount() > 0)  
        {  
            JsonString.append("[");  
            for (int i = 0; i < table.getRowCount(); i++)  
            {  
                JsonString.append("{");  
				for (int j = 0; j < table.getColCount(); j++)
				{
					JsonString.append("\"" + table.getColumnName(j) + "\":" + "\"" + table.getString(i, j) + "\",");
					if (j == table.getRowCount() - 1)
					{
						JsonString.substring(0, JsonString.length() - 1);
					}
				}  
                if (i == table.getRowCount() - 1)  
                {  
                    JsonString.append("}");  
                }  
                else  
                {  
                    JsonString.append("},");  
                }  
            }  
            JsonString.append("]");  
        }  
        return JsonString.toString();    
    }  
	
	public String checkArea (){
		try {
			String area1 = getParameter("area1");
			String area2 = getParameter("area2");
			String address = getParameter("address");
			// 非县级市地址顺序
			String str = "市，盟；州，县，区，旗；乡，镇，街，路，道，巷，委，段，里，社，园，苑；村，组，号，室，队，院，房，楼，栋";
			// 县级市地址顺序
			String str1 = "市，盟；乡，镇，街，路，道，巷，委，段，里，社，园，苑；村，组，号，室，队，院，房，楼，栋";
			String countyLevelCity = address.split("市")[0];
			String chose = null;
			if (area2.equals("福州市")) {
				if (StringUtil.isNotEmpty(countyLevelCity) && (countyLevelCity.equals("长乐") || countyLevelCity.equals("福清"))) {
					chose = str1;
				}else {
					chose = str;
				}
			}
			if (area2.equals("宁德市")) {
				if (StringUtil.isNotEmpty(countyLevelCity) && (countyLevelCity.equals("福安") || countyLevelCity.equals("福鼎"))) {
					chose = str1;
				}else {
					chose = str;
				}
			}
			if (area2.equals("泉州市")) {
				if (StringUtil.isNotEmpty(countyLevelCity) && (countyLevelCity.equals("石狮") || countyLevelCity.equals("南安") || countyLevelCity.equals("晋江"))) {
					chose = str1;
				}else {
					chose = str;
				}
			}
			if (area2.equals("漳州市")) {
				if (StringUtil.isNotEmpty(countyLevelCity) && (countyLevelCity.equals("龙海"))) {
					chose = str1;
				}else {
					chose = str;
				}
			}
			if (area2.equals("龙岩市")) {
				if (StringUtil.isNotEmpty(countyLevelCity) && (countyLevelCity.equals("漳平"))) {
					chose = str1;
				}else {
					chose = str;
				}
			}
			if (area2.equals("三明市")) {
				if (StringUtil.isNotEmpty(countyLevelCity) && (countyLevelCity.equals("永安"))) {
					chose = str1;
				}else {
					chose = str;
				}
			}
			if (area2.equals("南平市")) {
				if (StringUtil.isNotEmpty(countyLevelCity) && (countyLevelCity.equals("建瓯") || countyLevelCity.equals("邵武") || countyLevelCity.equals("武夷山"))) {
					chose = str1;
				}else {
					chose = str;
				}
			}
			boolean checkresult = AreaTest.isOrderContainAmong(area1 + area2 + address, chose);
			if (checkresult) {				
				return ajaxJson("1");
			}
			return ajaxJson("2");
		} catch (Exception e) {
			return ajaxJson("2");
			// TODO: handle exception
		}
		
	}
	
}
