package com.sinosoft.product;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.FEMSearchConditionInfoSchema;
import com.sinosoft.schema.FEMSearchConditionInfoSet;
import com.sinosoft.schema.SDAllCondtionSchema;
import com.sinosoft.schema.SDSearchAddressSchema;
import com.sinosoft.schema.SDSearchAddressSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 筛选条件-筛选条件配置.
 * 
 * @author congzn.
 * @date 20140422.
 * 
 */
public class FiltarteConfig extends Page {

	public void generate() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				execGenerate(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始生成......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	/**
	 * 自定义筛选条件配置生成.
	 * 
	 * @param ltt
	 */
	public void execGenerate(LongTimeTask ltt) {
		String SearchIDs = $V("SearchIDs");
		StringBuffer strId = new StringBuffer();
		try {

			Transaction trans = new Transaction();

			SDAllCondtionSchema tSDAllCondtion;

			// @ 为 单组合分割符
			String[] tempArr = SearchIDs.split("@");
			for (int i = 0; i < tempArr.length; i++) {
				// 将 名称#筛选规则组合#险种 拆分
				String[] data = tempArr[i].split("#");

				if (data.length < 2) {
					Response.setLogInfo(0, "数据不完整,无法保存!");
					return;
				}
				String searchName = data[0];
				String riskType = data[2];
				String SearchID = data[1];

				SearchID = SearchID.replaceAll(",", "#");
				SearchID = SearchID.replaceAll("-", ",");
				SearchID = TravelArea(SearchID);

				tSDAllCondtion = new SDAllCondtionSchema();

				QueryBuilder queryCondtion = new QueryBuilder(
						"select id from SDAllCondtion where SearchID = ?");
				queryCondtion.add(SearchID);
				String reslut = queryCondtion.executeString();

				if (StringUtil.isNotEmpty(reslut)) {
					tSDAllCondtion.setId(reslut);
					tSDAllCondtion.setSearchID(SearchID);
					tSDAllCondtion.setERiskSubType(riskType);
					tSDAllCondtion.setCreateDate(new Date());
					tSDAllCondtion.setSearchName(searchName);
					trans.add(tSDAllCondtion, Transaction.UPDATE);
					strId.append("'"+reslut+"',");
				} else {
					tSDAllCondtion.setId(NoUtil
							.getMaxIDLocal("SDAllCondtionID"));
					tSDAllCondtion.setSearchID(SearchID);
					tSDAllCondtion.setERiskSubType(riskType);
					tSDAllCondtion.setCreateDate(new Date());
					tSDAllCondtion.setSearchName(searchName);
					trans.add(tSDAllCondtion, Transaction.INSERT);
					strId.append("'"+tSDAllCondtion.getId()+"',");
				}
				
				if(ltt != null){
					ltt.setCurrentInfo("正在生成(" + (i + 1) + "/" + tempArr.length + ")" );
					ltt.setPercent(Integer.valueOf((i + 1) * 100 / (tempArr.length + 1)));
				}
				
			}
			strId.deleteCharAt(strId.length() - 1);

			trans.commit();
			
			//调用生成.
			AllAddress(strId.toString());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, e.getMessage() + "保存失败!" + e.getMessage());

		}
	}

