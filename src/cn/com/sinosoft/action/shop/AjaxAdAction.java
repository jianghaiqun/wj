package cn.com.sinosoft.action.shop;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

@ParentPackage("shop")
/**
 * 动态获取广告；涉及栏目：资讯、知识、百科、理财
 * @author lenovo
 *
 */
public class AjaxAdAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String articleID;//文章id
	
	/**
	 * 根据articleID获取文章推荐分类、获取广告内容
	 * @return
	 */
	public String getAdContent(){
		
		Map<String, String> jsonMap = new HashMap<String, String>();
	    if(StringUtil.isNotEmpty(articleID)){
	    	String adname = new QueryBuilder(" SELECT CASE WHEN  LOCATE('--', CodeName)=0 THEN CodeName ELSE SUBSTRING(CodeName,3) END  FROM zcarticle,zdcode WHERE codetype='RiskType' and codevalue=RiskType and id=? ",articleID).executeString();
	    	//String newAdName=StringUtil.htmlEncode(adname);
	    	StringBuffer sb = new StringBuffer(" SELECT a.ID,a.PositionName,a.Description,b.AdContent,a.PositionWidth,a.PositionHeight FROM ZCAdPosition a,ZCAdvertisement b WHERE a.code = b.positioncode AND a.PositionName=? ");
	    	sb.append(" AND b.IsOpen='Y' AND b.StartTime<=NOW() AND b.EndTime>=NOW() ");
	    	QueryBuilder qb = new QueryBuilder(sb.toString(),adname);
	    	DataTable dt = qb.executeDataTable();
	    	if(dt!=null && dt.getRowCount()>=1){
	    		jsonMap.put("content",  dt.getString(0, 3));
	    		jsonMap.put("width",  dt.getString(0, 4));
	    		jsonMap.put("height",  dt.getString(0, 5));
	    	}
	    }
		
		String jsonpname = this.getRequest().getParameter("callback");
		JSONObject jsonArrays = JSONObject.fromObject(jsonMap);
		String jsonstrs = jsonArrays.toString();
		return ajaxHtml(jsonpname + "(" + jsonstrs + ");");
	}


	public String getArticleID() {
		return articleID;
	}
	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}
}
