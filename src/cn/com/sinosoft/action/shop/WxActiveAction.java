package cn.com.sinosoft.action.shop;
 
import java.text.SimpleDateFormat;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
/**
 * 
 * @author
 *
 */
@ParentPackage("shop")
public class WxActiveAction extends BaseShopAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1071196349436889301L;
	
	
	private String activeID="";
	private String title="";
	private String summery="";
	private String content="";
	private String currentDate="";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public String getActivePage(){
		
		QueryBuilder qb = new QueryBuilder(" SELECT * FROM WxActive WHERE id=? ");
		qb.add(activeID);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			title = dt.getString(0, "Title");
			summery = dt.getString(0, "Summery");
			content = dt.getString(0, "Content");
		}
		currentDate = PubFun.getCurrentDate();
		return "activepage";
		
	}
	public String getActiveID() {
		return activeID;
	}
	public void setActiveID(String activeID) {
		this.activeID = activeID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	 
	public String getSummery() {
		return summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	
}