	/**
	 * 通过险种查询筛选条件.
	 */
	@SuppressWarnings("unchecked")
	public void queryConditions() {

		String riskType = $V("riskType");
		
		String selectValue = $V("selectValue");
		
		String newHtml = "";
		
		StringBuffer conditions = new StringBuffer();

		StringBuffer tHTML = new StringBuffer();
		FEMSearchConditionInfoSchema SCschema = new FEMSearchConditionInfoSchema();
		FEMSearchConditionInfoSet SCset = new FEMSearchConditionInfoSet();

		if (StringUtil.isNotEmpty(riskType)) {
			SCset = SCschema.query(new QueryBuilder(
							"where SearchLevel='1' and ERiskType=? order by -SearchOrder",
							riskType));

			tHTML.append("<div id=\"radiolist\" class=\"radiolist CsearchConditions\">");

			for (int i = 0; i < SCset.size(); i++) {
				// 拼装不限和全部
				SCschema = SCset.get(i);
				FEMSearchConditionInfoSchema SCschema1 = new FEMSearchConditionInfoSchema();
				FEMSearchConditionInfoSet SCset1 = new FEMSearchConditionInfoSet();
				FEMSearchConditionInfoSchema SCschema2 = new FEMSearchConditionInfoSchema();
				FEMSearchConditionInfoSet SCset2 = new FEMSearchConditionInfoSet();
				FEMSearchConditionInfoSet SCset3 = new FEMSearchConditionInfoSet();
				int[] subNode = new int[10];
				int k = 0;
				// 查询出一级查询条件对应的子条件
				SCset1 = SCschema1.query(new QueryBuilder(
						"where UpperId=? order by -SearchOrder", SCschema
								.getId()));
				if ("Y".equals(SCschema.getIsMultipleChoice())) {
					// 如果本条件为多选在外成多套一个“chklist”
					tHTML.append("<div class=\"chklist\" >");
				}
				tHTML.append("<div class=\"bznx-z\" id=\"CsearchConditions_"
						+ SCschema.getSearchCode() + "\">");
				tHTML.append("<span class=\"CConditionName\">"
						+ SCschema.getSearchName() + "：</span>");
				tHTML.append("<div class=\"select_nav\"><ul>");
				// IF判断是多选而且是并集时 02—Y为全部
				if ("02".equals(SCschema.getIntersection())
						&& "Y".equals(SCschema.getIsMultipleChoice())) {
					tHTML
							.append(" <li class=\"jiange\"><input class=\"rdwh\" type=\"radio\" onclick=\"clearconditions('"
									+ SCschema.getSearchCode()
									+ "','0')\" checked=\"checked\" id=\""
									+ SCschema.getSearchCode()
									+ "_all\" value=\"default_"
									+ SCschema.getSearchCode()
									+ "_"
									+ SCschema.getSearchLevel()
									+ "\" name=\""
									+ SCschema.getSearchCode());
					tHTML.append("\">全部</li>");
				}
				// 02以外-Y为不限
				else if ("Y".equals(SCschema.getIsMultipleChoice())
						&& !"02".equals(SCschema.getIntersection())) {
					tHTML
							.append("<li  class=\"jiange\"><input class=\"rdwh\" type=\"radio\" checked=\"checked\" onclick=\"clearconditions('"
									+ SCschema.getSearchCode()
									+ "','0')\" value=\"default_"
									+ SCschema.getSearchCode()
									+ "_"
									+ SCschema.getSearchLevel()
									+ "\" name=\""
									+ SCschema.getSearchCode());
					tHTML.append("\" id=\"" + SCschema.getSearchCode()
							+ "_all\">不限</li>");
				} else {
					tHTML
							.append("<li  class=\"jiange\"><input class=\"rdwh\" type=\"radio\" checked=\"checked\" value=\"default_"
									+ SCschema.getSearchCode()
									+ "_"
									+ SCschema.getSearchLevel()
									+ "\" name=\""
									+ SCschema.getSearchCode());
					tHTML.append("\">不限</li>");

				}
				// 拼装除不限和全部以外的子条件
				for (int j = 0; j < SCset1.size(); j++) {
					int m = j + 1;
					SCschema1 = SCset1.get(j);
					if ("Y".equals(SCschema.getIsMultipleChoice()))
					// IF判断是多选条件，多选条件单独样式
					{
						tHTML
								.append("<li><input type=\"checkbox\" class=\"rdwh\" onclick=\"clearconditions('"
										+ SCschema.getSearchCode()
										+ "','1')\" name=\""
										+ SCschema.getSearchCode()
										+ "\" value=\""
										+ SCschema1.getId()
										+ "\" >" + SCschema1.getSearchName());
						tHTML.append("</li>");
					} else { // 境内、外旅游有子菜单，需要单独拼装样式
						if ("02".equals(SCschema.getIntersection())
								&& "N".equals(SCschema.getIsMultipleChoice())) {
							tHTML
									.append("<li><input type=\"radio\" class=\"rdwh\" id=\"first"
											+ m
											+ "\" value=\""
											+ SCschema1.getId()
											+ "\" name=\""
											+ SCschema.getSearchCode());
							tHTML.append("\" style=\"display: none;\">"
									+ SCschema1.getSearchName() + "</li>");
						} else {
							tHTML
									.append("<li><input type=\"radio\" class=\"rdwh\" value=\""
											+ SCschema1.getId()
											+ "\" name=\""
											+ SCschema.getSearchCode());
							tHTML.append("\">");
							tHTML.append("" + SCschema1.getSearchName()
									+ "</li>");

						}
						if (StringUtil.isNotEmpty(SCschema1.getSubNodeNum())
								&& Integer.parseInt(SCschema1.getSubNodeNum()) > 0) {
							SCset3.add(SCschema1);
							SCset2.add(SCschema2.query(new QueryBuilder(
									"where Upperid=? order by -SearchOrder",
									SCschema1.getId())));
							subNode[k] = SCset2.size();
							k++;
						}

					}
					if (j != 0 && (j + 1) % 4 == 0) {
						tHTML.append(" <li class=\"jiange\"></li>");
					}
				}
				tHTML.append("</ul></div>");
				tHTML.append("</div>");
				if ("Y".equals(SCschema.getIsMultipleChoice())) {
					tHTML.append("</div>");
				}
				// 拼装境内境外旅游的子菜单
				if (SCset2.size() > 0) {
					int m = 0;
					for (int n = 0; n < SCset3.size(); n++) {
						if (n == 0) {
							tHTML
									.append("<div class=\"jn-k\" id=\"CsearchConditions_"
											+ SCschema.getSearchCode() + "_\">");
						} else {
							tHTML
									.append("<div class=\"jn-k up_admin_jn\" id=\"CsearchConditions_"
											+ SCschema.getSearchCode() + "_\">");
						}

						tHTML
								.append("<div id=\"CsearchConditions_"
										+ SCschema.getSearchCode()
										+ "_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -1px 20px;\"><img alt=\"\" src=\""
										+ Config.getFrontServerContextPath()
										+ "/images/sj.gif\"></div><div class=\"jn-qbbg\" >");
						tHTML
								.append("<ul style=\"display: block;width: 20px;\" id=\"CsearchConditions_"
										+ SCschema.getSearchCode()
										+ "_"
										+ SCset3.get(n).getId() + "\">");
						tHTML
								.append("<li style=\"padding-right:10px\"><input type=\"radio\" class=\"rdwh\" id=\""
										+ SCschema.getSearchCode()
										+ "_sub_"
										+ ""
										+ SCset3.get(n).getId()
										+ "\" value=\""
										+ SCset3.get(n).getId()
										+ "\" name=\""
										+ SCschema.getSearchCode()
										+ "\">全部</li>");
						for (; m < subNode[n]; m++) {
							SCschema2 = SCset2.get(m);
							tHTML
									.append("<li style=\"padding-right:20px\"><input type=\"radio\" class=\"rdwh\" value=\""
											+ SCschema2.getId()
											+ "\" name=\""
											+ SCschema.getSearchCode()
											+ "\">"
											+ SCschema2.getSearchName()
											+ "</li>");
						}
						tHTML.append("</ul>");
						tHTML.append("</div></div>");
					}
					tHTML.append("<div class=\"clear\"></div>");
				}

				conditions.append(SCschema.getSearchCode() + ",");

			}

			conditions.deleteCharAt(conditions.length() - 1);
			tHTML.append("<input type=\"hidden\" id=\"p_group\" value='"
					+ conditions.toString() + "'");

			tHTML.append("</div>");
			
			if(StringUtil.isNotEmpty(selectValue)){
				newHtml = selectValue(selectValue,tHTML.toString());
			}else{
				newHtml = tHTML.toString();
			}
			
			Response.put("tHtml", newHtml);
		}
	}
	
