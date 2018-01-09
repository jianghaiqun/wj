package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.HashMap;
import java.util.Map;

@ParentPackage("shop") 
public class NavAction extends BaseShopAction{
	private static final long serialVersionUID = 1L;
	private String Name;
	private int pageIndex;
	public String ajaxGetArticle(){
		JSONObject jsonObject = new JSONObject();
		DataTable dt = new DataTable();
		StringBuffer result = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		try {
			Name = java.net.URLDecoder.decode(Name, "UTF-8");
			if(StringUtil.isNotEmpty(getConfigValue("专题"+Name))){
				String sql = "select * from zcarticle where cataloginnercode like ?";
				QueryBuilder qb = new QueryBuilder(sql, getConfigValue("专题"+Name)+"%");
				dt = qb.executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					if (pageIndex != 0) {
						pageIndex = pageIndex - 1;
					}
					int nextPage = pageIndex + 2;
					int start = pageIndex * 20;
					int total = dt.getRowCount();
					int pageCount = new Double(Math.ceil(total * 1.0 / 20)).intValue();
					for( int i=start;i < start + 20 && i < dt.getRowCount();i++){
						result.append(" <div class=\"brand_list_info\">");
						result.append(" <span class=\"productName\" > ");
						result.append(" <a href=\""+ Config.getFrontServerContextPath() + "/"+dt.get(i).getString("URL")+"\" class=\"title\" title=\""+dt.get(i).getString("title")+"\" target=\"_blank\"> \""+dt.get(i).getString("title")+"\" </a> </span>");
						result.append(" <div class=\"content\">");
						String summary = dt.get(i).getString("summary");
						if(summary!=null && !"".equalsIgnoreCase(summary.trim()) && summary.length()>100){
							summary = summary.substring(0,100);
						}
						result.append(summary);
						result.append(" </div> ");
						result.append(" </div> ");
					}
					sb2.append("<div class='plpage'>");
					sb2.append("<div class='plpagecont'>");
					if (pageIndex > 0) {
						sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'>首页</a></span>");
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageIndex + "\");'><</a></span>");
					} else {
						sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'>首页</a></span>");
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><</a></span>");
					}
					int j = 1;  
					for( j = 1;j<=(total%20==0?total/20:(total/20+1));j++){    
						if(j==(pageIndex+1)){
							sb2.append("<span class='plpage04'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><font color = '#FFFFFF'> "+j+"</font></a></span>&nbsp;");
						}
						//如果总页面大于5
						else if(pageCount>5){
							if(pageIndex>3 && pageCount>(pageIndex+1)){
								if(j>(pageIndex-3)&&j<(pageIndex+3)){
									sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'> "+j+" </a></span>&nbsp;");
								}
								if(j==(pageIndex+2)&&j!=pageCount){
									sb2.append("...&nbsp;");
								}else if(j==(pageCount-1)&&(pageIndex+1)==(pageCount-2)){
									sb2.append("...&nbsp;");
								}
							}
							if(pageIndex>3&&pageCount<(pageIndex+2)){
								if(j>pageCount-5){					
									sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
								}
							}
							if(pageIndex<4){
								if(j<6){				
									sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
									if(j==5){						
										sb2.append("...&nbsp;");
									}
								}
							}
							
						}
						//如果总页面小于5 则全部显示
						else if(pageCount<6){
							sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
						}
					}
					if (pageIndex + 1 != pageCount && pageCount > 0) {
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + nextPage + "\");'>></a></span>");
						sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
					} else {
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>></a></span>");
						sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
					}

					sb2.append("&nbsp;&nbsp;共" + (total%10==0?total/10:(total/10+1)) + "页&nbsp;&nbsp;");
					sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
					sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

					sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
									+ pageCount
									+ "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newJump();}else{newJump();}}\" style='' value='跳转'></td>");
					
					sb2.append("</div>");
					sb2.append("</div>");
				}
			}else{
				logger.warn("专题导航无法获取栏目对应配置项的值");
			}
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("ZTarticle", result.toString());
			jsonMap.put("ZTpage", sb2.toString());
			jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ajax(jsonObject.toString(), "text/html");
	}
	public  String getConfigValue(String name) {
		try {
			QueryBuilder qb = new QueryBuilder("select value from zdconfig where name=? ", name);
			return qb.executeString();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