	/**
	 * 替换选中.
	 * @param tHtml
	 * @return
	 */
	private static String selectValue(String value,String tHtml){
		String checkedStr = "checked=\"checked\"";
		String reslutStr = tHtml.replaceAll(checkedStr, "");
		String[] valueArr = value.split(",");
		
		for(int i = 0; i < valueArr.length; i++){
			String replaceStr = "value=\""+valueArr[i]+"\"";
			reslutStr = reslutStr.replaceAll(replaceStr, replaceStr+" "+checkedStr);
		}
		
		return reslutStr;
	}

	/**
	 * 出行地区处理. 存在三级菜单.
	 * 
	 * @param SearchID
	 * @return
	 */
	public String TravelArea(String SearchID) {

		String[] arr = SearchID.split("#");

		String result;
		QueryBuilder qb;
		String sql = "select id from FEMSearchConditionInfo  where id = (select Upperid from FEMSearchConditionInfo where id = ?) and SearchLevel = '2'";
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].indexOf(",") == -1) {
				qb = new QueryBuilder(sql);
				qb.add(arr[i]);
				String oldStr = arr[i];
				result = (String) qb.executeOneValue();
				if (StringUtil.isNotEmpty(result)) {
					SearchID = SearchID.replace(oldStr, result + "," + oldStr);
				}
			}
		}

		SearchID = SearchID.replaceAll("#", "|");
		return SearchID;
	}
	
	/**
	 * 生成地址方法.
	 * @param id
	 */
	private void AllAddress(String id) {
		try{
			String SearchID = "";
			String sql = "select * from SDAllCondtion where id in ("+id+")";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			
			// 生成数据
			SDSearchAddressSet updateSet = new SDSearchAddressSet();
			SDSearchAddressSet insertSet = new SDSearchAddressSet();
			Transaction trans = new Transaction();
			String sql1 = "select SearchCode from FEMSearchConditionInfo where ERiskType = ? and searchlevel = '1' order by -SearchOrder";
			for(int i=0;i<dt.getRowCount();i++){
				Map<String, String> map = new HashMap<String, String>();
				String newSort = "";
				SearchID = dt.get(i).getString("SearchID");
				String[] sea = SearchID.split("\\|");
				for(int j=0;j<sea.length;j++){
					if(sea[j].startsWith("default_")){
						continue;
					}else{
						//如果sea[j]中是多选，根据第一个值获取searchCode
						if(sea[j].indexOf(",")!=-1){
							String[] mul = sea[j].split(",");
							map.put(getSearchCode(mul[0]), sea[j]);
						}else{
							map.put(getSearchCode(sea[j]), sea[j]);
						}
					}
				}
				//去掉difault之后，把map中根据key按字母顺序排序
				map = sort(map);
				Iterator<String> itr = map.keySet().iterator();
				String[] key = new String[map.keySet().size()];
				//把map的key值存到数组中
				int n=0;
				while(itr.hasNext()){
					key[n] = itr.next();
					n++;
				}
				String[] sortSea = new String[sea.length];
				ArrayList<String> list=new ArrayList<String>();
				QueryBuilder qb1 = new QueryBuilder(sql1);
				DataTable dt1 = new DataTable();
				qb1.add(dt.get(i).getString("ERiskSubType").substring(0,1));
				dt1 = qb1.executeDataTable();
				//取当前险种类型的searchCode，根据字母顺序排序
				for(int j=0;j<dt1.getRowCount();j++){
					list.add(dt1.get(j).getString("SearchCode"));
				}
				Collections.sort(list);
				//将排好序的searchcode进行组合
				for(int k=0;k<list.size();k++){
					Boolean flag = false;
					for(int l=0;l<n;l++){
						if(list.get(k).equals(key[l])){
							sortSea[k] = key[l]+":"+map.get(key[l]);
							flag = true;
						}
					}
					if(!flag){
						sortSea[k] = list.get(k);
					}
				}
				//拼装成串
				for(int p=0;p<sortSea.length;p++){
					newSort += sortSea[p];
					if(p!=sortSea.length-1){
						newSort += "|";
					}
				}
				//更新到SDSearchAddress表中
				SDSearchAddressSchema searchAddressSchema = new SDSearchAddressSchema();
				SDSearchAddressSet searchAddressSet = new SDSearchAddressSet();
				searchAddressSchema.setAddressCode(newSort);
				String ID = "";
				searchAddressSet = searchAddressSchema.query();
				if(searchAddressSet.size()>0){
					searchAddressSchema = searchAddressSet.get(0);
					searchAddressSchema.setModifyDate(new Date());
					searchAddressSchema.setSearchAddress(getSearchAddress(searchAddressSchema.getID()));
					updateSet.add(searchAddressSchema);
				}else{
					ID = StringUtil.leftPad(String.valueOf(NoUtil.getMaxID("SearchAddressID")),'0',10);
					searchAddressSchema.setID(ID);
					searchAddressSchema.setSearchID(SearchID);
					searchAddressSchema.setSearchAddress(getSearchAddress(ID));
					searchAddressSchema.setERiskType(dt.get(i).getString("ERiskSubType"));
					searchAddressSchema.setCreateDate(new Date());
					searchAddressSchema.setModifyDate(new Date());
					insertSet.add(searchAddressSchema);
				}
				
			}
			
			trans.add(updateSet, Transaction.UPDATE);
			trans.add(insertSet, Transaction.INSERT);
			trans.commit();
		} catch (Exception e) {
			logger.error("生成筛选地址ID方法中发生异常："+e.getMessage(), e);
		}
	}
	
	/**
	 * 生成地址方法
	 * @param ID
	 * @return
	 */
	public static String getSearchAddress(String ID){
		try{
			String SearchAddress = "";
			String DetailAddress = "";
			String sql = "select value from zdconfig where type = 'SearchAddress'";
			QueryBuilder qb = new QueryBuilder(sql);
			SearchAddress = (String)qb.executeOneValue();
			DetailAddress = SearchAddress+ID+".shtml";
			return DetailAddress;
		} catch (Exception e){
			logger.error("生成地址方法异常"+e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据ID获取SearchCode
	 * @param ID
	 * @return
	 */
	public static String getSearchCode(String ID){
		try{
			String SearchCode = "";
			String SearchGroupID = "";
			String ERiskType = "";
			String sql = "select SearchGroupID,ERiskType from FEMSearchConditionInfo where id = ?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(ID);
			DataTable dt = qb.executeDataTable();
			if(dt.getRowCount()>0){
				SearchGroupID = dt.get(0).getString("SearchGroupID").substring(0, 4);
				ERiskType = dt.get(0).getString("ERiskType");
				sql = "select SearchCode from FEMSearchConditionInfo where SearchGroupID = ? and ERiskType = ?";
				qb = new QueryBuilder(sql);
				qb.add(SearchGroupID);
				qb.add(ERiskType);
				dt = qb.executeDataTable();
				if(dt.getRowCount()>0){
					SearchCode = dt.get(0).getString("SearchCode");
				}
			}
			return SearchCode ;
		} catch (Exception e){
			logger.error("获取searchcode方法中发生异常："+e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * Map把key根据字母顺序排序
	 * @param map
	 * @return
	 */
	 @SuppressWarnings("unchecked")
	public static Map<String, String> sort(Map map) {
	        Map<String, String> mapVK = new TreeMap<String, String>(
	            new Comparator<String>() {
	                public int compare(String obj1, String obj2) {
	                    String v1 = (String)obj1;
	                    String v2 = (String)obj2;
	                    int s = v1.compareTo(v2);
	                    return s;
	                }
	            }
	        );
	        Set col = map.keySet();
	        Iterator iter = col.iterator();
	        while (iter.hasNext()) {
	            String key = (String) iter.next();
	            String value = (String) map.get(key);
	            mapVK.put(key, value);
	        }
	        return mapVK;
	    }
}
